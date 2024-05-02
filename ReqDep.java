package SWE312Assignment1;
import java.util.Scanner;

public class ReqDep
{
      //a list to store requirments to later get their identifiers and how many of them.
      private static LinkedList<String> reqList = new LinkedList<String>();

      //a list to store the depandancies between requierments. an example of one element in this list is : R1 R2 R3.
      private static LinkedList<String> depList = new LinkedList<String>();

    public static void main(String[] args)
    {
      try
      {
        Scanner sc = new Scanner(System.in);
        String dep = "";

        System.out.println("format: 'Rx Ry Rz', where x, y, z is any any integer. And only one space between each requirment");
        System.out.print("Dependacies: ");
        try
        {
          dep = sc.nextLine().stripLeading().toUpperCase();
        }
        catch(Exception e)
        {
          System.out.println("wrong input, needs to be in the following format: Rx Ry Rz. with only one space between each identifer");
          System.exit(0);
        }
        do
        {
          //an array of string containing the identifier of indivisual requirments.
          String[] reqs = dep.split(" ");

          //if reqList is empty then depList is also empty.
          if(reqList.empty())
          {
            depList.insert(dep);
            for(int i =0; i<reqs.length; i++)
            {
              insertReqOrdered(reqs[i]);
            }
          }
          //store unique name of requirments

          /* loop through the list of requirement we have , then loop through the new input requirments 
          and check if there is any new requirmens to insert in the requirment list. */
          else
          {
            for(int i = 0; i<reqs.length; i++)
            {
              String req = reqs[i];
              insertUnique(req);
            }
            //store dependancie
            depList.insert(dep);
          }
            System.out.print("Dependacies: ");
          
            dep = sc.nextLine().stripLeading().toUpperCase();
            
        }while(!dep.replaceAll("\\s","").equals("0"));

        //the main list that will store requirements as Req objects. 
        ArrayList listOfReq = new ArrayList(reqList.getSize());

        //insert requirements as req objects in listOfReq arraylist.
        reqList.findfirst();
        for(int i=0; i<reqList.getSize(); i++)
        {
          listOfReq.insert(new Req(reqList.retrieve(),reqList.getSize()));

          reqList.findnext();
        }

        /*a copy of the listOfReq list that will be given to every Req object in the listOfReq list
        this is needed because a Req objects are given the listOfReq each Req object will have a different copy,
        that is why a copy of the req object is needed before any modifications happens to them(the Req objects).*/
        ArrayList listOfReqCopy = new ArrayList(reqList.getSize());
        listOfReqCopy.copy(listOfReq);

        Req data;
        listOfReq.findFirst();
        for(int i=0; i<reqList.getSize(); i++)
        {
          data = listOfReq.retrieve();
          data.setReqList(listOfReqCopy);

          listOfReq.findNext();
        }

        /*store dependacies, depList will have for example 1 element as such: R1 R2 R3
        we go into the R1 object in listOfReq list which will have another arrayList of Req objects (which is the a copy of listOfReq),
        we look for R2 R3 in that list and set the dependOn boolean to true.
        since every Req Object in listOfReq has a different copy of listOfReq other Req wont be affected by this change.
        */

        //an array that will store the relation as 1 requirment in each postion in the array.
        String[] reqs;
        String firstReq;
        depList.findfirst();
        for(int i=0; i<depList.getSize(); i++)
        {
          reqs = depList.retrieve().split(" ");
          //the requirment which depend on other requirments.
          firstReq = reqs[0];
          
          //look for firstReq in the listOfReq list.
          listOfReq.findFirst();
          for(int j = 0; j<listOfReq.getSize(); j++)
          {
            if(listOfReq.retrieve().getName().equals(firstReq))
            {
              //loop through the requiremnts that firstReq depend on in the reqs array.
              for(int k=1; k<reqs.length; k++)
              {
                String currentReq = reqs[k];
                
                //loop through the req object arrayList and look for the "dependOn" requirment to set it's boolean to true.
                ArrayList internalList = listOfReq.retrieve().getReqList();
                internalList.findFirst();
                for(int m =0; m< internalList.getSize(); m++)
                {
                  if(internalList.retrieve().getName().equals(currentReq))
                  {
                    internalList.retrieve().setDependOn(true);
                    break;
                  }
                  internalList.findNext();
                }
              }
            }
            listOfReq.findNext();
          }
          depList.findnext();
        }
            
          //find first level of depandancies

          String mainReq = args[0];
          //an array that will store every requirement depandacie toward the mainReq. the index is the number of the req identfier and the value
          //is the level of dependancie.
          int[] depLevels = new int[listOfReq.getSize() + 1];//index 0 wil be skipped

          //an array that will only store the requirements which have a first level dependacie.
          String[] firstLevel = new String[listOfReq.getSize() + 1];//index 0 wil be skipped
        
          int reqNumber = 0;
          listOfReq.findFirst();
          for(int i=0; i< listOfReq.getSize();i++)
          {
            reqNumber++;
            Req currentReq = listOfReq.retrieve();
            ArrayList currentList = currentReq.getReqList();
            
            currentList.findFirst();
            for(int j =0; j<currentList.getSize(); j++)
            {
              if(currentList.retrieve().getName().equals(mainReq) && currentList.retrieve().getDependOn() == true)
              {
                depLevels[reqNumber] = 1;
                firstLevel[reqNumber] = currentReq.getName();
                break;
              }
              currentList.findNext();
            }
            listOfReq.findNext();
          }

          //other level of dependancies
          
          LinkedQueue<String> anyDep = new LinkedQueue<String>(); 
          //copy first level dependancie requirments into anyDep queue
          for(int i = 0;i <firstLevel.length;i++)
            if(firstLevel[i] != null)
              anyDep.enqueue(firstLevel[i]);

          /*this while loop will iterate until last level of dependance.
          first it will loop for the length of before any enqueuing happened to it.
          the loop will look for requirments that depends on requirments in the queue then the requirments that depend on them will 
          a level of dependacie equal to currentLevel + 1 and also these requirments will be pushed into queue to later look for 
          requirements that depend on them . when the loop equal to length is done , elements will be served out of the queue for length number of times.
          then a new length will be calculated that will be equal to number of new elements pushed to queue and currentLevel is increased by 1.
          */
          int currentLevel = 1;
          int length = anyDep.length();
          String currentReqName = "";
          while(length != 0)
          {
            for(int i = 0; i<length; i++)
            {
              currentReqName = anyDep.serve();

              listOfReq.findFirst();
              for(int j = 0; j<listOfReq.getSize(); j++)
              {
                ArrayList internalList = listOfReq.retrieve().getReqList();
                internalList.findFirst();
                for(int k = 0; k<internalList.getSize(); k++)
                {
                  /*loop through the Req object and check if it depends on currentReqName, if it does add it to the queue and change
                  it's dependecie level by the counter*/ 
                  if(internalList.retrieve().getName().equals(currentReqName) && internalList.retrieve().getDependOn() == true)
                  {
                    //if(depLevels[j+1] == 0)//this line can be used if shortest path is desired

                    //since j is used to for requirments iteration it can be used in depLevels
                    depLevels[j+1] = currentLevel + 1;
                    anyDep.enqueue(listOfReq.retrieve().getName());
                    break;
                  }
                  internalList.findNext();
                }
                listOfReq.findNext();
              }
            }
            length = anyDep.length();
            currentLevel++;
          }

          System.out.println("Dependancie levels:");
          //a linked that will have requirments sorted by dependancie level then by thei identifier.
          LinkedList<Req> printDep = new LinkedList<Req>();
          for(int i =1; i<depLevels.length ;i++)
          {
            //if requirement has dependancie insert it in the list sorted.
            if(depLevels[i] != 0)
            {
              Req req = new Req(("R" + i));
              req.setLevel(depLevels[i]);
              insertReqDepOrdered(req,printDep);
            }
          }

          //print requirments and their dependancie levels.
          if(printDep.empty())
          {
            System.out.println("No dependancies");
            System.exit(0);
          }
          printDep.findfirst();
          while(!printDep.last())
          {
            Req req = printDep.retrieve();
            System.out.println(req.getLevel() +" " + req.getName());
            printDep.findnext();
          }
          //last element in printDep list
          Req req = printDep.retrieve();
          System.out.println(req.getLevel() +" " + req.getName());


          System.out.println();
          System.out.println("Matrix representation: \n");
          //print matrix
          listOfReq.findFirst();
          for(int i =0; i<listOfReq.getSize();i++)
          {
            System.out.print("   " + listOfReq.retrieve().getName());
            listOfReq.findNext();
          }
          System.out.println();

          listOfReq.findFirst();
          for(int i =0; i<listOfReq.getSize();i++)
          {
            System.out.print(listOfReq.retrieve().getName() + " ");
            ArrayList internalList = listOfReq.retrieve().getReqList();

            internalList.findFirst();
            for(int j =0; j<internalList.getSize();j++)
            {
              //5 spaces between each R
              if(internalList.retrieve().getDependOn() == true)
                System.out.print("X    ");
              else
                System.out.print("     ");
              internalList.findNext();
            }
            System.out.println();
            listOfReq.findNext();
          }
        }
        catch(Exception e)
        {
          System.out.println("Wrong input! input need to be in this format: 'Rx Ry Rz', where x, y, z is any any integer. \n "+
           "And only one space between each requirment");
        }
      }

