package edu.eiu.tourist_app;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PlacesRepository {

    public LiveData<List<WikipediaPage>> getTouristSites() {
        final MutableLiveData<List<WikipediaPage>> liveData = new MutableLiveData<>();

        AsyncTask<Void, Void, List<WikipediaPage>> task = new AsyncTask<Void, Void, List<WikipediaPage>>() {
            @Override
            protected List<WikipediaPage> doInBackground(Void[] objects) {
                List<WikipediaPage> places = null;
                try {
                    String response = doRequest();
                    places = handleResponse(response);
                    liveData.postValue(places);
                } catch (Exception ex) {
                    Log.e("PlacesRepository", "FAILED!!!" + ex.getMessage(), ex);
                    //FIXME handle errors
                }
                return places;
            }

            @Override
            protected void onPostExecute(List<WikipediaPage> places) {
                liveData.setValue(places);
            }
        };
        task.execute();

        return liveData;
    }

    private String doRequest() throws Exception {
        String urlString = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=pageimages&generator=geosearch&pithumbsize=250&ggscoord=10.7712404%7C106.6978887&ggsradius=10000";

        StringBuilder response = new StringBuilder();

        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            urlConnection.disconnect();
        }
        return response.toString();
    }

    private List<WikipediaPage> handleResponse(String respone) {
        Gson gson = new Gson();
        WikipediaRespone wikipediaRespone = gson.fromJson(respone, WikipediaRespone.class);
        QueryResponse queryResponse = wikipediaRespone.getQuery();
        Map<Integer, WikipediaPage> pageMap = queryResponse.getPages();
        List<WikipediaPage> pages = new ArrayList<>(pageMap.values());

        return pages;
    }

//    private List<String> handleResponse(String respone) throws JSONException {
//        List<String> placesNames = new ArrayList<>();
//
//        JSONObject responeObject = new JSONObject(respone);
//        JSONObject queryObject = responeObject.getJSONObject("query");
//        JSONObject pagesObject = queryObject.getJSONObject("pages");
//
//        Iterator<String> keys = pagesObject.keys();
//
//        while (keys.hasNext()){
//            JSONObject page = pagesObject.getJSONObject(keys.next());
//            String title = page.getString("title");
//            placesNames.add(title);
//        }
////        liveData.postValue(placesNames);
//
//        return placesNames ;
//    }
}
