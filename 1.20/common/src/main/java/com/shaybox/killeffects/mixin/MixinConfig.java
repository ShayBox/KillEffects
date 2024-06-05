package com.shaybox.killeffects.mixin;

public class MixinConfig {

    private static boolean self = false;

    public static boolean isSelf() {
        return self;
    }

    public static void setSelf(boolean self) {
        MixinConfig.self = self;
    }
}
