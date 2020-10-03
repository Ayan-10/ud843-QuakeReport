package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    public static final String Log_TAG = QueryUtils.class.getName();
    //making url obj
    private static URL createUrl(String stringUrl){
        URL url = null;

        try {
            url= new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //http request

    private static String makeHttpRequest(URL url) throws IOException{
        String JsonResponse="";
         if(url==null){
             return JsonResponse;
         }
        HttpURLConnection httpURLConnection =null;
        InputStream inputStream = null;
        try{
            httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                JsonResponse = readFromStream(inputStream);
            }else {
                Log.e(Log_TAG,"Error"+httpURLConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(Log_TAG,"problrm",e);
        }finally {
            if (httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
            if(inputStream != null){
                //it can trow ioexptin so ....
                inputStream.close();
            }
        }
        return JsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
                    while(line!=null){
                        output.append(line);
                        line = reader.readLine();
                    }
        }
        return output.toString();
    }

    //public method for interaction
    public static List<Earthquake> fetchEarthqukaeData(String requestUrl){
        //create url
        URL url = createUrl(requestUrl);

        //make http request
        String jsonresponse = null;
        try{
            jsonresponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(Log_TAG,"prblm",e);
        }
        List<Earthquake> earthquakes = extractFeatureFromJson(jsonresponse);

        return earthquakes;
    }
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Earthquake> extractFeatureFromJson(String earthquakeJson) {

        if(TextUtils.isEmpty(earthquakeJson)){
            return null;
        }
        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            JSONObject baseJsonResponse = new JSONObject(earthquakeJson);

            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            for(int i = 0; i <earthquakeArray.length(); i++){
                JSONObject  currentEarthquake= earthquakeArray.getJSONObject(i);

                JSONObject properties = currentEarthquake.getJSONObject("properties");

                double mag= properties.getDouble("mag");

                String loc= properties.getString("place");

                long time= properties.getLong("time");

 //               long timeinmili = Long.parseLong(time);
//
//                Date dateObj = new Date(timeinmili);
//
//                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
//
//                String date = dateFormat.format(dateObj);

                String url = properties.getString("url");

                Earthquake earthquake=new Earthquake(mag, loc, time, url);

                earthquakes.add(earthquake);

            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return  earthquakes;
    }

}