package minHeap;
import java.util.ArrayList;

public class MinHeapTest {

	public static void main(String[] args) {
		// ArrayList<Integer> list1 = new ArrayList<Integer>();
		// list1.add(6);
		// list1.add(9);
		// list1.add(5);
		// list1.add(1);
		// list1.add(2);
		// list1.add(3);

		// MinHeap.minHeapifyList(list1);
		
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(-22);
		list2.add(1);
		list2.add(8);
		list2.add(5);
		list2.add(4);
		list2.add(7);
		list2.add(6);
		list2.add(9);
		// list2.add(null);
		MinHeap.heapSort(list2);
		// MinHeap.maxHeapifyList(list2);

		// MinHeap newHeap = new MinHeap();
		// newHeap.insert(2);
		// newHeap.insert(9);
		// newHeap.insert(8);
		// newHeap.insert(5);
		// newHeap.insert(4);
		// newHeap.insert(7);
		// newHeap.insert(100);
		// newHeap.insert(6);
		// newHeap.insert(1);
		// newHeap.insert(3);
		// newHeap.insert(-3);
		// newHeap.insert(-5);
		// newHeap.insert(-10);
		// newHeap.removeValue(1);
		// newHeap.removeValue(-3);
		// newHeap.removeValue(-5);


		// newHeap.display();
	}
}
