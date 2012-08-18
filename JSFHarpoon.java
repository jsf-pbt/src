import org.powerbot.concurrent.Task;
import org.powerbot.concurrent.strategy.Strategy;
import org.powerbot.game.api.ActiveScript;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.wrappers.Tile;


@Manifest(authors = {"JSF"}, name = "JSFHarpoon", version = 0.1)
public class JSFHarpoon extends ActiveScript {

	private static final int GERRANT = 0;
	private static final int HARPOON = 0;
	private static final Tile[] PATH = {new Tile(0, 0, 0)};
	private static final int PARENT = 0;
	private static final int CHILD = 0;
	private static final int HARPOON_INVENTORY_COST = 1215;
	private static final int COINS = 0;
	@Override
	public void setup() {
		provide(new MainHarpoon());
	}

	private class MainHarpoon extends Strategy implements Task {
		@Override
		public void run() {
			if(Inventory.getCount(COINS) > HARPOON_INVENTORY_COST);
			if(Inventory.isFull()) {
				walkToBank();
			} else if(!Inventory.isFull() && !inShop()){
				walkToShop();
			} else if(!Inventory.isFull() && inShop()) {
				buyHarpoons();
			}
		}

		@Override
		public boolean validate() {

			return false;
		}
	}

}
