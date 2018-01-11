package com.example.taras.weather.repository.local.fiveDaysThreeHours;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by Taras on 03.12.2017.
 */
@Dao
public interface RepoDAO {
    @Query("SELECT * FROM repo")
    Flowable<List<Repo>> getAll();

    @Query("SELECT * FROM repo WHERE cityID = :cityID")
    List<Repo> getAllByCityId(long cityID);

//    @Query("SELECT * FROM repo WHERE first_name LIKE :first AND "
//            + "last_name LIKE :last LIMIT 1")
//    Repo findByName(String first, String last);
//
//    @Query("SELECT * FROM repo where uid = :id")
//    Maybe<Repo> findById(int id);


    @Insert
    void insertAll(Repo... repos);

    @Insert
    void insert(List<Repo> list);

    @Delete
    void delete(Repo repo);

    @Delete
    void delete(List<Repo> list);

    @Update
    public void updateRepo(Repo... repos);
}
