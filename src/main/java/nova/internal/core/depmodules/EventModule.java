package nova.internal.core.depmodules;

import nova.core.event.bus.GlobalEvents;
import se.jbee.inject.bind.BinderModule;
import se.jbee.inject.util.Scoped;

public class EventModule extends BinderModule {

	@Override
	protected void declare() {
		per(Scoped.APPLICATION).bind(GlobalEvents.class).toConstructor();
	}

}
