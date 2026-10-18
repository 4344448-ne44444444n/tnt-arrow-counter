package com.example.tntarrowcounter;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

public final class TntArrowCounterClient implements ClientModInitializer {

    private static boolean hasClassNamed(Class<?> c, String name) {
        for (; c != null; c = c.getSuperclass()) {
            if (c.getSimpleName().equals(name)) return true;
        }
        return false;
    }

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
                        String id = String.valueOf(entity.getType());
                        Class<?> cls = entity.getClass();
                        if (id.contains("tnt_minecart") || hasClassNamed(cls, "MinecartTNT")) {
                            tnt++;
                        } else if (id.contains("arrow") || id.contains("trident")
                                || hasClassNamed(cls, "AbstractArrow")) {
                            arrows++;
                        }
                    }
                    graphics.text(client.font, Component.literal("TNT Minecarts: " + tnt), 8, 8, 0xFFFFFFFF, true);
                    graphics.text(client.font, Component.literal("Arrows: " + arrows), 8, 20, 0xFFFFFFFF, true);
                });
    }
}
