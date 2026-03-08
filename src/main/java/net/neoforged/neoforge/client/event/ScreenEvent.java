package net.neoforged.neoforge.client.event;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import org.jetbrains.annotations.Nullable;

/**
 * NeoForge ScreenEvent hierarchy with Forge wrapper constructors for bridging.
 *
 * <p>Each inner class provides two constructor variants:</p>
 * <ol>
 *   <li>Standard NeoForge constructor (explicit parameters)</li>
 *   <li>Wrapper constructor taking the Forge equivalent for automatic bridging
 *       via {@link org.xiyu.reforged.shim.NeoForgeEventBusShim}</li>
 * </ol>
 */
public abstract class ScreenEvent extends Event {
    private final Screen screen;

    protected ScreenEvent(Screen screen) {
        this.screen = Objects.requireNonNull(screen);
    }

    public Screen getScreen() {
        return screen;
    }

    // ── Init ──────────────────────────────────────────────
    public static abstract class Init extends ScreenEvent {
        private final Consumer<GuiEventListener> add;
        private final Consumer<GuiEventListener> remove;
        private final List<GuiEventListener> listenerList;

        protected Init(Screen screen, List<GuiEventListener> listenerList,
                        Consumer<GuiEventListener> add, Consumer<GuiEventListener> remove) {
            super(screen);
            this.listenerList = Collections.unmodifiableList(listenerList);
            this.add = add;
            this.remove = remove;
        }

        public List<GuiEventListener> getListenersList() { return listenerList; }
        public void addListener(GuiEventListener listener) { add.accept(listener); }
        public void removeListener(GuiEventListener listener) { remove.accept(listener); }

        public static class Pre extends Init implements ICancellableEvent {
            public Pre(Screen screen, List<GuiEventListener> list,
                       Consumer<GuiEventListener> add, Consumer<GuiEventListener> remove) {
                super(screen, list, add, remove);
            }
            /** Wrapper constructor: bridges from Forge ScreenEvent.Init.Pre */
            public Pre(net.minecraftforge.client.event.ScreenEvent.Init.Pre forge) {
                super(forge.getScreen(), forge.getListenersList(),
                      forge::addListener, forge::removeListener);
            }
        }

        public static class Post extends Init {
            public Post(Screen screen, List<GuiEventListener> list,
                        Consumer<GuiEventListener> add, Consumer<GuiEventListener> remove) {
                super(screen, list, add, remove);
            }
            /** Wrapper constructor: bridges from Forge ScreenEvent.Init.Post */
            public Post(net.minecraftforge.client.event.ScreenEvent.Init.Post forge) {
                super(forge.getScreen(), forge.getListenersList(),
                      forge::addListener, forge::removeListener);
            }
        }
    }

    // ── Render ────────────────────────────────────────────
    public static abstract class Render extends ScreenEvent {
        private final GuiGraphics guiGraphics;
        private final int mouseX;
        private final int mouseY;
        private final float partialTick;

        protected Render(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            super(screen);
            this.guiGraphics = guiGraphics;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.partialTick = partialTick;
        }

        public GuiGraphics getGuiGraphics() { return guiGraphics; }
        public int getMouseX() { return mouseX; }
        public int getMouseY() { return mouseY; }
        public float getPartialTick() { return partialTick; }

        public static class Pre extends Render implements ICancellableEvent {
            public Pre(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                super(screen, guiGraphics, mouseX, mouseY, partialTick);
            }
            /** Wrapper constructor: bridges from Forge ScreenEvent.Render.Pre */
            public Pre(net.minecraftforge.client.event.ScreenEvent.Render.Pre forge) {
                super(forge.getScreen(), forge.getGuiGraphics(),
                      forge.getMouseX(), forge.getMouseY(), forge.getPartialTick());
            }
        }

        public static class Post extends Render {
            public Post(Screen screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                super(screen, guiGraphics, mouseX, mouseY, partialTick);
            }
            /** Wrapper constructor: bridges from Forge ScreenEvent.Render.Post */
            public Post(net.minecraftforge.client.event.ScreenEvent.Render.Post forge) {
                super(forge.getScreen(), forge.getGuiGraphics(),
                      forge.getMouseX(), forge.getMouseY(), forge.getPartialTick());
            }
        }
    }

    // ── BackgroundRendered ────────────────────────────────
    public static class BackgroundRendered extends ScreenEvent {
        private final GuiGraphics guiGraphics;

        public BackgroundRendered(Screen screen, GuiGraphics guiGraphics) {
            super(screen);
            this.guiGraphics = guiGraphics;
        }
        /** Wrapper constructor */
        public BackgroundRendered(net.minecraftforge.client.event.ScreenEvent.BackgroundRendered forge) {
            super(forge.getScreen());
            this.guiGraphics = forge.getGuiGraphics();
        }

