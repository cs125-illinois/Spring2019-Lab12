package edu.illinois.cs.cs125.spring2019.lab12;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Main class for our UI design lab.
 */
public final class MainActivity extends AppCompatActivity {
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "Lab12:Main";

    /** Request queue for our API requests. */
    private static RequestQueue requestQueue;

    /**
     * Run when this activity comes to the foreground.
     *
     * @param savedInstanceState unused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up the queue for our API requests
        requestQueue = Volley.newRequestQueue(this);

        setContentView(R.layout.activity_main);

        final Button lookupAddress = findViewById(R.id.lookup_address);
        lookupAddress.setOnClickListener(v -> startAPICall("192.17.96.8"));
    }

    /**
     * Run when this activity is no longer visible.
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Make a call to the IP geolocation API.
     *
     * @param ipAddress IP address to look up
     */
    void startAPICall(final String ipAddress) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://ipinfo.io/" + ipAddress + "/json",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            apiCallDone(response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(final VolleyError error) {
                            Log.e(TAG, error.toString());
                        }
                    });
            jsonObjectRequest.setShouldCache(false);
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle the response from our IP geolocation API.
     *
     * @param response response from our IP geolocation API.
     */
    void apiCallDone(final JSONObject response) {
        try {
            Log.d(TAG, response.toString(2));
            // Example of how to pull a field off the returned JSON object
            final TextView addressInfo = findViewById(R.id.address_info);
            addressInfo.setText("Hostname: " + response.get("hostname").toString());
            addressInfo.append("\nCity: " + response.get("city").toString());
            addressInfo.append("\nRegion: " + response.get("region").toString());
            addressInfo.append("\nCountry: " + response.get("country").toString());
            addressInfo.append("\nLocation: " + response.get("loc").toString());
            addressInfo.append("\nPostal: " + response.get("postal").toString());
            addressInfo.append("\nOrganization: " + response.get("org").toString());
            Log.i(TAG, response.get("hostname").toString());
            Log.i(TAG, response.get("city").toString());
            Log.i(TAG, response.get("region").toString());
            Log.i(TAG, response.get("country").toString());
            Log.i(TAG, response.get("loc").toString());
            Log.i(TAG, response.get("postal").toString());
            Log.i(TAG, response.get("org").toString());
        } catch (JSONException ignored) { }
    }
}
