package apps.shaan.subreddalert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;


public class subreddit extends ActionBarActivity {
     ArrayList<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subreddit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ListView subList = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, arrayList);

        registerForContextMenu(subList);



        subList.setAdapter(adapter);
        subList.setBackgroundColor(-16777216);
       // registerForContextMenu(subList);

        try {
            FileInputStream fin = openFileInput("All_Subs.txt");
            int c;
            String temp = "";
            while ((c = fin.read()) != -1) {
                if (c == 32&&!temp.equals("")) {
                    arrayList.add(temp);
                    subList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    temp = "";
                }
                temp = temp + Character.toString((char) c);


            }

                arrayList.add(temp);
                subList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            fin.close();
        } catch (Exception e) {

        }




    }
    public void delete(String to,int position){
        String toDelete=to.trim();
        final ListView subList = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, arrayList);
        try {
            FileInputStream inp = openFileInput("subs.txt");
            int num;
            String objEnc = "";
            BufferedReader input = new BufferedReader(new InputStreamReader(inp, "UTF-8"));

            StringBuilder sbuilder = new StringBuilder();

            objEnc = input.readLine();

            while (objEnc != null) {
                sbuilder.append(objEnc);
                objEnc = input.readLine();
                if (objEnc != null) {

                    //sbuilder.append("\n");

                }
            }
            objEnc=sbuilder.toString();
            inp.close();
            Gson gson = new Gson();
            ManySubs obj = gson.fromJson(objEnc, ManySubs.class);
            //search the ManySubs object
            int length = obj.newPosts.size();
            int i = 0;
            while (i < length) {
                String test = obj.newPosts.get(i).name.trim();
                if ((obj.newPosts.get(i).name).equals(toDelete)) {
                    obj.newPosts.remove(i);
                    break;
                }
                i++;
            }
            FileOutputStream out = openFileOutput("subs.txt", MODE_PRIVATE);
            String json = gson.toJson(obj);
            out.write(json.getBytes());
            out.close();

        } catch (Exception e) {

        }
        //delete from All_Subs.txt end
        //delete from list
        arrayList.remove(position);
        subList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        try {

            FileInputStream fin = openFileInput("All_Subs.txt");
            FileOutputStream fOut = openFileOutput("All_Subs.txt", MODE_PRIVATE);
            fOut.write("".getBytes());
            fOut.close();
            FileOutputStream fOut1 = openFileOutput("All_Subs.txt", MODE_APPEND);

            //PrintWriter writer = new PrintWriter("All_Subs");
            //writer.print("");
            //writer.close();
            String temp1 = "";
            int cc;
            while ((cc = fin.read()) != -1) {
                temp1 = temp1 + Character.toString((char) cc);

            }
            Toast.makeText(getBaseContext(), "he" + temp1, Toast.LENGTH_SHORT).show();
            String temp = "";
            int c;
            int i = 0;
            while (arrayList.size() > i) {
                temp = arrayList.get(i);

                if (toDelete.toString().equals(temp)) {
                    continue;
                }


                fOut1.write(temp.getBytes());
                System.out.println(temp);

                i++;


            }
            fOut1.close();
        } catch (Exception e) {

        }


    }




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_subreddit, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position= info.position;
        ListView subList = (ListView) findViewById(R.id.listView);
        View a=subList.findViewById(item.getItemId());
       String b= arrayList.get(position);
        int i=1;
        switch (item.getItemId()) {
            case R.id.delete:
                delete(b,position);
                return true;
            case R.id.top:
                //deleteNote(info.id);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
