package com.hakimen.turtlecosmeticsfabric.utils;

import com.google.gson.Gson;
import com.hakimen.turtlecosmeticsfabric.client.TurtleCosmeticsFabricClient;

import java.io.*;

public class Config {
    public static String[] cosmetics;
    public static String[] labels;


    class ConfigMedium{
        public String[] cosmetics;
        public String[] labels;
    }
    public static void loadConfig(){
        Gson gson = new Gson();

        File f = new File(System.getProperty("user.dir")+"/config");
        if(!f.exists()){
            TurtleCosmeticsFabricClient.LOGGER.info("[CC Cosmetics] Config Folder not found... making folder");
            f.mkdir();
        }else{
            TurtleCosmeticsFabricClient.LOGGER.info("[CC Cosmetics] Config Folder found");
        }
        f = new File(System.getProperty("user.dir")+"/config/cc-cosmetics-config.json");
        if(!f.exists()){
            TurtleCosmeticsFabricClient.LOGGER.info("[CC Cosmetics] Config file not present... making file");
            try {
                FileWriter fileWriter = new FileWriter(f);
                fileWriter.write("""
                        {
                            "cosmetics" : [],
                            "labels" : []
                        }
                        """);
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            var temp = gson.fromJson(new FileReader(f), ConfigMedium.class);
            cosmetics = temp.cosmetics;
            labels = temp.labels;
            TurtleCosmeticsFabricClient.LOGGER.info("[CC Cosmetics] Config file was read");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
