package rina.rocan.client.system;

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
 * 25/09/2020.
 *
 **/
public class RocanSystemPacket extends RocanModule {
	private boolean event_strafe_bypass_update;

	public RocanSystemPacket() {
		super(new String[] {"System Packet", "SystemPacket", "System util to handler packet stuff."}, Category.ROCAN_SYSTEM, true);
	}

	@Override
	public void onUpdate() {
		getSettingModule().setInteger(-1);
	}

	@Listener
	public void receivePacket(RocanEventPacketReceive event) {
		if (event.getPacket() instanceof SPacketEntityVelocity) {
			SPacketEntityVelocity velocity = (SPacketEntityVelocity) event.getPacket();

			if (Rocan.getModuleManager().getModuleByTag("Velocity").getState()) {
				if (velocity.getEntityID() == mc.player.entityId) {
					event.cancel();

					velocity.motionX *= 0.0f;
					velocity.motionY *= 0.0f;
					velocity.motionZ *= 0.0f;
				}
			}
		}

		if (event.getPacket() instanceof SPacketExplosion) {
			SPacketExplosion explosion = (SPacketExplosion) event.getPacket();

			if (Rocan.getSettingManager().getSettingByModuleAndTag("Strafe", "StrafeSmartBypassUpdate").getBoolean()) {
				event_strafe_bypass_update = true;

				Rocan.getSettingManager().getSettingByModuleAndTag("Strafe", "StrafeBypassSpeed").setBoolean(true);
			}

			if (Rocan.getModuleManager().getModuleByTag("Velocity").getState()) {
				event.cancel();

				explosion.motionX *= 0.0f;
				explosion.motionY *= 0.0f;
				explosion.motionZ *= 0.0f;
			}
		} else {
			if (Rocan.getSettingManager().getSettingByModuleAndTag("Strafe", "StrafeSmartBypassUpdate").getBoolean() && event_strafe_bypass_update) {
				Rocan.getSettingManager().getSettingByModuleAndTag("Strafe", "StrafeBypassSpeed").setBoolean(false);

				event_strafe_bypass_update = false;
			}
		}
	}
}