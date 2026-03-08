package snownee.jade.gui;

/**
 * Stub interface matching Jade's JadeFont marker interface.
 * <p>
 * Jade's FontMixin adds this interface to {@code net.minecraft.client.gui.Font}
 * at runtime. Since NeoForge mod Mixins cannot be registered with Forge's Mixin
 * system (timing constraint), ReForged provides this stub and applies it via its
 * own {@code FontJadePatchMixin}.
 * </p>
 * <p>
 * The classloader is configured to load this class from the parent (TRANSFORMER)
 * classloader, ensuring class identity matches when Jade code casts Font to JadeFont.
 * </p>
 */
public interface JadeFont {
    void jade$setGlint(float glint1, float glint2);
    void jade$setGlintStrength(float strength1, float strength2);
}
