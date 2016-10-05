/*
 Take two, I tried to implement it as a mergesort. It's a little messy here and there, but I did manage to figure it out.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

public class LLMergeSort {

    /*I went ahead and just initialized the dma.txt. I briefly considered making this entire class a reference type,
    * to be called from another static class to support actual user input, but I ultimately decided against it
    * since it was going to be pretty difficult for me already without having to deal with different user inputs.
    * Hopefully that helps explain why there are so many private static methods.
    * */
    private static Node first = new Node();
    private static Node last = new Node();
    private static Node firstNL = new Node();
    private static Node lastNL = new Node();
    private static int listLength = 0;
    private static int nListLength = 0;
    private static File fileName = new File("dma.txt");

    /*Nothing too original here, found examples of this in the Linked Lists classes included with algs4.jar
    I kept all of the operations relating to the Nodes up here above the main method. All the others I wrote
    from scratch.
     */
    private static class Node {
        private CityOb item = null;
        private Node next = null;
    }

    /*In case what I am doing here isn't obvious, I created two 'add' methods so that I could natively house two
    separate LinkedLists. I did it this way so that I didn't have to wrestle around with Generics and instead focus
    on moving the data between LinkedLists. The NL stands for 'new list,' it's effectively the same as the aux[] array
    listed in the book in terms of my intent. I also created a 'flush()' method so that I could completely empty it
    after each merge for use in the next merge.
    */
    
    public static void add(CityOb a){
        if(first.item == null){first.item = a; first.next=null;}
        else if(first.next == null){last.item = a; last.next = null; first.next = last;}
        else {
            Node oldLast = last;
            last = new Node();
            last.item = a;
            last.next = null;
            oldLast.next = last;
        }
        listLength++;
    }

    public static void addNL(CityOb a){
        if(firstNL.item == null){firstNL.item = a; firstNL.next=null;}
        else if(firstNL.next == null){lastNL.item = a; lastNL.next = null; firstNL.next = lastNL;}
        else {
            Node oldLastNL = lastNL;
            lastNL = new Node();
            lastNL.item = a;
            lastNL.next = null;
            oldLastNL.next = lastNL;
        }
        nListLength++;
    }

    public static void flushNL(){
        firstNL.item = null;
    }

    /*I rebuilt my get method to be agnostic so that I could use it for either List housed here. I no longer needed
    * to account for the possibility that a null value would be returned because of my adjusted implementation,
    * all it needs to know is how deep in I need to go and where to start iterating
    * */
    private static Node get(int index, Node subFirst){
        Node count = subFirst;
        for (int i = 1; i < index; i++) {
            count = count.next;
        }
        return count;
        //else return getAble;
    }

    //I actually completely removed my exchange method. As I went through the process of creating the mergesort, I realized
    //I didn't need it at all. Kind of a bummer since that's what took up most of my time last time, but I guess it was
    //good practice. 
    
    /*I tried to keep the main method as simple as possible, taking a que from your suggestion in class.
    * It ended up being a good idea. It helped me a LOT with the process of debugging.
     */
    public static void main(String[]args){

        readFile(fileName);
        sort(first, last, listLength);
        saveFile();

        System.out.println("Your file has been saved to 'testout.txt'");

    }

    /* readFile imports the data from dma.txt and converts each line into a CityOb.
View the constructor in CityOb.java.
 */
    public static void readFile(File file){
        try {
            Scanner fileScan = new Scanner(file);
            fileScan.nextLine();
            while (fileScan.hasNextLine()){
                CityOb current;
                String s = fileScan.nextLine();
                current = new CityOb(s);
                add(current);
            }
            fileScan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    /* I used a recursive method similar to what they use in the book for a regular mergesort. I think I managed to make
    the sort method even simpler than usual. Sorry if the merge is a little confusing. It just copies the list in the first
    for loop, then the i Node is the pointer for rewriting the objects in my initial list while I compare k and j (first
    and mid respectively in the new list). The int variables m and n are just there to keep track of how many times I added
    from either point so the program knows when to fill the rest of the data like a regular mergesort. Finally, I just flush
    at the end of the merge so that I can use the new list again for the next merge.
    */
    private static void sort(Node subFirst, Node subLast, int length) {
        Node mid = get((length/2), subFirst);
        if((length/2 > 0)){
            sort(subFirst, mid, length/2);
            sort(mid.next,subLast, (length/2)+(length%2));
        }
        merge(subFirst, mid, subLast, length);
    }

    private static void merge(Node subFirst, Node mid, Node subLast, int length){
        for(int i = 1; i <= length; i++){
            Node temp = get(i, subFirst);
            addNL(temp.item);
        }
        
        Node i = subFirst, k = firstNL, j = get(length/2+1, firstNL);
        int l = 1, m = length/2 + 1;

        for(int n = 1; n <= length; n++) {
            if (l > (length/2)) {i.item = j.item; j = j.next; i = i.next;}
            else if (m > length) {i.item = k.item; i = i.next; k = k.next;}
            else if (CityComparator.compare(k.item, j.item) > 0) {
                i.item = j.item;
                i = i.next;
                j = j.next;
                m++;
            }
            else {
                i.item = k.item;
                i = i.next;
                k = k.next;
                l++;
            }
        }
        flushNL();
    }

    /*This part was also surprisingly easy. Just prints what's stored in the local list to a file called testout.txt
    using the toString() method I wrote in the CityOb class
     */
    public static void saveFile(){
        try{
            FileWriter fw = new FileWriter(new File("testout.txt"));
            Node current;
            fw.write("dma code,region,state\n");
            for(int i = 1; i <= listLength; i++){
                current = get(i, first);
                fw.write(current.item.toString() + "\n");
            }
            fw.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}