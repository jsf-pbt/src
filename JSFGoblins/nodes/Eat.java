package JSFGoblins.nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;

import JSFGoblins.misc.Constants;

public class Eat extends Node {

	@Override
	public boolean activate() {
		int maxHealth = Skills.getLevel(Skills.CONSTITUTION) * 10;
        int currentHealth = Settings.get(1240) >>> 1;
        int currentHealthPercent = (int) (((double) currentHealth / maxHealth) * 100.0);
        if(currentHealthPercent <= 60) {
        	return true;
        }
		return false;
	}

	@Override
	public void execute() {
		if(Inventory.contains(Constants.FOOD_IDS)) {
			Inventory.selectItem(Inventory.getItem(Constants.FOOD_IDS));
		} else {
			Game.logout(true);
		}
	}

}