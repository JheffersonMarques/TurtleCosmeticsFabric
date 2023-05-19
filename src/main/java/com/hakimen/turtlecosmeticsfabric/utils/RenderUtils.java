package com.hakimen.turtlecosmeticsfabric.utils;



import com.hakimen.turtlecosmeticsfabric.api.Overlay;
import com.hakimen.turtlecosmeticsfabric.api.Overlays;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static dan200.computercraft.shared.turtle.core.TurtleBrain.NBT_OVERLAY;

public class RenderUtils {

    private static final Identifier ELF_OVERLAY_MODEL = new Identifier( "computercraft", "block/turtle_elf_overlay" );


    public static Identifier[] getTurtleOverlayModel( Identifier overlay[], boolean christmas)
    {
        if( overlay != null ) return overlay;
        if( christmas ) return new Identifier[]{ELF_OVERLAY_MODEL};
        return null;
    }
    public static Identifier[] getTurtleOverlayModel(String label, Identifier overlay, boolean christmas)
    {
        if( overlay != null ) return new Identifier[]{overlay};
        if( christmas ) return new Identifier[]{ELF_OVERLAY_MODEL};
        if(label != null){
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

}
