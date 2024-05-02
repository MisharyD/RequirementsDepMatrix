package SWE312Assignment1;

public class LinkedList<T> 
{
    private class Node<T>
    {
        private T data;
        private Node<T> next;

        public Node(T data)
        {
            this.data = data;
            this.next = null;
        }

    }
    
    private Node<T> head;
	private Node<T> current;

    public LinkedList() 
    {
		head = current = null;
	}

    public void insert(T val) 
    {
		Node<T> tmp;
		if (empty())
			current = head = new Node<T>(val);
		else 
        {
			tmp = current.next;
			current.next = new Node<T>(val);
			current = current.next;
			current.next = tmp;
		}
	}

    public void insertBefore(T val)
    {
        if(current == head)
        {
            Node<T> tmp = head;
            head = new Node<T>(val);
            head.next = tmp;
        }
        else
        {
            Node<T> tmp = head;

			while (tmp.next != current)
				tmp = tmp.next;

                Node<T> newData = new Node<T>(val);
                newData.next = current;
                tmp.next = newData;
        }


    }
    
    public void remove(String val)
    {
        if(head.data.equals(val))
        {
            head = head.next;
            return;
        }
        else
        {
            Node<T> tmp = head;
            
            //iterate through the list until next node is val, or until next is null meaning it was not found
            while(tmp.next != null && tmp.next.data != val) 
                tmp = tmp.next;

            //if val was found remove it
            if(tmp.next != null && tmp.next.data == val )
                tmp.next = tmp.next.next;
        }
        
    }

    public void remove() 
    {
		if (current == head) 
			head = head.next;
		else 
        {
			Node<T> tmp = head;

			while (tmp.next != current)
				tmp = tmp.next;

			tmp.next = current.next;
		}

		if (current.next == null)
			current = head;
		else
			current = current.next;
	}

    public boolean search(String data) 
    {
            Node<T> tmp = head;

			while (tmp != null) 
            {
				if (tmp.data.equals(data))
                {
                   current = tmp; 
                   return true;
                }
                tmp = tmp.next;
			}
		return false;
	}

    public int getSize()
    {
        if(empty())
            return 0;
        
        int counter = 0;
        Node<T> tmp = head;
        while(tmp != null)
        {
            tmp = tmp.next;
            counter++;
        }
        
        return counter;
    }

    public boolean empty() 
    {
		return head == null;
	}

	public boolean last() 
    {
		return current.next == null;
	}

    public boolean full() 
    {
		return false;
	}

	public void findfirst() 
    {
		current = head;
	}

	public void findnext() 
    {
		current = current.next;
	}

	public T retrieve() 
    {
		return current.data;
	}

	public void update(T val) 
    {
		current.data = val;
	}

}