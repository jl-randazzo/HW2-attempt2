/**
 * Created by jl_ra on 9/27/2016.
 */


/*As I said in the CityOb class, I wasn't able to figured out implementing the comparator. I'm going to try to work on that
on my personal time.
 */

public class CityComparator{

    public static int compare(CityOb a,CityOb b){
        int i = 0;
        while(i < a.cityLength() && i < b.cityLength()){
            if(a.cityCharAt(i)>b.cityCharAt(i)) return 1;
            else if(a.cityCharAt(i)<b.cityCharAt(i)) return -1;
            i++;
        }
        if(a.cityLength() < b.cityLength()) return -1;
        else if(b.cityLength() < a.cityLength()) return 1;
        return 0;
    }
}
