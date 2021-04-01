package apps.shaan.subreddalert;

import java.util.ArrayList;

/**
 * Created by shaan on 6/27/15.
 */
public class SubredditNews {
    ArrayList<SubredditIN> subreddits=new ArrayList<SubredditIN>();
    SubredditNews(){
        subreddits.clear();
    }
    public void newSub(String subName, Posts post){
        SubredditIN aSub=new SubredditIN();
        Posts aPost=new Posts();
        if(subreddits.isEmpty()){
            aSub.name=subName;
            aSub.posts.add(post);
            subreddits.add(aSub);
            return;
        }

        for(int i=0;i<subreddits.size();i++){
            if(subreddits.get(i).name.equals(subName)){
                subreddits.get(i).posts.add(post);
                return;
            }
        }
        aSub.name=subName;
        aSub.posts.add(post);
        subreddits.add(aSub);
        return;




    }




}
