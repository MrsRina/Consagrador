package rina.rocan.client.modules.movement;

// Minecraft.
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.init.MobEffects;

// Pomelo.
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

// Event.
import rina.rocan.event.player.RocanEventPlayerMove;

// Client.
import rina.rocan.client.RocanSetting;
import rina.rocan.client.RocanModule;

// Turok.
import rina.turok.TurokString;

// Utils.
import rina.rocan.util.RocanUtilClient;
import rina.rocan.util.RocanUtilMath;

/**
 *
 * @author Rina!
 *
 * Created by Rina!
 * 16/08/2020. // 00:05 am
 *
 * Update in 04/09/2020. // 00:40 am.
 *
 **/
public class RocanStrafe extends RocanModule {
	RocanSetting modes_movement       = createSetting(new String[] {"Mode", "StrafeMode", "Modes for strafe."}, "Sprint", new String[] {"Sprint", "Static"});
	RocanSetting auto_jump            = createSetting(new String[] {"Auto Jump", "StrafeAutoJump", "Auto jump to strafe."}, false);
	RocanSetting fast_movement        = createSetting(new String[] {"Fast Movement", "StrafeFastMovement", "Fast strafe movement."}, true);
	RocanSetting strafe_potion_effect = createSetting(new String[] {"Potion Effect", "StrafePotionEffect", "Potion effect speed."}, true);

	double speed;

	public RocanStrafe() {
		super(new String[] {"Strafe", "Strafe", "Make fast."}, Category.ROCAN_MOVEMENT);
	}

	@Listener
	public void playerMove(RocanEventPlayerMove event) {
		if (mc.player == null || mc.world == null) {
			return;
		}

		if (mc.player.isSneaking() || mc.player.isOnLadder() || mc.player.isInWeb || mc.player.isInLava() || mc.player.isInWater() || mc.player.capabilities.isFlying) {
			return;
		}

		double[] player_movement = RocanUtilMath.transformStrafeMovement(mc.player);

		if (modes_movement.getString().equals("Sprint")) {
			mc.player.setSprinting(true);

			speed = (Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ()) > 0.2873d ? Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ()) : 0.2873d);
		
			verifySpeed();
		} else if (modes_movement.getString().equals("Static")) {
			speed = 0.2873d;

			verifySpeed();
		}

		// player_movement[0] = Yaw; player_movement[1] = Pitch; player_movement[2] = Forward; player_movement[3] = Strafe;

		if (player_movement[2] == 0.0d && player_movement[3] == 0.0d) {
			event.setX(0.0d);
			event.setZ(0.0d);
		} else {
			if (auto_jump.getBoolean()) {
				makeJump(event);
			} else {
				if (mc.gameSettings.keyBindJump.isKeyDown()) {
					makeJump(event);
				}
			}

			event.setX((player_movement[2] * speed) * Math.cos(Math.toRadians((player_movement[0] + 90.0f))) + (player_movement[3] * speed) * Math.sin(Math.toRadians((player_movement[0] + 90.0f))));
			event.setZ((player_movement[2] * speed) * Math.sin(Math.toRadians((player_movement[0] + 90.0f))) - (player_movement[3] * speed) * Math.cos(Math.toRadians((player_movement[0] + 90.0f))));
		}
	}

	public void verifySpeed() {
		if (mc.player.isPotionActive(MobEffects.SPEED) && strafe_potion_effect.getBoolean()) {
			final int amplifier = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();

			speed *= (1.0d + 0.2d * (amplifier + 1));
		}
	}

	public void makeJump(RocanEventPlayerMove event) {
		double jump = 0.40123128d;

		if (mc.player.onGround) {
			if (fast_movement.getBoolean()) {
				speed = 0.6174077d;
			}

			if (mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
				jump += ((mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f);
			}

			event.setY(mc.player.motionY = jump);
		}
	}
}