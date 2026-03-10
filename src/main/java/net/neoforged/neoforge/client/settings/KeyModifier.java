package net.neoforged.neoforge.client.settings;

/**
 * NeoForge KeyModifier shim.
 * Enum order matches NeoForge: CONTROL, SHIFT, ALT, NONE.
 * Provides conversion to Forge's equivalent enum via name mapping.
 */
public enum KeyModifier {
    CONTROL,
    SHIFT,
    ALT,
    NONE;

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