        public GuiGraphics getGuiGraphics() { return guiGraphics; }
    }

    // ── RenderInventoryMobEffects ─────────────────────────
    public static class RenderInventoryMobEffects extends ScreenEvent implements ICancellableEvent {
        private final int availableSpace;
        private boolean compact;
        private int horizontalOffset;

        public RenderInventoryMobEffects(Screen screen, int availableSpace, boolean compact, int horizontalOffset) {
            super(screen);
            this.availableSpace = availableSpace;
            this.compact = compact;
            this.horizontalOffset = horizontalOffset;
        }
        /** Wrapper constructor */
        public RenderInventoryMobEffects(net.minecraftforge.client.event.ScreenEvent.RenderInventoryMobEffects forge) {
            super(forge.getScreen());
            this.availableSpace = forge.getAvailableSpace();
            this.compact = forge.isCompact();
            this.horizontalOffset = forge.getHorizontalOffset();
        }

        public int getAvailableSpace() { return availableSpace; }
        public boolean isCompact() { return compact; }
        public int getHorizontalOffset() { return horizontalOffset; }
        public void setHorizontalOffset(int offset) { horizontalOffset = offset; }
        public void addHorizontalOffset(int offset) { horizontalOffset += offset; }
        public void setCompact(boolean compact) { this.compact = compact; }
    }

    // ── MouseInput (private base) ─────────────────────────
    private static abstract class MouseInput extends ScreenEvent {
        private final double mouseX;
        private final double mouseY;

        protected MouseInput(Screen screen, double mouseX, double mouseY) {
            super(screen);
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        public double getMouseX() { return mouseX; }
        public double getMouseY() { return mouseY; }
    }

    // ── MouseButtonPressed ────────────────────────────────
    public static abstract class MouseButtonPressed extends MouseInput {
        private final int button;

        public MouseButtonPressed(Screen screen, double mouseX, double mouseY, int button) {
            super(screen, mouseX, mouseY);
            this.button = button;
        }

        public int getButton() { return button; }

        public static class Pre extends MouseButtonPressed implements ICancellableEvent {
            public Pre(Screen screen, double mouseX, double mouseY, int button) {
                super(screen, mouseX, mouseY, button);
            }
            /** Wrapper constructor */
            public Pre(net.minecraftforge.client.event.ScreenEvent.MouseButtonPressed.Pre forge) {
                super(forge.getScreen(), forge.getMouseX(), forge.getMouseY(), forge.getButton());
            }
        }

        public static class Post extends MouseButtonPressed {
            private final boolean handled;
            private ClickResult clickResult = ClickResult.DEFAULT;

            public Post(Screen screen, double mouseX, double mouseY, int button, boolean handled) {
                super(screen, mouseX, mouseY, button);
                this.handled = handled;
            }
            /** Wrapper constructor */
            public Post(net.minecraftforge.client.event.ScreenEvent.MouseButtonPressed.Post forge) {
                super(forge.getScreen(), forge.getMouseX(), forge.getMouseY(), forge.getButton());
                this.handled = forge.wasHandled();
            }

            public boolean wasClickHandled() { return handled; }
            public void setClickResult(ClickResult result) { this.clickResult = result; }
            public ClickResult getClickResult() { return clickResult; }

            public boolean getClickResultValue() {
                if (this.clickResult == ClickResult.FORCE_HANDLED) return true;
                return this.clickResult == ClickResult.DEFAULT && this.wasClickHandled();
            }

            public static enum ClickResult { FORCE_HANDLED, DEFAULT, FORCE_UNHANDLED }
        }
    }

    // ── MouseButtonReleased ───────────────────────────────
    public static abstract class MouseButtonReleased extends MouseInput {
        private final int button;

        public MouseButtonReleased(Screen screen, double mouseX, double mouseY, int button) {
            super(screen, mouseX, mouseY);
            this.button = button;
        }

        public int getButton() { return button; }

        public static class Pre extends MouseButtonReleased implements ICancellableEvent {
            public Pre(Screen screen, double mouseX, double mouseY, int button) {
                super(screen, mouseX, mouseY, button);
            }
            /** Wrapper constructor */
            public Pre(net.minecraftforge.client.event.ScreenEvent.MouseButtonReleased.Pre forge) {
                super(forge.getScreen(), forge.getMouseX(), forge.getMouseY(), forge.getButton());
            }
        }

        public static class Post extends MouseButtonReleased {
            private final boolean handled;
            private ReleaseResult releaseResult = ReleaseResult.DEFAULT;

            public Post(Screen screen, double mouseX, double mouseY, int button, boolean handled) {
                super(screen, mouseX, mouseY, button);
                this.handled = handled;
            }
            /** Wrapper constructor */
            public Post(net.minecraftforge.client.event.ScreenEvent.MouseButtonReleased.Post forge) {
                super(forge.getScreen(), forge.getMouseX(), forge.getMouseY(), forge.getButton());
                this.handled = forge.wasHandled();
            }

