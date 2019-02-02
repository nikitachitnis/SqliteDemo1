package com.example.student.sqlitedemo1;

import java.util.Random;

/**
 * Created by STUDENT on 02-07-2018.
 */

public class ContactModel

{
    String name,phoneno;
    int id;
    ContactModel(String name,String phoneno)
    {

        this.name=name;
        this.phoneno=phoneno;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
