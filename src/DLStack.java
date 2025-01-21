/**
 * @author Hassan Zeeshan
 * @version July 23, 2023
 * @param <T>, generic parameter
 */

public class DLStack<T> implements DLStackADT<T> {

	// Initializing private instance variables
	private DoubleLinkedNode<T> top;
	private int numItems;

	/**
	 * Constructor for class DLStack
	 */
	public DLStack() {
		// Empty stack
		top = null;
		numItems = 0;
	}

	/**
	 * Push method for our stack
	 * 
	 * @param dataItem, takes in the data item that we want to push into the stack
	 */
	public void push(T dataItem) {
		// Creating a new node with using the data item
		DoubleLinkedNode<T> node = new DoubleLinkedNode<T>(dataItem);
		// If the stack has items in it...
		if (top != null) {
			// Setting the new node as the top node in the stack
			node.setPrevious(top);
			top.setNext(node);
		}
		top = node;
		// Increasing the num Items (we added to it)
		numItems++;
	}

	/**
	 * Pop method
	 * 
	 * @throws EmptyStackException thrown if the stack is empty
	 * 
	 * @return data item of the top node
	 */
	public T pop() throws EmptyStackException {
		// Creating a new node
		DoubleLinkedNode<T> node = new DoubleLinkedNode<T>();
		// If the stack is empty we throw the exception
		if (isEmpty() == true) {
			throw new EmptyStackException("Error: Stack is empty");
		}
		// Setting the node equal to top
		node = top;
		// If there's only one item in the stack...
		if (numItems == 1) {
			// Updating pointers of the top node
			top.setNext(null);
			top.setPrevious(null);
			// Decrementing number of items
			numItems--;
			// Returning value of the popped node
			return node.getElement();
		}
		// Else...
		// Setting top to the node before the top node
		top = top.getPrevious();
		// Setting the next node after top to null
		top.setNext(null);
		// Decrementing number of items
		numItems--;
		// Returning value of the popped node
		return node.getElement();
	}

	/**
	 * Overloading pop method
	 * 
	 * @param k, index of the item we're trying to pop
	 * 
	 * @throws InvalidItemException throwing an error if the index is not valid
	 *                              (can't remove item at said index)
	 * @return data item of node we want to pop
	 */
	public T pop(int k) throws InvalidItemException {
		// Creating a node curr to track the current node
		DoubleLinkedNode<T> curr = new DoubleLinkedNode<T>();
		// Setting curr to top
		curr = top;
		// Item is invalid if the stack is empty, if the item is too large, or if the
		// item is too small (index wise)
		if (isEmpty() == true || k > numItems || k <= 0) {
			throw new InvalidItemException("Error: Invalid item");
		}
		// Getting curr to our desired location
		for (int i = 1; i < k; i++) {
			curr = curr.getPrevious();
		}
		// If the item is the second item we just point top to it's previous
		if (k == 1) {
			top = top.getPrevious();
			// Else...
		} else {
			// Updating pointers for items behind and after curr (so we can remove curr out
			// the linked list)
			curr.getPrevious().setNext(curr.getNext());
			curr.getNext().setPrevious(curr.getPrevious());
		}
		// Decrementing num items
		numItems--;
		// Returning value of curr
		return curr.getElement();
	}

	/**
	 * Peek method: returning the item at top of the stack
	 * 
	 * @throws EmptyStackException meaning the stack is empty
	 * 
	 * @return data item of top node in the stack
	 */
	public T peek() throws EmptyStackException {
		// If the stack is empty, throw exception
		if (isEmpty() == true) {
			throw new EmptyStackException("Error: Stack is empty");
		}
		// Otherwise we just return the top element
		return top.getElement();
	}

	/**
	 * Method to check if the stack is empty
	 * 
	 * @return whether or not stack is empty
	 */
	public boolean isEmpty() {
		// If the number of items is zero the stack is empty, we return true
		if (numItems == 0) {
			return true;
		}
		// Else, we return false
		return false;
	}

	/**
	 * Obtaining the size of the items
	 * 
	 * @return number of items
	 */
	public int size() {
		// returning the number of items
		return numItems;
	}

	/**
	 * returning the top node in the stack
	 * 
	 * @return top node
	 */
	public DoubleLinkedNode<T> getTop() {
		return top;
	}

	/**
	 * toString method for the stack
	 * 
	 * @return toString of the stack
	 */
	public String toString() {
		// Creating an empty String
		String s = "";
		// Creating a current node to go through the stack
		DoubleLinkedNode<T> curr = new DoubleLinkedNode<T>();
		curr = top;
		for (int i = 0; i < numItems; i++) {
			// Adding the element of the current node to the string
			s = s + curr.getElement() + " ";
			curr = curr.getPrevious();
		}
		// Returning the completed string
		return s;
	}
}
