package net.neoforged.neoforge.client.event;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.neoforged.bus.api.Event;

/**
 * NeoForge ContainerScreenEvent hierarchy with Forge wrapper constructors.
 */
public abstract class ContainerScreenEvent extends Event {
    private final AbstractContainerScreen<?> containerScreen;

    protected ContainerScreenEvent(AbstractContainerScreen<?> containerScreen) {
        this.containerScreen = containerScreen;
    }

    public AbstractContainerScreen<?> getContainerScreen() { return containerScreen; }

    public static abstract class Render extends ContainerScreenEvent {
        private final GuiGraphics guiGraphics;
        private final int mouseX;
        private final int mouseY;

        protected Render(AbstractContainerScreen<?> guiContainer, GuiGraphics guiGraphics, int mouseX, int mouseY) {
            super(guiContainer);
            this.guiGraphics = guiGraphics;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        public GuiGraphics getGuiGraphics() { return guiGraphics; }
        public int getMouseX() { return mouseX; }
        public int getMouseY() { return mouseY; }

        public static class Foreground extends Render {
            public Foreground(AbstractContainerScreen<?> c, GuiGraphics g, int mx, int my) {
                super(c, g, mx, my);
            }
            /** Wrapper constructor */
            public Foreground(net.minecraftforge.client.event.ContainerScreenEvent.Render.Foreground forge) {
                super(forge.getContainerScreen(), forge.getGuiGraphics(), forge.getMouseX(), forge.getMouseY());
            }
        }

        public static class Background extends Render {
            public Background(AbstractContainerScreen<?> c, GuiGraphics g, int mx, int my) {
                super(c, g, mx, my);
            }
            /** Wrapper constructor */
            public Background(net.minecraftforge.client.event.ContainerScreenEvent.Render.Background forge) {
                super(forge.getContainerScreen(), forge.getGuiGraphics(), forge.getMouseX(), forge.getMouseY());
            }
        }
    }
}
