package net.neoforged.neoforge.event.entity.player;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * NeoForge ItemTooltipEvent — fired when an item's tooltip is gathered.
 */
public class ItemTooltipEvent extends PlayerEvent {
    private TooltipFlag flags;
    @NotNull private ItemStack itemStack;
    private List<Component> toolTip;
    private Item.TooltipContext context;

    /** Required by Forge's EventListenerHelper */
    public ItemTooltipEvent() { super(); }

    public ItemTooltipEvent(@NotNull ItemStack itemStack, @Nullable Player player,
                            List<Component> list, TooltipFlag flags) {
        super(player != null ? player : fakeLookup());
        this.flags = flags;
        this.itemStack = itemStack;
        this.toolTip = list;
        this.context = Item.TooltipContext.EMPTY;
    }

    /** Wrapper constructor */
    public ItemTooltipEvent(net.minecraftforge.event.entity.player.ItemTooltipEvent forge) {
        super(forge);
        this.flags = forge.getFlags();
        this.itemStack = forge.getItemStack();
        this.toolTip = forge.getToolTip();
        this.context = Item.TooltipContext.EMPTY;
    }

    @NotNull public ItemStack getItemStack() { return itemStack; }
    public TooltipFlag getFlags() { return flags; }
    public List<Component> getToolTip() { return toolTip; }
    public Item.TooltipContext getContext() { return context; }

    @Override
    @Nullable
    public Player getEntity() {
        try {
            return super.getEntity();
        } catch (Exception e) {
            return null;
        }
    }

    private static Player fakeLookup() {
        // Player can be null in tooltip events (e.g., loading screens)
        return null;
    }
}
