package nova.core.block;

import nova.core.component.misc.Collider;

/**
 * A default block, pre-added with essential components.
 * @author Calclavia
 */
public abstract class BlockDefault extends Block {
	public BlockDefault() {
		add(new Collider());
	}
}
