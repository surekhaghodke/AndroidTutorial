package com.example.myapplication5;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    List<String> items;
    ImageView imageView;
    Button Camera1 ,Gallery1;

    EditText firstname,lastname, email, phone, password, confirm,dateformat;
    int year,month,day;


    MaterialButton register;
    TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstname = findViewById(R.id.edit1);
        lastname = findViewById(R.id.edit7);
        email = findViewById(R.id.edit2);
        phone = findViewById(R.id.edit3);
        password = findViewById(R.id.edit4);
        confirm = findViewById(R.id.edit5);
        dateformat = findViewById(R.id.edit6);
        signin = findViewById(R.id.createtext);
        register = findViewById(R.id.registerbtn);
        spinner = findViewById(R.id.spinner);
        imageView = findViewById(R.id.image);
        Camera1 = findViewById(R.id.camera);
        Gallery1 = findViewById(R.id.Gallery);


        items = new ArrayList<>();

        items.add("Marathi");
        items.add("Hindi");
        items.add("English");
        items.add("Gujrati");
        items.add("Spanish");
        items.add("Jarman");
        items.add("Bangali");
        items.add("Tamil");
        items.add("Telgu");
        items.add("Kokani");
        spinner.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items));

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateformat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "-" + month + "-" + year;
                        dateformat.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirminput()) {
                    submitUserData();
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED);

        ActivityCompat.requestPermissions(MainActivity.this,new String[] {
           Manifest.permission.CAMERA},0);
    }

    public void pick_up_camera(View view){
        Intent takepicture=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takepicture,0);
    }

    public void pick_up_gallery(View view)
    {
        Intent pickphoto=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickphoto,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 0:
                if(resultCode==RESULT_OK)
                {
                    Bundle extras=data.getExtras();
                    Bitmap imagebitmap=(Bitmap)  extras.get("data");
                    imageView.setImageBitmap(imagebitmap);
                }
                break;
            case 1:

                if (resultCode==RESULT_OK)
                {
                    Uri selectedIamge=data.getData();
                    imageView.setImageURI(selectedIamge);
                }
                break;

        }
    }

    private boolean validateText(String text, EditText textCompoent)
    {
        if(text.isEmpty())
        {
            textCompoent.setError(textCompoent.getHint()+"field is required");
            return false;
        }
        else{
            textCompoent.setError(null);
            return true;
        }
    }
    private boolean validatePassword(String pass)
    {
        if(pass.isEmpty())
        {
            password.setError("field is required");
            return false;
        }
        else{
            password.setError(null);
            return true;
        }
    }

    public Boolean confirminput(){
        String first_name=firstname.getText().toString();
        String last_name=lastname.getText().toString();
        String email_id=email.getText().toString();
        String mobile_no=phone.getText().toString();
        String pass_word=password.getText().toString();
        String  con_firm=confirm.getText().toString();

        if(!validateText(first_name,firstname)|!validateText(last_name,lastname)|!validateText(email_id,email)
                |!validateText(mobile_no,phone)|!validatePassword(pass_word)|!validatePassword(con_firm))

        {
            return false;
        }
        else
        {
            return true;
        }

    }

private void submitUserData()
{
    String first_name=firstname.getText().toString();
    String last_name=lastname.getText().toString();
    String email_id=email.getText().toString();
    String mobile_no=phone.getText().toString();
    String pass_word=password.getText().toString();
    String  con_firm=confirm.getText().toString();

    String msg="first name:"+first_name+"\n lastname:"+last_name+"\n emailid:"+email_id+"" +
            "\n mobile no:"+ mobile_no+"\n password is:"+pass_word+"\n confirm:"+con_firm;

    Toast.makeText(this,"registerd succesfully",Toast.LENGTH_SHORT).show();
   Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT) .toString();



}


}



