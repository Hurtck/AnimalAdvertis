package animaladvertis.com.animaladvertis.util;

import android.util.Log;

/**
 * Created by 47321 on 2017/3/8 0008.
 */

public class StringUtil {

    /**
     * 比较两个Sring类型的数date1,date2如果他们没有不同的字母返回true有不同的字母返回false
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSame(String date1, String date2) {
        boolean state = true;
        for (int i = 0; i < Math.min(date1.length(), date2.length()) - 1; i++) {
            if (date1.charAt(i) != date2.charAt(i)) return false;
        }
        return state;
    }

    /**
     * 根据date2,来截取date1;
     *
     * @param date1
     * @param date2
     * @return String
     */
    public static String cutString(String date1, String date2) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int j = 0;
        Log.d("SeachActivitymsg",date1.length()+" "+date2.length());
        while (j <(date1.length()-date2.length())&&i<date2.length()) {
            if (date1.charAt(j) == date2.charAt(i)) i++;
            else i =0;
            j++;
        }

        if(j>=date1.length()-date2.length()) return null;
        else {
            for(int k=0;k<j;k++){
                sb.append(date1.charAt(k));
            }
            return sb.toString();
        }
    }
}
