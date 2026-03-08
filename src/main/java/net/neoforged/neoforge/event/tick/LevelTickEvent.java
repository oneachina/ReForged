package net.neoforged.neoforge.event.tick;

import net.minecraft.world.level.Level;
import java.util.function.BooleanSupplier;

/**
 * NeoForge LevelTickEvent with Forge wrapper constructors.
 */
public class LevelTickEvent extends net.neoforged.bus.api.Event {
    private final Level level;
    private final BooleanSupplier haveTime;

    protected LevelTickEvent(Level level, BooleanSupplier haveTime) {
        this.level = level;
        this.haveTime = haveTime;
    }

    public Level getLevel() { return level; }
    public boolean haveTime() { return haveTime != null && haveTime.getAsBoolean(); }

    public static class Pre extends LevelTickEvent {
        public Pre(Level level, BooleanSupplier haveTime) { super(level, haveTime); }

        /** Wrapper constructor */
        public Pre(net.minecraftforge.event.TickEvent.LevelTickEvent.Pre forge) {
            super(forge.level, forge::haveTime);
        }
    }

    public static class Post extends LevelTickEvent {
        public Post(Level level, BooleanSupplier haveTime) { super(level, haveTime); }

        /** Wrapper constructor */
        public Post(net.minecraftforge.event.TickEvent.LevelTickEvent.Post forge) {
            super(forge.level, forge::haveTime);
        }
    }
}
