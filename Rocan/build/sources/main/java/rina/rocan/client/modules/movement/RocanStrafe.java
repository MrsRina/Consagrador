package rina.rocan.client.modules.movement;

// Minecraft.
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.init.MobEffects;

// OpenGL.
import org.lwjgl.input.Keyboard;

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

// Rocan.
import rina.rocan.Rocan;

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
	RocanSetting modes_movement       = createSetting(new String[] {"Mode", "StrafeMode", "Modes movementation for strafe."}, "OnGround", new String[] {"OnGround", "AutoJump"});
	RocanSetting strafe_speed         = createSetting(new String[] {"Speed", "StrafeSpeed", "Handler speed."}, 0.0, 0.0, 100.0);
	RocanSetting automatically_sprint = createSetting(new String[] {"Sprint", "StrafeSprint", "Automatically sprint."}, true);
	RocanSetting smooth_jump          = createSetting(new String[] {"Smooth Jump", "StrafeSmoothJump", "Smooth speed jump."}, false);
	RocanSetting speed_potion_effect  = createSetting(new String[] {"Speed Potion Handler", "StrafeSpeedPotionHandler", "Enable speed handler to potion."}, true);
	RocanSetting jump_potion_effect   = createSetting(new String[] {"BJump Potion Handler", "StrafeJumpPotionHandler", "Enable jump boost to potion."}, true);
	RocanSetting smart_bypass_update  = createSetting(new String[] {"Smart Bypass Update", "StrafeSmartBypassUpdate", "Automatically enable bypass in explosion."}, false);
	RocanSetting bypass_speed         = createSetting(new String[] {"Bypass Speed", "StrafeBypassSpeed", "All damage explosions make you controlled and fast."}, -1, false);

	private int jump = mc.gameSettings.keyBindJump.getKeyCode();

	private double speed;

	private boolean jump_state;

	public RocanStrafe() {
		super(new String[] {"Strafe", "Strafe", "Make fast."}, Category.ROCAN_MOVEMENT);
	}

	@Override
	public void onUpdate() {
		if (mc.player == null) {
			return;
		}

		if (!bypass_speed.getBoolean()) {
			this.info = modes_movement.getString();

			return;
		} else {
			this.info = "Bypass";
		}

		if (mc.currentScreen instanceof GuiChat || mc.currentScreen != null) {
			return;
		}

		if (Keyboard.isKeyDown(jump)) {
			if (mc.player.onGround) {
				mc.player.jump();
			}
		}
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

		if (automatically_sprint.getBoolean()) {
			mc.player.setSprinting(true);
		}

		speed = (Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ()) > 0.2873d ? Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ()) + (Math.sqrt(event.getX() * event.getX() + event.getZ() * event.getZ()) >= 0.34D ? strafe_speed.getDouble() / 1000d : 0.0d) : 0.2873d);

		if (mc.player.isPotionActive(MobEffects.SPEED) && speed_potion_effect.getBoolean()) {
			final int amplifier = mc.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();

			speed *= (1.0d + 0.2d * (amplifier + 1));
		}

		if (player_movement[2] == 0.0d && player_movement[3] == 0.0d) {
			event.setX(0.0d);
			event.setZ(0.0d);
		} else {
			if (modes_movement.getString().equals("OnGround")) {
				if (mc.gameSettings.keyBindJump.isKeyDown() && mc.player.onGround) {
					jump_state = true;
				}
			} else if (modes_movement.getString().equals("AutoJump")) {
				if (automatically_sprint.getBoolean()) {
					mc.player.setSprinting(true);
				}

				if (mc.gameSettings.keyBindJump.pressed == false) {
					mc.gameSettings.keyBindJump.pressed = true;

					if (mc.player.onGround) {
						mc.player.jump();
					}
				}

				if (mc.gameSettings.keyBindJump.isKeyDown()) {
					jump_state = true;
				}
			}

			if (jump_state) {
				double jump = 0.40123128d;

				if (mc.player.onGround) {
					if (!smooth_jump.getBoolean()) {
						speed = 0.6174077d;
					}

					if (mc.player.isPotionActive(MobEffects.JUMP_BOOST) && jump_potion_effect.getBoolean()) {
						jump += ((mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f);
					}

					event.setY(mc.player.motionY = jump);
				}

				jump_state = false;
			}

			event.setX((player_movement[2] * speed) * Math.cos(Math.toRadians((player_movement[0] + 90.0f))) + (player_movement[3] * speed) * Math.sin(Math.toRadians((player_movement[0] + 90.0f))));
			event.setZ((player_movement[2] * speed) * Math.sin(Math.toRadians((player_movement[0] + 90.0f))) - (player_movement[3] * speed) * Math.cos(Math.toRadians((player_movement[0] + 90.0f))));
		}
	}
}