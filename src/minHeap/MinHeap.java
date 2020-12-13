package minHeap;
import java.util.ArrayList;
import java.util.HashMap;

public class MinHeap {

	// min heapify the give ArrayList
	public static void minHeapifyList(ArrayList<Integer> inputList) {
		System.out.println("Original list was " +inputList);
		if(inputList.contains(null)){
			throw new Error("Array cannot contain null");
		}else{
			int listSize = inputList.size();
			privateMinHeapifyList(inputList, listSize);
			System.out.println("After heapifying, the list is " +inputList);
		}
	}

	private static void privateMinHeapifyList(ArrayList<Integer> inputList, int listSize){
		// key point is start position is floor of size /2
		int startPos = listSize/2;

		for(int i = startPos; i >=0; i--){
			if(i*2+2 < listSize && (inputList.get(i) > inputList.get(i*2+1) || inputList.get(i) > inputList.get(i*2+2))){

				Integer diff = inputList.get(i*2+1) - inputList.get(i*2+2);
				if(diff > 0){
					swap(inputList, i, i*2+2);
				}else{
					swap(inputList, i, i*2+1);
				}
			}else if(i*2+2 >=listSize && i*2+1 < listSize && inputList.get(i) > inputList.get(i*2+1)){
				swap(inputList, i, i*2+1);
			}
		}
		if(isMinHeapified(inputList, listSize)) return;
		privateMinHeapifyList(inputList, listSize);
	}

	private static boolean isMinHeapified(ArrayList<Integer> inputList, int listSize){
		int startPos = listSize/2;

		for(int i = startPos; i >=0; i--){
			// one child
			if(i*2+2 >=listSize && i*2+1 <listSize && inputList.get(i) > inputList.get(i*2+1)) return false;
			// two children
			if(i*2+2 < listSize && (inputList.get(i) > inputList.get(i*2+1) || inputList.get(i) > inputList.get(i*2+2))) return false;
		}
		return true;
	}
	
	


	// max heapify the give ArrayList
	public static void maxHeapifyList(ArrayList<Integer> inputList) {
		System.out.println("Original list was " +inputList);
		if(inputList.contains(null)){
			throw new Error("Array cannot contain null");
		}else{
			int listSize = inputList.size();
			privateMaxHeapifyList(inputList, listSize);
			System.out.println("After heapifying, the list is " +inputList);
		}
	}

	private static void privateMaxHeapifyList(ArrayList<Integer> inputList, int listSize){
		// key point is start position is floor of size /2
		int startPos = listSize/2;

		for(int i = startPos; i >=0; i--){
			if(i*2+2 < listSize && (inputList.get(i) < inputList.get(i*2+1) || inputList.get(i) < inputList.get(i*2+2))){

				Integer diff = inputList.get(i*2+1) - inputList.get(i*2+2);
				if(diff >= 0){
					swap(inputList, i, i*2+1);
				}else{
					swap(inputList, i, i*2+2);
				}
			}else if(i*2+2 >=listSize && i*2+1 < listSize && inputList.get(i) < inputList.get(i*2+1)){
				swap(inputList, i, i*2+1);
			}
		}
		if(isMaxHeapified(inputList, listSize)) return;
		privateMaxHeapifyList(inputList, listSize);
	}

	private static boolean isMaxHeapified(ArrayList<Integer> inputList, int listSize){
		int startPos = listSize/2;

		for(int i = startPos; i >=0; i--){
			// one child
			if(i*2+2 >=listSize && i*2+1 <listSize && inputList.get(i) < inputList.get(i*2+1)) return false;
			// two children
			if(i*2+2 < listSize && (inputList.get(i) < inputList.get(i*2+1) || inputList.get(i) < inputList.get(i*2+2))) return false;
		}
		return true;
	}

	
	private ArrayList<Integer> minHeap;
	private int size;
	private HashMap<Integer, Integer> cacheMap; 

	// constructure
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

	public static void heapSort(ArrayList<Integer> inputList){
		if(inputList.size() == 0){
			throw new Error("List cannot be empty");
		}
		privateHeapSort(inputList);
		System.out.println(inputList);
	}

	private static void privateHeapSort(ArrayList<Integer> inputList){
		int listSize = inputList.size();
		if(listSize == 1) return;

		while(listSize > 1){
			privateMaxHeapifyList(inputList, listSize);
			swap(inputList, 0, listSize-1);
			listSize--;
		}
	}


	
	private static void swap(ArrayList<Integer> inputList, int pos1, int pos2){
		Integer temp = inputList.get(pos1);
		inputList.set(pos1, inputList.get(pos2));
		inputList.set(pos2, temp);
	}

	public void display(){
		System.out.println("The minHeap is " + minHeap);
		// System.out.println("cache map is " + cacheMap);
	}

	
}