    private static void insertUnique(String req) {
      boolean exist = false;
      reqList.findfirst();
      while(!reqList.last())
      {
        if(reqList.retrieve().equals(req))
        {
          exist = true;
          break;
        }
        reqList.findnext();
      }
      //last node
      if(reqList.retrieve().equals(req))
          exist = true;
      
      //insert requirment identifer in the list if it is not already there. 
      if(!exist)
        insertReqOrdered(req);
    }

      public static void insertReqDepOrdered(Req req, LinkedList<Req> list)
      {
        Req req2;
        if(list.empty())
        {
          list.insert(req);
          return;
        }

        int result;
        list.findfirst();
        while(!list.last())
        {
          req2 = list.retrieve();
          result = compareToInt(req.getLevel(),req2.getLevel());
          if(result == -1)
            {
                list.insertBefore(req);
                return;
            }
            //if they have the same dependancie sort by identifier.
            else if(result == 0)
            {
              int result2 = compareToString(req.getName(), req2.getName());
              //if req is lower in number than req2, insert req before req2.
              if(result2 == -1)
            {
                list.insertBefore(req);
                return;
            }
            //if req is higher in number than req2 , insert req after req2.
            else
            {
                list.insert(req);
                return;
            }
            }
            list.findnext();
        }
        //last element in list
        req2 = list.retrieve();
        result = compareToInt(req.getLevel(),req2.getLevel());
        if(result == -1)
            list.insertBefore(req); 
        else
            list.insert(req);
      }
      
      
    public static void insertReqOrdered(String s)
    {
       String s2 = "";
       //if list is empty insert at head .
       if(reqList.empty())               
       {
           reqList.insert(s); 
           return;
       }

       int result;
       reqList.findfirst();
       while(!reqList.last())
       {
           s2 = reqList.retrieve();
           result = compareToString(s,s2);
           /*if current node is coming later in alphabetic insert before it,
           insertBefore method was used instead of normal insert in case of inserting before first element.
           in case of same word it will be inserted after. */
           if(result == -1)
           {
               reqList.insertBefore(s);
               return;
           }
           reqList.findnext();
       }

       //last node
       s2 = reqList.retrieve();
       result = compareToString(s,s2);
       //if last node is coming later in the alphabetic insert before it.
       if(result == -1)
           reqList.insertBefore(s);
       //if same word or last node is coming first in the alphabetic insert after.
       else
           reqList.insert(s);
    }


    public static int compareToInt(int i1, int i2)
    {
      if(i1 > i2)
        return 1;
      if(i1 < i2)
        return -1;
      else
        return 0;
    }

    public static int compareToString(String s1 , String s2) {
      
      String string1 = s1.replaceAll("R", "");
      String string2 = s2.replaceAll("R", "");
      if(Integer.parseInt(string1) > Integer.parseInt(string2))
          return 1;
      if(Integer.parseInt(string1) < Integer.parseInt(string2))
          return -1;
      else
          return 0;
  }
}