package rina.rocan.mixin.mixins;

// Minecraft.
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

// Mixin.
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

// Events.
import rina.rocan.event.render.RocanEventRenderEntity;

// Rocan.
import rina.rocan.Rocan;

/**
  * @author Rina
  *
  * Created by Rina!
  * 14/09/2020.
  *
  **/
@Mixin(value = Render.class, priority = 999)
public abstract class RocanMixinRender <T extends Entity> {
	@Shadow
	protected abstract boolean bindEntityTexture(T entity);

	@Inject(method = "doRender", at = @At("HEAD"))
	private void onRender(T entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
		RocanEventRenderEntity event = new RocanEventRenderEntity(RocanEventRenderEntity.EventStage.PRE, entity, x, y, z, yaw, partial_ticks);
	
		Rocan.getPomeloEventManager().dispatchEvent(event);
	}

	@Inject(method = "doRender", at = @At("RETURN"))
	private void onLastRender(T entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
		RocanEventRenderEntity event = new RocanEventRenderEntity(RocanEventRenderEntity.EventStage.POST, entity, x, y, z, yaw, partial_ticks);
	
		Rocan.getPomeloEventManager().dispatchEvent(event);
	}
}