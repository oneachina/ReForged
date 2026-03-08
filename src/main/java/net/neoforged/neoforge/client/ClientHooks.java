package net.neoforged.neoforge.client;

import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.jetbrains.annotations.NotNull;

/**
 * NeoForge ClientHooks — delegates to Forge's ForgeHooksClient.
 * Method signatures match NeoForge's API; the implementation forwards to Forge.
 */
public class ClientHooks {

    // ── GUI Layer Management ──────────────────────────────

    public static void pushGuiLayer(Minecraft minecraft, Screen screen) {
        ForgeHooksClient.pushGuiLayer(minecraft, screen);
    }

    public static void popGuiLayer(Minecraft minecraft) {
        ForgeHooksClient.popGuiLayer(minecraft);
    }

    public static void clearGuiLayers(Minecraft minecraft) {
        ForgeHooksClient.clearGuiLayers(minecraft);
    }

    public static void resizeGuiLayers(Minecraft minecraft, int width, int height) {
        ForgeHooksClient.resizeGuiLayers(minecraft, width, height);
    }

    public static float getGuiFarPlane() {
        return ForgeHooksClient.getGuiFarPlane();
    }

    // ── Screen Rendering ──────────────────────────────────

    public static void drawScreen(Screen screen, GuiGraphics guiGraphics,
                                   int mouseX, int mouseY, float partialTick) {
        ForgeHooksClient.drawScreen(screen, guiGraphics, mouseX, mouseY, partialTick);
    }

    // ── Tooltip Methods ───────────────────────────────────

    public static Font getTooltipFont(@NotNull ItemStack stack, Font fallbackFont) {
        return ForgeHooksClient.getTooltipFont(stack, fallbackFont);
    }

    public static RenderTooltipEvent.Pre onRenderTooltipPre(
            @NotNull ItemStack stack, GuiGraphics graphics, int x, int y,
            int screenWidth, int screenHeight,
            @NotNull List<ClientTooltipComponent> components,
            @NotNull Font fallbackFont,
            @NotNull ClientTooltipPositioner positioner) {
        return ForgeHooksClient.onRenderTooltipPre(
                stack, graphics, x, y, screenWidth, screenHeight,
                components, fallbackFont, positioner);
    }

    public static RenderTooltipEvent.Color onRenderTooltipColor(
            @NotNull ItemStack stack, GuiGraphics graphics, int x, int y,
            @NotNull Font font, @NotNull List<ClientTooltipComponent> components) {
        return ForgeHooksClient.onRenderTooltipColor(stack, graphics, x, y, font, components);
    }

    public static List<ClientTooltipComponent> gatherTooltipComponents(
            ItemStack stack, List<? extends FormattedText> textElements,
            int mouseX, int screenWidth, int screenHeight, Font fallbackFont) {
        return ForgeHooksClient.gatherTooltipComponents(
                stack, textElements, mouseX, screenWidth, screenHeight, fallbackFont);
    }

    public static List<ClientTooltipComponent> gatherTooltipComponents(
            ItemStack stack, List<? extends FormattedText> textElements,
            Optional<TooltipComponent> itemComponent,
            int mouseX, int screenWidth, int screenHeight, Font fallbackFont) {
        return ForgeHooksClient.gatherTooltipComponents(
                stack, textElements, itemComponent, mouseX, screenWidth, screenHeight, fallbackFont);
    }

    public static List<ClientTooltipComponent> gatherTooltipComponentsFromElements(
            ItemStack stack, List<Either<FormattedText, TooltipComponent>> elements,
            int mouseX, int screenWidth, int screenHeight, Font fallbackFont) {
        return ForgeHooksClient.gatherTooltipComponentsFromElements(
                stack, elements, mouseX, screenWidth, screenHeight, fallbackFont);
    }
}
