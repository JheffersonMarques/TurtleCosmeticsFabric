package com.hakimen.turtlecosmeticsfabric.mixin;

import com.hakimen.turtlecosmeticsfabric.api.Overlays;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.client.platform.ClientPlatformHelper;
import dan200.computercraft.client.render.TurtleBlockEntityRenderer;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import dan200.computercraft.shared.util.Holiday;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;


@Mixin(TurtleBlockEntityRenderer.class)
public abstract class TurtleRendererMixin {


    @Shadow @Final private static Identifier ELF_OVERLAY_MODEL;

    @Shadow
    protected abstract void renderModel(MatrixStack transform, VertexConsumerProvider buffers, int lightmapCoord, int overlayLight, Identifier modelLocation, @Nullable int[] tints);

    @Inject(method = "render(Ldan200/computercraft/shared/turtle/blocks/TurtleBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At(value = "INVOKE" ,target = "Ldan200/computercraft/client/render/TurtleBlockEntityRenderer;getTurtleOverlayModel(Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/util/Identifier;"))
    public void render(TurtleBlockEntity turtle, float partialTicks, MatrixStack transform, VertexConsumerProvider buffers, int lightmapCoord, int overlayLight, CallbackInfo ci) {
        var overlayModels = getTurtleOverlayModel(turtle.getLabel(), turtle.getOverlay(), Holiday.getCurrent() == Holiday.CHRISTMAS);
        if (overlayModels != null) {
            for (var overlayModel:overlayModels) {
                if(overlayModel != null){
                    renderModel(transform, buffers, lightmapCoord, overlayLight, overlayModel, null);
                }
            }
        }
    }


    private static Identifier[] getTurtleOverlayModel(String label, Identifier overlay, boolean christmas) {
        if (overlay != null) return new Identifier[]{ overlay };
        if (christmas) return new Identifier[]{ ELF_OVERLAY_MODEL };
        if (label != null){
            ArrayList<Identifier> overlays = new ArrayList<>();
            Overlays.getOverlays().forEach((key,value)->{
                if(label.contains(key)){
                    overlays.add(value);
                }
            });
            return overlays.toArray(Identifier[]::new);
        }
        return null;
    }
}
