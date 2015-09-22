package apps.shaan.subreddalert;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaan on 6/27/15.
 */
public class SubredditIN {
    String name;
    String url;
    ArrayList<Posts>posts=new ArrayList<Posts>();
    private final String URL_TEMPLATE= "http://www.reddit.com/r/SUBREDDIT_NAME/" +".json" +"?after=AFTER";


    SubredditIN(){
        name="";
        url="";
    }
    public String getPostLink(){
        String ret="";
        try {
            String raw = RemoteData.readContents(url);
            List<Posts> list = new ArrayList<Posts>();
            JSONObject data = new JSONObject(raw)
                    .getJSONObject("data");
            JSONArray children = data.getJSONArray("children");
            JSONObject cur = children.getJSONObject(0)
                    .getJSONObject("data");
            ret=cur.optString("url");

        }catch(Exception e){

        }
        return ret;

    }
    public void setURL(){
        url=URL_TEMPLATE.replace("SUBREDDIT_NAME", name);
        url=url.replace("AFTER", "");
    }
}
