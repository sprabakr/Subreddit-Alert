package apps.shaan.subreddalert;

/**
 * Created by shaan on 6/28/15.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.BaseExpandableListAdapter;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup; import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class ExpandableAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<Object> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;
    Context mContext;

    public ExpandableAdapter(Context context,ArrayList<String> parents, ArrayList<Object> childern) {
        this.parentItems = parents;
        this.childtems = childern;
        mContext = context;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }




    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        child = (ArrayList<String>) childtems.get(groupPosition);

        TextView textView = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group, null);
        }

        textView = (TextView) convertView.findViewById(R.id.textView1);
        textView.setText(child.get(childPosition));
        textView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    String link="";
                    ArrayList<String>a=child;
                    FileInputStream fin = mContext.openFileInput("news.txt");
                    String test=child.get(childPosition);
                    String objEnc = "";
                    BufferedReader br = new BufferedReader(new InputStreamReader(fin, "UTF-8"));

                    StringBuilder sbuilder = new StringBuilder();
                    objEnc = br.readLine();
                    while (objEnc != null) {
                        sbuilder.append(objEnc);
                        objEnc = br.readLine();
                        if (objEnc != null) {

                            // sbuilder.append("\n");

                        }
                    }
                    br.close();
                    fin.close();
                    objEnc=sbuilder.toString();
                    String aa=objEnc;
                    Gson gson = new Gson();
                    SubredditNews obj = gson.fromJson(objEnc, SubredditNews.class);
                    for(int i=0;i<obj.subreddits.size();i++){
                        for(int j=0;j<obj.subreddits.get(i).posts.size();j++){
                            if(obj.subreddits.get(i).posts.get(j).title.equals(test)){
                                link="https://www.reddit.com"+obj.subreddits.get(i).posts.get(j).permalink;
                            }

                        }
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    activity.startActivity(browserIntent);
                }catch (Exception e){
                    String q="";
                }
            }
        });
        //textView.setText(Html.fromHtml("<a href=http://www.stackoverflow.com> STACK OVERFLOW "));
        // textView.setMovementMethod(LinkMovementMethod.getInstance());
        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    String link="";
                    ArrayList<String>a=child;
                    FileInputStream fin = mContext.openFileInput("news.txt");
                    String test=child.get(childPosition);
                    String objEnc = "";
                    BufferedReader br = new BufferedReader(new InputStreamReader(fin, "UTF-8"));

                    StringBuilder sbuilder = new StringBuilder();
                    objEnc = br.readLine();
                    while (objEnc != null) {
                        sbuilder.append(objEnc);
                        objEnc = br.readLine();
                        if (objEnc != null) {

                            // sbuilder.append("\n");

                        }
                    }
                    br.close();
                    fin.close();
                    objEnc=sbuilder.toString();
                    String aa=objEnc;
                    Gson gson = new Gson();
                    SubredditNews obj = gson.fromJson(objEnc, SubredditNews.class);
                    for(int i=0;i<obj.subreddits.size();i++){
                        for(int j=0;j<obj.subreddits.get(i).posts.size();j++){
                            if(obj.subreddits.get(i).posts.get(j).title.equals(test)){
                                link="https://www.reddit.com"+obj.subreddits.get(i).posts.get(j).permalink;
                            }

                        }
                    }
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    activity.startActivity(browserIntent);
                }catch (Exception e){
                    String q="";
                }
            }
        });

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child, null);
        }

        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
        ((CheckedTextView) convertView).setChecked(isExpanded);


        return convertView;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) childtems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
