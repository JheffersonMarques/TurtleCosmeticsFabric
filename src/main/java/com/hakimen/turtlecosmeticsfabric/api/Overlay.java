package com.hakimen.turtlecosmeticsfabric.api;

import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

public class Overlay {
    String label;
    Identifier overlayLocation;

    public Overlay(String label, Identifier overlayLocation) {
        this.label = label;
        this.overlayLocation = overlayLocation;
    }

    public String getLabel() {
        return label;
    }

    public Identifier getOverlay() {
        return overlayLocation;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setOverlayLocation(Identifier overlayLocation) {
        this.overlayLocation = overlayLocation;
    }
}
