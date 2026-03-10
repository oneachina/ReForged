package net.neoforged.neoforge.client.settings;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

/**
 * NeoForge KeyModifier shim.
 * Enum order matches NeoForge: CONTROL, SHIFT, ALT, NONE.
 * Delegates active-state checks to Forge's equivalent enum where possible,
 * and provides the static methods NeoForge mods rely on (e.g. getActiveModifier()).
 */
public enum KeyModifier {
    CONTROL {
        @Override
        public boolean matches(InputConstants.Key key) {
            return net.minecraftforge.client.settings.KeyModifier.CONTROL.matches(key);
        }

        @Override
        public boolean isActive(@Nullable IKeyConflictContext conflictContext) {
            return Screen.hasControlDown();
        }

        @Override
        public Component getCombinedName(InputConstants.Key key, Supplier<Component> defaultLogic) {
            return net.minecraftforge.client.settings.KeyModifier.CONTROL.getCombinedName(key, defaultLogic);
        }
    },
    SHIFT {
        @Override
        public boolean matches(InputConstants.Key key) {
            return net.minecraftforge.client.settings.KeyModifier.SHIFT.matches(key);
        }

        @Override
        public boolean isActive(@Nullable IKeyConflictContext conflictContext) {
            return Screen.hasShiftDown();
        }

        @Override
        public Component getCombinedName(InputConstants.Key key, Supplier<Component> defaultLogic) {
            return net.minecraftforge.client.settings.KeyModifier.SHIFT.getCombinedName(key, defaultLogic);
        }
    },
    ALT {
        @Override
        public boolean matches(InputConstants.Key key) {
            return net.minecraftforge.client.settings.KeyModifier.ALT.matches(key);
        }

        @Override
        public boolean isActive(@Nullable IKeyConflictContext conflictContext) {
            return Screen.hasAltDown();
        }

        @Override
        public Component getCombinedName(InputConstants.Key key, Supplier<Component> defaultLogic) {
            return net.minecraftforge.client.settings.KeyModifier.ALT.getCombinedName(key, defaultLogic);
        }
    },
    NONE {
        @Override
        public boolean matches(InputConstants.Key key) {
            return false;
        }

        @Override
        public boolean isActive(@Nullable IKeyConflictContext conflictContext) {
            if (conflictContext != null && !conflictContext.conflicts(KeyConflictContext.IN_GAME)) {
                for (KeyModifier keyModifier : MODIFIER_VALUES) {
                    if (keyModifier.isActive(conflictContext)) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public Component getCombinedName(InputConstants.Key key, Supplier<Component> defaultLogic) {
            return defaultLogic.get();
        }
    };

    public static final KeyModifier[] MODIFIER_VALUES = { SHIFT, CONTROL, ALT };

    /**
     * Returns whichever modifier key is currently pressed. Called by YSM and others.
     */
    public static KeyModifier getActiveModifier() {
        for (KeyModifier keyModifier : MODIFIER_VALUES) {
            if (keyModifier.isActive(null)) {
                return keyModifier;
            }
        }
        return NONE;
    }

    /**
     * Returns all currently active modifier keys.
     */
    public static List<KeyModifier> getActiveModifiers() {
        List<KeyModifier> modifiers = new ArrayList<>();
        for (KeyModifier keyModifier : MODIFIER_VALUES) {
            if (keyModifier.isActive(null)) {
                modifiers.add(keyModifier);
            }
        }
        return modifiers;
    }

    /**
     * Get the KeyModifier that matches the given key.
     */
    public static KeyModifier getKeyModifier(InputConstants.Key key) {
        for (KeyModifier keyModifier : MODIFIER_VALUES) {
            if (keyModifier.matches(key)) {
                return keyModifier;
            }
        }
        return NONE;
    }

    /**
     * Returns true if the given key is a modifier key.
     */
    public static boolean isKeyCodeModifier(InputConstants.Key key) {
        return getKeyModifier(key) != NONE;
    }

    public static KeyModifier valueFromString(String stringValue) {
        try {
            return valueOf(stringValue);
        } catch (NullPointerException | IllegalArgumentException ignored) {
            return NONE;
        }
    }

    public abstract boolean matches(InputConstants.Key key);

    public abstract boolean isActive(@Nullable IKeyConflictContext conflictContext);

    public abstract Component getCombinedName(InputConstants.Key key, Supplier<Component> defaultLogic);

    /**
     * Convert this NeoForge KeyModifier to the corresponding Forge KeyModifier.
     */
    public net.minecraftforge.client.settings.KeyModifier toForge() {
        return net.minecraftforge.client.settings.KeyModifier.valueOf(name());
    }

    /**
     * Convert a Forge KeyModifier to the corresponding NeoForge KeyModifier.
     */
    public static KeyModifier fromForge(net.minecraftforge.client.settings.KeyModifier forgeModifier) {
        return KeyModifier.valueOf(forgeModifier.name());
    }
}
