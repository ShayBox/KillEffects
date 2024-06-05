package com.shaybox.killeffects.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(LivingEntity.class)
public class EntityLivingMixin {

    @Unique
    private Entity entity;

    @Unique
    private final Map<Entity, Entity> playerAttacker = new HashMap<>();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        entity = (Entity) (Object) this;
    }

    @Inject(method = "handleDamageEvent", at = @At("RETURN"))
    private void handleDamageEvent(DamageSource damageSource, CallbackInfo ci) {
        if (!damageSource.is(DamageTypes.PLAYER_ATTACK) && !damageSource.is(DamageTypes.PLAYER_EXPLOSION)) return;
        if (this.entity instanceof Player) {
            Entity attacker = damageSource.getEntity();
            this.playerAttacker.put(this.entity, attacker);
        }
    }

    @Inject(method = "handleEntityEvent", at = @At("RETURN"))
    private void handleEntityEvent(byte b, CallbackInfo ci) {
        if (b != 3) return;
        if (this.entity instanceof Player) {
            Minecraft minecraft = Minecraft.getInstance();
            assert minecraft.level != null;

            Entity attacker = this.playerAttacker.get(this.entity);
            if (attacker == null) return;
            else this.playerAttacker.remove(this.entity);

            if (MixinConfig.isSelf() && attacker != minecraft.player) return;

            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, minecraft.level);
            lightningBolt.setPos(this.entity.position());
            minecraft.level.addEntity(lightningBolt);
        }
    }

}
