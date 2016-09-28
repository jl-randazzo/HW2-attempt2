/**
 * Created by jl_ra on 9/27/2016.
 */
import java.util.Comparator;

public class CityComparator{

    public static int compare(CityOb a,CityOb b){
        for(int i = 0; i < a.cityLength(); i++){
            if(a.cityCharAt(i)>b.cityCharAt(i)) return 1;
            else if(a.cityCharAt(i)<b.cityCharAt(i)) return -1;
        }
        return 0;
    }
}
