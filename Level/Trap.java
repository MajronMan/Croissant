package Croissant.Level;

import Croissant.Abstract.IInteractable;
import Croissant.Items.Stat;

public class Trap extends Pavement implements IInteractable {

	private Stat Damage;
	private String Type;
	public void Interact(){
		throw new UnsupportedOperationException();
	}

}