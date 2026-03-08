package net.neoforged.neoforge.event.entity.living;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.EntityEvent;

/**
 * NeoForge LivingEvent — extends EntityEvent for proper hierarchy.
 */
public class LivingEvent extends EntityEvent {

    /** No-arg constructor for subclasses that don't have an entity yet. */
    protected LivingEvent() {
        super((net.minecraft.world.entity.Entity) null);
    }

    public LivingEvent(LivingEntity entity) {
        super(entity);
    }

    /** Wrapper constructor */
    public LivingEvent(net.minecraftforge.event.entity.living.LivingEvent forge) {
        super(forge.getEntity());
    }

    @Override
    public LivingEntity getEntity() {
        Entity e = super.getEntity();
        return e instanceof LivingEntity le ? le : null;
    }

    public static class LivingJumpEvent extends LivingEvent {
        public LivingJumpEvent(LivingEntity entity) { super(entity); }

        /** Wrapper constructor */
        public LivingJumpEvent(net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent forge) {
            super(forge);
        }
    }

    public static class LivingVisibilityEvent extends LivingEvent {
        private double visibilityModifier;
        private final Entity lookingEntity;

        public LivingVisibilityEvent(LivingEntity entity, Entity lookingEntity, double originalMultiplier) {
            super(entity);
            this.lookingEntity = lookingEntity;
            this.visibilityModifier = originalMultiplier;
        }

        /** Wrapper constructor */
        public LivingVisibilityEvent(net.minecraftforge.event.entity.living.LivingEvent.LivingVisibilityEvent forge) {
            super(forge);
            this.lookingEntity = forge.getLookingEntity();
            this.visibilityModifier = forge.getVisibilityModifier();
        }

        public double getVisibilityModifier() { return visibilityModifier; }
        public void modifyVisibility(double mod) { visibilityModifier *= mod; }
        public Entity getLookingEntity() { return lookingEntity; }
    }
}
