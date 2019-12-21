package com.example.test;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import static androidx.room.OnConflictStrategy.REPLACE;
import static androidx.room.OnConflictStrategy.IGNORE;
@Dao
public interface  OwnerDao {



    @Query("SELECT * FROM owner inner join pet on Owner.id = pet.ownerid where pet.petname=:petname")
    LiveData<List<Owner>> getOwnerInfo(String petname);


    @Query("SELECT * FROM owner WHERE id = :id")
    Owner loadOwnerbyID(int id);

    @Query("SELECT id FROM owner WHERE phonenumber = :phonenumber")
    int loadOwnerbyphone(String phonenumber);

    @Query("Select * from Owner")
    LiveData<List<Owner>> getall();



    @Query("Select * from owner o inner join pet p on o.id = p.ownerid")
    List<Owner> getAllInfo();


    @Query("Select * from owner o inner join pet p on o.id = p.ownerid where o.phonenumber=:phonenumber")
    List<Owner> getownphone(String phonenumber);



    @Query("Select o.*,p.* from Owner o inner join pet p on o.id = p.ownerid where o.phonenumber like :phonenumber")
    List<Owner> getPetandOwner(String phonenumber);

    @Query("DELETE FROM owner")
    void deleteAll();


    @Update
    void updateOwner(Owner owner);


    @Insert(onConflict = IGNORE)
    void createOwner(Owner owner);


    @Query("Select * from pet")
    LiveData<List<Pet>> getPets();

    @Query("Select * from pet where petid= :id")
     List<Pet> getPets(int id);



    @Query("SELECT * FROM pet inner join Owner on Owner.id = pet.ownerid where Owner.phonenumber=:phonenumber")
    LiveData<List<Pet>> getPetinfophone(long phonenumber);


    @Query("SELECT * FROM Pet inner join Owner on Owner.id = Pet.ownerid where Owner.name=:ownername")
    LiveData<List<Pet>> getPetinfoname(String ownername);


    @Query("SELECT * FROM Pet inner join Owner on Owner.id = Pet.ownerid where Pet.date=:visitdate and Pet.vet=:vet")
    LiveData<List<Pet>> getPetinfovetdate(String visitdate,String vet);


    @Insert(onConflict = REPLACE)
    void saveOwners(List<Owner> owners);

    @Insert(onConflict = REPLACE)
    void savePets(List<Pet> pets);




}
