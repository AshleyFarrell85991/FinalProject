package com.example.test;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface PetandOwnerDAO {

   @Transaction
    @Query("Select * from Owner o inner join pet p on o.id = p.ownerid")
        List<PetandOwner> getAllInfo();


    @Transaction
    @Query("Select * from Owner o inner join pet p on o.id = p.ownerid")
    List<PetandOwner> getid();



}
