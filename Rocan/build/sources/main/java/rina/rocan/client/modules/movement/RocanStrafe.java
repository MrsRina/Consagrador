package rina.rocan.client.modules.movement;

// Minecraft.
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.init.MobEffects;

// Pomelo.
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

// Event.
import rina.rocan.event.player.RocanEventPlayerMove;

// Module.
import rina.rocan.client.RocanModule.Category;
import rina.rocan.client.RocanModule.Define;
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Turok.
import rina.turok.TurokString;

// Utils.
import rina.rocan.util.RocanUtilMath;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 16/08/2020. // 00:05 am
 *
 **/
@Define(name = "Strafe", tag = "Strafe", description = "Make you fast and stable.", category = Category.ROCAN_MOVEMENT)
public class RocanStrafe extends RocanModule {
	RocanSetting modes_strafe  = createSetting(new String[] {"Modes", "Modes", "Modes for strafe"}, "FHM", new String[] {"FHM", "Normal"});
	RocanSetting onground_move = createSetting(new String[] {"OnGround", "OnGround", "Make movement in ground"}, true);
	RocanSetting auto_jump     = createSetting(new String[] {"Auto Jump", "AutoJump", "Auto jump to strafe"}, false);

	private double speed;

	@Listener
	public void playerMove(RocanEventPlayerMove event) {
		if (mc.player == null || mc.world == null) {
			return;
		}

		if (modes_strafe.getString().equals("FHM")) {
			if (mc.player.isSneaking() || mc.player.isOnLadder() || mc.player.isInWeb || mc.player.isInLava() || mc.player.isInWater() || mc.player.capabilities.isFlying) {
				return;
			}

			if (!onground_move.getBoolean() && !auto_jump.getBoolean()) {
				if (mc.player.onGround) {
					return;
				}
			}

			speed = 0.2873d;

			double[] player_movement = RocanUtilMath.transformStrafeMovement(mc.player);

			// player_movement[0] = Yaw; player_movement[1] = Pitch; player_movement[2] = Forward; player_movement[3] = Strafe;

			if (player_movement[2] == 0.0d && player_movement[3] == 0.0d) {
				event.setX(0.0d);
				event.setZ(0.0d);
			} else {
				if (auto_jump.getBoolean()) {
					multiplicJumpSpeed(event);
				} else {
					if (mc.gameSettings.keyBindJump.isKeyDown()) {
						multiplicJumpSpeed(event);
					}
				}

				event.setX((player_movement[2] * speed) * Math.cos(Math.toRadians((player_movement[0] + 90.0f))) + (player_movement[3] * speed) * Math.sin(Math.toRadians((player_movement[0] + 90.0f))));
				event.setZ((player_movement[2] * speed) * Math.sin(Math.toRadians((player_movement[0] + 90.0f))) - (player_movement[3] * speed) * Math.cos(Math.toRadians((player_movement[0] + 90.0f))));
			}
		} else if (modes_strafe.getString().equals("Normal")) {}
	}

	public void multiplicJumpSpeed(RocanEventPlayerMove event) {
		double new_motion_y = 0.40123128;

		if (mc.player.onGround) {
			event.setY(mc.player.motionY = new_motion_y);

			speed *= 2.149;
		}
	}
}