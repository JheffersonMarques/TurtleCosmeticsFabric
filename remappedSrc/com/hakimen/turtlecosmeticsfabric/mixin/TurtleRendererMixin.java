package com.hakimen.turtlecosmeticsfabric.mixin;

import ;
import F;
import I;
import com.hakimen.turtlecosmeticsfabric.api.Overlay;
import com.hakimen.turtlecosmeticsfabric.api.Overlays;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.client.platform.ClientPlatformHelper;
import dan200.computercraft.client.render.TurtleBlockEntityRenderer;
import dan200.computercraft.client.turtle.TurtleUpgradeModellers;
import dan200.computercraft.shared.computer.core.ComputerFamily;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import dan200.computercraft.shared.util.DirectionUtil;
import dan200.computercraft.shared.util.Holiday;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;

@Pseudo
@Mixin(value = TurtleBlockEntityRenderer.class, remap = false)
public abstract class TurtleRendererMixin {

    @Shadow protected abstract void renderUpgrade(MatrixStack transform, VertexConsumer renderer, int lightmapCoord, int overlayLight, TurtleBlockEntity turtle, TurtleSide side, float f);


    @Shadow @Final private static Identifier ELF_OVERLAY_MODEL;

    @Shadow @Final private BlockEntityRenderDispatcher renderer;

    @Shadow @Final private TextRenderer font;

    @Shadow protected abstract void renderModel(MatrixStack transform, VertexConsumer renderer, int lightmapCoord, int overlayLight, Identifier model, int[] tints);

    @Shadow @Final private static Identifier COLOUR_TURTLE_MODEL;

    @Shadow @Final private static ModelIdentifier NORMAL_TURTLE_MODEL;

    @Shadow @Final private static ModelIdentifier ADVANCED_TURTLE_MODEL;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public static Identifier getTurtleModel(ComputerFamily family, boolean coloured)
    {
        switch( family )
        {
            case NORMAL:
            default:
                return coloured ? COLOUR_TURTLE_MODEL : NORMAL_TURTLE_MODEL;
            case ADVANCED:
                return coloured ? COLOUR_TURTLE_MODEL : ADVANCED_TURTLE_MODEL;
        }
    }
    private static Identifier[] getTurtleOverlayModel(String label, Identifier overlay, boolean christmas)
    {
        if( overlay != null ) return new Identifier[]{overlay};
        if( christmas ) return new Identifier[]{ELF_OVERLAY_MODEL};
        if( label != null){
            var toReturn = new ArrayList<Identifier>();
            label = label.toLowerCase();
            for (Overlay over: Overlays.getOverlays()) {
                if(label.contains(over.getLabel())){
                    toReturn.add(over.getOverlay());
                }
            }
            return toReturn.toArray(new Identifier[toReturn.size()]);
        }
        return null;
    }
    /**
     * @author
     * @reason
     */
    @Overwrite
    public void render(TurtleBlockEntity turtle, float partialTicks, MatrixStack transform, VertexConsumerProvider buffers, int lightmapCoord, int overlayLight) {
        transform.push();

        // Translate the turtle first, so the label moves with it.
        var offset = turtle.getRenderOffset(partialTicks);
        transform.translate(offset.x, offset.y, offset.z);

        // Render the label
        var label = turtle.getLabel();
        var hit = renderer.crosshairTarget;
        if (label != null && hit.getType() == HitResult.Type.BLOCK && turtle.getPos().equals(((BlockHitResult) hit).getBlockPos())) {
            var mc = MinecraftClient.getInstance();
            var font = this.font;

            transform.push();
            transform.translate(0.5, 1.2, 0.5);
            transform.multiply(mc.getEntityRenderDispatcher().getRotation());
            transform.scale(-0.025f, -0.025f, 0.025f);

            var matrix = transform.peek().getPositionMatrix();
            var opacity = (int) (mc.options.getTextBackgroundOpacity(0.25f) * 255) << 24;
            var width = -font.getWidth(label) / 2.0f;
            font.draw(label, width, (float) 0, 0x20ffffff, false, matrix, buffers, TextRenderer.TextLayerType.SEE_THROUGH, opacity, lightmapCoord);
            font.draw(label, width, (float) 0, 0xffffffff, false, matrix, buffers, TextRenderer.TextLayerType.NORMAL, 0, lightmapCoord);

            transform.pop();
        }

        // Then apply rotation and flip if needed.
        transform.translate(0.5f, 0.5f, 0.5f);
        var yaw = turtle.getRenderYaw(partialTicks);
        transform.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - yaw));
        if (label != null && (label.equals("Dinnerbone") || label.equals("Grumm"))) {
            transform.scale(1.0f, -1.0f, 1.0f);
        }
        transform.translate(-0.5f, -0.5f, -0.5f);

        // Render the turtle
        var colour = turtle.getColour();
        var family = turtle.getFamily();
        var overlay = turtle.getOverlay();

        var buffer = buffers.getBuffer(TexturedRenderLayers.getEntityTranslucentCull());
        renderModel(transform, buffer, lightmapCoord, overlayLight, getTurtleModel(family, colour != -1), colour == -1 ? null : new int[]{ colour });

        // Render the overlay
        var overlayModels = getTurtleOverlayModel(label,overlay, Holiday.getCurrent() == Holiday.CHRISTMAS);
        if (overlayModels != null) {
            for (Identifier id:overlayModels) {
                renderModel(transform, buffer, lightmapCoord, overlayLight, id, null);
            }

        }

        // Render the upgrades
        renderUpgrade(transform, buffer, lightmapCoord, overlayLight, turtle, TurtleSide.LEFT, partialTicks);
        renderUpgrade(transform, buffer, lightmapCoord, overlayLight, turtle, TurtleSide.RIGHT, partialTicks);

        transform.pop();
    }
}
