package com.example.coronatracker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class QueryUtils {
    /** Sample JSON response */
    private static final String SAMPLE_JSON_RESPONSE = "{\"status\":\"ok\",\"totalResults\":55745,\"articles\":[{\"source\":{\"id\":null,\"name\":\"Pib.gov.in\"},\"author\":null,\"title\":\"Suo-Moto statement by Dr. Harsh Vardhan, Minister for Health and F.W. ON 5th March, 2020 in Rajya Sabha in view of large number of reported cases across the world and reported cases in India of Novel Coronavirus (COVID-19) and the steps taken by the Government of India\",\"description\":\"In continuation of the statement made by me in Rajya Sabha on 7th February and Lok Sabha on 10th February, I would further like to update the Hon’ble members on present situation related to the outbreak of novel Corona Virus Disease and the actions taken by t…\",\"url\":\"https://pib.gov.in/newsite/PrintRelease.aspx?relid=199857\",\"urlToImage\":null,\"publishedAt\":\"2020-03-05T06:17:00Z\",\"content\":null},\n" +
            "{\"source\":{\"id\":null,\"name\":\"Vnexpress.net\"},\"author\":\"VnExpress\",\"title\":\"Nửa tỷ người trên thế giới ở nhà\",\"description\":\"Khoảng 500 triệu người được đặt dưới các biện pháp phong toả trên khắp thế giới khi Covid-19 từ Trung Quốc lan rộng toàn cầu.\",\"url\":\"https://vnexpress.net/the-gioi/nua-ty-nguoi-tren-the-gioi-o-nha-4071600.html\",\"urlToImage\":\"https://vcdn-vnexpress.vnecdn.net/2020/03/19/3000-1584593322-6262-1584593672_1200x0.jpg\",\"publishedAt\":\"2020-03-19T05:04:59Z\",\"content\":\"Khong 500 triu ngi c t di các bin pháp phong to trên khp th gii khi Covid-19 t Trung Quc lan rng toàn cu.\\r\\nCovid-19 khi phát thành ph V Hán, tnh H Bc, Trung Quc t tháng 12/2019 và sau ba tháng ã tr thành i dch toàn cu vi hn 210.000 ngi nhim, trong ó gn 9.000 … [+2051 chars]\"},{\"source\":{\"id\":\"abc-news\",\"name\":\"ABC News\"},\"author\":\"DON THOMPSON and JOHN ANTCZAK Associated Press\",\"title\":\"State to spend $150 million to protect homeless from virus\",\"description\":\"As worries about the spread of the coronavirus confine millions of Californians to their homes, concern is growing about those who have no homes in which to shelter\",\"url\":\"https://abcnews.go.com/Health/wireStory/state-spend-150-million-protect-homeless-virus-69679391\",\"urlToImage\":\"https://s.abcnews.com/images/Health/WireAP_efe21c5e20c44e74b6b4d25fc53ed148_16x9_992.jpg\",\"publishedAt\":\"2020-03-19T05:03:59Z\",\"content\":\"SACRAMENTO, Calif. -- \\r\\nAs worries about the spread of the coronavirus confine millions of Californians to their homes, concern is growing about those who have no homes in which to shelter.\\r\\nCalifornia has more than 150,000 homeless people, the most in the na… [+6807 chars]\"}" +
            "]}";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link CoronaNews} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<CoronaNews> extractCoronaNews() {

        // Create an empty ArrayList that we can start adding corona news to
        ArrayList<CoronaNews> cNews = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the SAMPLE_JSON_RESPONSE string
            JSONObject baseJsonResponse = new JSONObject(SAMPLE_JSON_RESPONSE);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or corona News).
            JSONArray CoronaNews = baseJsonResponse.getJSONArray("articles");

            // For each coronaNews in the coronaNewsArray, create an {@link CoronaNews} object
            for (int i = 0; i < CoronaNews.length(); i++) {

                // Get a single corona news at position i within the list of Corona News
                JSONObject currentCoronaNews = CoronaNews.getJSONObject(i);

                // For a given CoronaNews, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                // for that corona News.
                JSONObject properties = currentCoronaNews.getJSONObject("source");
                //Extract the value of source name
                String sName=properties.getString("name");

                // Extract the value for the key called "title"
                String title = currentCoronaNews.getString("title");

                // Extract the value for the key called "content"
                String content =  currentCoronaNews.getString("content");

                // Extract the value for the key called "description"
                String description = currentCoronaNews.getString("description");

                // Extract the value for the key called "publishedAt"
                String publishedAt = currentCoronaNews.getString("publishedAt");

                // Extract the value for the key called "url"
                String url = currentCoronaNews.getString("url");



                // Create a new {@link CoronaNews} object with the ,
                // and url from the JSON response.
                CoronaNews coNews = new CoronaNews(sName,title,content,description,publishedAt,url);

                // Add the new {@link CoronaNews} to the list of CoronaNews.
               cNews.add(coNews);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the CoronaNews JSON results", e);
        }

        // Return the list of CoronaNews
        return cNews;
    }

}
