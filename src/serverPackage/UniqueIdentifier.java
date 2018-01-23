package serverPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Muhib on 8/8/2016.
 */
public class UniqueIdentifier {
    private static List<Integer> ids=new ArrayList<>();
    private static final int RANG=1000;
    private static int index=0;

    static {
        for(int i=0;i<RANG;i++){
            ids.add(i);
        }
        Collections.shuffle(ids);
    }
    public static int getIdentifier(){
        if(index>ids.size()-1)
            index=0;
        return ids.get(index++);
    }
}
