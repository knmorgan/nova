package nova.game.util;

/**
 * A generic Linked List class that is optimized for situations where iteration
 * and removing objects during iteration are important.  All methods are able to
 * operate in O(1) efficiency.
 * 
 * @author Kyle Morgan (knmorgan)
 * @version 1.0
 * @param <T> The generic type that this list will contain.
 */
public class LinkList<T>
{
	private Node<T> head;
	private Node<T> tail;
	private Node<T> current;
	private int size;
	
	/**
	 * Initialized an empty LinkList.
	 */
	public LinkList()
	{
		size = 0;
	}
	
	/**
	 * Adds an object to the list.  Runs in O(1) time.
	 * 
	 * @param obj The object to be added.
	 */
	public void add(T obj)
	{
		if(head == null)
		{
			head = new Node<T>(obj, null, null);
			tail = head;
		}
		else
		{
			Node<T> node = new Node<T>(obj, null, head);
			head.setPrevious(node);
			head = node;
		}
		
		current = head;
		size++;
	}
	
	/**
	 * Clears the list.  Runs in O(1) time.
	 */
	public void clear()
	{
		head = null;
		tail = null;
		size = 0;
	}
	
	/**
	 * Returns true if there is another element to iterate through, false otherwise.
	 * @return Whether or not more iteration can occur.
	 */
	public boolean hasNext()
	{
		return current != null;
	}
	
	/**
	 * Returns the next object in the list.
	 * @return The next object.
	 */
	public T next()
	{
		T object = current.getObject();
		current = current.getNext();
		return object;
	}
	
	/**
	 * Removes the object returned by the most recent call to next.
	 * Runs in O(1) time.
	 */
	public void remove()
	{
		if(current == null)
		{
			if(tail == head)
			{
				head = null;
				tail = null;
			}
			else
			{
				tail.getPrevious().setNext(null);
				tail = tail.getPrevious();
			}
		}
		else if(current.getPrevious() == head)
		{
			current.setPrevious(null);
			head = current;
		}
		else
		{
			current.getPrevious().getPrevious().setNext(current);
			current.setPrevious(current.getPrevious().getPrevious());
		}
		size--;
	}
	
	/**
	 * Returns the size of the list.
	 * @return The size of the list.
	 */
	public int size()
	{
		return size;
	}
	
	/**
	 * Starts the iteration of the list over.
	 */
	public void startOver()
	{
		current = head;
	}
	
	/**
	 * Helper class that contains a node in the list, as well as references to
	 * the next and previous nodes in the list.
	 * 
	 * @author Kyle Morgan
	 *
	 * @param <E> The generic type this Node will contain.
	 */
	private class Node<E>
	{
		private Node<E> prev;
		private Node<E> next;
		private E obj;
		
		/**
		 * Constructor that initializes this Node.
		 * @param o The object this node will contain.
		 * @param p The object before this one in the list.
		 * @param n The next object in the list.
		 */
		public Node(E o, Node<E> p, Node<E> n)
		{
			obj = o;
			prev = p;
			next = n;
		}
		
		/**
		 * Returns the object this Node contains.
		 * @return The object.
		 */
		public E getObject()
		{
			return obj;
		}
		
		/**
		 * Returns the previous Node in the list.
		 * @return The previous Node.
		 */
		public Node<E> getPrevious()
		{
			return prev;
		}
		
		/**
		 * Sets the previous Node in the list.
		 * @param p The new previous Node.
		 */
		public void setPrevious(Node<E> p)
		{
			prev = p;
		}
		
		/**
		 * Returns the next Node in the list.
		 * @return The next Node.
		 */
		public Node<E> getNext()
		{
			return next;
		}
		
		/**
		 * Sets the next Node in the list.
		 * @param n The new next Node.
		 */
		public void setNext(Node<E> n)
		{
			next = n;
		}
	}
}
