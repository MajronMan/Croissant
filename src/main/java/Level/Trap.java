package Level;

import Abstract.IInteractable;
import Items.Stat;

public class Trap extends Pavement implements IInteractable {

	private Stat Damage;
	private String Type;
	public void Interact(){
		throw new UnsupportedOperationException();
	}

}