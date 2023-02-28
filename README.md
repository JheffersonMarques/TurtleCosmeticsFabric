# TurtleCosmeticsFabric
A lib mod/cosmetic mod for ComputerCraft Turtles

This client side mod permits adding new turtle overlays via config and resourcepacks

```json

{
    "cosmetics" : ["computercraft:block/turtle_elf_overlay"],
    "labels" : ["elf"]
}

```

by adding the elf label on a turtle it gets the elf overlay, overlays can be stacked, by adding in the label the keywords for the overlay, this mod doesn't contain any overlays by itself

More overlays can be added by other mods by pushing Overlays like this:

```java
	Overlays.addOverlay(new Overlay("glass",new ResourceLocation("minecraft:block/glass")));
```
