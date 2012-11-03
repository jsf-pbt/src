package JSFGoblins.nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;

import JSFGoblins.misc.Constants;

public class Attack extends Node {

	/**
	 * Here we check if our player has enough health, is not in combat and is
	 * not moving. If we are moving, it is likely we are moving towards our next
	 * enemy. If we are in combat, we do not want to attack another Goblin. If
	 * we don't have enough HP, then we want to move on to the 'Eat' node.
	 */
	@Override
	public boolean activate() {
		int maxHealth = Skills.getLevel(Skills.CONSTITUTION) * 10;
		int currentHealth = Settings.get(1240) >>> 1;
		int currentHealthPercent = (int) (((double) currentHealth / maxHealth) * 100.0);
		if (currentHealthPercent > 60 && !Players.getLocal().isInCombat()
				&& !Players.getLocal().isMoving()) {
			System.out.println("Attack TRUE");
			return true;
		}
		return false;
	}

	/**
	 * Here we attack a goblin or walk toward a tile that is inside an area
	 * populated by Goblins.
	 */
	@Override
	public void execute() {
		System.out.println("Executing");
		NPC goblin = NPCs.getNearest(Constants.GOBLIN_IDS);
		/*
		 * Is the goblin is alive, not in combat,
		 * in the screen, and we are not in combat or moving, we attack.
		 */
		if (canAttack(goblin)
				&& goblin.isOnScreen()
				&& (Players.getLocal().getAnimation() == -1
						|| !Players.getLocal().isInCombat() || !Players
						.getLocal().isMoving())) {
			goblin.click(true);
			waitForCombat(5000);
			/*
			 * If the goblin is not null (he's in a loaded area)
			 * but is not on the screen, we can attempt to turn the camera
			 * to it and try to see it.
			 */
		} else if (goblin != null && !goblin.isOnScreen()) {
			Camera.turnTo(goblin.getLocation());
		} else {
			/*
			 * If our player is not moving, we try to walk towards the area.
			 */
			if (!Players.getLocal().isMoving()) {
				Walking.walk(new Tile(Random.nextInt(3246, 3250), Random
						.nextInt(3240, 3244), 0));
				waitForWalking(5000);
				/*
				 * If our player IS moving, we just wait until we have stopped.
				 */
			} else {
				waitToStop(5000);
			}
		}
	}

	/**
	 * This method checks for if the NPC is alive and not interacting with anyone.
	 * @param n The NPC to check for.
	 * @return true if the NPC is attackable, false otherwise.
	 */
	private final boolean canAttack(NPC n) {
		if (n != null) {
			if (n.getHpRatio() > 0 && n.getInteracting() == null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Method to wait until we are in combat, up to 'timeout' millis.
	 * @param timeout The maximum wait time
	 */
	private final void waitForCombat(long timeout) {
		long start = System.currentTimeMillis();
		while ((System.currentTimeMillis() - start) <= timeout) {
			sleep(50);
			if (Players.getLocal().isInCombat())
				break;
		}
	}

	/**
	 * Method to wait until we are moving, up to 'timeout' millis.
	 * @param timeout The maximum wait time
	 */
	private final void waitForWalking(long timeout) {
		long start = System.currentTimeMillis();
		while ((System.currentTimeMillis() - start) <= timeout) {
			sleep(50);
			if (Players.getLocal().isMoving())
				break;
		}
	}

	/**
	 * Method to wait until we are no longer moving, up to 'timeout' millis.
	 * @param timeout The maximum wait time
	 */
	private final void waitToStop(long timeout) {
		long start = System.currentTimeMillis();
		while ((System.currentTimeMillis() - start) <= timeout) {
			sleep(50);
			if (!Players.getLocal().isMoving())
				break;
		}
	}
}