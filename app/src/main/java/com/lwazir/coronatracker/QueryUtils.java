package com.lwazir.coronatracker;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

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
    public static ArrayList<CoronaNews> extractCoronaNews(String requestUrl)  {

        // Create URL object
        URL url = createUrl(requestUrl);
        ArrayList<CoronaNews> corona = null;
        ArrayList<CoronaNews> Response = new ArrayList<CoronaNews>();

        // Perform HTTP request to the URL and receive a JSON response back
        String rssResponse = null;
        try {
            rssResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link CoronaNews}
          Response = extractItemsFromRss(rssResponse);

        // Return the list of {@link CoronaNews}s
        return Response;

    }

    private static ArrayList<CoronaNews> extractItemsFromRss(String rssResponse) {
        ArrayList<CoronaNews> Response = new ArrayList<CoronaNews>();
        rssResponse = rssResponse.trim();
        String pattern1 = "<item>";
        String pattern2 = "</item>";
        String itemElement ="";
        ArrayList<String>  extractedItems = new ArrayList<String>();
        int Itemlength=0;
        while(!rssResponse.startsWith("</channel>"))
        {
            int sIndex = rssResponse.indexOf("<item>")+6;
            int eIndex = rssResponse.indexOf("</item>");
            int length = rssResponse.length();
            if(sIndex<length)
            {
                extractedItems.add(rssResponse.substring(sIndex,eIndex));
               // Itemlength+=extractedItems.size();
                rssResponse = rssResponse.substring(eIndex+7);
            }
        }
        Response=extractDatafromItems(extractedItems);

        return Response;
    }

    private static ArrayList<CoronaNews> extractDatafromItems(ArrayList<String> extractedItems)
    {
        String title = "";
        String url = "";
        String source = "";
        String pubDate = "";
        ArrayList<CoronaNews> Response = new ArrayList<CoronaNews>();
        for (String item: extractedItems)
        {
           title = item.substring(item.indexOf("<title>")+7,item.indexOf("</title>"));
            url = item.substring(item.indexOf("<link>")+6,item.indexOf("</link>"));
            pubDate = item.substring(item.indexOf("<pubDate>")+9,item.indexOf("</pubDate>"));
            item = item.substring(item.indexOf("</description>")+14);
            item=item.replaceAll("<(\\S*?)[^>]*>.*?|<.*? />","@");
            source = item.replaceAll("@","");
            CoronaNews coronaNews = new CoronaNews(source,title,pubDate,url);

            Response.add(coronaNews);
        }
        return Response;
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
    private static String makeHttpRequest(URL url) throws IOException {
        String rssResponse = "";

        // If the URL is null, then return early.

        InputStream stream = null;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        ArrayList<CoronaNews> corona = null;

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
                rssResponse = readFromStream(inputStream);


            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
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
        return rssResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        // Create an empty ArrayList that we can start adding corona news to
       /* ArrayList<CoronaNews> cNews = new ArrayList<>();
        String title = "";
        String publishedAt ="" ;
        String description = "";
        String url = "";
        String sName = "";*/

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = "";

            while ((line=reader.readLine()) != null)
            {
               output.append(line);
            }
        }
        return  output.toString();
    }




}
