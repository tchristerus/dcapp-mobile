package videosync.dcsecurity.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RestHandler {
    // main url
    private static String url = "https://dcapp.frozendroid.com/";

    // restPath is the path after URL
    // like url + /api/code/1/locationname
    public static String POST(String restPath, HashMap<String, String> values) {
        try {
            URL url = new URL(RestHandler.url + restPath);
            HttpURLConnection client = (HttpURLConnection) url.openConnection();

            client.setRequestMethod("POST");
            client.setDoOutput(true);
            client.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
            client.setRequestProperty("Accept", "*/*");

            OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());

            if (values != null) {
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(outputPost, "UTF-8"));
                writer.write(getPostDataString(values));
                writer.flush();
            }

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
            System.out.println("RESPONSE: " + String.valueOf(response));
            if(client.getResponseCode() != 200)
                return null;
            return String.valueOf(response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLocationName(int locationId) {
        try {
            JSONObject jsonObject = new JSONObject(RestHandler.POST("/api/code/" + locationId + "/locationname", null));
            if (jsonObject.getString("name").length() > 0)
                return jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean staffExists(int staffId) {
        try {
            HashMap<String, String> values = new HashMap<>();
            values.put("staff_nr", Integer.toString(staffId));
            JSONObject jsonObject = new JSONObject(RestHandler.POST("api/staff/exists/", values));
            if (jsonObject.getString("exists").length() > 0)
                return (jsonObject.getString("exists").equals("true")) ? true : false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean addEntry(int locationId, int staffId, String particularities) {
        //voor api/record/new is het "location_id", "staff_id" en "particularities"
        HashMap<String, String> values = new HashMap<>();
        values.put("location_id", Integer.toString(locationId));
        values.put("staff_id", Integer.toString(staffId));
        values.put("particularities", particularities);
        if(RestHandler.POST("api/record/new/", values) != null)
            return true;
        return false;
    }

    // only used to build post data string
    public static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
