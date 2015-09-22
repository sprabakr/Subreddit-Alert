package apps.shaan.subreddalert;

/**
 * Created by shaan on 6/25/15.
 */
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class PostsRank {
    private final String URL_TEMPLATE= "http://www.reddit.com/r/SUBREDDIT_NAME/" +".json" +"?after=AFTER";

    String subreddit;
    String url;
    String after;
    ArrayList<String>topPosts=new ArrayList<String>();
    int topAmt;
    PostsRank(String sr){
        subreddit=sr;
        after="";
        generateURL();
    }
    private void generateURL(){
        url=URL_TEMPLATE.replace("SUBREDDIT_NAME", subreddit);
        url=url.replace("AFTER", after);
    }
    ArrayList<Posts> fetchPosts(int topAmt){
        String raw=RemoteData.readContents(url);
        ArrayList<Posts> list=new ArrayList<Posts>();
        Posts p=new Posts();
        try{
            JSONObject data=new JSONObject(raw)
                    .getJSONObject("data");
            JSONArray children=data.getJSONArray("children");

            //Using this property we can fetch the next set of
            //posts from the same subreddit
            after=data.getString("after");

            for(int i=0;i<topAmt;i++){
                JSONObject cur=children.getJSONObject(i)
                        .getJSONObject("data");
                 p=new Posts();
                p.title=cur.optString("title");
                topPosts.add(p.title);
                p.url=cur.optString("url");
                p.numComments=cur.optInt("num_comments");
                p.points=cur.optInt("score");
                p.author=cur.optString("author");
                p.subreddit=cur.optString("subreddit");
                p.permalink=cur.optString("permalink");
                p.domain=cur.optString("domain");
                p.id=cur.optString("id");
                list.add(p);
                if(p.title!=null)
                    list.add(p);
            }
        }catch(Exception e){
            Log.e("fetchPosts()", e.toString());
        }
        return list;
    }





}
