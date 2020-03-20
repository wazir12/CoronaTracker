package com.example.coronatracker;

import android.content.AsyncTaskLoader;
import android.content.Context;


import org.xmlpull.v1.XmlPullParserException;

import java.util.List;

/**
 * Loads a list of Corona News by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class CoronaNewsLoader extends AsyncTaskLoader<List<CoronaNews>> {

    /** Tag for log messages */
    private static final String LOG_TAG = CoronaNewsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link CoronaNewsLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public CoronaNewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<CoronaNews> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<CoronaNews> coronaNews = null;
        try {
            coronaNews = QueryUtils.extractCoronaNews(mUrl);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return coronaNews;
    }
}
