package org.isep.rottencave.GameEnvironement;

public class PathNode {
	private int i, j;
	private PathNode parent;

	public PathNode(int i, int j, PathNode parent) {
		this.i = i;
		this.j = j;
		this.parent = parent;
	}

	public PathNode getParent() {
		return parent;
	}
	
	public int getI(){
		return this.i;
	}
	
	public int getJ(){
		return this.j;
	}
	
}
