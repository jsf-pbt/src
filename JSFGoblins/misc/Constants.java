package JSFGoblins.misc;

import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;

public class Constants {
	
	/*
	 * This is the area in which goblins appear in Lumbridge.
	 * If we are inside this area then we attack goblins.
	 * If not, AND there are no goblins in screen, we will walk to this area.
	 */
	public static final Area GOBLIN_AREA = new Area(new Tile[] { new Tile(3241, 3249, 0), new Tile(3253, 3249, 0), new Tile(3253, 3238, 0), 
			new Tile(3244, 3238, 0) });
	
	/*
	 * These are the IDs of goblins.
	 * We will get NPCs with these IDs to attack.
	 */
	public static final int[] GOBLIN_IDS = {11232, 12353, 12352, 12355, 11236, 12357, 11234};
	
	/*
	 * These are the food IDs.
	 * We will eat any of them when our HP is < 60%.
	 */
	public static final int[] FOOD_IDS = {};
}
