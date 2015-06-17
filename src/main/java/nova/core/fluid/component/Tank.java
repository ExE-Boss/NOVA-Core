package nova.core.fluid.component;

import nova.core.fluid.Fluid;
import nova.core.fluid.FluidFactory;

import java.util.Optional;

/**
 * Classes with this interface declare ability to store fluids
 * @see FluidConsumer
 * @see Tank
 */
public interface Tank extends FluidConsumer, FluidProvider {

	/**
	 * @return Maximum capacity of this container
	 */
	int getFluidCapacity();

	/**
	 * @return Fluid stored in this container
	 */
	Optional<Fluid> getFluid();

	default int getFluidAmount() {
		return hasFluid() ? getFluid().get().amount() : 0;
	}

	/**
	 * @return Whether this container is storing a fluid
	 */
	default boolean hasFluid() {
		return getFluid().isPresent();
	}

	default boolean hasFluidType(String fluidID) {
		if (hasFluid()) {
			return getFluid().get().getID().equals(fluidID);
		}

		return false;
	}

	default boolean hasFluidType(Fluid sample) {
		if (hasFluid()) {
			return getFluid().get().sameType(sample);
		}

		return false;
	}

	default boolean hasFluidType(FluidFactory factory) {
		return hasFluidType(factory.getDummy());
	}

}
