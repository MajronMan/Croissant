package Level;

import Abstract.Interfaces.IContainer;
import Items.Item;

public class Chest implements IContainer {

	private Item Content;

	public void Loot(){
        throw new UnsupportedOperationException();
    }

    public void RandomizeContent(){
        throw new UnsupportedOperationException();
    }

}