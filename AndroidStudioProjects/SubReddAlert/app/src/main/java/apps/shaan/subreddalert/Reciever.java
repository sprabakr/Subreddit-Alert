package apps.shaan.subreddalert;

/**
 * Created by shaan on 6/27/15.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Reciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, SubService.class);
        context.startService(service);
    }
}
