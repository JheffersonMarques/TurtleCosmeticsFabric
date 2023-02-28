package com.hakimen.turtlecosmeticsfabric.client;

import com.hakimen.turtlecosmeticsfabric.api.Overlay;
import com.hakimen.turtlecosmeticsfabric.api.Overlays;
import com.hakimen.turtlecosmeticsfabric.utils.Config;
import com.mojang.logging.LogUtils;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.client.ClientRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class TurtleCosmeticsFabricClient implements ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();
    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> registerExtraModels(out));
    }

    public static void registerExtraModels(Consumer<Identifier> register) {
        Config.loadConfig();
        Overlays.clear();
        Overlays.load();
        for (Overlay overlay: Overlays.getOverlays()){
            register.accept(overlay.getOverlay());
        }
    }
}
