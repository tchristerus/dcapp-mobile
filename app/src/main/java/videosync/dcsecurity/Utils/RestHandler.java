package videosync.dcsecurity.Utils;

import android.support.test.espresso.core.deps.guava.base.Charsets;
import android.support.test.espresso.core.deps.guava.io.ByteStreams;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class RestHandler {
    // main url
    private static String url = "https://dcapp.frozendroid.com/";

    // restPath is the path after URL
    // like url + /api/code/1/locationname
    public static String POST(String restPath){
        try{
            URL url = new URL(RestHandler.url);
            HttpURLConnection client = (HttpURLConnection) url.openConnection();

            client.setRequestMethod("POST");
            client.setDoOutput(true);
            client.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
            client.setRequestProperty("Accept","*/*");

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
}
