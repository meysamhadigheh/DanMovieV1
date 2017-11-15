package ir.meysam.com.danmoviev1.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.meysam.com.danmoviev1.R;
import ir.meysam.com.danmoviev1.helper.FontsOverride;
import ir.meysam.com.danmoviev1.helper.HttpHandler;
import ir.meysam.com.danmoviev1.helper.global;

public class TvSeries_info extends AppCompatActivity {

    global globalVar = global.getInstance();
    String url = "http://api.tvmaze.com/singlesearch/shows?q=";
    String url_show = "http://api.tvmaze.com/shows/";


    ImageView wallpaper;
    TextView name, rate, description, geners;
    String imagelink_j, rate_j, summary_j, id_j, geners_j;
    ProgressBar progressbar;

    Button download_btn;



    ArrayList<String> seasons_list = new ArrayList<>();

    List<String> seasons_number = new ArrayList<>();
    List<String> episodes_number = new ArrayList<>();
    List<String> quality = new ArrayList<>();
    int seasons, episodes;

    Spinner spinner_seasons, spinner_episodes, spinner_quality;


    RelativeLayout download_section;

    Context context=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontsOverride.setDefaultFont(this, "DEFAULT", "MyFontAsset.otf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "MyFontAsset.otf");
        FontsOverride.setDefaultFont(this, "SERIF", "MyFontAsset.otf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "MyFontAsset.otf");

        setContentView(R.layout.activity_tv_series_info);
        getSupportActionBar().show();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Description");


        name = (TextView) findViewById(R.id.title);
        name.setText(globalVar.TvSeriesName);

        rate = (TextView) findViewById(R.id.imdbRate);

        description = (TextView) findViewById(R.id.description);
        geners = (TextView) findViewById(R.id.geners);
        wallpaper = (ImageView) findViewById(R.id.wallpaper);

        progressbar = (ProgressBar) findViewById(R.id.progressBar);




        spinner_seasons = (Spinner) findViewById(R.id.set_season);
        spinner_episodes = (Spinner) findViewById(R.id.set_episode);
        spinner_quality = (Spinner) findViewById(R.id.set_quality);





        download_section= (RelativeLayout) findViewById(R.id.download_section);

        //download part
        download_btn = (Button) findViewById(R.id.download);
        download_btn.setOnClickListener(v -> {

            new tvSeries_details().execute();
            download_btn.setVisibility(View.GONE);
            description.setVisibility(View.GONE);






        });


        new tvSeries_info().execute();
    }

    private class tvSeries_info extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressbar.setVisibility(View.VISIBLE);


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url + globalVar.TvSeriesName.replaceAll(" ", "%20"));

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Object node
                    JSONObject image = jsonObj.getJSONObject("image");
                    imagelink_j = image.getString("medium");

                    JSONObject imdb = jsonObj.getJSONObject("rating");
                    rate_j = imdb.getString("average");

                    summary_j = jsonObj.getString("summary");

                    id_j = jsonObj.getString("id");

                    JSONArray gener = jsonObj.getJSONArray("genres");

