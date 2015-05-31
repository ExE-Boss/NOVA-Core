package nova.wrapper.mc1710.launcher;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import nova.core.game.Game;
import nova.core.item.ItemDictionary;
import nova.wrapper.mc1710.wrapper.item.ItemConverter;

/**
 * Created by Stan on 8/02/2015.
 */
public class ForgeEventHandler {
	@SubscribeEvent
	public void onOreRegister(OreDictionary.OreRegisterEvent event) {
		ItemDictionary novaItemDictionary = Game.instance().itemDictionary();

		String id = ItemConverter.instance().getNovaItem(event.Ore).getID();
		if (!novaItemDictionary.get(event.Name).contains(id))
			novaItemDictionary.add(event.Name, id);
	}
}
