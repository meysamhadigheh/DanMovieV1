package ir.meysam.com.danmoviev1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.mikepenz.materialdrawer.DrawerBuilder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import ir.meysam.com.danmoviev1.R;
import ir.meysam.com.danmoviev1.helper.FontsOverride;
import ir.meysam.com.danmoviev1.helper.global;


public class MainActivity extends AppCompatActivity {

    global globalVar = global.getInstance();
    ArrayAdapter<String> adapter;
    ArrayList<String> tvseriesList;
    ListView list;
    ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FontsOverride.setDefaultFont(this, "DEFAULT", "MyFontAsset.otf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "MyFontAsset.otf");
        FontsOverride.setDefaultFont(this, "SERIF", "MyFontAsset.otf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "MyFontAsset.otf");
        setContentView(R.layout.activity_main);
        // getSupportActionBar().hide();

        new DrawerBuilder().withActivity(this).build();


        progressbar = findViewById(R.id.progressBar);

        downloadThread.start();
        list = findViewById(R.id.tvseriesNameList);




    }

    Thread downloadThread = new Thread() {

        public void run() {

            progressbar.setVisibility(View.VISIBLE);
            Document doc;
            String tvName;
            try {

                tvseriesList = new ArrayList<>();
                doc = Jsoup.connect("http://o2tvseries.com/search/list_all_tv_series/?sort=a-z").get();
                Elements elements = doc.select("a");
                for (int i = 0; i < elements.size() - 1; i++) {

                    tvName = elements.get(i).text();
                    tvseriesList.add(tvName);
                }
                for (int i = 0; i < 11; i++) {
                    tvseriesList.remove(0);
                }
                runOnUiThread(() -> {
                    progressbar.setVisibility(View.GONE);


                });

                adapter = new ArrayAdapter<>(MainActivity.this, R.layout.list_item_tvseries_name, R.id.tvSeries_name, tvseriesList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            list.post(() -> {

                list.setAdapter(adapter);
                list.setOnItemClickListener((parent, view, position, id) -> {
                    view.setSelected(true);

                    globalVar.TvSeriesName = (list.getItemAtPosition(position).toString());



                    Intent intent = new Intent(MainActivity.this, TvSeries_info.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_left);


                });


            });



        }


    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}
