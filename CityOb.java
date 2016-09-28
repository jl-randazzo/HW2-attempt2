

/**
 * Created by jl_ra on 9/27/2016
 */

/*I'll just put some notes up here. The CityOb is simple: I just used a string for each line and then searched for the commas
 *and assigned the strings between to the values dma, city, state. I originally was trying to implement the comparator and override it
 * as a static method in this class, but I just wasn't able to quite figure it out. I'm going to need to work on that a bit more.
 * For the time being I just created another class called CityComparator.java that used the return values of the methods
 * below to compare two CityOb's. And obviously the toString method is what I used to write the final file.
 */
public class CityOb{
    private String dma, city, state;
    public CityOb(String constructor){
        int locator = constructor.indexOf(',');
        dma = constructor.substring(0, locator);
        locator++;
        int locator2 = constructor.indexOf(',', locator);
        city = constructor.substring(locator, locator2);
        locator2++;
        state = constructor.substring(locator2, constructor.length());

    }

    public String toString(){
        return dma + "," + city + "," + state;
    }

    public String citySubstring(int a, int b){
        return city.substring(a, b);
    }

    public int cityIndexOf(char a, int b){
        return city.indexOf(a, b);
    }

    public char cityCharAt(int a){
        return city.charAt(a);
    }

    public int cityLength(){
        return city.length();
    }

}
