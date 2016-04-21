package manjeet_hooda.movies.global;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import manjeet_hooda.movies.activity.MainActivity;

/**
 * Created by manjeet on 21/4/16.
 */
public class NoConnectionDialog {

    public static void showDialog(Context context) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("No Data Connection availaible. Please connect to Internet");
        alertDialogBuilder.setTitle("Connectivity Issues");
        alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick (DialogInterface arg0,int arg1){

            }
        }

        );
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
