package com.shaybox.killeffects.fabric.client;

import com.shaybox.killeffects.Main;
import net.fabricmc.api.ClientModInitializer;

public final class MainFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Main.init();
    }

}
