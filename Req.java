package SWE312Assignment1;

public class Req 
{
    private String name;
    private boolean dependOn;
    private ArrayList reqList;
    //only used in sorting when printing
    int level;

    Req(String name) 
    {
        this.name = name;
    }
   
    Req(String name,int listSize) 
    {
        this.name = name;
        reqList = new ArrayList(listSize);
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDependOn(boolean b)
    {
        this.dependOn =b;
    }

    public boolean getDependOn()
    {
        return dependOn;
    }

    public void setReqList(ArrayList list)
    {
        reqList = new ArrayList(list.getMaxSize());
        reqList.copy(list);
    }

    public ArrayList getReqList()
    {
        return reqList;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getLevel()
    {
        return level;
    }
}

