package Trie;


import Util.NodeInterface;


public class TrieNode<T> implements NodeInterface<T> {
	TrieNode children[] = new TrieNode[257];
	TrieNode parent;
	Person hi;
	Object h;
	int value;
	boolean isEndOfWord;
	public int count;
    TrieNode(){ 
    	count=0;
        isEndOfWord = false; 
        for (int i = 0; i < 257; i++) 
            children[i] = null; 
    }
    
    public T getValue() {
        return (T)("[Name: "+((Person)hi).name+","+" Phone="+((Person)hi).phone_number+"]");
    }
}