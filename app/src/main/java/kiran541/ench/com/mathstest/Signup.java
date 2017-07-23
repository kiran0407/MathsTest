package kiran541.ench.com.mathstest;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class Signup extends AppCompatActivity {
    TextView signinlink;
    Button signup;
    EditText usrname,umail,uphno,upswd;
    String usrname1,umail1,uphno1,upswd1;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signinlink=(TextView)findViewById(R.id.signinlink);
        usrname=(EditText)findViewById(R.id.username);
        umail=(EditText)findViewById(R.id.email);
        uphno=(EditText)findViewById(R.id.phno);
        upswd=(EditText)findViewById(R.id.password);
        signup=(Button)findViewById(R.id.signupBtn);
        session = new SessionManager(getApplicationContext());

        signinlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent(Signup.this,
                        Signin.class);
                Bundle bndlanimation =
                        null;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.bottomup,R.anim.bottomdown).toBundle();
                    startActivity(mainIntent, bndlanimation);
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usrname1=usrname.getText().toString();
                umail1=umail.getText().toString();
                uphno1=uphno.getText().toString();
                upswd1=upswd.getText().toString();
                if(usrname1.isEmpty()){
                    TastyToast.makeText(getApplicationContext(), " please enter user name", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else if(umail1.isEmpty()){
                    TastyToast.makeText(getApplicationContext(), " please enter email adress", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else if(uphno1.isEmpty()){
                    TastyToast.makeText(getApplicationContext(), " please enter phone number", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else if(uphno1.length()<10){
                    TastyToast.makeText(getApplicationContext(), "phone number is not less than 10 characters", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else if(upswd1.isEmpty()){
                    TastyToast.makeText(getApplicationContext(), "please enter password", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else if(upswd1.length()<6){
                    TastyToast.makeText(getApplicationContext(), "password is not less than 6 characters", TastyToast.LENGTH_LONG,
                            TastyToast.ERROR);
                }
                else{
                    insert_service(usrname1,umail1,uphno1,upswd1);
                }

            }
        });

    }
    private void insert_service(final String usrname1, final String umail1,final String uphno1, final String upswd1) {

        StringRequest stringreqs = new StringRequest(Request.Method.POST, MyGlobal_Url.MYBASIC_SIGNUP, new Response.Listener<String>() {
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

                           // session.setLogin(true);
                        session.createLoginSession(user, email,true);
                            Intent intent = new Intent(Signup.this, MainActivity.class);
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
                uandme.put("username", usrname1);
                uandme.put("uemail", umail1);
                uandme.put("uphno", uphno1);
                uandme.put("upswd", upswd1);
                return uandme;
            }
        };
        AppController.getInstance().addToRequestQueue(stringreqs);

    }


}
