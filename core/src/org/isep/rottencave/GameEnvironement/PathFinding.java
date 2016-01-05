package org.isep.rottencave.GameEnvironement;

import org.isep.matrice.Matrice;
import org.isep.matrice.MatriceCell;

public class PathFinding extends Thread{

	private Character monsterCharacter;
	private Character playerCharacter;
	private Matrice matriceMap;

	public PathFinding(Character monsterCharacter, Character playerCharacter, Matrice matriceMap) {
		this.monsterCharacter = monsterCharacter;
		this.playerCharacter = playerCharacter;
		this.matriceMap = matriceMap;
	}
	
	@Override
	public void run() {
		MatriceCell[][] tab = matriceMap.matrice;
		
		
	}

}
