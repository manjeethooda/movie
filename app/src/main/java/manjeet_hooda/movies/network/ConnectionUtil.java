package manjeet_hooda.movies.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by manjeet on 21/4/16.
 */
public class ConnectionUtil {

    public static final boolean hasDataConnection(Context context) {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            if (activeNetwork == null) return false;
            return activeNetwork.isConnected();
    }
}
