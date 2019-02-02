package com.example.student.sqlitedemo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    Button btnadd,btnshow;
    EditText txtname,txtphnno;
    DatabaseHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnadd=(Button)findViewById(R.id.btnadd);
        btnshow=(Button)findViewById(R.id.btnshow);
        txtname=(EditText)findViewById(R.id.editextname);
        txtphnno=(EditText)findViewById(R.id.edittextphnno);
        dbhelper=new DatabaseHelper(this);
        btnadd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name=txtname.getText().toString();
                String phnno=txtphnno.getText().toString();
                if(name.trim().equals(""))
                {
                    Toast.makeText(MainActivity.this,"Contact Name can not be blank!",Toast.LENGTH_SHORT).show();
                    txtname.requestFocus();
                    return;
                }
                else if (phnno.trim().equals(""))
                {
                    Toast.makeText(MainActivity.this,"Contact Number can not be blank!",Toast.LENGTH_SHORT).show();
                    txtphnno.requestFocus();
                    return;
                }
                else if (phnno.length()<10)
                {
                    Toast.makeText(MainActivity.this,"Contact Number is not valid ",Toast.LENGTH_SHORT).show();
                    txtphnno.requestFocus();
                    return;
                }

                ContactModel contact=new ContactModel(name,phnno);
                boolean isinserted=dbhelper.insert_Into_Contact(contact);

                if(isinserted)
                {
                    Toast.makeText(MainActivity.this,"Contact is added successfully",Toast.LENGTH_SHORT).show();
                    txtname.setText("");
                    txtphnno.setText("");
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Failed to add contact",Toast.LENGTH_SHORT).show();
                }


            }
        });
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent contactlistactivity=new Intent(MainActivity.this,AllContactsActivity.class);
                startActivity(contactlistactivity);
            }
        });

    }
}
