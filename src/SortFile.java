/**
 * Created by jl_ra on 9/27/2016.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
public class SortFile {

    private static Node first = new Node();
    private static Node last = new Node();
    private static int listLength = 0;
    private static File fileName = new File("dma.txt");


    private static class Node {
        private CityOb item = null;
        private Node next = null;
    }

    public static void main(String[]args){

        readFile(fileName);
        sortFile();
        saveFile();

        System.out.println("Your file has been saved to 'testout.txt'");

    }

    public static void add(CityOb a){
        if(first.item == null){first.item = a; first.next=null;}
        else if(first.next == null){last.item = a; last.next = null; first.next = last;}
        else {
            //int i = listLength - 1;
            //Node oldPrevious = get(i, 1);
            Node oldLast = last;
            //oldPrevious.next = oldLast;
            last = new Node();
            last.item = a;
            last.next = null;
            oldLast.next = last;
        }
        listLength++;
    }

    private static void exch(Node current1, Node current2, Node previous1, Node previous2){
        if(current1 == first && current2 == first.next){first = current2; current1.next = current2.next; first.next = current1;}
        //else if(previous2==first){}
        else if(current2 == last && current1 == first){last = current1; first = current2; first.next = current1.next; previous2.next = last; last.next = null;}
        else if(previous1 == null){Node temp = current1.next; first = current2; current1.next = current2.next; previous2.next = current1; first.next = temp;}
        else{Node temp = current2.next; previous1.next = current2; current2.next = current1.next; previous2.next = current1; current1.next = temp;}
    }

    private static Node get(int index, int counter, Node getAble){
        if(index < 1)return null;
        //Node getAble = null;
        if(counter == 1 && index == 1) return first;

        getAble = first.next;
       // Node previous = null;
        Node temp = null;
        for (int i = counter+1; i < index; i++) {
            temp = getAble.next;
            getAble = temp;
        }
        return getAble;
        //else return getAble;
    }




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

    public static void sortFile() {
        Node current1, current2, previous1, previous2;
        for (int i = 1; i < listLength; i++) {
            current1 = get(i, 1, null);
            previous1 = get(i - 1, 1, null);
            for (int j = i+1; j <= listLength; j++) {
                current2 = get(j, 1, null);
                previous2 = get(j - 1, 1, null);
                int k = CityComparator.compare(current2.item, current1.item);
                if (k < 0) exch(current2, current1, previous1, previous2);
            }

        }
    }

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
