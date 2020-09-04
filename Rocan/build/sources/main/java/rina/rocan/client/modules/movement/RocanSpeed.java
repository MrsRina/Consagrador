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
public class RocanSpeed extends RocanModule {
	RocanSetting modes_speed = createSetting(new String[] {"Mode", "SpeedModes", "Modes for speed."}, "Strafe", new String[] {"Strafe", "Sprint"});
	RocanSetting auto_jump   = createSetting(new String[] {"Auto Jump", "SpeedAutoJump", "Auto jump to speed."}, false);

	private double speed;
	private double speed_event;

	boolean jumping;
	boolean jumping_state;

	public RocanSpeed() {
		super(new String[] {"Speed", "Speed", "Make fast."}, Category.ROCAN_MOVEMENT);
	}

	@Listener
	public void playerMove(RocanEventPlayerMove event) {
		if (mc.player == null || mc.world == null) {
			return;
		}

		if (modes_speed.getString().equals("Strafe")) {
			if (mc.player.isSneaking() || mc.player.isOnLadder() || mc.player.isInWeb || mc.player.isInLava() || mc.player.isInWater() || mc.player.capabilities.isFlying) {
				return;
			}

			double[] player_movement = RocanUtilMath.transformStrafeMovement(mc.player);

			if (Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ()) > 0.2873d) {
				speed = Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ());
			} else {
				speed = 0.2873d;
			}

			if (mc.player.isPotionActive(MobEffects.SPEED)) {
				final int amplifier = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();

				speed *= (1.0d + 0.2d * (amplifier + 1));
			}

			// player_movement[0] = Yaw; player_movement[1] = Pitch; player_movement[2] = Forward; player_movement[3] = Strafe;

			if (player_movement[2] == 0.0d && player_movement[3] == 0.0d) {
				event.setX(0.0d);
				event.setZ(0.0d);
			} else {
				mc.player.setSprinting(true);

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
		} else if (modes_speed.getString().equals("Sprint")) {
			if ((mc.gameSettings.keyBindForward.isKeyDown()) && !(mc.player.isSneaking()) && !(mc.player.isHandActive()) && !(mc.player.collidedHorizontally) && mc.currentScreen == null && !(mc.player.getFoodStats().getFoodLevel() <= 6f)) {
				mc.player.setSprinting(true);

				if (auto_jump.getBoolean()) {
					makeJump(event);
				}
			}
		}
	}

	public void makeJump(RocanEventPlayerMove event) {
		double jump = 0.40123128d;

		if (mc.player.onGround) {
			speed = 0.6174077d;

			if (mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
				jump += ((mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f);
			}

			event.setY(mc.player.motionY = jump);
		}
	}
}