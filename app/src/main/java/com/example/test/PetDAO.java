package com.example.test;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface PetDAO {


    @Query("Select * from pet")
    List<Pet> getall();

    @Query("SELECT * FROM pet inner join Owner on Owner.id = pet.ownerid where Owner.phonenumber=:phonenumber")
    Pet getPetinfophone(String phonenumber);


    @Query("SELECT * FROM Pet inner join Owner on Owner.id = Pet.ownerid where Owner.name=:ownername")
    LiveData<List<Pet>> getPetinfoname(String ownername);


    @Query("SELECT * FROM Pet inner join Owner on Owner.id = Pet.ownerid where Pet.date=:visitdate and Pet.vet=:vet")
    LiveData<List<Pet>> getPetinfovetdate(String visitdate,String vet);


    @Query(
       "Select * from pet p inner join owner o on o.id = p.ownerid where o.id=:ownerid"
    )
    Pet getPet(int ownerid);


    @Query(
            "Select * from pet p inner join owner o on o.id = p.ownerid"
    )
    List<Pet>getPet1();

    @Query("Select * from pet p inner join owner o on o.id = p.ownerid where o.phonenumber=:phonenumber")
    List<Pet> getAllInfo(String phonenumber);



    @Query("SELECT * FROM pet WHERE petid = :id")
    Pet loadPetbyid(int id);

    @Insert(onConflict = REPLACE)
    void createPet(Pet pet);


    @Update
    void upDatePet(Pet pet);



}
