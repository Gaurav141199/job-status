package RedBlack;

import RedBlack.Person;
public class RBTree<T extends Comparable, E> implements RBTreeInterface<T, E>  {
	RedBlackNode root = null;
	RedBlackNode nil=null;
	public void insert(T key , E value) {
		RedBlackNode node = new RedBlackNode();
		node.key= key;
		node.object= value;
		
		RedBlackNode temp = root;
        if (root == nil) {
            root = node;
            node.colour = 1;
            node.parent = nil;
        } else {
            node.colour = 0;
            while (true) {
                if ((node.key).compareTo(temp.key)<0) {      //node.key < temp.key
                    if (temp.left == nil) {
                        temp.left = node;
                        node.parent = temp;
                        node.arr.add(value);
                        break;
                    } else {
                        temp = temp.left;
                    }
                    fixTree(node);
                } else if ((node.key).compareTo(temp.key)>0) {
                    if (temp.right == nil) {
                        temp.right = node;
                        node.parent = temp;
                        node.arr.add(value);
                        break;
                    } else {
                        temp = temp.right;
                    }
                    fixTree(node);
                } else if ((node.key).equals(temp.key)) {
                	temp.arr.add(value);
                	return;
                }
            }
            
        }
    }
	
	
	
    private void fixTree(RedBlackNode node) {
    	if(node.parent!=null && node.parent.parent!=nil) {
    		while (node.parent.colour == 0) {			//if colour of node is red 
	        	RedBlackNode uncle = nil;
	        	System.out.println("hi");
	            if (node.parent == node.parent.parent.left) {
	                uncle = node.parent.parent.right;
	                //red uncle
	                if (uncle != null && uncle.colour != 1) { //if colour of uncle is red 
	                    node.parent.colour = 1;
	                    uncle.colour = 1;
	                    node = node.parent.parent;
	                    node.colour = 0;
	                    if(node!=root)
	                    continue;
	                    else {
	                    	node.colour=1;
	                    	return;
	                    }
	                } 
	                if (node == node.parent.right) {
	                    //Double rotation needed
	                	
	                    node = node.parent;
	                    rotateLeft(node);
	                } 

	                node.parent.parent.colour = 0;
	                node.parent.colour = 1;
	                //if the "else if" code hasn't executed, this
	                //is a case where we only need a single rotation 
	                rotateRight(node.parent.parent);
	            } else {
	                uncle = node.parent.parent.left;
	                 if (uncle != null && uncle.colour == 0) {
	                    node.parent.colour = 1;
	                    uncle.colour = 1;
	                    node.parent.parent.colour = 0;
	                    node = node.parent.parent;
	                    if(node!=root)
	                    continue;
	                    else {
	                    	node.colour=1;
	                    	return;
	                    }
	                }
	                if (node == node.parent.left) {
	                    //Double rotation needed
	                    node = node.parent;
	                    rotateRight(node);
	                }
	                node.parent.colour = 1;
	                node.parent.parent.colour = 0;
	                //if the "else if" code hasn't executed, this
	                //is a case where we only need a single rotation
	                rotateLeft(node.parent.parent);
	            }
	        }
	        root.colour = 1;
    }
    }


	void rotateRight(RedBlackNode node) {
        if (node.parent != null) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }

            node.left.parent = node.parent;
            node.parent = node.left;
            if (node.left.right != null) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else {//Need to rotate root
        	RedBlackNode left = root.left;
            root.left = root.left.right;
            if(left.right!=null)left.right.parent = root;
            root.parent = left;
            left.right = root;
            left.parent = null;
            root = left;
        }
    }
    
	void rotateLeft(RedBlackNode node) {
        if (node.parent != null) {
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != null) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {//Need to rotate root
        	RedBlackNode right = root.right;
            root.right = right.left;
            if(right.left !=null)right.left.parent = root;
            root.parent = right;
            right.left = root;
            right.parent = null;
            root = right;
        }
    }
	
    public RedBlackNode<T, E> search(T key) {
    	if(root == null) {
    		return null;
    	}
    	else {
		RedBlackNode current = root;
		RedBlackNode parent = null;
		while(true){
			parent = current;
			T key1 = (T) current.key;
			if (key.equals(key1)) {
				return current;
			}
			else if(key.compareTo(key1)<0){				
				current = current.left;
				if(current==null){
					return null;
				}
			}
			else if(key.compareTo(key1)>0){
				current = current.right;
				if(current==null){
					return null;
				}
			}
		} 
    	}
    }


}