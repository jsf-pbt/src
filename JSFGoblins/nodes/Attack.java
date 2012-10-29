package JSFGoblins.nodes;

import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Settings;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;

import JSFGoblins.misc.Constants;

public class Attack extends Node {

	@Override
	public boolean activate() {
		int maxHealth = Skills.getLevel(Skills.CONSTITUTION) * 10;
		int currentHealth = Settings.get(1240) >>> 1;
		int currentHealthPercent = (int) (((double) currentHealth / maxHealth) * 100.0);
		if (currentHealthPercent > 60 && !Players.getLocal().isInCombat() && !Players.getLocal().isMoving()) {
			System.out.println("Attack TRUE");
			return true;
		}
		return false;
	}

	@Override
	public void execute() {
		System.out.println("Executing");
		NPC goblin = NPCs.getNearest(Constants.GOBLIN_IDS);
		if (canAttack(goblin)
				&& goblin.isOnScreen()
				&& (Players.getLocal().getAnimation() == -1
						|| !Players.getLocal().isInCombat() || !Players
						.getLocal().isMoving())) {
			goblin.click(true);
			waitForCombat(5000);
		} else if (goblin != null && !goblin.isOnScreen()) {
			Camera.turnTo(goblin.getLocation());
		} else {
			Walking.walk(new Tile(Random.nextInt(3246, 3250), Random.nextInt(3240, 3244), 0));
		}
	}

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
	
	private final void waitForCombat(long timeout) {
		long start = System.currentTimeMillis();
		while((System.currentTimeMillis() - start) <= timeout) {
			sleep(50);
			if(Players.getLocal().isInCombat()) break;
		}
	}
}