                    geners_j=gener.toString();


                } catch (final JSONException e) {
                    runOnUiThread(() -> {

                    });

                }
            } else {
                runOnUiThread(() -> {

                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            progressbar.setVisibility(View.GONE);


            rate.setText("IMDB: " + rate_j);
            description.setMovementMethod(new ScrollingMovementMethod());

            geners.setText(geners_j.replaceAll("\"","").replaceAll("\\[", "").replaceAll("\\]",""));


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                description.setText(Html.fromHtml(summary_j, Html.FROM_HTML_MODE_LEGACY));
            } else {
                description.setText(Html.fromHtml(summary_j));
            }

            try {

                //image loading
                Log.i("Dan", "onPostExecute: "+imagelink_j);
                Picasso.with(context).setIndicatorsEnabled(true);

                Picasso.with(context).load(imagelink_j).placeholder(R.drawable.black).into(wallpaper);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }


    private class tvSeries_details extends AsyncTask<Void, Void, Void> implements AdapterView.OnItemSelectedListener {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressbar.setVisibility(View.VISIBLE);


        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url_show + id_j + "/seasons");

            seasons_list.clear();

            if (jsonStr != null) {
                try {
                    JSONArray jsonAry = new JSONArray(jsonStr);
                    // Getting JSON Object node

                    for (int i = 0; i < jsonAry.length(); i++) {
                        JSONObject c = jsonAry.getJSONObject(i);

                        seasons = c.getInt("number");
                        episodes = c.getInt("episodeOrder");


                        String sample = seasons + "," + episodes;


                        seasons_list.add(sample);

                    }


                } catch (final JSONException e) {
                    runOnUiThread(() -> {

                    });

                }
            } else {
                runOnUiThread(() -> {

                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            progressbar.setVisibility(View.GONE);

            download_section.setVisibility(View.VISIBLE);

            seasons_number.clear();
            episodes_number.clear();
            quality.clear();
            for (int i = 1; i < seasons + 1; i++) {
                seasons_number.add(i + "");

            }


            if (seasons_list.size()==0){
                new DroidDialog.Builder(TvSeries_info.this)
                        .icon(R.drawable.ic_action_tick)
                        .title("Alert")
                        .content("This tvseries is not available yet")
                        .positiveButton("OK", droidDialog -> {
                            droidDialog.dismiss();

                            finish();
                            startActivity(getIntent());



                        })
                        .animation(AnimUtils.AnimFadeInOut)
                        .show();
            }else{
                String Edit = seasons_list.get(0);
                String[] spliting = Edit.split(",");
                int episodes_new = Integer.parseInt(spliting[1]);


                for (int i = 1; i < episodes_new + 1; i++) {
                    episodes_number.add(i + "");

                }

                quality.add("480P");
                quality.add("720P");


                spinner_seasons.setOnItemSelectedListener(this);
                spinner_episodes.setOnItemSelectedListener(this);
                spinner_quality.setOnItemSelectedListener(this);

                ArrayAdapter<String> dataAdapter_seasons = new ArrayAdapter<>(TvSeries_info.this, R.layout.spinner_item, seasons_number);
                ArrayAdapter<String> dataAdapter_episodes = new ArrayAdapter<>(TvSeries_info.this, R.layout.spinner_item, episodes_number);
                ArrayAdapter<String> dataAdapter_quality = new ArrayAdapter<>(TvSeries_info.this, R.layout.spinner_item, quality);

                // Drop down layout style - list view with radio button
                dataAdapter_seasons.setDropDownViewResource(R.layout.spinner_item);
                dataAdapter_episodes.setDropDownViewResource(R.layout.spinner_item);
                dataAdapter_quality.setDropDownViewResource(R.layout.spinner_item);

                // attaching data adapter to spinner
                spinner_seasons.setAdapter(dataAdapter_seasons);
                spinner_episodes.setAdapter(dataAdapter_episodes);
                spinner_quality.setAdapter(dataAdapter_quality);

            }



        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            if (parent.getId() == R.id.set_season) {

                episodes_number.clear();
                String item = parent.getItemAtPosition(position).toString();


                if (Integer.parseInt(item) > seasons_list.size()) {




                    new DroidDialog.Builder(TvSeries_info.this)
                            .icon(R.drawable.ic_action_tick)
                            .title("Alert")
                            .content("This season has not been aired yet")
                            .positiveButton("OK", Dialog::dismiss)
                            .animation(AnimUtils.AnimFadeInOut)
                            .show();


                } else {
                    String Edit = seasons_list.get(Integer.parseInt(item) - 1);

                    String[] spliting = Edit.split(",");
                    int episodes_new = Integer.parseInt(spliting[1]);
                    for (int i = 1; i < episodes_new + 1; i++) {
                        episodes_number.add(i + "");

                    }
                    ArrayAdapter<String> dataAdapter_episodes = new ArrayAdapter<>(TvSeries_info.this, R.layout.spinner_item, episodes_number);
                    dataAdapter_episodes.setDropDownViewResource(R.layout.spinner_item);
                    spinner_episodes.setAdapter(dataAdapter_episodes);

                    globalVar.selected_season = parent.getItemAtPosition(position).toString();

                }

            }
            if (parent.getId() == R.id.set_episode) {
                globalVar.selected_episode = parent.getItemAtPosition(position).toString();


            }

            if (parent.getId() == R.id.set_quality) {
                globalVar.selected_quality = parent.getItemAtPosition(position).toString();


            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void cancel(View view) {


        download_section.setVisibility(View.GONE);
        download_btn.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);



    }

    public void start_download(View view) {

        Intent intent = new Intent(TvSeries_info.this, Download.class);
        startActivity(intent);
    }
}
