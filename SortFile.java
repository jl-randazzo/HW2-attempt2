/*
 * The way I engineered this program, the list construction is housed within the sorting class; in retrospect,
 * I probably should have made them two separate classes, but I've included notes to make it reasonably clear what
 * my purpose/intent was at every juncture. I know I probably didn't need to do it this way (including writing my
 * own code for the Linked List), but I think it helped me get in the mud with actually understanding the underlying
 * structure of a Linked List a little better.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

public class SortFile {

    /*I went ahead and just initialized the dma.txt. I briefly considered making this entire class a reference type,
    * to be called from another static class to support actual user input, but I ultimately decided against it
    * since it was going to be pretty difficult for me already without having to deal with different user inputs.
    * Hopefully that helps explain why there are so many private static methods.
    * */
    private static Node first = new Node();
    private static Node last = new Node();
    private static int listLength = 0;
    private static File fileName = new File("dma.txt");

    /*Nothing too original here, found examples of this in the Linked Lists classes included with algs4.jar
    I kept all of the operations relating to the Nodes up here above the main method. All the others I wrote
    from scratch.
     */
    private static class Node {
        private CityOb item = null;
        private Node next = null;
    }

    //this part was surprisingly easy. Didn't really have to fix any bugs, just created a new node and brought in the CityOb
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

    /*my get method is used to retrieve a node at a given position in the list. It returns a null value if the index is
    less than one, so I made sure that the possibility of null values for previous1 and previous 2 in the exch method
    were accounted for*/
    private static Node get(int index, int counter, Node getAble){
        if(index < 1)return null;
        if(counter == 1 && index == 1) return first;
        getAble = first.next;
        Node temp = null;
        for (int i = counter+1; i < index; i++) {
            temp = getAble.next;
            getAble = temp;
        }
        return getAble;
        //else return getAble;
    }

    /*This was the real nightmare of the whole thing. Let me know if you have any tips to make it simpler.
    I think this is obvious, but this is the method that does all of the exchanging based on data fed to it by
    the sortFile method.
    */
    private static void exch(Node current1, Node current2, Node previous1, Node previous2){
        if(current1 == first && current2 == first.next){Node temp = current2.next; first = current2; first.next=current1; current1.next = temp; return;}
        //else if(previous2==first){}
        else if(current2 == last && current1 == first){last = current1; first = current2; first.next = current1.next; previous2.next = last; last.next = null; return;}
        else if(previous1 == null){Node temp = current1.next; first = current2; current1.next = current2.next; previous2.next = current1; first.next = temp; return;}
        else if(previous2==current1){Node temp = current2.next; previous1.next=current2; current2.next = current1; current1.next = temp; return;}
        else{Node temp = current2.next; previous1.next = current2; current2.next = current1.next; previous2.next = current1; current1.next = temp;}
    }

    /*I tried to keep the main method as simple as possible, taking a que from your suggestion in class.
    * It ended up being a good idea. It helped me a LOT with the process of debugging.
     */
    public static void main(String[]args){

        readFile(fileName);
        sortFile();
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

    /* sortFile does what it sounds like. I ended up just using bubbleSort. I could have done an implementation for
    any of the others, but I wanted to really focus on the Linked List structure and not worry about trouble shooting
    a sort I wasn't used to for the time being.
    */
    public static void sortFile() {
        Node current1, current2, previous1, previous2;
        for (int i = 1; i < listLength; i++) {
            for (int j = i+1; j <= listLength; j++) {
                current1 = get(i, 1, null);
                previous1 = get(i - 1, 1, null);
                current2 = get(j, 1, null);
                previous2 = get(j - 1, 1, null);
                int k = CityComparator.compare(current2.item, current1.item);
                if (k < 0) exch(current1, current2, previous1, previous2);
            }

        }
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
                current = get(i, 1, null);
                fw.write(current.item.toString() + "\n");
            }
            fw.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
