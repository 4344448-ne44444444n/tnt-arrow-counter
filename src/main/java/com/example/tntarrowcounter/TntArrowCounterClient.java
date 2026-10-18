package com.example.tntarrowcounter;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public final class TntArrowCounterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.MISC_OVERLAYS,
                Identifier.fromNamespaceAndPath("tntarrowcounter", "counter"),
                (graphics, tickCounter) -> {
                    Minecraft client = Minecraft.getInstance();
                    if (client.level == null || client.player == null) return;
                    int tnt = 0, arrows = 0;
                    for (Entity entity : client.level.entitiesForRendering()) {
                        EntityType<?> type = entity.getType();
                        if (type == EntityType.TNT_MINECART) tnt++;
                        else if (type.is(EntityTypeTags.ARROWS) || type == EntityType.TRIDENT) arrows++;
                    }
                    graphics.text(client.font, Component.literal("TNT Minecarts: " + tnt), 8, 8, 0xFFFFFFFF, true);
                    graphics.text(client.font, Component.literal("Arrows: " + arrows), 8, 20, 0xFFFFFFFF, true);
                });
    }
}
