/*
 * Copyright (c) 2015 NOVA, All rights reserved.
 * This library is free software, licensed under GNU Lesser General Public License version 3
 *
 * This file is part of NOVA.
 *
 * NOVA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NOVA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NOVA.  If not, see <http://www.gnu.org/licenses/>.
 */

package nova.core.wrapper.mc.forge.v18.launcher;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import nova.core.event.PlayerEvent;
import nova.internal.core.Game;

/**
 * Handles FML events and forwards them to NOVA.
 * @author Calclavia
 */
public class FMLEventHandler {
	@SubscribeEvent
	public void playerJoin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent evt) {
		Game.events().publish(new PlayerEvent.Join(Game.natives().toNova(evt.player)));
	}

	@SubscribeEvent
	public void playerLeave(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent evt) {
		Game.events().publish(new PlayerEvent.Leave(Game.natives().toNova(evt.player)));
	}

	@SubscribeEvent
	public void tickEnd(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Game.syncTicker().update();
		}
	}

	@SubscribeEvent
	public void tickEnd(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Game.syncTicker().update();
		}
	}
}
