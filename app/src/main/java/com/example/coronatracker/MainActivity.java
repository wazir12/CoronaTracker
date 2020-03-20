package com.example.coronatracker;


import android.app.LoaderManager;
import android.app.LoaderManager.*;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<CoronaNews>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    /** URL for corona data from the News API */
    private static final String CORONA_NEWS_REQUEST_URL ="https://news.google.com/rss/search?q=Corona%20Case%20update%20in%20india&hl=en-IN&gl=IN&ceid=IN:en";



    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    /** Adapter for the list of earthquakes */
    private CoronaNewsAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView CoronaNewsListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        CoronaNewsListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of corona news as input
        mAdapter = new CoronaNewsAdapter(this, new ArrayList<CoronaNews>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        CoronaNewsListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected CoronaNews.
        CoronaNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current Corona News that was clicked on
                CoronaNews currentCoronaNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri coronaUri = Uri.parse(currentCoronaNews.getUrl());

                // Create a new intent to view the Corona URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, coronaUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            android.app.LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).

            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }



    @Override
    public Loader<List<CoronaNews>> onCreateLoader(int id, Bundle args) {
        return new CoronaNewsLoader(this, CORONA_NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<CoronaNews>> loader, List<CoronaNews> data) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No Corona News Found found."
        mEmptyStateTextView.setText(R.string.no_corona_news);


        // If there is a valid list of {@link CoronaNews}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<CoronaNews>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mbEmbeddedIndiaStateWiseWebView) {
            Intent settingsIntent = new Intent(this, india_statewiise_corona_cases.class);
            startActivity(settingsIntent);
            return true;
        }
        else if (id == R.id.mbEmbeddedWebView)
        {
            Intent settingsIntent = new Intent(this, web_view_for_corona_map.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