            public boolean wasReleaseHandled() { return handled; }
            public void setReleaseResult(ReleaseResult result) { this.releaseResult = result; }
            public ReleaseResult getReleaseResultState() { return releaseResult; }

            public boolean getReleaseResult() {
                if (this.releaseResult == ReleaseResult.FORCE_HANDLED) return true;
                return this.releaseResult == ReleaseResult.DEFAULT && this.wasReleaseHandled();
            }

            public static enum ReleaseResult { FORCE_HANDLED, DEFAULT, FORCE_UNHANDLED }
        }
    }

    // ── MouseDragged ──────────────────────────────────────
    public static abstract class MouseDragged extends MouseInput {
        private final int mouseButton;
        private final double dragX;
        private final double dragY;

        public MouseDragged(Screen screen, double mouseX, double mouseY, int mouseButton, double dragX, double dragY) {
            super(screen, mouseX, mouseY);
            this.mouseButton = mouseButton;
            this.dragX = dragX;
            this.dragY = dragY;
        }

        public int getMouseButton() { return mouseButton; }
        public double getDragX() { return dragX; }
        public double getDragY() { return dragY; }

        public static class Pre extends MouseDragged implements ICancellableEvent {
            public Pre(Screen screen, double mouseX, double mouseY, int mouseButton, double dragX, double dragY) {
                super(screen, mouseX, mouseY, mouseButton, dragX, dragY);
            }
            /** Wrapper constructor */
            public Pre(net.minecraftforge.client.event.ScreenEvent.MouseDragged.Pre forge) {
                super(forge.getScreen(), forge.getMouseX(), forge.getMouseY(),
                      forge.getMouseButton(), forge.getDragX(), forge.getDragY());
            }
        }

        public static class Post extends MouseDragged {
            public Post(Screen screen, double mouseX, double mouseY, int mouseButton, double dragX, double dragY) {
                super(screen, mouseX, mouseY, mouseButton, dragX, dragY);
            }
            /** Wrapper constructor */
            public Post(net.minecraftforge.client.event.ScreenEvent.MouseDragged.Post forge) {
                super(forge.getScreen(), forge.getMouseX(), forge.getMouseY(),
                      forge.getMouseButton(), forge.getDragX(), forge.getDragY());
            }
        }
    }

    // ── MouseScrolled ─────────────────────────────────────
    public static abstract class MouseScrolled extends MouseInput {
        private final double scrollDeltaX;
        private final double scrollDeltaY;

        public MouseScrolled(Screen screen, double mouseX, double mouseY, double scrollDeltaX, double scrollDeltaY) {
            super(screen, mouseX, mouseY);
            this.scrollDeltaX = scrollDeltaX;
            this.scrollDeltaY = scrollDeltaY;
        }

        public double getScrollDeltaX() { return scrollDeltaX; }
        public double getScrollDeltaY() { return scrollDeltaY; }

        public static class Pre extends MouseScrolled implements ICancellableEvent {
            public Pre(Screen screen, double mouseX, double mouseY, double scrollDeltaX, double scrollDeltaY) {
                super(screen, mouseX, mouseY, scrollDeltaX, scrollDeltaY);
            }
            /** Wrapper constructor */
            public Pre(net.minecraftforge.client.event.ScreenEvent.MouseScrolled.Pre forge) {
                super(forge.getScreen(), forge.getMouseX(), forge.getMouseY(),
                      forge.getDeltaX(), forge.getDeltaY());
            }
        }

        public static class Post extends MouseScrolled {
            public Post(Screen screen, double mouseX, double mouseY, double scrollDeltaX, double scrollDeltaY) {
                super(screen, mouseX, mouseY, scrollDeltaX, scrollDeltaY);
            }
            /** Wrapper constructor */
            public Post(net.minecraftforge.client.event.ScreenEvent.MouseScrolled.Post forge) {
                super(forge.getScreen(), forge.getMouseX(), forge.getMouseY(),
                      forge.getDeltaX(), forge.getDeltaY());
            }
        }
    }

    // ── KeyInput (private base) ───────────────────────────
    private static abstract class KeyInput extends ScreenEvent {
        private final int keyCode;
        private final int scanCode;
        private final int modifiers;

        protected KeyInput(Screen screen, int keyCode, int scanCode, int modifiers) {
            super(screen);
            this.keyCode = keyCode;
            this.scanCode = scanCode;
            this.modifiers = modifiers;
        }

        public int getKeyCode() { return keyCode; }
        public int getScanCode() { return scanCode; }
        public int getModifiers() { return modifiers; }
    }

