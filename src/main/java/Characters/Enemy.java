package Characters;

import Abstract.Interfaces.IContainer;
import Items.Item;

public class Enemy extends Fighting implements IContainer {

	private boolean asleep;
	private Item drop;

	public void Flee() {
		// TODO - implement Enemy.Flee
		throw new UnsupportedOperationException();
	}

	@Override
	public void Move(String dir) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void Loot() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void RandomizeContent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void Interact() {
		throw new UnsupportedOperationException();
	}
}