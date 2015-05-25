package nova.wrapper.mc1710.forward.util;

import net.minecraft.util.AxisAlignedBB;
import nova.core.util.transform.shape.Cuboid;

/**
 * @author Calclavia
 */
public class FWCuboid extends AxisAlignedBB {
	public FWCuboid(Cuboid cuboid) {
		super(cuboid.min.x, cuboid.min.y, cuboid.min.z, cuboid.max.x, cuboid.max.y, cuboid.max.z);
	}
}
