package com.hakimen.turtlecosmeticsfabric.client;

import com.hakimen.turtlecosmeticsfabric.api.Overlays;
import com.hakimen.turtlecosmeticsfabric.utils.Config;
import com.mojang.logging.LogUtils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.loading.v1.*;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.impl.client.model.loading.ModelLoaderHooks;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class TurtleCosmeticsFabricClient implements ClientModInitializer {
    public static final Logger LOGGER = LogUtils.getLogger();



    @Override
    public void onInitializeClient() {
        ModelLoadingPlugin.register(new LoadExtraModelsPlugin());
    }

}
