package Trie;
import java.util.ArrayList;
import java.util.Collections;



public class Trie<T> implements TrieInterface {
	
	TrieNode root = new TrieNode();
	ArrayList<Integer> counts=new ArrayList<Integer>();
	public boolean isLeaf(TrieNode hi) {
		int j =0;
		while(j<257) {
			if (hi.children[j]!=null) {
				return false;
			}
			else {
				j++;
			}
		}
		return true;
	}
	
	
    @Override
    public boolean delete(String word) {
    	if(search(word)!=null) {
    	Collections.sort(counts);
    	for(int p=0;p<counts.size()-1;p++) {
    		if(counts.get(p)==word.length()) {
    			counts.remove(p);
    			break;
    		}
    	}
    	TrieNode node = root;
    	int index;
    	int i,count;
    	int length = word.length();
    	for(i=0;i<length;i++) {
    		index= word.charAt(i);
    		if(node.children[index]==null) {
    			return false;
    		}
    		else {
    			node=node.children[index];
    		}
    	}
    	node.isEndOfWord=false;
    	while(isLeaf(node) && !(node.isEndOfWord) ){
    		int l = node.value;
    		node.parent.children[l]=null;
    		node=node.parent;
    	}
        return true;
    	}
    	else {
    		System.out.println("ERROR DELETING");
    		return false;
    	}
    }

    
    
    
    
    @Override
    public TrieNode search(String word) {
    	TrieNode chalo = root;
    	int i=0;
    	int index;
        int length = word.length();
        while(i<length) {
        	index = word.charAt(i);
        	if(chalo.children[index]!=null) {
        		chalo=chalo.children[index];
        		i++;
        	}
        	else {
        		break;
        		
        	}
        }
        if (i==length & (chalo.isEndOfWord==true) ) {
        	return chalo;
        }
        else {
        return null;
        }
    }
    
    
    //checked
   
    @Override
    public TrieNode startsWith(String prefix) {
    	int length = prefix.length();
    	TrieNode chalo = root;
    	int index;
    	int i = 0;
    	while(i<length){
    		index = prefix.charAt(i);
    		if(chalo.children[index]==null)
    			return null;
    		else {
    			chalo=chalo.children[index];
    			i++;
    		}
    	}
    	if(i==length) {
    		return chalo;
    	}
    	else {
    		return null;
    	}
    }
    
    
    
    //checked 
    
    @Override
    public void printTrie(TrieNode trieNode) {
    	TrieNode chalo = trieNode;
    	if((chalo.isEndOfWord)) {
    		System.out.println("[Name: "+chalo.hi.name+","+" Phone="+chalo.hi.phone_number+"]");
    	}
    		for(int j = 0; j<257;j++) {
    			if(chalo.children[j]!=null) {
    				printTrie(chalo.children[j]);
    			}
    		}
    }
    
    
    
    //checked
    
    @Override
    public boolean insert(String word, Object value) { 
    	
    	if(search(word)==null) {
    		
          int length = word.length(); 
          counts.add(length);
          int index; 
          TrieNode chalo = root; 
         
          for (int level = 0; level < length; level++) 
          {   
              index = word.charAt(level); 
              if (chalo.children[index] == null) { 
                  chalo.children[index] = new TrieNode(); 
                  chalo.count++;
                  chalo.children[index].parent=chalo;
                  chalo.children[index].value=index;
              }
              chalo = chalo.children[index]; 
          } 
         
          chalo.isEndOfWord = true; 
          chalo.hi = (Person) value;
    	return true;
    	}
    	else {
    		return false;
    	}

    }
    //checked
    
    
    ArrayList<Character> arrli = new ArrayList<Character>();
    public void printNode(TrieNode node) {
    	for(int i=0 ;i<257;i++) {
    		if(node.children[i]!=null) {
    			arrli.add(((char)i));
    		}
    	}
    }
    

    

    public void print1(TrieNode hello,int level) {
    	TrieNode chalo = hello;
    	int i = level;
    	if(i==1) {
    		printNode(chalo);
    	}
    	else {
    	for(int k=0;k<257;k++) {
    		if(chalo.children[k]!=null) {
    			if(!(isLeaf(chalo)))
    			print1(chalo.children[k],i-1);
    			else
    				print1(chalo,i-1);
    		}
    	}
    	}
    }
    
	@Override
	public void printLevel(int level) {
		// TODO Auto-generated method stub
		print1(root,level);
		Collections.sort(arrli);
		System.out.print("Level "+level+ ": ");
		for(int i=0;i<arrli.size()-1;i++) {
			if((arrli.get(i))!= ((char)32)) {
			Character line = arrli.get(i);
			System.out.print(line + ",");
			}
		}
		System.out.println(arrli.get(arrli.size()-1));
		arrli = new ArrayList<Character>();
	}  
	//checked


    @Override
    public void print() {
    	Collections.sort(counts);
    	int y =counts.size();
    	int l = counts.get(y-1);
    	System.out.println("-------------");
    	for (int j=1;j<l+1;j++) {
    		printLevel(j);
    	}
    	System.out.println("Level "+(l+1)+ ": ");
    	System.out.println("-------------");
    }


}