package SWE312Assignment1;

public class ArrayList 
{
	private int maxsize;
	private int size;
	private int current;
	private Req[] nodes;
 
	public ArrayList(int n) 
    {
		maxsize = n;
		size = 0;
		current = -1;
		nodes = new Req[n];
	}

    public boolean full() 
    {
		return size == maxsize;
	}

	public boolean empty() 
    {
		return size == 0;
	}

	public boolean last() 
    {
		return current == size - 1;
	}

	public void findFirst() 
    {
		current = 0;
	}

	public void findNext() 
    {
		current++;
	}

    public Req retrieve () {
		return nodes[current];
	}

	public void update (Req val) {
		nodes[current] = val;
	}

	public void insert (Req val) 
    {
		for(int i = size-1; i > current; --i) 
			nodes[i+1] = nodes[i];
		current++;
		nodes[current] = val;
		size++;
	}

    public void remove () 
    {
		for (int i = current + 1; i < size; i++) 
			nodes[i-1] = nodes[i];
		size--;
		if (size == 0)
			current = -1;
		else if (current == size)
			current = 0;
	}

	public void copy(ArrayList list)
	{
		if(maxsize < list.maxsize)
			return;

		for(int i = 0; i<list.size;i++)
			nodes[i] = new Req(list.nodes[i].getName());

		this.size = list.size;
	}

	public int getSize()
	{
		return this.size;
	}

	public int getMaxSize()
	{
		return this.maxsize;
	}
}

