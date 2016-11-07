package Croissant.Characters;

import Croissant.Abstract.IContainer;
import Croissant.Abstract.IInteractable;
import Croissant.Abstract.IMoving;
import Croissant.Items.Item;

public class Enemy extends Fighting implements IMoving, IInteractable, IContainer {

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