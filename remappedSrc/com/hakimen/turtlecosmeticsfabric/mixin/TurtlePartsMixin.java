package com.hakimen.turtlecosmeticsfabric.mixin;

import ;
import com.hakimen.turtlecosmeticsfabric.utils.RenderUtils;
import dan200.computercraft.api.client.TransformedModel;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.client.model.turtle.TurtleModelParts;
import dan200.computercraft.client.platform.ClientPlatformHelper;
import dan200.computercraft.client.render.TurtleBlockEntityRenderer;
import dan200.computercraft.client.turtle.TurtleUpgradeModellers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.AffineTransformation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;


@Pseudo
@Mixin(value = TurtleModelParts.class, remap = false)
public abstract class TurtlePartsMixin {

    private static String name = null;
    @Inject(at = @At("HEAD"), method = "getCombination")
    private void getCombo(ItemStack stack, CallbackInfoReturnable<TurtleModelParts.Combination> cir){
        if(stack.hasCustomName()){
            name = stack.getName().getString();
        }else {
            name = null;
        }
    }
    @Shadow @Final private static AffineTransformation identity;

    @Shadow @Final private static AffineTransformation flip;

    @Shadow public abstract BakedModel transform(BakedModel model, AffineTransformation transformation);

    @Shadow @Final private BakedModel colourModel;

    @Shadow @Final private BakedModel familyModel;

    /**
     * @author
     * @reason
     */
    @Overwrite
    public List<BakedModel> buildModel(TurtleModelParts.Combination combo) {
        var mc = MinecraftClient.getInstance();
        var modelManager = mc.getItemRenderer().getModels().getModelManager();

        var transformation = combo.flip() ? flip : identity;
        var parts = new ArrayList<BakedModel>();

        var overlayModelLocation = RenderUtils.getTurtleOverlayModel(name,combo.overlay(), combo.christmas());
        if (overlayModelLocation != null) {
            for (var model:overlayModelLocation) {
                parts.add(transform(ClientPlatformHelper.get().getModel(modelManager, model), transformation));
            }
        }

        parts.add(transform(combo.colour() ? colourModel : familyModel, transformation));


        if (combo.leftUpgrade() != null) {
            var model = TurtleUpgradeModellers.getModel(combo.leftUpgrade(), null, TurtleSide.LEFT);
            parts.add(transform(model.getModel(), transformation.multiply(model.getMatrix())));
        }
        if (combo.rightUpgrade() != null) {
            var model = TurtleUpgradeModellers.getModel(combo.rightUpgrade(), null, TurtleSide.RIGHT);
            parts.add(transform(model.getModel(), transformation.multiply(model.getMatrix())));
        }

        return parts;
    }

}
