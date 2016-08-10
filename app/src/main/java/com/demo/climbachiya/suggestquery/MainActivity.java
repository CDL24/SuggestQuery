package com.demo.climbachiya.suggestquery;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import adapters.QueryAdapter;
import parsers.HandleXML;
import utility.Utility;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    static final String baseUrl = "http://suggestqueries.google.com/complete/search?output=XML&hl=en&q=";
    private HandleXML obj;
    ProgressBar progressBar;
    EditText edtQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUIControls();
    }

    //Init class UI controls
    private void initUIControls() {
        recycler = (RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        edtQuery = (EditText) findViewById(R.id.edit_query_box);
    }

    //Get query result
    public void onSearchQueryResult(View view) {
        if (Utility.hasConnection(this)) {
            if (!edtQuery.getText().toString().isEmpty()) {
                new GetSuggestionsAsync(edtQuery.getText().toString()).execute();
            }
        }else{
            Toast.makeText(MainActivity.this, "No internet available!", Toast.LENGTH_SHORT).show();
        }

    }

    //AsyncTask for getting xml data with parsing
    public class GetSuggestionsAsync extends AsyncTask<String, String, String> {

        String query;

        public GetSuggestionsAsync(String query) {
            this.query = query;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utility.listQueryData.clear();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                obj = new HandleXML(baseUrl + query);
                obj.fetchXML();
            }catch (Exception e){}

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);

            if (Utility.listQueryData != null && Utility.listQueryData.size() > 0) {
                recycler.setVisibility(View.VISIBLE);
                findViewById(R.id.text_no_data_found).setVisibility(View.INVISIBLE);
                setupViews();
            } else {
                recycler.setVisibility(View.INVISIBLE);
                findViewById(R.id.text_no_data_found).setVisibility(View.VISIBLE);
            }

        }
    }

    //Setup recyleview data
    private void setupViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(new QueryAdapter(this, Utility.listQueryData));
    }
}