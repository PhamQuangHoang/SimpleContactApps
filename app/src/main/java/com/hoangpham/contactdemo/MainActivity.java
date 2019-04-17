package com.hoangpham.contactdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdap.OnClickNodeListener,RecyclerViewAdap.SendData {
    List<Contact> contactDTOS;
    RecyclerViewAdap adap;
    RecyclerView mRecyclerView;
    MyDatabase db;
    int code;
    public static final int REQUEST_CODE = 1;
    public static final int RESULT_CODE_ADD = 1;
    public static final int RESULT_CODE_UPDATE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        Button button = findViewById(R.id.btnClear);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAll();
            }
        });


        contactDTOS = new ArrayList<Contact>();
        db = new MyDatabase(this);
        contactDTOS = db.getAllContacts();


        initRecyclerView();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditContactActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void initRecyclerView() {
        adap = new RecyclerViewAdap(this, R.layout.itemcontact, contactDTOS, this,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adap);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_CODE_ADD:
                    if (data != null) {
                        contactDTOS.clear();
                        contactDTOS = db.getAllContacts();
                        initRecyclerView();
                        Toast.makeText(this, "Adding successfully", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case RESULT_CODE_UPDATE:
                    if (data != null) {
                       contactDTOS.clear();
                       contactDTOS = db.getAllContacts();
                        adap.updateList(contactDTOS);

                        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    @Override
    public void onItemRecyclerClicked(int postion, int actions) {
        //  Toast.makeText(MainActivity.this, "Bạn đã click và dòng :" + postion + "Name : " + contactDTOS.get(postion).getmFullname(), Toast.LENGTH_LONG).show();

        switch (actions){
            case 1 :
                Contact contact = contactDTOS.get(postion);
                Toast.makeText(this, " " + contact.getmId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, UpdateContactActivity.class);
                intent.putExtra("Object", contact);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case 2 :
                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactDTOS.get(postion).getmMobile()));
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void sendData(int pos) {
        Contact contact = contactDTOS.get(pos);
       if (db.deleteContact(contact)){
           Toast.makeText(this, "Delete success", Toast.LENGTH_SHORT).show();

           adap.removeItem(pos);
       }

    }
}
