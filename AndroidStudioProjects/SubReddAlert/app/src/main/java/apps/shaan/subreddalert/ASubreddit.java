package apps.shaan.subreddalert;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shaan on 6/25/15.
 */
public class ASubreddit {
    int topAmount;
    String name;
    String url;
    String after;
   String linkCont;
    ArrayList<Posts>list=new ArrayList<Posts>();
    private final String URL_TEMPLATE= "http://www.reddit.com/r/SUBREDDIT_NAME/" +".json" +"?after=AFTER";
    ArrayList<String>newPosts=new ArrayList<String>();
    ArrayList<String>oldPosts=new ArrayList<String>();
    public ASubreddit(){
        topAmount=10;
        after="";

    }
    public void generateURL(){
        url=URL_TEMPLATE.replace("SUBREDDIT_NAME", name);
        url=url.replace("AFTER", after);
    }
   public ArrayList<Posts> fetchPosts(){
        String raw=linkCont;
        ArrayList<String>topPosts=new ArrayList<String>();
        Posts p=new Posts();
       list.clear();
        try{
            JSONObject data=new JSONObject(raw)
                    .getJSONObject("data");
            JSONArray children=data.getJSONArray("children");

            //Using this property we can fetch the next set of
            //posts from the same subreddit
            after=data.getString("after");

            for(int i=0;i<topAmount;i++){
                p=new Posts();
                JSONObject cur=children.getJSONObject(i)
                        .getJSONObject("data");
                p.url=cur.optString("url");
                p.title=cur.optString("title");
                p.permalink=cur.optString("permalink");
                list.add(p);
                //Posts p=new Posts();
               // p.title=cur.optString("title");
                //String a=p.title;
                topPosts.add(cur.optString("title"));


            }
            String a;
        }catch(Exception e){
            Log.e("fetchPosts()", e.toString());
        }
        oldPosts=topPosts;
        return list;

    }
    public ArrayList<String> updatePosts(){
       // postList=postList.subList(0,topAmount-1);
        ArrayList<String> comp=new ArrayList<String>();
        try {
            ContentUrl down = new ContentUrl();
            String content = down.execute(url).get();
            linkCont=content;
            ArrayList<String> postList = oldPosts;
            fetchPosts();
            int length1 = postList.size();
            int length = oldPosts.size();
            newPosts.clear();
            boolean is=false;
            for (int i = 0; i < length; i++) {
                is=false;
                for (int j = 0; j < length1; j++) {
                    if (oldPosts.get(i).equals(postList.get(j))) {
                        is=true;
                    }
                }
                if(is==false)
                newPosts.add(oldPosts.get(i));

            }
        }catch (Exception e){
            String esd="";

        }
        return newPosts;

    }
    private class ContentUrl extends AsyncTask<String, Void, String> {
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
