package minHeap;
import java.util.ArrayList;
import java.util.HashMap;

public class MinHeap {

	private static void swap(ArrayList<Integer> inputList, int pos1, int pos2){
		Integer temp = inputList.get(pos1);
		inputList.set(pos1, inputList.get(pos2));
		inputList.set(pos2, temp);
	}

	private static void privateHeapifyList(ArrayList<Integer> inputList){
		int n = inputList.size();
		// key point is start position is floor of size /2
		int startPos = n/2;

		for(int i = startPos; i >=0; i--){
			if(i*2+2 < n && (inputList.get(i) > inputList.get(i*2+1) || inputList.get(i) > inputList.get(i*2+2))){

				Integer diff = inputList.get(i*2+1) - inputList.get(i*2+2);
				if(diff > 0){
					swap(inputList, i, i*2+2);
				}else{
					swap(inputList, i, i*2+1);
				}
			}else if(i*2+2 >=n && i*2+1 < n && inputList.get(i) > inputList.get(i*2+1)){
				swap(inputList, i, i*2+1);
			}
		}
		if(isHeapified(inputList)) return;
		privateHeapifyList(inputList);
	}

	private static boolean isHeapified(ArrayList<Integer> inputList){
		int n = inputList.size();
		int startPos = n/2;

		for(int i = startPos; i >=0; i--){
			// one child
			if(i*2+2 >=n && i*2+1 <n && inputList.get(i) > inputList.get(i*2+1)) return false;
			// two children
			if(i*2+2 < n && (inputList.get(i) > inputList.get(i*2+1) || inputList.get(i) > inputList.get(i*2+2))) return false;
		}
		return true;
	}

	public static void HeapifyList(ArrayList<Integer> inputList) {
		System.out.println("Original list was " +inputList);
		if(inputList.contains(null)){
			throw new Error("Array cannot contain null");
		}else{
			privateHeapifyList(inputList);
			System.out.println("After heapifying, the list is " +inputList);
		}
	}
	
	private ArrayList<Integer> minHeap;
	private int size;
	private HashMap<Integer, Integer> cacheMap; 

	public MinHeap(){
		minHeap = new ArrayList<Integer>();
		size =0;
		cacheMap = new HashMap<Integer, Integer>();
	}
	
	public void insert(int value){
		addNewElement(value);
	}
	
	public void push(int value){
		addNewElement(value);
	}

	private void heapify(ArrayList<Integer> minHeap, int oldPos, int newPos, int value){
		swap(minHeap, oldPos, newPos);
		// update parent value position after swap. it will be the position where new value was at
		cacheMap.put(minHeap.get(oldPos), oldPos);

		// update new value positin in hash map. it will be the position where parent value was at
		cacheMap.put(value, newPos);
	}

	private void addNewElement(int value){
		if(size ==0){
			minHeap.add(value);
			size++;
			cacheMap.put(value, 0);
		}else{

			minHeap.add(value);
			size++;

			int newValuePos = size -1;
			cacheMap.put(value, newValuePos);

			while(needToHeapifyUp(value, newValuePos)){
				
				if(isLeftChild(newValuePos)){
					// value is at left child position
					// heapify up
					heapify(minHeap, newValuePos, (newValuePos -1)/2, value);
					newValuePos = (newValuePos -1)/2;

				}else{
					// value is at right child position
					// heapify up
					heapify(minHeap, newValuePos, (newValuePos -2)/2, value);
					newValuePos = (newValuePos -2)/2;
				}
			}

		}
	}

	public int removeValue (int value){
		if(cacheMap.get(value) == null){
			throw new Error("Input value does not exist");
		}
		return privateRemoveValue(value);
	}
	
	private int privateRemoveValue(int value){
		int valueToRemovePos = cacheMap.get(value);
		int oldLastElement = minHeap.get(size-1);
		// swap with the last element
		swap(minHeap, valueToRemovePos, size-1);
		// update last element new position in hash map
		cacheMap.put(oldLastElement, valueToRemovePos);
		cacheMap.remove(value);
	
		int oldValue = minHeap.get(size-1);
		minHeap.remove(size-1);
		size--;

		// swap min heap is not valid
		// if has two children, swap with smaller child
		while(needToHeapifyDown(oldLastElement, valueToRemovePos)){
			
			if(hasTwoChildren(oldLastElement)){
				
				int diff = minHeap.get(valueToRemovePos*2+1) -minHeap.get(valueToRemovePos*2+2);
				if(diff > 0){
					// heapify down
					heapify(minHeap, valueToRemovePos, valueToRemovePos*2+2, oldLastElement);
					valueToRemovePos = valueToRemovePos*2+2;
				}else{
					// heapify down
					heapify(minHeap, valueToRemovePos, valueToRemovePos*2+1, oldLastElement);
					valueToRemovePos = valueToRemovePos*2+1;
				}
			}else if(hasOneChild(oldLastElement)){
				// heapify down
				heapify(minHeap, valueToRemovePos, valueToRemovePos*2+1, oldLastElement);
				valueToRemovePos = valueToRemovePos*2+2;
			}
		}
		return oldValue;
	}

	private boolean hasOneChild(int num){
		if(cacheMap.get(num) == null) return false;
		int numPos = cacheMap.get(num);
		if(numPos*2+1 == size-1) return true;
		return false;
	}
	private boolean hasTwoChildren(int num){
		if(cacheMap.get(num) == null) return false;
		int numPos = cacheMap.get(num);
		if(numPos*2+2 <= size-1) return true;
		return false;
	}

	private boolean needToHeapifyDown(int num, int numPos){
		if(hasOneChild(num) && num > minHeap.get(numPos*2+1)){
			return true;
		}else if(hasTwoChildren(num) && (num > minHeap.get(numPos*2+1) || num > minHeap.get(numPos*2+2))){
			return true;
		}else{
			return false;
		}
	}

	private boolean isLeftChild(int position){
		if(position %2 == 1) return true;
		return false;
	}

	private boolean needToHeapifyUp(int num, int numPos){
		if(numPos <= 0) return false;
		if(isLeftChild(numPos) && num < minHeap.get((numPos-1)/2)){
			return true;
		}else if(!isLeftChild(numPos) && num < minHeap.get((numPos-2)/2)){
			return true;
		}
		return false;
	}


	
	public void display(){
		System.out.println("The minHeap is " + minHeap);
		// System.out.println("cache map is " + cacheMap);
	}

	
}
