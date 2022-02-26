package com.example.uidemo;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView pass,otp,timer,send;
    EditText editpass,editemail;
    boolean setotp;
    SQLiteDatabaseHandler db;
    boolean timerOn = true;
    ImageButton submitdata;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        db = new SQLiteDatabaseHandler(this);
//       long a= db.addUser_info();
        db.insertdata();
//       Log.d("sdsdsada", String.valueOf(a));
         pass = (TextView) findViewById(R.id.password_select);
         otp = (TextView) findViewById(R.id.otp_select);
         timer = (TextView)findViewById(R.id.timeertext);
         send = (TextView)findViewById(R.id.send);
         editemail = (EditText) findViewById(R.id.editemail);
         editpass = (EditText)findViewById(R.id.password);
         submitdata = (ImageButton) findViewById(R.id.submit);
         submitdata.setOnClickListener(this);
         pass.setOnClickListener(this);
         otp.setOnClickListener(this);
         timer.setVisibility(View.INVISIBLE);
         send.setVisibility(View.INVISIBLE);

         send.setOnClickListener(this);

    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.password_select:
                editpass.setText("");
                pass.setBackground(getResources().getDrawable(R.drawable.select_type));
                otp.setBackground(getResources().getDrawable(R.drawable.type_unselect));
                otp.setTextColor(getResources().getColor(R.color.pink));
                pass.setTextColor(getResources().getColor(R.color.white));
                pass.setElevation(8);
                send.setVisibility(view.INVISIBLE);
                timer.setVisibility(view.INVISIBLE);
                otp.setElevation(0);
                if (setotp){
                    editpass.setHint("Enter OTP");
                    editpass.setInputType(InputType.TYPE_CLASS_NUMBER);

                }else {
                    editpass.setHint("Password");
                    editpass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.otp_select:
                editpass.setText("");
                pass.setBackground(getResources().getDrawable(R.drawable.type_unselect));
                otp.setBackground(getResources().getDrawable(R.drawable.select_type));
                otp.setTextColor(getResources().getColor(R.color.white));
                pass.setTextColor(getResources().getColor(R.color.pink));
                otp.setElevation(8);
                pass.setElevation(0);
                send.setVisibility(view.VISIBLE);
                timer.setVisibility(view.VISIBLE);
                if (setotp){
                    editpass.setHint("Enter OTP");
                    editpass.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                else {
                    editpass.setHint("Phone No");
                    editpass.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                break;

            case R.id.send:
                editpass.setHint("Enter OTP");
                setotp = true;
                if (timerOn) {
                    new CountDownTimer(30000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            timerOn = false;
                            NumberFormat f = new DecimalFormat("00");
                            long min = (millisUntilFinished / 60000) % 60;
                            long sec = (millisUntilFinished / 1000) % 60;
                            timer.setText(f.format(min) + ":" + f.format(sec));
                        }

                        public void onFinish() {
                            timerOn = true;
                            send.setText("Re-Send");
                            timer.setText("00:00");
                        }
                    }.start();
                }
            case  R.id.submit:
                if (!(editemail.getText().toString().trim().matches(emailPattern)) || editpass.length()==0 || editpass.length() < 8&& !(setotp)){
                    Toast.makeText(getApplicationContext(),"Enter Valid Detail",Toast.LENGTH_SHORT).show();
                }
                if (editpass.length() != 6 && setotp){
                    Toast.makeText(getApplicationContext(),"Enter valid OTP",Toast.LENGTH_SHORT).show();
                }
                String email =editemail.getText().toString();
                String password = editpass.getText().toString();
               /* Log.d("sdsdsd",email+"=="+password);
                Boolean isLogin = db.checkusernamepassword(email,password);*/
                Cursor cursor = db.fetch();
                cursor.moveToFirst();
                Log.d("sdssd",cursor.getString(0)+"==="+cursor.getString(1));

                if (email.equals(cursor.getString(0)) && password.equals(cursor.getString(1))){
                    Toast.makeText(getApplicationContext(),"Login Succesful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,Homepage.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_SHORT).show();
                }

        }
    }


}