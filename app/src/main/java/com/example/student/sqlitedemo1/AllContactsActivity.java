package com.example.student.sqlitedemo1;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

public class AllContactsActivity extends AppCompatActivity
{
    ListView contactlist;

    ArrayList<String> list=new ArrayList<>();
    ArrayAdapter<String> adapter;
    DatabaseHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);
        contactlist=(ListView)findViewById(R.id.contactlist);
        dbhelper=new DatabaseHelper(this);
       refreshlist();
        contactlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, final long l)
            {
                final int position=i;

                PopupMenu menu=new PopupMenu(AllContactsActivity.this,view);

                menu.getMenuInflater().inflate(R.menu.listmenu,menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem)

                    {

                       switch(menuItem.getItemId())
                        {
                            case R.id.edit:

                                final Dialog customdialog=new Dialog(AllContactsActivity.this);
                                customdialog.setContentView(R.layout.customdialog);
                                Button btnupdate =(Button)customdialog.findViewById(R.id.btnupdate);
                                final EditText txtphnno=(EditText)customdialog.findViewById(R.id.edtphnno);
                               final String arr[]=list.get(position).split("\n");

                                txtphnno.setText(arr[1]);
                                btnupdate.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view) {

                                       int updatecpount= dbhelper.updateContact(new ContactModel(arr[0],txtphnno.getText().toString()));
                                       if(updatecpount>0)
                                       {
                                           Toast.makeText(AllContactsActivity.this,"Contact no has been updated ",Toast.LENGTH_SHORT).show();
                                       }
                                       else
                                       {
                                           Toast.makeText(AllContactsActivity.this,"Contact is not updated ",Toast.LENGTH_SHORT).show();
                                       }

                                        refreshlist();
                                        customdialog.dismiss();
                                    }
                                });
                                customdialog.show();

                                break;
                            case R.id.delete:

                                AlertDialog.Builder builder=new AlertDialog.Builder(AllContactsActivity.this);
                                builder.setMessage("Are you sure ,you want to delete this contact?");
                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String arr1[]=list.get(position).split("\n");

                                        dbhelper.deleteContact(new ContactModel(arr1[0],arr1[1]));

                                        refreshlist();
                                        Toast.makeText(AllContactsActivity.this,"Contact has been deleted successfully",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder.show();

                                break;
                        }
                        return true;
                    }
                });
                menu.show();

            }
        });


    }
    void refreshlist()
    {
        list.clear();
        final Cursor cursor=dbhelper.getContacts();
        int size=cursor.getCount();

        if(cursor!=null)
        {
            if(cursor.getCount()!=0)
            {
                cursor.moveToFirst();

                while(cursor.isAfterLast() == false){

                    String name;
                    String phnno;
                    int namecolumnindex=cursor.getColumnIndex(DatabaseHelper.KEY_COLUMN_NAME);
                    name=cursor.getString(namecolumnindex);








                    String mcontact=
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_COLUMN_NAME))+"\n"+ cursor.getString(cursor.getColumnIndex(DatabaseHelper.KEY_COLUMN_PH_NO));
                    list.add(mcontact);
                    cursor.moveToNext();
                }


            }
            else
            {
                Toast.makeText(AllContactsActivity.this,"No contacts in database",Toast.LENGTH_SHORT).show();
            }

        }
        if(list.size()!=0)
        {
            String arrcontacts[]=new String[list.size()];

            for(int i=0;i<list.size();i++)
            {
                arrcontacts[i]=list.get(i);


            }
            adapter=new ArrayAdapter<String>(AllContactsActivity.this,R.layout.customrow,android.R.id.text1,arrcontacts);
            contactlist.setAdapter(adapter);

        }
        else
        {
            adapter=new ArrayAdapter<String>(AllContactsActivity.this,R.layout.customrow,android.R.id.text1,list);
            contactlist.setAdapter(adapter);
        }
    }
}
