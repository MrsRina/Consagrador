package rina.rocan.mixin.mixins;

// Minecraft.
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Entity;

// IO.
import io.netty.channel.ChannelHandlerContext;

// Mixin.
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

// Java.
import java.io.IOException;

// Event.
import rina.rocan.event.entity.RocanEventEntityCollision;

// Rocan.
import rina.rocan.Rocan;

/**
  * @author Rina
  *
  * Created by Rina!
  * 23/08/20.
  *
  **/
@Mixin(value = Entity.class, priority = 999)
public abstract class RocanMixinEntity {
	@Shadow
	public void move(MoverType type, double x, double y, double z) {}

	@Redirect(method = "applyEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
	private void applyCollisionEvent(Entity entity, double x, double y, double z) {
		RocanEventEntityCollision event = new RocanEventEntityCollision(entity, x, y, z);

		Rocan.getPomeloEventManager().dispatchEvent(event);

		if (event.isCancelled()) {
			return;
		}

		entity.motionX += x;
		entity.motionY += y;
		entity.motionZ += z;

		entity.isAirBorne = true;
	}
}