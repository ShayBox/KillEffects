package com.shaybox.killeffects.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class MixinConfigPlugin implements IMixinConfigPlugin {

    private final File file = new File("config/killeffects.properties");

    @Override
    public void onLoad(String mixinPackage) {
        try {
            if (!this.file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                this.file.createNewFile();

                // Write default properties to the file
                FileOutputStream fileOutputStream = new FileOutputStream(this.file);
                Properties properties = new Properties();
                properties.setProperty("self", "false");
                properties.store(fileOutputStream, "KillEffects Configuration");
                fileOutputStream.close();
            }

            // Load properties from the file
            FileInputStream fileInputStream = new FileInputStream(this.file);
            Properties properties = new Properties();
            properties.load(fileInputStream);

            String self = properties.getProperty("self");
            if (self != null) MixinConfig.setSelf(Boolean.parseBoolean(self));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

}
