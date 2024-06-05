package com.shaybox.killeffects.forge;

import com.shaybox.killeffects.Main;
import net.minecraftforge.fml.common.Mod;

@Mod(Main.MOD_ID)
public final class MainForge {

    public MainForge() {
        Main.init();
    }

}
