package com.hakimen.turtlecosmeticsfabric.utils;


import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.shared.ComputerCraft;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class RenderUtils {
    public record Combination(
            boolean colour,
            @Nullable ITurtleUpgrade leftUpgrade,
            @Nullable ITurtleUpgrade rightUpgrade,
            @Nullable Identifier[] overlay,
            boolean christmas,
            boolean flip
    ) {
    }
    private static final Identifier ELF_OVERLAY_MODEL = new Identifier( "computercraft", "block/turtle_elf_overlay" );


    public static Identifier[] getTurtleOverlayModel( Identifier overlay[], boolean christmas)
    {
        if( overlay != null ) return overlay;
        if( christmas ) return new Identifier[]{ELF_OVERLAY_MODEL};
        return null;
    }
}
