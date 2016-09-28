

/**
 * Created by jl_ra on 9/27/2016.
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
