package net.neoforged.neoforge.event.tick;

import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.Event;

/**
 * NeoForge EntityTickEvent — wraps Forge's LivingTickEvent.
 * NeoForge fires for all entities; Forge only for LivingEntity.
 */
public class EntityTickEvent extends Event {
    private final Entity entity;

    protected EntityTickEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() { return entity; }

    public static class Pre extends EntityTickEvent {
        public Pre(Entity entity) { super(entity); }

        /** Wrapper constructor */
        public Pre(net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent forge) {
            super(forge.getEntity());
        }
    }

    public static class Post extends EntityTickEvent {
        public Post(Entity entity) { super(entity); }
    }
}
