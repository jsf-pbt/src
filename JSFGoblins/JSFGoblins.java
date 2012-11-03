package JSFGoblins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.util.Random;

import JSFGoblins.nodes.Attack;
import JSFGoblins.nodes.Eat;

@Manifest(name ="JSFGoblins", authors={"JSF"})
public class JSFGoblins extends ActiveScript {
	
	private final List<Node> jobs_collection = Collections.synchronizedList(new ArrayList<Node>());
	private Tree job_container = null;
	
	@Override
	public int loop() {
		if (job_container != null) {
			final Node job = job_container.state();
			if (job != null) {
				job_container.set(job);
				getContainer().submit(job);
				job.join();
			}
		}
		return Random.nextInt(15, 50);
	}
	
	public final void provide(final Node... jobs) {
		for (final Node job : jobs)
			if (!jobs_collection.contains(job))
				jobs_collection.add(job);
		job_container = new Tree(jobs_collection.toArray(new Node[jobs_collection.size()]));
	}
	
	@Override
	public void onStart() {
		System.out.println("Starting...");
		provide(new Attack(), new Eat());
	}

}