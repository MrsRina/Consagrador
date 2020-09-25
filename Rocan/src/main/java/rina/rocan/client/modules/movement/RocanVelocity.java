package rina.rocan.client.modules.movement;

// Minecraft.
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

// Pomelo.
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

// Event.
import rina.rocan.event.entity.RocanEventEntityCollision;
import rina.rocan.event.network.RocanEventPacketReceive;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Rocan.
import rina.rocan.Rocan;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 23/09/2020.
 *
 **/
public class RocanVelocity extends RocanModule {
	public RocanVelocity() {
		super(new String[] {"Velocity", "Velocity", "Handler knockback values to 0."}, Category.ROCAN_MOVEMENT);
	}

	@Listener
	public void listenEntityCollision(RocanEventEntityCollision event) {
		if (mc.player == event.getEntity()) {
			event.cancel();

			return;
		}
	}
}