package com.example.coronatracker;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {


    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Sample JSON response
     */
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
    public static ArrayList<CoronaNews> extractCoronaNews(String requestUrl) throws XmlPullParserException {

        // Create URL object
        URL url = createUrl(requestUrl);
        ArrayList<CoronaNews> corona = null;

        // Perform HTTP request to the URL and receive a JSON response back
        InputStream rssResponse = null;
        try {
            corona = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        //  ArrayList<CoronaNews> corona = extractArticlesFromJson(rssResponse);

        // Return the list of {@link CoronaNews}s
        return corona;

    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static ArrayList<CoronaNews> makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.

        InputStream stream = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        ArrayList<CoronaNews> corona =null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                //   jsonResponse = readFromStream(inputStream);
                corona = extractArticlesFromJson(inputStream);

            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException | XmlPullParserException e) {
            Log.e(LOG_TAG, "Problem retrieving the Corona JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
    return corona;
    }



    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link CoronaNews} objects that has been built up from
     * parsing the given JSON response.
     */
    private static ArrayList<CoronaNews> extractArticlesFromJson(InputStream stream) throws XmlPullParserException {
        // If the JSON string is empty or null, then return early.
        if (stream == null) {
            return null;
        }

        // Create an empty ArrayList that we can start adding corona news to
        ArrayList<CoronaNews> cNews = new ArrayList<>();
        String title=null;
        String publishedAt =null ;
        String description =null;
        String url=null;
        String sName=null;
        boolean parsingComplete = true;
        try {

            XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser myparser = xmlFactoryObject.newPullParser();

            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(stream, null);

            int event;
            String text = null;


                event = myparser.getEventType();

                while (event != XmlPullParser.END_DOCUMENT) {
                    String name = myparser.getName();

                    switch (event) {
                        case XmlPullParser.START_TAG:
                            break;

                        case XmlPullParser.TEXT:
                            text = myparser.getText();
                            break;

                        case XmlPullParser.END_TAG:

                            if (name.equals("title")) {
                                title = text;
                            } else if (name.equals("link")) {
                                url = text;
                            } else if (name.equals("description")) {
                                description = text;
                            } else if (name.equals("pubDate")) {
                                publishedAt = text;
                            } else if (name.equals("source")) {
                                sName = text;
                            } else {
                            }

                            break;
                    }

                    event = myparser.next();
                }

                parsingComplete = false;


                // Create a new {@link CoronaNews} object with the ,
                // and url from the JSON response.
                CoronaNews coNews = new CoronaNews(sName, title, description, publishedAt, url);

                // Add the new {@link CoronaNews} to the list of CoronaNews.
                cNews.add(coNews);




        }catch (XmlPullParserException | IOException e) {

            Log.e("QueryUtils", "Problem parsing the CoronaNews JSON results", e);
        }
        // Return the list of CoronaNews
        return cNews;
    }
}
