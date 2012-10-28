package JSFGoblins;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.game.api.util.Random;

public class JSFGoblins extends ActiveScript {

	@Override
	public int loop() {
		return Random.nextInt(15, 20);
	}

}
