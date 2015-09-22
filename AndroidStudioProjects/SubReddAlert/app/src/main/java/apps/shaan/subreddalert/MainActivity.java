package apps.shaan.subreddalert;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.os.Handler.Callback;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import com.google.gson.Gson;
import java.io.Serializable;


import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.Toolbar;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class MainActivity extends ActionBarActivity {
    private SubService s;
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Home","Events"};
    int Numboftabs =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String FILENAME="subreddit";
        String string="hello";
        Button add=(Button)findViewById(R.id.button);
        Button viewSubs=(Button)findViewById(R.id.viewsubs);
        Button viewAlerts=(Button)findViewById(R.id.button2);

        Intent serviceInt = new Intent(this,SubService.class);
        this.startService(serviceInt);




        //SQLiteDatabase subreddits = openOrCreateDatabase("subreddits",MODE_PRIVATE,null);
       // SharedPreferences subreddits = getSharedPreferences("SubReddits", 0);
        //FeedReaderDbHelper dat = new FeedReaderDbHelper(this);
        //SQLiteDatabase db = dat.getWritableDatabase();
       // final ArrayList<HashMap<String, String>> subData=dat.getAllSubreddits();
        // Create a new map of values, where column names are the keys
        final Intent intent2 = new Intent(this, LetestNews.class);
        viewAlerts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent2);
            }
        });



        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        final EditText editText = (EditText) findViewById(R.id.sub);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                String theSub = editText.getText().toString();


                theSub=theSub.toLowerCase();
                editText.setText("");
                HashMap<String, String>aSub=new HashMap<String, String>();
                aSub.put("subreddit",theSub);
                aSub.put("rank", "10");
                ASubreddit ASub=new ASubreddit();
                ASub.name=theSub;
                Gson gson1 = new Gson();

                try {
                    if(theSub.equals("")){
                        throw new EmptyStackException();
                    }
                    FileOutputStream subO = openFileOutput("subs.txt", MODE_APPEND);
                    subO.close();
                    String string="";
                    int d;

                    FileInputStream fin = openFileInput("subs.txt");
                    BufferedReader input = new BufferedReader(new InputStreamReader(fin, "UTF-8"));

                    StringBuilder sbuilder = new StringBuilder();

                    string = input.readLine();

                    while (string != null) {
                        sbuilder.append(string);
                        string = input.readLine();
                        if (string != null) {

                            //sbuilder.append("\n");

                        }
                    }
                    string=sbuilder.toString();
                    ManySubs obj = gson1.fromJson(string, ManySubs.class);
                    // SharedPreferences mPrefs = getSharedPreferences("subreddits.txt", MODE_PRIVATE);AAAA
                    //String json1 = mPrefs.getString("ManySubs", "");AAAA
                    // ManySubs obj = gson1.fromJson(json1, ManySubs.class);AAAA
                    fin.close();
                    FileOutputStream subOO = openFileOutput("subs.txt", MODE_PRIVATE);
                    ManySubs many;
                    if (obj != null) {
                        many = obj;
                    } else {
                        many = new ManySubs();
                    }
                    ASub.generateURL();
                    DownloadFilesTask down=new DownloadFilesTask();
                    String content=down.execute(ASub.url).get();
                    ASub.linkCont=content;
                    ASub.fetchPosts();
                    ArrayList<Posts>what=ASub.list;
                    many.newPosts.add(ASub);
                    Gson gson = new Gson();
                    String json = gson.toJson(many);
                    subOO.write(json.getBytes());
                    string="";
                    subOO.close();
                    FileInputStream fin1 = openFileInput("subs.txt");

                    fin1.close();
                    String test="";
                    for(int i=0;i<what.size();i++){
                        test=test+" "+what.get(i).title;
                    }

                    Toast.makeText(getBaseContext(), "here " + test, Toast.LENGTH_SHORT).show();
                }catch(Exception e){

                }


                try {
                    if(!theSub.equals("")) {
                        int c;
                        String temp = "";
                        FileOutputStream fOut = openFileOutput("All_Subs.txt", MODE_APPEND);
                        OutputStreamWriter osw = new OutputStreamWriter(fOut);
                        //osw.append(theSub);
                        File file = new File("All_Subs.txt");
                        //FileOutputStream fOut= new FileOutputStream(file,true);
                        fOut.write((theSub + " ").getBytes());
                        fOut.close();
                        FileInputStream fin = openFileInput("All_Subs.txt");
                        Toast.makeText(getBaseContext(), "subreddit added", Toast.LENGTH_SHORT).show();
                        temp = "";
                        boolean a = false;
                        while ((c = fin.read()) != -1) {
                            temp = temp + Character.toString((char) c);

                        }
                        Toast.makeText(getBaseContext(), temp, Toast.LENGTH_SHORT).show();
                        fOut.close();
                        fin.close();
                    }
                }

                catch (Exception e) {
                    ;
                    e.printStackTrace();
                }
            }
        });
       final Intent intent = new Intent(this, subreddit.class);
        viewSubs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });




        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    //sendMessage();
                    handled = true;
                }
                return handled;
                     }
                 });


             }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
             public boolean onCreateOptionsMenu(Menu menu) {
                 // Inflate the menu; this adds items to the action bar if it is present.
                 getMenuInflater().inflate(R.menu.menu_main, menu);
                 return true;
             }

             @Override
             public boolean onOptionsItemSelected(MenuItem item) {
                 // Handle action bar item clicks here. The action bar will
                 // automatically handle clicks on the Home/Up button, so long
                 // as you specify a parent activity in AndroidManifest.xml.
                 int id = item.getItemId();

                 //noinspection SimplifiableIfStatement
                 if (id == R.id.action_settings) {
                     return true;
                 }

                 return super.onOptionsItemSelected(item);
             }


    private class DownloadFilesTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            String some = "";
            try {
                StringBuffer sb=new StringBuffer(819200);
                String tmp="";
                URL oracle = new URL(urls[0]);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(oracle.openStream()));

                while((tmp = in.readLine())!=null)
                    sb.append(tmp).append("\n");
                some=sb.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return some;
        }
        protected void onProgressUpdate(Integer...a){
            Log.d("Asyntask","You are in progress update ... " + a[0]);}
        protected void onPostExecute(String result) {
            Log.d("Asyntask",result); }


    }


}
