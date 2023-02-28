package com.hakimen.turtlecosmeticsfabric.api;

import com.hakimen.turtlecosmeticsfabric.TurtleCosmeticsFabric;
import com.hakimen.turtlecosmeticsfabric.client.TurtleCosmeticsFabricClient;
import com.hakimen.turtlecosmeticsfabric.utils.Config;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class Overlays {
    static ArrayList<Overlay> overlays = new ArrayList<Overlay>();
    public static void load(){
        var cosmeticPaths = Config.cosmetics;
        var labels = Config.labels;
        for (int i = 0; i < cosmeticPaths.length; i++) {
            var path = cosmeticPaths[i].split(":");
            var resource = new Identifier(path[0],path[1]);
            overlays.add(new Overlay(labels[i],resource));
            TurtleCosmeticsFabricClient.LOGGER.info("[CC Cosmetics] Registered "+ resource + " with label "+labels[i]);
        }
    }
    public static void clear(){
        overlays.clear();
    }
    public static List<Overlay> getOverlays(){
        return overlays.subList(0,overlays.size());
    }

    public static void addOverlay(Overlay overlay){
        overlays.add(overlay);
    }
    public static void addOverlays(List<Overlay> newOverlays){
        overlays.addAll(newOverlays);
    }

    public static Overlay getOverlay(int index){
        return overlays.get(index);
    }
}
