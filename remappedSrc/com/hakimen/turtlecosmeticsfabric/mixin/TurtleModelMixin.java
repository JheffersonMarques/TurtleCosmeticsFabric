package com.hakimen.turtlecosmeticsfabric.mixin;

import dan200.computercraft.client.model.CompositeBakedModel;
import dan200.computercraft.client.model.turtle.TurtleModel;
import dan200.computercraft.client.model.turtle.TurtleModelParts;
import java.util.List;
import net.minecraft.client.render.model.BakedModel;
import org.spongepowered.asm.mixin.*;

@Pseudo
@Mixin(value = TurtleModel.class, remap = false)
public class TurtleModelMixin {


    @Shadow @Final private TurtleModelParts parts;

    /**
     * @author
     * @reason
     */
    @Overwrite
    private BakedModel buildModel(TurtleModelParts.Combination combo) {
        var models = parts.buildModel(combo);
        return models.size() == 1 ? models.get(0) : new CompositeBakedModel(models);
    }
}
