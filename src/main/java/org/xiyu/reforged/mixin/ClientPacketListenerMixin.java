package org.xiyu.reforged.mixin;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.common.extensions.IClientCommonPacketListenerExtension;
import net.neoforged.neoforge.network.connection.ConnectionType;
import net.neoforged.neoforge.network.connection.ConnectionUtils;
import org.xiyu.reforged.shim.network.PayloadChannelRegistry;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Adds NeoForge's client-side payload send shortcut to {@link ClientPacketListener}.
 */
@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin implements IClientCommonPacketListenerExtension {

    private static final Logger REFORGED_LOGGER = LogUtils.getLogger();

    public void send(CustomPacketPayload payload) {
        // Check if this payload is registered in our NeoForge payload bridge
        if (PayloadChannelRegistry.getEntry(payload.type().id()) != null) {
            REFORGED_LOGGER.debug("[ReForged] Routing client payload through bridge: {}", payload.type().id());
            PayloadChannelRegistry.sendToServer(payload);
        } else {
            // Vanilla or Forge-native payload — send normally
            ((ClientPacketListener) (Object) this).send(new ServerboundCustomPayloadPacket(payload));
        }
    }

    public void disconnect(Component reason) {
        ((ClientPacketListener) (Object) this).getConnection().disconnect(reason);
    }

    public net.minecraft.util.thread.ReentrantBlockableEventLoop<?> getMainThreadEventLoop() {
        return Minecraft.getInstance();
    }

    public ConnectionType getConnectionType() {
        return ConnectionUtils.getConnectionType(((ClientPacketListener) (Object) this).getConnection());
    }
}