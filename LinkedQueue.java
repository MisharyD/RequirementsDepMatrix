package SWE312Assignment1;

public class LinkedQueue<T> {

    public class Node<T> 
    {
        public T data;
        public Node<T> next;
        
        public Node() {
            data = null;
            next = null;
        }
        
        public Node(T val) {
            data = val;
            next = null;
        }
    }
    private Node<T> head, tail;
	private int size;
	
    public LinkedQueue() 
    {
		head = tail = null;
		size = 0;
	}

    public boolean full() {
		return false;
	}
	
	public int length (){
		return size;
	}

    public void enqueue(T e) {
		if(tail == null){
			head = tail = new Node<T>(e);
		}
		else {
			tail.next = new Node<T>(e);
			tail = tail.next;
		}
		size++;
	}

    public T serve() {
		T x = head.data;
		head = head.next;
		size--;
		if(size == 0)
			tail = null;
		return x;
	}

}
