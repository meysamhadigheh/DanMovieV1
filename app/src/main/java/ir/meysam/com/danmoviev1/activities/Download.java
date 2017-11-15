package ir.meysam.com.danmoviev1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

import ir.meysam.com.danmoviev1.R;
import ir.meysam.com.danmoviev1.helper.global;

public class Download extends AppCompatActivity {

    private static final String TAG ="links" ;
    TextView name, season, episode, quality;
    global globalVar = global.getInstance();

    String selected_season = "";
    String selected_episode = "";
    String url_upload8, tehmovies, serverdl, sv4avadl, my_film, harmonydl, bia2sv, negarfilm, film2movie, irani_dl;


    String links[] = new String[8];
    String names[] = new String[8];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);


        name = (TextView) findViewById(R.id.tvseries_name);
        season = (TextView) findViewById(R.id.tvseries_season);
        episode = (TextView) findViewById(R.id.tvseries_episode);
        quality = (TextView) findViewById(R.id.tvseries_quality);


        name.setText(globalVar.TvSeriesName);
        season.setText(globalVar.selected_season);
        episode.setText(globalVar.selected_episode);
        quality.setText(globalVar.selected_quality);




        if (globalVar.selected_quality.equalsIgnoreCase("480p")) {



            downloadType480();

        }
        if (globalVar.selected_quality.equalsIgnoreCase("720p")) {
            downloadType720();
        }


    }

    private void downloadType480() {

        if (Integer.parseInt(globalVar.selected_season) < 10) {
            selected_season = "0" + globalVar.selected_season;
        }

        if (Integer.parseInt(globalVar.selected_episode) < 10) {
            selected_episode = "0" + globalVar.selected_episode;
        }


        //http://dl.upload8.net/Serial/Queen%20of%20the%20South/S02/queen.of.the.south.s02e01.480p.mkv
        url_upload8 = "http://dl.upload8.net/Serial/"
                + globalVar.TvSeriesName.replaceAll(" ", "%20") + "/S" + selected_season + "/"
                + globalVar.TvSeriesName.toLowerCase().replaceAll(" ", ".") + ".s" + selected_season + ".s" + selected_episode + ".480p.mkv";

        Log.d(TAG+"fardadownload", "downloadType480: "+url_upload8);
        //http://dl.upload8.net/Serial/13%20Reasons%20Why/S01/13.reasons.why.s01e06.480p.webrip.mkv



        //http://dl.tehmovies.me/94/series/game.of.throne/s2/Game.of.Thrones.S02.E04.480p.Tehmovies_bid.mkv
        tehmovies = "http://dl.tehmovies.me/94/series/" + globalVar.TvSeriesName.toLowerCase().replaceAll(" ", ".")
                + "/s" + globalVar.selected_season + "/" + globalVar.TvSeriesName.replaceAll(" ", ".")
                + ".S" + selected_season + ".E" + selected_episode + ".480p.Tehmovies_bid.mkv";


        //http://dl.tehmovies.me/94/series/13reasons.why/s1/13.reasons.why.s01e06.480p.webrip.Tehmovies_me.mkv

        //http://dl.serverdl.in/1/files/Falling.Skies.S05E01.480p.mkv
        serverdl = "http://dl.serverdl.in/1/files/" + globalVar.TvSeriesName.replaceAll(" ", ".")
                + ".S" + selected_season + ".E" + selected_episode + ".480p.mkv";


        //http://sv4avadl.uploadet.ir/Serial/13.Reasons.Why/13%20Reasons%20Why%20S01E06%20480p%20WEBRip%20x264_AVADL.BiZ.mkv
        sv4avadl = "http://sv4avadl.uploadet.ir/Serial/" + globalVar.TvSeriesName.replaceAll(" ", ".")
                + "/" + globalVar.TvSeriesName.replaceAll(" ", "%20") + "S" + selected_season
                + "E" + selected_episode + "%20480p%20WEBRip%20x264_AVADL.BiZ.mkv";

        //http://sv4avadl.uploadet.ir/Serial/13.Reasons.Why/13%20Reasons%20Why%20S01E06%20480p%20WEBRip%20x264_AVADL.BiZ.mkv


        //http://dl.my-film.in/serial/Fear%20the%20Walking%20Dead/03-480p%20x264.HDTV/Fear.the.Walking.Dead.S03E01.480p.HDTV.x264-[My-Film].mkv
        my_film = "http://dl.my-film.in/serial/" + globalVar.TvSeriesName.replaceAll(" ", "%20")
                + "/" + selected_season + "-480p%20x264.HDTV/" + globalVar.TvSeriesName.replaceAll(" ", ".")
                + ".S" + selected_season + "E" + selected_episode + ".480p.HDTV.x264-";
        my_film=my_film+"[My-Film].mkv";

        //http://dl.my-film.in/serial/Arrow/Season%201%20-%20480p%20HDTV/Arrow.S01E14.480p.BluRay.x264-[My-Film].mkv



        //http://www.harmonydl.info/goto/http://harmonydl.direct2.filegozar.com/series/96/Silicon%20Valley/Silicon.Valley.S04E01_Harmonydl_.mkv
        harmonydl = "http://www.harmonydl.info/goto/http://harmonydl.direct2.filegozar.com/series/96/"
                + globalVar.TvSeriesName.replaceAll(" ", "%20") + "/" + globalVar.TvSeriesName.replaceAll(" ", ".")
                + ".S" + selected_season + ".E" + selected_episode + "_Harmonydl_.mkv";


        //http://s1.bia2sv.in/Series/Wynonna%20Earp/s2/Wynonna%20Earp%20S02E01%20(Bia2Movies).mkv
        bia2sv = "http://s1.bia2sv.in/Series/" + globalVar.TvSeriesName.replaceAll(" ", "%20")
                + "/s" + globalVar.selected_season + "/" + globalVar.TvSeriesName.replaceAll(" ", "%20")
                + "S" + selected_season + "E" + selected_episode + "%20"+"(Bia2Movies).mkv";

        //http://s1.bia2sv.in/Series/13%20Reasons%20Why/s1/13%20Reasons%20Why%20S01E06%20480p%20WEBRip%20(Bia2Movies).mkv


        //http://sr.negarfilm.ir/seryal-khareji/The-Flash/03/the.flash.s03e01.480p.mkv
        negarfilm = "http://sr.negarfilm.ir/seryal-khareji/" + globalVar.TvSeriesName.replaceAll(" ", "-")
                + "/" + selected_season + "/" + globalVar.TvSeriesName.replaceAll(" ", ".")
                + ".s" + globalVar.selected_season + "e" + selected_episode + ".480p.mkv";




        links = new String[]{url_upload8, tehmovies, serverdl, sv4avadl, my_film, harmonydl, bia2sv, negarfilm};
        names = new String[]{"url_upload8", "tehmovies", "serverdl", "sv4avadl", "my_film", "harmonydl", "bia2sv", "negarfilm"};


        checkThread.start();


    }


    private void downloadType720() {
        if (Integer.parseInt(globalVar.selected_season) < 10) {
            selected_season = "0" + globalVar.selected_season;
        }

        if (Integer.parseInt(globalVar.selected_episode) < 10) {
            selected_episode = "0" + globalVar.selected_episode;
        }


        // http://dl.upload8.net/Serial/Queen%20of%20the%20South/S02/queen.of.the.south.s02e01.720p.x265.mk
        url_upload8 = "http://dl.upload8.net/Serial/"
                + globalVar.TvSeriesName.replaceAll(" ", "%20") + "/S" + selected_season + "/"
                + globalVar.TvSeriesName.toLowerCase().replaceAll(" ", ".") + ".s" + selected_season + ".s" + selected_episode + ".720p.x265.mk";


        // http://dl2.film2movie.co/serial/Better%20Call%20Saul/S01/Better.Call.Saul.S01E01.720p.Film2Movie_INFO.mkv
        film2movie = "http://dl2.film2movie.co/serial/" + globalVar.TvSeriesName.replaceAll(" ", "%20")
                + "/S" + globalVar.TvSeriesName.replaceAll(" ", ".") + ".S" + selected_season + ".E"
                + selected_episode + ".720p.Film2Movie_INFO.mkv";


        //http://dl.serverdl.in/1/files/Falling.Skies.S05E02.720p.mkv
        serverdl = "http://dl.serverdl.in/1/files/" + globalVar.TvSeriesName.replaceAll(" ", ".")
                + ".S" + selected_season + ".E" + selected_episode + ".720p.mkv";


        //http://sv4avadl.uploadet.ir/Serial/Wynonna/Wynonna.Earp.S02E01.REPACK.720p.HDTV.HEVC.x265.AVADL.BiZ.mkv
        sv4avadl = "http://sv4avadl.uploadet.ir/Serial/" + globalVar.TvSeriesName.replaceAll(" ", ".")
                + "/" + globalVar.TvSeriesName.replaceAll(" ", "%20") + "S" + selected_season
                + "E" + selected_episode + ".REPACK.720p.HDTV.HEVC.x265.AVADL.BiZ.mkv";


        //http://dl.my-film.in/serial/Fear%20the%20Walking%20Dead/03-720p%20x264.HDTV/Fear.the.Walking.Dead.S03E01.720p.HDTV.x264-[My-Film].mkv
        my_film = "http://dl.my-film.in/serial/" + globalVar.TvSeriesName.replaceAll(" ", "%20")
                + "/" + selected_season + "-720p%20x264.HDTV/" + globalVar.TvSeriesName.replaceAll(" ", ".")
                + ".S" + selected_season + "E" + selected_episode + ".720p.HDTV.x264-[My-Film].mkv";


        //http://www.harmonydl.info/goto/http://harmonydl.direct2.filegozar.com/series/96/Silicon%20Valley/Si.li.con_va.lley.s04e01.720p.hdtv.hevc.x265.rmteam.HarmonyDL_Com.mkv
        harmonydl = "http://www.harmonydl.info/goto/http://harmonydl.direct2.filegozar.com/series/96/"
                + globalVar.TvSeriesName.replaceAll(" ", "%20") + "/" + globalVar.TvSeriesName.replaceAll(" ", ".")
                + ".s" + selected_season + ".e" + selected_episode + ".720p.hdtv.hevc.x265.rmteam.HarmonyDL_Com.mkv";


        //http://dl1.irani-dl.com/serial/The%20Last%20Kingdom/The%20Last%20Kingdom%20S01E01%20HDTV%20720P%20-%20(www.irani-dl.ir).mkv
        irani_dl = "http://dl1.irani-dl.com/serial/" + globalVar.TvSeriesName.replaceAll(" ", "%20") + "/"
                + globalVar.TvSeriesName.replaceAll(" ", "%20") + "S" + selected_season + "E" + selected_episode
                + "%20HDTV%20720P%20-%20(www.irani-dl.ir).mkv";


        //http://s1.bia2sv.in/Series/Wynonna%20Earp/s2/Wynonna%20Earp%20S02E01%20720p%20(Bia2Movies).mkv
        bia2sv = "http://s1.bia2sv.in/Series/" + globalVar.TvSeriesName.replaceAll(" ", "%20")
                + "/s" + globalVar.selected_season + "/" + globalVar.TvSeriesName.replaceAll(" ", "%20")
                + "S" + selected_season + "E" + selected_episode + "%20720p%20(Bia2Movies).mkv";


        //http://sr.negarfilm.ir/seryal-khareji/The-Flash/03/the.flash.s03e01.720p.mkv
        negarfilm = "http://sr.negarfilm.ir/seryal-khareji/" + globalVar.TvSeriesName.replaceAll(" ", "-")
                + "/" + selected_season + "/" + globalVar.TvSeriesName.replaceAll(" ", ".")
                + ".s" + globalVar.selected_season + "e" + selected_episode + ".720p.mkv";

        links = new String[]{url_upload8, serverdl, sv4avadl, my_film, harmonydl, bia2sv, negarfilm, irani_dl};
        names = new String[]{"url_upload8", "serverdl", "sv4avadl", "my_film", "harmonydl", "bia2sv", "negarfilm", "irani_dl"};

        checkThread.start();

    }


    Thread checkThread = new Thread() {

        public void run() {
            //your "file checking code" goes here like this
            //write your results to log cat, since you cant do Toast from threads without handlers also...


            for (int i = 0; i < links.length; i++) {
                String URLName = links[i];




                try {
                    HttpURLConnection.setFollowRedirects(false);
                    // note : you may also need
                    //HttpURLConnection.setInstanceFollowRedirects(false)

                    HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
                    con.setRequestMethod("HEAD");


                    if ((con.getResponseCode() == HttpURLConnection.HTTP_OK)) {


                        Toast.makeText(getApplicationContext(), names[i], Toast.LENGTH_SHORT).show();


                        Intent intent = new Intent(Download.this, MainActivity.class);
                        startActivity(intent);


                    } else {


                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        }

    };
}
