package org.xiyu.reforged.mixin;

import net.minecraft.client.gui.Font;
import org.spongepowered.asm.mixin.Mixin;
import snownee.jade.gui.JadeFont;

/**
 * Makes {@link Font} implement Jade's {@link JadeFont} marker interface.
 * <p>
 * Jade's own FontMixin would normally do this, but NeoForge mod Mixins cannot be
 * registered with Forge's Mixin system due to timing constraints (Mixin bootstrap
 * occurs before NeoForge mods are discovered). This Mixin provides a compatibility
 * bridge with no-op implementations of the glint methods.
 * </p>
 */
@Mixin(Font.class)
public abstract class FontJadePatchMixin implements JadeFont {

    @Override
    public void jade$setGlint(float glint1, float glint2) {
        // No-op: glint effect requires Jade's full FontMixin injection
    }

    @Override
    public void jade$setGlintStrength(float strength1, float strength2) {
        // No-op: glint strength requires Jade's full FontMixin injection
    }
}
