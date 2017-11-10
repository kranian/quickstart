package kranian.testapp.util;

import java.util.Random;

/**
 * Created by kranian on 17. 11. 10.
 */
public class StringUtil {
    public static String getRanAlphabet(int length){

        Random rnd =new Random();
        StringBuffer buf =new StringBuffer();
        for(int i=0; i<length; i++){
            if(rnd.nextBoolean()){
                buf.append((char)((int)(rnd.nextInt(26))+97));
            }else{
                buf.append((rnd.nextInt(10)));
            }
        }
        return buf.toString();
    }
}
