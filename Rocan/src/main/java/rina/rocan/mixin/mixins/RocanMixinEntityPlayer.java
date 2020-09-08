package rina.rocan.mixin.mixins;

// Minecraft.
import net.minecraft.entity.player.EntityPlayer;

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
@Mixin(value = EntityPlayer.class, priority = 999)
public abstract class RocanMixinEntityPlayer extends RocanMixinEntity {}