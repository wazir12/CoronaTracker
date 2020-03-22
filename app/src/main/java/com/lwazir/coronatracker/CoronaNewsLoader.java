package com.lwazir.coronatracker;

//import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;


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

        // Perform the network request, parse the response, and extract a list of News.
        List<CoronaNews> coronaNews = null;

            coronaNews = QueryUtils.extractCoronaNews(mUrl);

        return coronaNews;
    }
}
