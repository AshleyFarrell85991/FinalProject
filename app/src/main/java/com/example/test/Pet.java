package com.example.test;


import java.io.Serializable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "pet",foreignKeys = @ForeignKey(entity=Owner.class,
        parentColumns="id", childColumns="ownerid",onDelete = CASCADE),indices =@Index(value = "ownerid"))

public class Pet implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int petid;

    @ColumnInfo
    private int ownerid;

    @ColumnInfo(name = "petname")
    private String petname;

    @ColumnInfo(name = "vet")
    private String vet;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "visit summary")
    private String visitsummary;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Pet(){

        this.petid = petid;
        this.petname = petname;
        this.vet = vet;
        this.date = date;
        this.visitsummary = visitsummary;
        this.image = image;
        this.ownerid = ownerid;


    }

    public void setOwnerid(int ownerid){

        this.ownerid = ownerid;
    }

    public int getOwnerid(){
        return ownerid;   }

    public String getPetname(){

        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public String getVet(){
        return vet;
    }

    public void setVet(String vet){

        this.vet= vet;
    }

    public String getDate(){

        return  date;
    }

    public void setDate(String date){
        this.date = date;

    }
    public void setPetid(int petid){

        this.petid=petid;
    }

    public int getPetid(){
        return petid;
    }

    public void setVisitsummary(String visitsummary){

        this.visitsummary= visitsummary;
    }

    public String getVisitsummary(){
        return visitsummary;
    }

    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
}
