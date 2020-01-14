package RedBlack;

import Util.RBNodeInterface;
import java.util.ArrayList;
import java.util.List;

import RedBlack.Person;
public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {
	int colour;
	T key;
	public E object;
	ArrayList<E> arr = new ArrayList<E>();
	RedBlackNode parent, right , left;
	
	RedBlackNode(){
		left= null;	
		right=null;
		parent=null;
		colour=1;          //1 for black
		}
    @Override
    public E getValue() {
        return object;
    }

    @Override
    public List<E> getValues() {
        return arr;
    }
}
