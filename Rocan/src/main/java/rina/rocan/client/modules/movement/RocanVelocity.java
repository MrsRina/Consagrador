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
	RocanSetting handler_x = createSetting(new String[] {"Handler X", "VelocityHandlerX", "Handler to x value knockback."}, 0, 5, 10);
	RocanSetting handler_y = createSetting(new String[] {"Handler Y", "VelocityHandlerY", "Handler to y value knockback."}, 0, 5, 10);

	public RocanVelocity() {
		super(new String[] {"Velocity", "Velocity", "Handler knockback values."}, Category.ROCAN_MOVEMENT);
	}

	@Listener
	public void receivePacket(RocanEventPacketReceive event) {
		if (event.getPacket() instanceof SPacketEntityVelocity) {
			SPacketEntityVelocity velocity = (SPacketEntityVelocity) event.getPacket();

			if (velocity.getEntityID() == mc.player.entityId) {
				if (handler_x.getInteger() == 0 && handler_y.getInteger() == 0) {
					event.cancel();
				} 

				velocity.motionX *= handler_x.getInteger();
				velocity.motionY *= handler_y.getInteger();
				velocity.motionZ *= handler_x.getInteger();
			}
		}
	}

	@Listener
	public void listenEntityCollision(RocanEventEntityCollision event) {
		if (mc.player == event.getEntity()) {
			if (handler_x.getInteger() == 0 && handler_y.getInteger() == 0) {
				event.cancel();

				return;
			}

			event.setX(-event.getX() * handler_x.getInteger());
			event.setY(0);
			event.setZ(-event.getZ() * handler_x.getInteger());
		}
	}
}