package kiran541.ench.com.mathstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.sdsmdg.tastytoast.TastyToast;

public class Signin extends AppCompatActivity {
EditText user,pswd;
    Button login;
    String username="admin",pswrd="admin",usertxt,pass;
    CheckBox show_hide_password;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signin);
        session = new SessionManager(getApplicationContext());
        show_hide_password = (CheckBox)findViewById(R.id.show_hide_password);
        user=(EditText)findViewById(R.id.userid);
        pswd=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.loginBtn);
        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {


                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change


                            pswd.setInputType(InputType.TYPE_CLASS_TEXT);
                            pswd.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change

                            pswd.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            pswd.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usertxt = user.getText().toString();
                pass = pswd.getText().toString();
                if (usertxt.isEmpty()) {
                    TastyToast.makeText(getApplicationContext(), " please enter user id", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                } else if (pass.isEmpty()) {
                    TastyToast.makeText(getApplicationContext(), " please enter password", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                } else {

                if (usertxt.equals(username) && pass.equals(pswrd)) {

                    session.setLogin(true);
                    Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                    i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i1);
                    finish();
                } else {
                    TastyToast.makeText(getApplicationContext(), "Invalid Credentials Please Try Again..", TastyToast.LENGTH_LONG,
                            TastyToast.WARNING);

                }
            }
            }
        });

    }
}
