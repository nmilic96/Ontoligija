package com.example.ontoligija;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Adapter mAdapter;
    private ArrayList<Resource> mResourceList;
    private RequestQueue mRequestQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mResourceList = new ArrayList<>();

        mRequestQ = Volley.newRequestQueue(this);
        parseJSON("/");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                parseJSON("/" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchView.getQuery().length() == 0) {
                    parseJSON("/");
                }
                return false;
            }
        });

        return  super.onCreateOptionsMenu(menu);
    }

    private void parseJSON(String query) {
        String url = "https://oziz.ffos.hr/nastava20192020/nmilic_19/ontologija/search" + query;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            mResourceList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject resource = response.getJSONObject(i);
                                String resourceName  = resource.getString("resourceName");
                                String resourceType  = resource.getString("resourceType");
                                String resourceData  = resource.getString("resourceData");

                                resourceData = resourceData.replace("\n", System.getProperty("line.separator"));
                                System.out.println(resourceData);


                                if (resourceType.trim().length() < 1) {
                                    resourceType = "Nema vrijednost";
                                }

                                if (resourceData.trim().length() < 1) {
                                    resourceData = "Nema vrijednost";
                                }


                                mResourceList.add(new Resource(resourceName, resourceType, resourceData));

                            }

                            mAdapter = new Adapter(MainActivity.this, mResourceList);
                            mRecyclerView.setAdapter(mAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        mRequestQ.add(request);
    }
}