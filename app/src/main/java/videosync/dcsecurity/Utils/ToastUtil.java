package videosync.dcsecurity.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by fsociety on 10/9/16.
 */

public class ToastUtil {
    public static void shortToast(Context context,String message){
        makeToast(context,message,Toast.LENGTH_SHORT);
    }

    public static void longToast(Context context, String message){
        makeToast(context,message,Toast.LENGTH_SHORT);
    }

    private static void makeToast(Context context, String message, int length){
        Toast toast = Toast.makeText(context,message,length);
        toast.show();
    }
}
