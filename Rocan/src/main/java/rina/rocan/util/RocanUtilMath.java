package rina.rocan.util;

// Minecraft.
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;

// Java.
import java.util.*;

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

	// KAMI. :")
	public static List<BlockPos> getSphereList(BlockPos pos, float r, int h, boolean hollow, boolean sphere) {
		int plus_y = 0;

		List<BlockPos> sphere_block = new ArrayList<BlockPos>();

		int cx = pos.getX();
		int cy = pos.getY();
		int cz = pos.getZ();

		for (int x = cx - (int) r; x <= cx + r; ++x) {
			for (int z = cz - (int) r; z <= cz + r; ++z) {
				for (int y = sphere ? (cy - (int) r) : cy; y < (sphere ? (cy + r) : ((float) (cy + h))); ++y) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0);
					if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
						BlockPos spheres = new BlockPos(x, y + plus_y, z);

						sphere_block.add(spheres);
					}
				}
			}
		}

		return sphere_block;
	}

	public static BlockPos getPlayerBlockpos() {
		return new BlockPos(Math.floor((double) RocanUtilMinecraftHelper.getMinecraft().player.posX), Math.floor((double) RocanUtilMinecraftHelper.getMinecraft().player.posY), Math.floor((double) RocanUtilMinecraftHelper.getMinecraft().player.posZ));
	}
}