package nova.core.wrapper.mc18.asm;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import nova.core.event.BlockEvent;
import nova.core.event.bus.GlobalEvents;
import nova.core.wrapper.mc18.wrapper.block.forward.FWTile;
import nova.core.wrapper.mc18.wrapper.block.forward.FWTileLoader;
import nova.internal.core.Game;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Static forwarder forwards injected methods.
 * @author Calclavia
 */
public class StaticForwarder {

	public static void chunkSetBlockEvent(Chunk chunk, int x, int y, int z, Block oldBlock, int oldMeta, Block newBlock, int newMeta) {
		// Publish the event
		Game.events().publish(new BlockEvent.Change(Game.natives().toNova(chunk.getWorld()), new Vector3D((chunk.xPosition << 4) + x, y, (chunk.zPosition << 4) + z), Game.natives().toNova(oldBlock), Game.natives().toNova(newBlock)));
	}

	/**
	 * Used to inject forwarded TileEntites
	 * @param data
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static TileEntity loadTileEntityHook(NBTTagCompound data, Class<? extends TileEntity> clazz) throws Exception {
		if (FWTile.class.isAssignableFrom(clazz)) {
			return FWTileLoader.loadTile(data);
		} else {
			return clazz.newInstance();
		}
	}
}
