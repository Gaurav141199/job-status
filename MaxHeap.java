package PriorityQueue;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable> implements PriorityQueueInterface<T> {
	
	public ArrayList<ArrayList<T>> Heap ;
	
	public MaxHeap() { 
	        Heap = new ArrayList<ArrayList<T>>();
	        Heap.add(null);
	}
	
    private void swap(int pos, int pos1) 
    { 
        ArrayList<T> temp; 
        temp = Heap.get(pos); 
        Heap.set(pos, Heap.get(pos1));
        Heap.set(pos1, temp); 
    }
    
    public void insert(T element) {

    	int size1=Heap.size();
    	for (int i =1 ; i<size1;i++){
    		if(Heap.get(i).size() > 0 && element.compareTo(Heap.get(i).get(0))==0){
    			Heap.get(i).add(element);
    			sort(Heap.get(i));
    			return;
    		}
    	}
    	ArrayList<T> list = new ArrayList<T>();
    	list.add(element);
    	Heap.add(list);
    	int pos = Heap.size()-1;
    	if(pos>1){
    		while(Heap.get(pos/2).size() > 0 && element.compareTo((Heap.get(pos/2)).get(0))>0){
    			swap(pos,pos/2);
    			pos = pos/2;
    			if(pos ==1)break;
    		}
    	}
    }
    
    public void sort(ArrayList<T> list1) {
    	int size= list1.size();
    	for(int i=0; i<size;i++) {
    		for(int j=i+1; j<size;j++) {
    			if(list1.get(i).compareTo(list1.get(j))<0) {
    				T temp  =list1.get(i);
    				list1.set(i, list1.get(j));
    				list1.set(j, temp);
    			}
    		}
    	}
    }
    private void Heapify(int pos)
    {		int size=Heap.size()-1;
        	if(2*pos + 1 <= size) {
	            if ((( Heap.get(2*pos).size() > 0 && Heap.get(pos).size() > 0  && Heap.get(pos).get(0).compareTo(Heap.get(2*pos).get(0))<0 )  ||  (Heap.get(2*pos+1).size() > 0 && Heap.get(pos).size() > 0  &&   Heap.get(pos).get(0).compareTo(Heap.get(2*pos+1).get(0))<0)))
	            {
	                if (Heap.get(2*pos).size() > 0 && Heap.get(2*pos+1).size() > 0 &&   Heap.get(2*pos+1).get(0).compareTo(Heap.get(2*pos).get(0))<0)
	                {
	                    swap(pos, 2*pos);
	                    Heapify(2*pos);
	                }else
	                {
	                    swap(pos, 2*pos+1);
	                    Heapify(2*pos+1);
	                }
	            }
        	}
        	else if(2*pos == size) {
        		if(Heap.get(2*pos).size() > 0 && Heap.get(pos).size() > 0 && Heap.get(2*pos).get(0).compareTo(Heap.get(pos).get(0) )> 0)
        			swap(pos, 2*pos);
        	}
        	
        
    }
    

    @Override
    public T extractMax() {
    	for (int j=1 ; j<Heap.size();j++) {
    		if(Heap.get(j).size()==0)
    			Heap.remove(j);
    	}
    	if(Heap.size()>=2) {
	    	if (Heap.get(1).size()>1) {
	    		T max = Heap.get(1).get(0);
	    		Heap.get(1).remove(0);
	    		return max;
	    	}
	    	else {
//	    		T popped = null;
//	    		if(Heap.get(1).size()>0 && Heap.get(1).get(0)!=null)
		    	T popped = Heap.get(1).get(0);
	    		Heap.set(1, Heap.get(Heap.size()-1));
		        int x =Heap.size()-1;
		        Heap.remove(x);
		        if(Heap.size()>1)
		        Heapify(1);
		        return popped;
	    	}
    	}
    	return null;
    }

}