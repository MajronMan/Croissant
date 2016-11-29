package Characters;

import Items.Item;

public class Enemy extends Fighting implements Abstract.IMoving, Abstract.IInteractable, Abstract.IContainer {

	private boolean Asleep;
	private Item Drop;

	public void Flee() {
		// TODO - implement Enemy.Flee
		throw new UnsupportedOperationException();
	}

	@Override
	public void Move() {
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