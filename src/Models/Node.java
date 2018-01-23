package Models;

import java.util.*;

public class Node implements Comparable<Node>{

	public char _char;
	public int _value;
	public Node _leftChild;
	public Node _rightChild;
	public Node _parent;




	public Node() {

	}


	public Node(char c, int v) {
		this._char = c;
		this._value = v;
	}


	public Node(char c, int v, Node lf, Node rt, Node p) {
		this._char = c;
		this._value = v;
		this._leftChild = lf;
		this._rightChild = rt;
		this._parent = p;
	}


	@Override
	public String toString() {
		String s = this._char + " - " + this._value + "[parent=" + this._parent + "]";
		return s;
	}


	@Override
	public int compareTo(Node n) {
		return this._value - n._value;
	}
}
