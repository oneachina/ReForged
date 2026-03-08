package net.neoforged.neoforge.event.level;

import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;

/**
 * NeoForge LevelEvent with level field and Forge wrapper constructors.
 */
public class LevelEvent extends Event {
    private final LevelAccessor level;

    public LevelEvent(LevelAccessor level) {
        this.level = level;
    }

    /** Wrapper constructor */
    public LevelEvent(net.minecraftforge.event.level.LevelEvent forge) {
        this.level = forge.getLevel();
    }

    public LevelAccessor getLevel() { return level; }

    public static class Load extends LevelEvent {
        public Load(LevelAccessor level) { super(level); }

        /** Wrapper constructor */
        public Load(net.minecraftforge.event.level.LevelEvent.Load forge) {
            super(forge);
        }
    }

    public static class Unload extends LevelEvent {
        public Unload(LevelAccessor level) { super(level); }

        /** Wrapper constructor */
        public Unload(net.minecraftforge.event.level.LevelEvent.Unload forge) {
            super(forge);
        }
    }

    public static class PotentialSpawns extends LevelEvent {
        public PotentialSpawns(LevelAccessor level) { super(level); }

        /** Wrapper constructor */
        public PotentialSpawns(net.minecraftforge.event.level.LevelEvent.PotentialSpawns forge) {
            super(forge);
        }
    }
}
