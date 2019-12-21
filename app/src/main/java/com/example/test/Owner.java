package com.example.test;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;
import androidx.annotation.NonNull;

import java.util.Random;
import 	java.util.UUID;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName ="owner",indices = @Index("id"))
public class Owner implements Serializable {
@Ignore
private List<Pet> pets;

//    public Owner(int id, String name, String address, String phonenumber) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//        this.phonenumber = phonenumber;
//
//    }


    public Owner() {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phonenumber = phonenumber;
        this.ownerimage=ownerimage;

    }

//
//    @Ignore
//    public Owner(String name) {
//        this(name, null);
//    }
//
//    @Ignore
//    public Owner(String name, String parentId) {
//        this(UUID.randomUUID().toString(), name, parentId);
//    }
    @PrimaryKey()
    @NonNull
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name= "address")
    private String address;

    @ColumnInfo(name = "phonenumber")
    private String phonenumber;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] ownerimage;

    public String getName(){
        return name;

    }


    public byte[] getOwnerimage() {
        return ownerimage;
    }
    public void setOwnerimage(byte[] image) {
        this.ownerimage = image;
    }



    public void setName(String name) {
        this.name = name;

    }

    public String getAddress(){
        return address;

    }


    public void setAddress(String address) {
        this.address = address;

    }

    public String getPhonenumber() {
        return phonenumber;
    }


    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;

    }

//  public int createID(int position){
//    Random m = new Random();
////
//      for(int i =1; i< 1000 ; i++) {
////
////            position = m.nextInt(20) + i;
////        }
////
////        return position;
////
////    }

    public void setId(int id){
        this.id= id;
    }
//
   public int getId(){
//
       return this.id;
  }



//  public String getId() {
////
//     return UUID.randomUUID().toString();
//   }

    public void setPets(List<Pet> pets){
        this.pets = pets;

    }

    public List<Pet> getPets(){

        return pets;
    }


}