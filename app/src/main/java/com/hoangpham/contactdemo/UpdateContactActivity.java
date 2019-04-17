package com.hoangpham.contactdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpdateContactActivity extends AppCompatActivity {
    Contact contact ;
    EditText name,mobile,email;
    ImageButton btnAdd, btnClose;
    private MyDatabase myDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_layout);
        name = findViewById(R.id.edName1);
        mobile = findViewById(R.id.edMobile1);
        email = findViewById(R.id.edEmail1);
        btnAdd = findViewById(R.id.btnAdd1);
        btnClose = findViewById(R.id.btnClose1);

        myDatabase = new MyDatabase(this);


        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra("Object");
        final String oldname = contact.getmFullname();
        name.setText(contact.getmFullname());
        mobile.setText(contact.getmMobile());
        email.setText(contact.getmEmail());
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contact.setmFullname(name.getText().toString());
                contact.setmMobile(mobile.getText().toString());
                contact.setmEmail(email.getText().toString());
                if(myDatabase.updateContact(contact)){
                    Intent intentSendBack = new Intent(UpdateContactActivity.this,MainActivity.class);

                    setResult(MainActivity.RESULT_CODE_UPDATE,intentSendBack);

                    finish();
                }else {

                    Toast.makeText(UpdateContactActivity.this,contact.getmId() + "  " , Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
