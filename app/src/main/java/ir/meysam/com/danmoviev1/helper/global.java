package ir.meysam.com.danmoviev1.helper;

import android.app.Application;


public class global extends Application {
    private static global instance;
    public String TvSeriesName;
    public String selected_season;
    public String selected_episode;
    public String selected_quality;

    private global(){
    }

    public static synchronized global getInstance(){
        if(instance==null){
            instance=new global();
        }
        return instance;
    }
}
