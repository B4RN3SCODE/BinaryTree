/**
 * BinaryTree
 * Generates a character tree based on
 * values in a phrase, and then associates
 * binary values for the leafs
 *
 * @author		Tyler J Barnes
 * @contact 	b4rn3scode@gmail.com
 * @version		1
 *
 */
import java.util.*;
import java.lang.StringBuilder;
import Models.Node;

public class BinaryTree {


	/* Properties */

	// phrase or data to build tree and eventually compress
	public String _phrase;
	// list of nodes that will be used
	public PriorityQueue<Node> _nodes = new PriorityQueue();
	// root node
	public Node _root;




	/*
	 * ctor
	 */
	public BinaryTree() {
		this._root = new Node();
	}


	/*
	 * ctor
	 * @param phrase String phrase to use
	 * @return void
	 */
	public BinaryTree(String phrase) {
		this._phrase = phrase;
		this._root = new Node();
		this.getPhraseLeafs();
	}




	/*
	 * getPhraseLeafs
	 * Gets the 'leaf' nodes which are the unique characters
	 * and their frequency based on the phrase
	 *
	 * @return void
	 */
	public void getPhraseLeafs() {
		for(int i = 0; i < this._phrase.length(); i++) {

			char tmp = this._phrase.charAt(i);
			if(!this.inLeafs(tmp)) {
				this._nodes.add(new Node(tmp, this.charFreq(tmp)));
			}

		}


	}



	/*
	 * charFreq
	 * Determines how often char found
	 *
	 * @param c char character to count
	 * @return cnt int count
	 */
	public int charFreq(char c) {
		int cnt = 0;
		for(int i = 0; i < this._phrase.length(); i++) {
			if(this._phrase.charAt(i) == c) {
				cnt++;
			}
		}
		return cnt;
	}





	/*
	 * inLeafs
	 * Determines if character was already recorded
	 *
	 * @param c char character to search for
	 * @return true if already in nodes
	 */
	public boolean inLeafs(char c) {
		for(Node l : this._nodes) {
			if(l._char == c) {
				return true;
			}
		}
		return false;
	}





	/*
	 * buildTree
	 * Builds the tree
	 *
	 * @return void
	 */
	public void buildTree() {
		while(this._nodes.size() > 1) {
			Node cl = this._nodes.poll();
			Node cr = this._nodes.poll();
			Node pt = new Node('_', 0, cl, cr, null);
			pt._value = cl._value + cr._value;
			cl._parent = cr._parent = pt;
			this._nodes.offer(pt);
		}
		this._root = this._nodes.peek();
	}





	/*
	 * not done yet
	 * 	TODO
	 * 		finish
	 */
	public String getTree(Node focus) {
		String s = "";
		if(focus != null) {
			s = focus.toString() + "\n";
			s += getTree(focus._leftChild);
			s += getTree(focus._rightChild);
		}
		return s;
	}





	/*
	 * searchNode
	 * Searches nodes for a value
	 *
	 * @param n Node to start or focus at
	 * @param c char character to search for
	 * @return Node containing value
	 */
	public Node searchNode(Node n, char c) {

		Node result = null;

		if(n != null) {

			if(n._char == c) {
				return n;
			}

			result = this.searchNode(n._leftChild, c);

			if(result == null) {
				result = this.searchNode(n._rightChild, c);
			}
		}

		return result;
	}






	/*
	 * mapPath
	 * Maps path with 1s or 0s from root node to
	 * node containing char
	 *
	 * @param n Node to map to
	 * @return StringBuilder string of map
	 */
	public StringBuilder mapPath(Node n) {
		if(n == null) { return new StringBuilder(1); }

		StringBuilder sb = new StringBuilder(256);
		if(n == this._root || n._parent == null) {
			sb.insert(0, "0");
			return sb;
		}
		Node p = n._parent;
		while(p != null) {
			if(p._leftChild == n) {
				sb.insert(0, "0");
			} else {
				if(p._rightChild == n) {
					sb.insert(0, "1");
				}
			}
			n = p;
			p = p._parent;
		}

		return sb;

	}




	/*
	 * encodeString
	 * Encodes a string with appropriate binary value
	 *
	 * @param s String to encode
	 * @returns encoded String(Builder)
	 */
	public StringBuilder encodeString(String s) {
		StringBuilder sb = new StringBuilder(s.length() * 10);
		char[] st = s.toCharArray();
		for(char stt : st) {
			sb.append(this.mapPath(this.searchNode(this._root, stt)));
		}
		return sb;
	}





    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		BinaryTree bt = new BinaryTree(args[0]);
		bt.buildTree();
		//System.out.println(bt.searchNode(bt._root, args[1].toCharArray()[0]));
		System.out.println(bt.encodeString(args[1]));
    }

}
