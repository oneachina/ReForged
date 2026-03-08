package net.neoforged.neoforge.client.gui;

import java.util.Optional;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.IModInfo;

/**
 * Proxy: NeoForge's IConfigScreenFactory.
 * Mods register this to supply a config screen accessible from the mod list.
 */
@FunctionalInterface
public interface IConfigScreenFactory extends IExtensionPoint {
    Screen createScreen(ModContainer container, Screen modListScreen);

    static Optional<IConfigScreenFactory> getForMod(IModInfo selectedMod) {
        return ModList.get().getModContainerById(selectedMod.getModId())
                .flatMap(m -> m.getCustomExtension(IConfigScreenFactory.class));
    }
}
