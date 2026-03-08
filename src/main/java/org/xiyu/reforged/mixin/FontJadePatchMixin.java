package org.xiyu.reforged.mixin;

import net.minecraft.client.gui.Font;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Adds Jade's {@code JadeFont} interface methods to {@link Font}.
 * <p>
 * Jade's own FontMixin would normally do this, but NeoForge mod Mixins cannot be
 * registered with Forge's Mixin system due to timing constraints. This Mixin adds
 * the method implementations, and {@link ReForgedMixinPlugin#postApply} adds the
 * {@code snownee.jade.gui.JadeFont} interface via ASM (avoiding compile-time
 * dependency on Jade). The Mixin is only applied when Jade is present (checked
 * by the plugin).
 * </p>
 */
@Mixin(Font.class)
public abstract class FontJadePatchMixin {

    @SuppressWarnings("unused")
    public void jade$setGlint(float glint1, float glint2) {
        // No-op: glint effect requires Jade's full FontMixin injection
    }

    @SuppressWarnings("unused")
    public void jade$setGlintStrength(float strength1, float strength2) {
        // No-op: glint strength requires Jade's full FontMixin injection
    }
}
