package rina.rocan.mixin.mixins;

// Minecraft.
import net.minecraft.client.renderer.entity.RenderEntity;
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
import rina.rocan.event.render.RocanEventRenderEntityNonLivingBase;

// Rocan.
import rina.rocan.Rocan;

/**
  * @author Rina
  *
  * Created by Rina!
  * 20/09/2020.
  *
  **/
@Mixin(value = RenderEntity.class, priority = 999)
public abstract class RocanMixinRenderEntity {
	@Inject(method = "doRender", at = @At("HEAD"))
	private void doRender(Entity entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
		RocanEventRenderEntityNonLivingBase event = new RocanEventRenderEntityNonLivingBase(RocanEventRenderEntityNonLivingBase.EventStage.PRE, entity, x, y, z, yaw, partial_ticks);

		Rocan.getPomeloEventManager().dispatchEvent(event);
	}

	@Inject(method = "doRender", at = @At("RETURN"))
	private void doLastRender(Entity entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
		RocanEventRenderEntityNonLivingBase event = new RocanEventRenderEntityNonLivingBase(RocanEventRenderEntityNonLivingBase.EventStage.POST, entity, x, y, z, yaw, partial_ticks);

		Rocan.getPomeloEventManager().dispatchEvent(event);
	}
}