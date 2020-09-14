package rina.rocan.mixin.mixins;

// Minecraft.
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.client.Minecraft;

// Mixin.
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

// OpenGL.
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL11;

// Java.
import java.awt.*;

// Util.
import rina.rocan.util.RocanUtilMinecraftHelper;

// Event.
import rina.rocan.event.render.RocanEventRenderLivingBase;

// Rocan.
import rina.rocan.Rocan;

/**
  * @author Rina
  *
  * Created by Rina!
  * 13/09/2020.
  *
  **/
@Mixin(value = RenderLivingBase.class, priority = 999)
public abstract class RocanMixinRenderLivingBase <T extends EntityLivingBase> extends RocanMixinRender<T> {
	@Inject(method = "doRender", at = @At("HEAD"))
	private void onRender(T entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
		RocanEventRenderLivingBase event = new RocanEventRenderLivingBase(RocanEventRenderLivingBase.EventStage.PRE, entity, x, y, z, yaw, partial_ticks);
	
		Rocan.getPomeloEventManager().dispatchEvent(event);
	}

	@Inject(method = "doRender", at = @At("RETURN"))
	private void onLastRender(T entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
		RocanEventRenderLivingBase event = new RocanEventRenderLivingBase(RocanEventRenderLivingBase.EventStage.POST, entity, x, y, z, yaw, partial_ticks);
	
		Rocan.getPomeloEventManager().dispatchEvent(event);
	}
}