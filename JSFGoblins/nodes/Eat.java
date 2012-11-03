package JSFGoblins.nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;

import JSFGoblins.misc.Constants;

public class Eat extends Node {

	/**
	 * If this method returns true, then the execute() is ran.
	 * Here we check for if we need to eat.
	 * This is, if our health is under 60%.
	 */
	@Override
	public boolean activate() {
		int maxHealth = Skills.getLevel(Skills.CONSTITUTION) * 10;
        int currentHealth = Settings.get(1240) >>> 1;
        int currentHealthPercent = (int) (((double) currentHealth / maxHealth) * 100.0);
        if(currentHealthPercent <= 60) {
        	System.out.println("Eat TRUE");
        	return true;
        }
		return false;
	}

	/**
	 * This is the method that actually makes the bot perform actions.
	 * Here we logout if out of food, or we eat if we have food.
	 * Since we've checked if we have to eat before this, it is the correct way to do it.
	 */
	@Override
	public void execute() {
		if(Inventory.contains(Constants.FOOD_IDS)) {
			System.out.println("Trying to eat");
			Inventory.getItem(Constants.FOOD_IDS).getWidgetChild().interact("Eat");
			waitForEat(5000);
		} else {
			System.out.println("No food, logging out");
			Game.logout(true);
		}
	}
	
	/**
	 * waitForEat(long)
	 * This method waits up to 'timeout' milliseconds for an action to occur.
	 * It is especially useful when we're expecting something to happen,
	 * and we want to wait as little as possible.
	 * @param timeout
	 */
	private void waitForEat(long timeout) {
		int foodCount = Inventory.getCount(Constants.FOOD_IDS);
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis() - startTime < timeout) {
			sleep(50);
			if((Inventory.getCount(Constants.FOOD_IDS) != foodCount)) return;
		}
	}

}