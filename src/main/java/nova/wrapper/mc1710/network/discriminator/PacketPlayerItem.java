package nova.wrapper.mc1710.network.discriminator;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import nova.core.network.PacketHandler;
import nova.wrapper.mc1710.network.MCPacket;

/**
 * A packet handler for players who are currently holding their item.
 * @author Calclavia
 */
//TODO: Move to NOVA Core
public class PacketPlayerItem extends PacketAbstract {
	public int slotId;

	public PacketPlayerItem() {

	}

	public PacketPlayerItem(int slotId) {
		this.slotId = slotId;
	}

	public PacketPlayerItem(EntityPlayer player) {
		this(player.inventory.currentItem);
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(slotId);
		buffer.writeBytes(data);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		slotId = buffer.readInt();
		data = buffer.slice();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		ItemStack stack = player.inventory.getStackInSlot(this.slotId);

		if (stack != null && stack.getItem() instanceof PacketHandler) {
			MCPacket mcPacket = new MCPacket(data);
			mcPacket.setID(data.readInt());
			((PacketHandler) stack.getItem()).read(mcPacket);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		ItemStack stack = player.inventory.getStackInSlot(this.slotId);

		if (stack != null && stack.getItem() instanceof PacketHandler) {
			MCPacket mcPacket = new MCPacket(data);
			mcPacket.setID(data.readInt());
			((PacketHandler) stack.getItem()).read(mcPacket);
		}
	}
}
