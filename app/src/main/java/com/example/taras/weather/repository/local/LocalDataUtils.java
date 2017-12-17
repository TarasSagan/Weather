package com.example.taras.weather.repository.local;
import android.content.Context;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.Repo;
import com.example.taras.weather.repository.local.fiveDaysThreeHours.RepoDatabase;

import java.util.List;

/**
 * Created by Taras on 03.12.2017.
 */

public class LocalDataUtils {

    public synchronized static void updateFiveDayDB(Context context, List<Repo> list){
        // get list all forecast by city from db
        List<Repo> listForRemoving = RepoDatabase
                .getInstance(context)
                .getRepoDao()
                .getAllByCityId(list.get(0).getCityID());
        // delete all forecast by target city
        RepoDatabase.getInstance(context).getRepoDao().delete(listForRemoving);
        // add new forecast
        RepoDatabase.getInstance(context).getRepoDao().insert(list);
    }

}