    // ── KeyPressed ────────────────────────────────────────
    public static abstract class KeyPressed extends KeyInput {
        public KeyPressed(Screen screen, int keyCode, int scanCode, int modifiers) {
            super(screen, keyCode, scanCode, modifiers);
        }

        public static class Pre extends KeyPressed implements ICancellableEvent {
            public Pre(Screen screen, int keyCode, int scanCode, int modifiers) {
                super(screen, keyCode, scanCode, modifiers);
            }
            /** Wrapper constructor */
            public Pre(net.minecraftforge.client.event.ScreenEvent.KeyPressed.Pre forge) {
                super(forge.getScreen(), forge.getKeyCode(), forge.getScanCode(), forge.getModifiers());
            }
        }

        public static class Post extends KeyPressed implements ICancellableEvent {
            public Post(Screen screen, int keyCode, int scanCode, int modifiers) {
                super(screen, keyCode, scanCode, modifiers);
            }
            /** Wrapper constructor */
            public Post(net.minecraftforge.client.event.ScreenEvent.KeyPressed.Post forge) {
                super(forge.getScreen(), forge.getKeyCode(), forge.getScanCode(), forge.getModifiers());
            }
        }
    }

    // ── KeyReleased ───────────────────────────────────────
    public static abstract class KeyReleased extends KeyInput {
        public KeyReleased(Screen screen, int keyCode, int scanCode, int modifiers) {
            super(screen, keyCode, scanCode, modifiers);
        }

        public static class Pre extends KeyReleased implements ICancellableEvent {
            public Pre(Screen screen, int keyCode, int scanCode, int modifiers) {
                super(screen, keyCode, scanCode, modifiers);
            }
            /** Wrapper constructor */
            public Pre(net.minecraftforge.client.event.ScreenEvent.KeyReleased.Pre forge) {
                super(forge.getScreen(), forge.getKeyCode(), forge.getScanCode(), forge.getModifiers());
            }
        }

        public static class Post extends KeyReleased implements ICancellableEvent {
            public Post(Screen screen, int keyCode, int scanCode, int modifiers) {
                super(screen, keyCode, scanCode, modifiers);
            }
            /** Wrapper constructor */
            public Post(net.minecraftforge.client.event.ScreenEvent.KeyReleased.Post forge) {
                super(forge.getScreen(), forge.getKeyCode(), forge.getScanCode(), forge.getModifiers());
            }
        }
    }

    // ── CharacterTyped ────────────────────────────────────
    public static abstract class CharacterTyped extends ScreenEvent {
        private final char codePoint;
        private final int modifiers;

        public CharacterTyped(Screen screen, char codePoint, int modifiers) {
            super(screen);
            this.codePoint = codePoint;
            this.modifiers = modifiers;
        }

        public char getCodePoint() { return codePoint; }
        public int getModifiers() { return modifiers; }

        public static class Pre extends CharacterTyped implements ICancellableEvent {
            public Pre(Screen screen, char codePoint, int modifiers) {
                super(screen, codePoint, modifiers);
            }
            /** Wrapper constructor */
            public Pre(net.minecraftforge.client.event.ScreenEvent.CharacterTyped.Pre forge) {
                super(forge.getScreen(), forge.getCodePoint(), forge.getModifiers());
            }
        }

        public static class Post extends CharacterTyped {
            public Post(Screen screen, char codePoint, int modifiers) {
                super(screen, codePoint, modifiers);
            }
            /** Wrapper constructor */
            public Post(net.minecraftforge.client.event.ScreenEvent.CharacterTyped.Post forge) {
                super(forge.getScreen(), forge.getCodePoint(), forge.getModifiers());
            }
        }
    }

    // ── Opening ───────────────────────────────────────────
    public static class Opening extends ScreenEvent implements ICancellableEvent {
        @Nullable
        private final Screen currentScreen;
        private Screen newScreen;

        public Opening(@Nullable Screen currentScreen, Screen screen) {
            super(screen);
            this.currentScreen = currentScreen;
            this.newScreen = screen;
        }
        /** Wrapper constructor */
        public Opening(net.minecraftforge.client.event.ScreenEvent.Opening forge) {
            super(forge.getScreen());
            this.currentScreen = forge.getCurrentScreen();
            this.newScreen = forge.getNewScreen();
        }

        @Nullable
        public Screen getCurrentScreen() { return currentScreen; }
        @Nullable
        public Screen getNewScreen() { return newScreen; }
        public void setNewScreen(Screen newScreen) { this.newScreen = newScreen; }
    }

    // ── Closing ───────────────────────────────────────────
    public static class Closing extends ScreenEvent {
        public Closing(Screen screen) {
            super(screen);
        }
        /** Wrapper constructor */
        public Closing(net.minecraftforge.client.event.ScreenEvent.Closing forge) {
            super(forge.getScreen());
        }
    }
}
