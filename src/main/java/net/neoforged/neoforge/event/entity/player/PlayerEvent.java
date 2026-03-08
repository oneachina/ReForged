package net.neoforged.neoforge.event.entity.player;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

/**
 * NeoForge PlayerEvent — extends LivingEvent for proper hierarchy.
 * Each subclass has a wrapper constructor taking the Forge equivalent.
 */
public class PlayerEvent extends LivingEvent {

    /** No-arg constructor for subclasses that don't have a player yet. */
    protected PlayerEvent() {
        super((net.minecraft.world.entity.LivingEntity) null);
    }

    public PlayerEvent(Player player) {
        super(player);
    }

    /** Wrapper constructor */
    public PlayerEvent(net.minecraftforge.event.entity.player.PlayerEvent forge) {
        super(forge.getEntity());
    }

    @Override
    public Player getEntity() {
        return (Player) super.getEntity();
    }

    public static class StartTracking extends PlayerEvent {
        private final Entity target;

        public StartTracking(Player player, Entity target) {
            super(player);
            this.target = target;
        }

        /** Wrapper constructor */
        public StartTracking(net.minecraftforge.event.entity.player.PlayerEvent.StartTracking forge) {
            super(forge);
            this.target = forge.getTarget();
        }

        public Entity getTarget() { return target; }
    }

    public static class StopTracking extends PlayerEvent {
        private final Entity target;

        public StopTracking(Player player, Entity target) {
            super(player);
            this.target = target;
        }

        /** Wrapper constructor */
        public StopTracking(net.minecraftforge.event.entity.player.PlayerEvent.StopTracking forge) {
            super(forge);
            this.target = forge.getTarget();
        }

        public Entity getTarget() { return target; }
    }

    public static class Clone extends PlayerEvent {
        private final Player original;
        private final boolean wasDeath;

        public Clone(Player player, Player original, boolean wasDeath) {
            super(player);
            this.original = original;
            this.wasDeath = wasDeath;
        }

        /** Wrapper constructor */
        public Clone(net.minecraftforge.event.entity.player.PlayerEvent.Clone forge) {
            super(forge);
            this.original = forge.getOriginal();
            this.wasDeath = forge.isWasDeath();
        }

        public Player getOriginal() { return original; }
        public boolean isWasDeath() { return wasDeath; }
    }

    public static class PlayerLoggedInEvent extends PlayerEvent {
        public PlayerLoggedInEvent(Player player) { super(player); }

        /** Wrapper constructor */
        public PlayerLoggedInEvent(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent forge) {
            super(forge);
        }
    }

    public static class PlayerLoggedOutEvent extends PlayerEvent {
        public PlayerLoggedOutEvent(Player player) { super(player); }

        /** Wrapper constructor */
        public PlayerLoggedOutEvent(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent forge) {
            super(forge);
        }
    }

    public static class PlayerRespawnEvent extends PlayerEvent {
        private final boolean endConquered;

        public PlayerRespawnEvent(Player player, boolean endConquered) {
            super(player);
            this.endConquered = endConquered;
        }

        /** Wrapper constructor */
        public PlayerRespawnEvent(net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent forge) {
            super(forge);
            this.endConquered = forge.isEndConquered();
        }

        public boolean isEndConquered() { return endConquered; }
    }

    public static class PlayerChangedDimensionEvent extends PlayerEvent {
        private final net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> from;
        private final net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> to;

        public PlayerChangedDimensionEvent(Player player,
                net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> from,
                net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> to) {
            super(player);
            this.from = from;
            this.to = to;
        }

        /** Wrapper constructor */
        public PlayerChangedDimensionEvent(net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent forge) {
            super(forge);
            this.from = forge.getFrom();
            this.to = forge.getTo();
        }

        public net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> getFrom() { return from; }
        public net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> getTo() { return to; }
    }

    public static class ItemCraftedEvent extends PlayerEvent {
        private final net.minecraft.world.item.ItemStack crafting;
        private final net.minecraft.world.Container craftMatrix;

        public ItemCraftedEvent(Player player, net.minecraft.world.item.ItemStack crafting,
                                net.minecraft.world.Container craftMatrix) {
            super(player);
            this.crafting = crafting;
            this.craftMatrix = craftMatrix;
        }

        /** Wrapper constructor */
        public ItemCraftedEvent(net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent forge) {
            super(forge);
            this.crafting = forge.getCrafting();
            this.craftMatrix = forge.getInventory();
        }

        public net.minecraft.world.item.ItemStack getCrafting() { return crafting; }
        public net.minecraft.world.Container getInventory() { return craftMatrix; }
    }

    public static class ItemSmeltedEvent extends PlayerEvent {
        private final net.minecraft.world.item.ItemStack smelting;

        public ItemSmeltedEvent(Player player, net.minecraft.world.item.ItemStack smelting) {
            super(player);
            this.smelting = smelting;
        }

        /** Wrapper constructor */
        public ItemSmeltedEvent(net.minecraftforge.event.entity.player.PlayerEvent.ItemSmeltedEvent forge) {
            super(forge);
            this.smelting = forge.getSmelting();
        }

        public net.minecraft.world.item.ItemStack getSmelting() { return smelting; }
    }
}
