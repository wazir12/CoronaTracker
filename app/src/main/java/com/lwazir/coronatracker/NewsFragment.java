package com.lwazir.coronatracker;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class NewsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<CoronaNews>> {

    private static final String LOG_TAG = NewsFragment.class.getName();
    android.support.v4.app.LoaderManager loaderManager = null;
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
    public NewsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        // Find a reference to the {@link ListView} in the layout
        ListView CoronaNewsListView = (ListView) v.findViewById(R.id.list);

        mEmptyStateTextView = (TextView) v.findViewById(R.id.empty_view);
        CoronaNewsListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of corona news as input
        mAdapter = new CoronaNewsAdapter(getContext(), new ArrayList<CoronaNews>());

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
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
          loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).

         loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = v.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
        // Inflate the layout for this fragment
        return v;
    }


    @NonNull
    @Override
    public Loader<List<CoronaNews>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CoronaNewsLoader(getContext(), CORONA_NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<CoronaNews>> loader, List<CoronaNews> data) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = getView().findViewById(R.id.loading_indicator);
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
    public void onLoaderReset(@NonNull Loader<List<CoronaNews>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
