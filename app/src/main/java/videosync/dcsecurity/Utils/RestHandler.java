package videosync.dcsecurity.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RestHandler {
    // main url
    private static String url = "https://dcapp.frozendroid.com/";

    // restPath is the path after URL
    // like url + /api/code/1/locationname
    public static String POST(String restPath, HashMap<String, String> values){
        try{
            URL url = new URL(RestHandler.url + restPath);
            HttpURLConnection client = (HttpURLConnection) url.openConnection();

            client.setRequestMethod("POST");
            client.setDoOutput(true);
            client.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            client.setRequestProperty("Accept","*/*");

            if(values != null){
                Iterator it = values.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    client.addRequestProperty((String)pair.getKey(),(String)pair.getValue());
                    it.remove();
                }
            }

            OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());

            outputPost.flush();
            outputPost.close();

            System.out.println("RESPONSECODE: " + client.getResponseCode());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return String.valueOf(response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Iets gaat fout lol";
    }

    public static String getLocationName(int locationId){

        try {
            JSONObject jsonObject = new JSONObject(RestHandler.POST("/api/code/" + locationId + "/locationname", null));
            if(jsonObject.getString("name").length() > 0)
                return jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String staffExists(int staffId, HashMap<String,String> map){

        return null;
    }
}
