package nova.core.command;

import nova.core.entity.component.Player;
import nova.internal.core.Game;
import nova.internal.core.launch.NovaLauncher;
import nova.wrappertests.NovaLauncherTestFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static nova.testutils.NovaAssertions.assertThat;

/**
 * @author Calclavia
 */
public class CommandTest {

	@Before
	public void setUp() throws Exception {
		NovaLauncherTestFactory novaLauncherTestFactory = new NovaLauncherTestFactory();
		NovaLauncher launcher = novaLauncherTestFactory.createLauncher();

		Game.commands().register(new ExplodeCommand());
	}

	@Test
	public void testCommandValidation() throws Exception {
		Optional<Command> explode = Game.commands().get("explode");
		assertThat(explode).isPresent();

		Command command = explode.get();
		command.handle(null, new String[] { "radius:5" });
	}

	public static class ExplodeCommand extends Command {
		@Override
		public void handle(Player player, String... params) throws CommandException {
			if (params.length == 1) {
				if (params[0] != null && params[0].contains("radius")) {
					return;
				}
			}

			throw new CommandException("Invalid command parameters!");
		}

		@Override
		public String getID() {
			return "explode";
		}
	}
}
