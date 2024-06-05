package com.shaybox.killeffects.quilt.client;

import com.shaybox.killeffects.Main;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

@SuppressWarnings("unused")
public class MainQuiltClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(ModContainer mod) {
        Main.init();
    }

}
