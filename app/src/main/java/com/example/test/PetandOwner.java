package com.example.test;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Relation;
import java.util.List;

public class PetandOwner {

    @Embedded public Owner owner;
    @Relation(
            parentColumn = "id",
            entityColumn = "ownerid")

    public List<Pet> pets;



}
