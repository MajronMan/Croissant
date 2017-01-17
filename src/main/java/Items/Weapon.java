package Items;

import Abstract.Enums.PlayerBody;
import Engine.Stat;

public class Weapon extends Equipment {
	private Stat Damage;
	private String Type;
	public Weapon(){
		myPart = PlayerBody.Arm;
	}

}