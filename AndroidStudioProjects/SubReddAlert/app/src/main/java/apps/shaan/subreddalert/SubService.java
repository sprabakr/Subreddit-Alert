package apps.shaan.subreddalert;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by shaan on 6/25/15.
 */
public class SubService extends Service {

    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        Log.i("hi", "hi");
        Log.i("hello", "ello");
        new Thread(new Runnable() {
            @Override
            public void run() {
                SubredditNews news=new SubredditNews();


                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                while(true) {
                    SystemClock.sleep(1000);
                    try {
                        //looking if needed to update


                        FileInputStream fin = openFileInput("subs.txt");
                        String objEnc = "";

                        BufferedReader input = new BufferedReader(new InputStreamReader(fin, "UTF-8"));
                        StringBuilder sbuilder = new StringBuilder();
                        objEnc = input.readLine();
                        while (objEnc != null) {
                            sbuilder.append(objEnc);
                            objEnc = input.readLine();
                            if (objEnc != null) {

                                // sbuilder.append("\n");

                            }
                        }
                        input.close();
                        objEnc=sbuilder.toString();
                        fin.close();
                        Gson gson = new Gson();
                        ManySubs obj = gson.fromJson(objEnc, ManySubs.class);//not working
                        ArrayList<ASubreddit> theSubs=new ArrayList<ASubreddit>();
                        Posts temp=new Posts();
                        if(obj!=null)
                            theSubs=obj.newPosts;
                        ArrayList<ASubreddit> upd=new ArrayList<ASubreddit>();
                        ASubreddit aSub;
                        for(int i=0;i<theSubs.size();i++){
                            aSub=theSubs.get(i);
                            aSub.updatePosts();
                            if(!aSub.newPosts.isEmpty()){
                                upd.add(aSub);
                                temp.title=aSub.newPosts.get(aSub.newPosts.size()-1);
                                for(int j=0;j<aSub.list.size();j++){
                                    if(aSub.list.get(j).title.equals(temp.title)) {
                                        temp.permalink = aSub.list.get(j).permalink;
                                    }
                                }
                                news.newSub(aSub.name,temp);
                            }
                        }
                        //todo keep track of news
                        FileOutputStream newsOutput=openFileOutput("news.txt",MODE_PRIVATE);
                        String enb=gson.toJson(news);
                        newsOutput.write(enb.getBytes());
                        //notification build
                        //todo build a dynamic notification bar
                        if(!upd.isEmpty()) {
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                                    .setContentTitle(upd.get(0).name)
                                    .setContentText(upd.get(0).newPosts.get(0));
                            Intent intent = new Intent(getApplicationContext(), LetestNews.class);
                            intent.setAction(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_LAUNCHER);

                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                                    intent, 0);
                            mBuilder.setContentIntent(pendingIntent);

                            NotificationCompat.InboxStyle inboxStyle =
                                    new NotificationCompat.InboxStyle();
// Sets a title for the Inbox in expanded layout
                            ArrayList<String>details=new ArrayList<String>();
                            for(int j =0;j<news.subreddits.size();j++){
                                details.add(news.subreddits.get(j).name);
                                for(int k=0;k<news.subreddits.get(j).posts.size();k++){
                                    details.add(news.subreddits.get(j).posts.get(k).title);
                                }
                            }
                            inboxStyle.setBigContentTitle("Subreddit Details");
// Moves events into the expanded layout
                            for (int i=0; i < details.size(); i++) {

                                inboxStyle.addLine(details.get(i));
                            }
                            mBuilder.setStyle(inboxStyle);
                            mBuilder.setAutoCancel(true);
                            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(1, mBuilder.build());
                            Log.i("went through", "YEEEEEEEEEEEEEEEEEEEEEEEE");
                        }
                        FileOutputStream subOO = openFileOutput("subs.txt", MODE_PRIVATE);
                        Gson gson1 = new Gson();
                        String json = gson.toJson(obj);
                        subOO.write(json.getBytes());
                        subOO.close();
                        SystemClock.sleep(3000);
                        Log.i("updatinggggggggggggg", "YEEEEEEEEEEEEEEEEEEEEEEEE");
                    } catch (Exception e) {
                        String oajsod="";

                    }

                    if(true){
                        Log.i("aaa", "Service running");
                    }
                }


            }
        }).start();



        return START_STICKY;


    }
    public class MyBinder extends Binder {
        SubService getService() {
            return SubService.this;
        }
    }
    public void onDestroy() {
        try {
            FileInputStream fin = openFileInput("subs.txt");
            fin.close();
        }catch(Exception e){

        }
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }

    }

