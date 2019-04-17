package com.hoangpham.contactdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EditContactActivity extends AppCompatActivity {

    Contact contact ;
    EditText name,mobile,email;
    ImageButton btnAdd, btnClose;
    private MyDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        name = findViewById(R.id.edName);
        mobile = findViewById(R.id.edMobile);
        email = findViewById(R.id.edEmail);
        btnAdd = findViewById(R.id.btnAdd);
        btnClose = findViewById(R.id.btnClose);

        myDatabase = new MyDatabase(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contact = new Contact(name.getText().toString(),"công ty A " , "Đây là title ",mobile.getText().toString(),email.getText().toString());
                if(myDatabase.addContact(contact) != 0 ){
                    Toast.makeText(EditContactActivity.this,"add thanh cong" , Toast.LENGTH_SHORT).show();
                    Intent intentSendBack = new Intent(EditContactActivity.this,MainActivity.class);
                    intentSendBack.putExtra("objectSendBack",contact);
                    setResult(MainActivity.RESULT_CODE_ADD,intentSendBack);
                    finish();
                }

            }
        });






    }
}
