package com.hakimen.turtlecosmeticsfabric.api;

import com.hakimen.turtlecosmeticsfabric.TurtleCosmeticsFabric;
import com.hakimen.turtlecosmeticsfabric.client.TurtleCosmeticsFabricClient;
import com.hakimen.turtlecosmeticsfabric.utils.Config;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Overlays {
    static HashMap<String,Identifier> overlays = new HashMap<>();


    public static HashMap<String, Identifier> getOverlays() {
        return overlays;
    }

    public static void add(String label, Identifier resourceLocation){
        overlays.put(label.toLowerCase(),resourceLocation);
    }

    public static Identifier get(String label){
        return overlays.getOrDefault(label.toLowerCase(),null);
    }

    public static void load(){
        var cosmeticPaths = Config.cosmetics;
        var labels = Config.labels;
        for (int i = 0; i < cosmeticPaths.length; i++) {
            var path = cosmeticPaths[i].split(":");
            var resource = new Identifier(path[0],path[1]);
            overlays.put(labels[i],resource);
            TurtleCosmeticsFabricClient.LOGGER.info("[CC Cosmetics] Registered "+ resource + " with label "+labels[i]);
        }
    }

}
