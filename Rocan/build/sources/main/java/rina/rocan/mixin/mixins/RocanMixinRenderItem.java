package rina.rocan.mixin.mixins;

// Minecraft.
import net.minecraft.client.renderer.RenderItem;
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
  * 22/09/2020.
  *
  **/
@Mixin(value = RenderItem.class, priority = 999)
public abstract class RocanMixinRenderItem {}