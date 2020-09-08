package rina.rocan.mixin.mixins;

// Minecraft.
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Entity;

// IO.
import io.netty.channel.ChannelHandlerContext;

// Mixin.
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

// Java.
import java.io.IOException;

// Acess.
import rina.rocan.mixin.acess.type.*;

// Events.
import rina.rocan.event.player.RocanEventPlayerUpdateWalking;
import rina.rocan.event.player.RocanEventPlayerMove;
import rina.rocan.event.RocanEventStageable;

// Rocan.
import rina.rocan.Rocan;

/**
  * @author Rina
  *
  * Created by Rina!
  * 23/08/20.
  *
  **/
@Mixin(value = EntityPlayerSP.class, priority = 999)
public abstract class RocanMixinEntityPlayerSP extends RocanMixinEntityPlayer implements AcessEntityPlayerSP {
	@Override
	public void move(MoverType type, double x, double y, double z) {
		RocanEventPlayerMove event = new RocanEventPlayerMove(type, x, y, z);

		Rocan.getPomeloEventManager().dispatchEvent(event);

		super.move(type, event.getX(), event.getY(), event.getZ());
	}

	@Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"))
	public void preEventWalking(CallbackInfo info) {
		RocanEventPlayerUpdateWalking event = new RocanEventPlayerUpdateWalking(RocanEventStageable.EventStage.PRE);

		Rocan.getPomeloEventManager().dispatchEvent(event);
	}

	@Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"))
	public void postEventWalking(CallbackInfo info) {
		RocanEventPlayerUpdateWalking event = new RocanEventPlayerUpdateWalking(RocanEventStageable.EventStage.POST);
	
		Rocan.getPomeloEventManager().dispatchEvent(event);
	}
}