package com.example.tntarrowcounter;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.vehicle.MinecartTNT;

public final class TntArrowCounterClient implements ClientModInitializer {
    @Override public void onInitializeClient() {
        HudRenderCallback.EVENT.register((graphics, tickCounter) -> {
            Minecraft client = Minecraft.getInstance();
            if (client.level == null || client.player == null || client.options.hideGui) return;
            int tnt = 0, arrows = 0;
            for (Entity entity : client.level.entitiesForRendering()) {
                if (entity instanceof MinecartTNT) tnt++;
                else if (entity instanceof AbstractArrow) arrows++;
            }
            graphics.drawString(client.font, Component.literal("TNT Minecarts: " + tnt), 8, 8, 0xFFFFFF, true);
            graphics.drawString(client.font, Component.literal("Arrows: " + arrows), 8, 20, 0xFFFFFF, true);
        });
    }
}
