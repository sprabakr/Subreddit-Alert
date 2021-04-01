package apps.shaan.subreddalert;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class LetestNews extends ExpandableListActivity{

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // this is not really  necessary as ExpandableListActivity contains an ExpandableList
        //setContentView(R.layout.main);

        ExpandableListView expandableList = getExpandableListView(); // you can use (ExpandableListView) findViewById(R.id.list)

        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        setGroupParents();
        setChildData();

        ExpandableAdapter adapter = new ExpandableAdapter(getApplicationContext(),parentItems, childItems);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);


    }

    public void setGroupParents() {
        try {
            FileInputStream newsIn = openFileInput("news.txt");
            String newsObj = "";
            String objEnc="";
            Gson gson=new Gson();
            BufferedReader inputNews = new BufferedReader(new InputStreamReader(newsIn, "UTF-8"));
            StringBuilder sbuilderNews = new StringBuilder();
            newsObj = inputNews.readLine();
            while (newsObj != null) {
                sbuilderNews.append(newsObj);
                newsObj = inputNews.readLine();
                if (newsObj != null) {

                    // sbuilder.append("\n");

                }
            }
            newsIn.close();
            newsObj = sbuilderNews.toString();
            SubredditNews objNews = gson.fromJson(newsObj, SubredditNews.class);
            ArrayList<SubredditIN>subredditObjects=objNews.subreddits;
            for(int i=0;i<subredditObjects.size();i++){

                parentItems.add(subredditObjects.get(i).name);
            }

        }catch(Exception e){

        }

    }

    public void setChildData() {

        // Android
      ArrayList<Posts> child = new ArrayList<Posts>();
        ArrayList<String> child1 = new ArrayList<String>();
        String a="";
        /*child.add("Core");
        child.add("Games");
        childItems.add(child);*/
        try {
            FileInputStream newsIn = openFileInput("news.txt");
            String newsObj = "";
            String objEnc="";
            Gson gson=new Gson();
            BufferedReader inputNews = new BufferedReader(new InputStreamReader(newsIn, "UTF-8"));
            StringBuilder sbuilderNews = new StringBuilder();
            newsObj = inputNews.readLine();
            while (newsObj != null) {
                sbuilderNews.append(newsObj);
                newsObj = inputNews.readLine();
                if (newsObj != null) {

                    // sbuilder.append("\n");

                }
            }
            newsIn.close();
            newsObj = sbuilderNews.toString();
            SubredditNews objNews = gson.fromJson(newsObj, SubredditNews.class);
            ArrayList<SubredditIN>subredditObjects=objNews.subreddits;
            for(int i=0;i<objNews.subreddits.size();i++){
                child1=new ArrayList<String>();
                child = new ArrayList<Posts>();
               for(int j=0;j<objNews.subreddits.get(i).posts.size();j++) {
                   subredditObjects.get(i).setURL();
                   a=subredditObjects.get(i).getPostLink();
                   child.add(objNews.subreddits.get(i).posts.get(j));
                    child1.add(objNews.subreddits.get(i).posts.get(j).title);
                   Log.i("this is the link", a);

               }
                childItems.add(child1);
            }

        }catch(Exception e){
            String dd="";
        }


    }

}
