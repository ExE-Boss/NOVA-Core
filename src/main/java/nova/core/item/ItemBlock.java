package nova.core.item;

import nova.core.block.Block;
import nova.core.block.BlockFactory;
import nova.core.block.component.BlockProperties;
import nova.core.entity.Entity;
import nova.core.util.Direction;
import nova.core.world.World;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.Optional;

/**
 * An ItemBlock is an Item that is meant to be used to place blocks.
 *
 * @author Calclavia
 */
public class ItemBlock extends Item {

	public final BlockFactory blockFactory;

	public ItemBlock(BlockFactory blockFactory) {
		this.blockFactory = blockFactory;
		events.on(UseEvent.class).bind(this::onUse);
	}

	protected void onUse(UseEvent evt) {
		Optional<Block> opBlock = evt.entity.world().getBlock(evt.position);

		if (opBlock.isPresent()) {
			Block block = opBlock.get();
			Vector3D placePos = block.shouldDisplacePlacement() ? evt.position.add(evt.side.toVector()) : evt.position;
			if (onPrePlace(evt.entity, evt.entity.world(), placePos, evt.side, evt.hit)) {
				evt.action = onPostPlace(evt.entity, evt.entity.world(), placePos, evt.side, evt.hit);
			}
		}
	}

	protected boolean onPrePlace(Entity entity, World world, Vector3D placePos, Direction side, Vector3D hit) {
		Optional<Block> checkBlock = world.getBlock(placePos);
		if (checkBlock.isPresent() && checkBlock.get().canReplace()) {
			return world.setBlock(placePos, blockFactory);
		}
		return false;
	}

	protected boolean onPostPlace(Entity entity, World world, Vector3D placePos, Direction side, Vector3D hit) {
		Optional<Block> opBlock = world.getBlock(placePos);
		if (opBlock.isPresent() && opBlock.get().sameType(blockFactory.getDummy())) {
			//TODO: What if the block is NOT placed by a player?
			opBlock.get().events.publish(new Block.PlaceEvent(entity, side, hit, this));
			if (opBlock.get().has(BlockProperties.class)) {
				world.playSoundAtPosition(placePos, opBlock.get().get(BlockProperties.class).getSound(BlockProperties.BlockSoundTrigger.PLACE));
			}
		}

		addCount(-1);

		return true;
	}

	@Override
	public String getID() {
		return blockFactory.getID();
	}
}
