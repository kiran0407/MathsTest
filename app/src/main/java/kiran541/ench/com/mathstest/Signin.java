package kiran541.ench.com.mathstest;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signin extends AppCompatActivity {
EditText user,pswd;
    Button login;
    String username,pswrd,usertxt,pass;
    CheckBox show_hide_password;
    SessionManager session;
    TextView signuplink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signin);
        session = new SessionManager(getApplicationContext());
        show_hide_password = (CheckBox)findViewById(R.id.show_hide_password);
        signuplink=(TextView)findViewById(R.id.signuplink);
        user=(EditText)findViewById(R.id.userid);
        pswd=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.loginBtn);
        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent2 = new Intent(Signin.this,
                        Signup.class);
                Bundle bndlanimation2 =
                        null;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bndlanimation2 = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fadein,R.anim.fadeout).toBundle();
                    startActivity(mainIntent2, bndlanimation2);
                }
            }
        });
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
                    TastyToast.makeText(getApplicationContext(), " please enter user name", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                } else if (pass.isEmpty()) {
                    TastyToast.makeText(getApplicationContext(), " please enter password", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else if (pass.length()<6) {
                    TastyToast.makeText(getApplicationContext(), "password is not les than 6 characters", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }else {

                    insert_service(usertxt,pass);
            }
            }
        });

    }
    private void insert_service(final String usertxt, final String pass) {

        StringRequest stringreqs = new StringRequest(Request.Method.POST, MyGlobal_Url.MYBASIC_SIGNIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean abc = jObj.getBoolean("error");
                    if (abc) {
                        JSONObject users = jObj.getJSONObject("users");
                        String uid = users.getString("uid");
                        String phno = users.getString("phno");
                        String user=users.getString("name");
                        String email=users.getString("email");


                        //session.setLogin(true);
                        session.createLoginSession(user, email,true);
                        Intent intent = new Intent(Signin.this, MainActivity.class);
                        intent.putExtra("uid", uid);
                        intent.putExtra("mobile", phno);
                        intent.putExtra("username", user);
                        intent.putExtra("email", email);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

//                       Toast.makeText(getApplicationContext(),   name1, Toast.LENGTH_SHORT).show();
//                       Toast.makeText(getApplicationContext(), email1, Toast.LENGTH_SHORT).show();

                    }
                    else {
                        String msg=jObj.getString("messeade");
                        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        TastyToast.makeText(getApplicationContext(), msg, TastyToast.LENGTH_LONG,
                                TastyToast.WARNING);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Toast.makeText(getApplicationContext(), "INTERNET CONNECTION NOT AVAILABLE", Toast.LENGTH_SHORT).show();
                TastyToast.makeText(getApplicationContext(), "INTERNET CONNECTION NOT AVAILABLE", TastyToast.LENGTH_LONG,
                        TastyToast.ERROR);
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> uandme = new HashMap<String, String>();
                uandme.put("username", usertxt);
                uandme.put("pswd", pass);

                return uandme;
            }
        };
        AppController.getInstance().addToRequestQueue(stringreqs);

    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application

                moveTaskToBack(true);
//

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
