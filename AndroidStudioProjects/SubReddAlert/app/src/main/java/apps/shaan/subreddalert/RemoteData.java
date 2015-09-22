package apps.shaan.subreddalert;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

/**
 * Created by shaan on 6/25/15.
 */
public class RemoteData {
    /**
     * This methods returns a Connection to the specified URL,
     * with necessary properties like timeout and user-agent
     * set to your requirements.
     *
     * @param url
     * @return
     */
    public static HttpURLConnection getConnection(String url){
        System.out.println("URL: "+url);
        HttpURLConnection hcon = null;
        try {
            hcon=(HttpURLConnection)new URL(url).openConnection();
            hcon.setReadTimeout(30000); // Timeout at 30 seconds
            hcon.setRequestProperty("User-Agent", "Alien V1.0");
        } catch (MalformedURLException e) {
            Log.e("getConnection()",
                    "Invalid URL: "+e.toString());
        } catch (IOException e) {
            Log.e("getConnection()",
                    "Could not connect: "+e.toString());
        }
        return hcon;
    }


    /**
     * A very handy utility method that reads the contents of a URL
     * and returns them as a String.
     *
     * @param url
     * @return
     */
    public static String readContents(String url){
        StringBuilder response=null;
       try{
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            String a=website.getContent().toString();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();
        }catch(Exception e){
            String a=e.getMessage();
           Log.i("the exception", a);

       }

        return response.toString();
    }
}
