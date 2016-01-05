package org.isep.rottencave.GameEnvironement;

import java.util.LinkedList;

import org.isep.matrice.Matrice;
import org.isep.matrice.MatriceCell;

import com.badlogic.gdx.math.Vector2;

public class PathFinding extends Thread {

	private Character monsterCharacter;
	private int monsterI;
	private int monsterJ;

	private int playerI;
	private int playerJ;

	private MatriceCell[][] tab;
	private Character playerCharacter;

	public PathFinding(Character monsterCharacter, Character playerCharacter, Matrice matriceMap) {
		this.monsterCharacter = monsterCharacter;
		this.playerCharacter = playerCharacter;
		tab = matriceMap.matrice;
		

	}

	@Override
	public void run() {
		while (true) {
			PathNode[][] matricePath = new PathNode[tab.length][tab[0].length];
			LinkedList<PathNode> queue = new LinkedList<PathNode>();
			
			Vector2 monsterPos = this.monsterCharacter.getBody().getPosition();
			Vector2 playerPos = playerCharacter.getBody().getPosition();
			
			monsterI = (int) (monsterPos.x / BlockMap.BLOCK_SIZE);
			monsterJ = (int) (monsterPos.y / BlockMap.BLOCK_SIZE);

			playerI = (int) (playerPos.x / BlockMap.BLOCK_SIZE);
			playerJ = (int) (playerPos.y / BlockMap.BLOCK_SIZE);
			
			PathNode origPathNode = new PathNode(playerI, playerJ, null);
			queue.add(origPathNode);
			matricePath[playerI][playerJ] = origPathNode;
			while (!queue.isEmpty()) {
				PathNode curNode = queue.pop();

				if (curNode.getI() == monsterI && curNode.getJ() == monsterJ) {
					monsterCharacter.setPathToPlayer(curNode);
					queue.clear();
					break;
				}

				int curI = curNode.getI();
				int curJ = curNode.getJ();
				PathNode newPathNode;

				if (checkIfOnTab(curI, curJ + 1, tab) && matricePath[curI][curJ + 1] == null) {
					newPathNode = new PathNode(curI, curJ + 1, curNode);
					matricePath[curI][curJ + 1] = newPathNode;
					queue.add(newPathNode);
				}

				if (checkIfOnTab(curI, curJ - 1, tab) && matricePath[curI][curJ - 1] == null) {
					newPathNode = new PathNode(curI, curJ - 1, curNode);
					matricePath[curI][curJ - 1] = newPathNode;
					queue.add(newPathNode);
				}

				if (checkIfOnTab(curI - 1, curJ, tab) && matricePath[curI - 1][curJ] == null) {
					newPathNode = new PathNode(curI - 1, curJ, curNode);
					matricePath[curI - 1][curJ] = newPathNode;
					queue.add(newPathNode);
				}

				if (checkIfOnTab(curI + 1, curJ, tab) && matricePath[curI + 1][curJ] == null) {
					newPathNode = new PathNode(curI + 1, curJ, curNode);
					matricePath[curI + 1][curJ] = newPathNode;
					queue.add(newPathNode);
				}

				// diag
				if (checkIfOnTab(curI - 1, curJ + 1, tab) && matricePath[curI - 1][curJ + 1] == null) {
					newPathNode = new PathNode(curI - 1, curJ + 1, curNode);
					matricePath[curI - 1][curJ + 1] = newPathNode;
					queue.add(newPathNode);
				}

				if (checkIfOnTab(curI - 1, curJ - 1, tab) && matricePath[curI - 1][curJ - 1] == null) {
					newPathNode = new PathNode(curI - 1, curJ - 1, curNode);
					matricePath[curI - 1][curJ - 1] = newPathNode;
					queue.add(newPathNode);
				}

				if (checkIfOnTab(curI + 1, curJ - 1, tab) && matricePath[curI + 1][curJ - 1] == null) {
					newPathNode = new PathNode(curI + 1, curJ - 1, curNode);
					matricePath[curI + 1][curJ - 1] = newPathNode;
					queue.add(newPathNode);
				}

				if (checkIfOnTab(curI + 1, curJ + 1, tab) && matricePath[curI + 1][curJ + 1] == null) {
					newPathNode = new PathNode(curI + 1, curJ + 1, curNode);
					matricePath[curI + 1][curJ + 1] = newPathNode;
					queue.add(newPathNode);
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean checkIfOnTab(int i, int j, MatriceCell[][] tab) {
		if (i < 0 || j < 0 || tab.length <= i || tab[0].length <= j) {
			return false;
		} else if (tab[i][j].getStatus() != 1) {
			return false;
		}
		return true;
	}

}
