package com.hakimen.turtlecosmeticsfabric.client;

import com.hakimen.turtlecosmeticsfabric.api.Overlays;
import com.hakimen.turtlecosmeticsfabric.utils.Config;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

public class LoadExtraModelsPlugin implements ModelLoadingPlugin {
    @Override
    public void onInitializeModelLoader(Context pluginContext) {
        Config.loadConfig();
        Overlays.load();
        pluginContext.addModels(Overlays.getOverlays().values());
    }
}