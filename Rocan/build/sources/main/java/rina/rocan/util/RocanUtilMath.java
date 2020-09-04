package rina.rocan.util;

// Minecraft.
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

/**
  *
  * @author Rina!
  *
  * Created by Rina!
  * 23/08/2020.
  *
  **/
public class RocanUtilMath {
	public static double[] transformStrafeMovement(EntityPlayerSP entity) {
		double entity_rotation_yaw   = (double) entity.rotationYaw;
		double entity_rotation_pitch = (double) entity.rotationPitch;

		double entity_movement_forward = (double) entity.movementInput.moveForward;
		double entity_movement_strafe  = (double) entity.movementInput.moveStrafe;

		if (entity_movement_forward != 0.0d && entity_movement_strafe != 0.0d) {
			if (entity_movement_forward != 0.0d) {
				if (entity_movement_strafe > 0.0d) {
					entity_rotation_yaw += ((entity_movement_forward > 0.0d) ? -45 : 45);
				} else if (entity_movement_strafe < 0) {
					entity_rotation_yaw += ((entity_movement_forward > 0.0d) ? 45 : -45);
				}

				entity_movement_strafe = 0.0d;

				if (entity_movement_forward > 0.0d) {
					entity_movement_forward = 1.0d;
				} else if (entity_movement_forward < 0.0d) {
					entity_movement_forward = -1.0d;
				}
			}
		}

		return new double[] {
			entity_rotation_yaw, entity_rotation_pitch,
			entity_movement_forward, entity_movement_strafe
		};
	}

	public static double clamp(double value, double min, double max) {
		if (value > max) {
			return max;
		} else if (value < min) {
			return min;
		}

		return value;
	}
}