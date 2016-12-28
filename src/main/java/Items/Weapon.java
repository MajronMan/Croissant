package Items;

import Abstract.Enums.PlayerBody;

public class Weapon extends Equipment {
	private Stat Damage;
	private String Type;
	public Weapon(){
		myPart = PlayerBody.Arm;
	}

}