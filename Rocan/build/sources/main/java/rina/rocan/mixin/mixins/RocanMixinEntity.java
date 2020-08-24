package rina.rocan.mixin.mixins;

// Minecraft.
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

// Rocan.
import rina.rocan.Rocan;

/**
  * @author Rina
  *
  * Created by Rina!
  * 23/08/20.
  *
  **/
@Mixin(value = Entity.class)
public abstract class RocanMixinEntity {
	@Shadow
	public void move(MoverType type, double x, double y, double z) {}
}