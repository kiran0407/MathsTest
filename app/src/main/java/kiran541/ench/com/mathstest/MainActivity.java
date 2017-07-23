package kiran541.ench.com.mathstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yarolegovich.lovelydialog.LovelySaveStateHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    EditText id;
    Button start,highscore;
    String id1,usernam,usermail;
    private String[] levelNames = {"Easy", "Medium", "Hard"};
    private static final int ID_MULTI_CHOICE_DIALOG = R.id.start_btn;
    private LovelySaveStateHandler saveStateHandler;
    final CharSequence[] items = {" Easy "," Medium "," Hard "," Very Hard "};
    SessionManager session;
    int chosenLevel=2;
    String user,email,uid;
    TextView nam;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // id=(EditText)findViewById(R.id.username);
        session = new SessionManager(getApplicationContext());

      //  session.checkLogin();
        HashMap<String, String> user1 = session.getUserDetails();
        usernam = user1.get(SessionManager.KEY_NAME1);

        usermail = user1.get(SessionManager.KEY_EMAIL1);

        Intent intent = getIntent();
        user = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        uid = intent.getStringExtra("uid");
        nam=(TextView)findViewById(R.id.textView2);
      //  setTitle(usernam);

        if (!session.isLoggedIn()) {
            session.createLoginSession(user, email,false);
            Intent intent1 = new Intent(MainActivity.this, Signin.class);
            startActivity(intent1);
            finish();
        }
        nam.setText("Welcome  "+usernam);
      //  Toast.makeText(getApplicationContext(), usernam, Toast.LENGTH_SHORT).show();
        saveStateHandler = new LovelySaveStateHandler();
        start=(Button)findViewById(R.id.start_btn);
        highscore=(Button)findViewById(R.id.high_btn);
        final ArrayList seletedItems=new ArrayList();
        highscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   startActivity(new Intent(MainActivity.this,HighScore.class));
                Intent intent = new Intent(MainActivity.this, HighScore.class);
                intent.putExtra("username", user);
                intent.putExtra("email", email);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //id1=id.getText().toString();

                    //startActivity(new Intent(MainActivity.this, StartGame.class));

                Intent intent = new Intent(MainActivity.this, StartGame.class);
                intent.putExtra("level", chosenLevel);
                intent.putExtra("uid", uid);
                intent.putExtra("username", user);
                intent.putExtra("email", email);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//
//
//                builder.setTitle("Choose a level")
//                        .setSingleChoiceItems(levelNames, 0, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                //start gameplay
//                                startPlay(which);
//                            }
//                        });
//                AlertDialog ad = builder.create();
//                ad.show();



            }
        });

    }
    private void startPlay(int chosenLevel)
    {
        //start gameplay
        Intent playIntent = new Intent(this, StartGame.class);
        playIntent.putExtra("level", chosenLevel);
        this.startActivity(playIntent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            session.logoutUser();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
