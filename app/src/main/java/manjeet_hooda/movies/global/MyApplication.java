package manjeet_hooda.movies.global;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import manjeet_hooda.movies.db.moviesDBadapter;

/**
 * Created by manjeet on 16/4/16.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;
    private static moviesDBadapter mDBadapter;

    public static MyApplication getInstance() {
        return sInstance;
    }

    public synchronized static moviesDBadapter getadapter() {
        if (mDBadapter == null) {
            mDBadapter = new moviesDBadapter(getAppContext());
        }
        return mDBadapter;
    }

    public static void display(String str){
        Toast.makeText(getAppContext(),str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(){
        super.onCreate();
        sInstance = this;
    }

    public static MyApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }

    public void getDB(){

    }
}
