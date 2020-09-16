package rina.rocan.mixin.mixins;

// Minecraft.
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;

// IO.
import io.netty.channel.ChannelHandlerContext;

// Mixin.
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

// Java.
import java.io.IOException;

// Event.
import rina.rocan.event.network.RocanEventPacketReceive;
import rina.rocan.event.network.RocanEventPacketSend;
import rina.rocan.event.RocanEventStageable;

// Rocan.
import rina.rocan.Rocan;

/**
  * @author Rina
  *
  * Created by Rina!
  * 08/04/20.
  *
  **/
@Mixin(value = NetworkManager.class, priority = 999)
public class RocanMixinNetworkManager {
	@Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
	private void send(Packet<?> packet, CallbackInfo callback) {
		RocanEventPacketSend event = new RocanEventPacketSend(RocanEventStageable.EventStage.PRE, packet);

		Rocan.getPomeloEventManager().dispatchEvent(event);

		if (event.isCancelled()) {
			callback.cancel();
		}
	}

	@Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
	private void receive(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callback) {
		RocanEventPacketReceive event = new RocanEventPacketReceive(RocanEventStageable.EventStage.POST, packet);

		Rocan.getPomeloEventManager().dispatchEvent(event);

		if (event.isCancelled()) {
			callback.cancel();
		}
	}
}