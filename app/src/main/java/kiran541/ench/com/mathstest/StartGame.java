package kiran541.ench.com.mathstest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sdsmdg.tastytoast.TastyToast;
import com.yarolegovich.lovelydialog.LovelySaveStateHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import info.hoang8f.widget.FButton;

public class StartGame extends AppCompatActivity  {
TextView timer;
     long timeCountInMilliSeconds = 1 * 60000;
    String user,umail,uid;
     enum TimerStatus {
        STARTED,
        STOPPED
    }
    CardView cardView;

     TimerStatus timerStatus = TimerStatus.STOPPED;
     LovelySaveStateHandler saveStateHandler;
String cal_answer;
     ProgressBar progressBarCircle;
     TextView textViewTime,stor_ans,totalscore;
     ImageView imageViewReset;
     Button imageViewStartStop,playagain,share;
     CountDownTimer countDownTimer;
    FButton disabledBtn;
    EditText val;
     int level = 0, answer, operator = 0, operand1 = 0, operand2 = 0;
     final int ADD_OPERATOR = 0, SUBTRACT_OPERATOR = 1, MULTIPLY_OPERATOR = 2, DIVIDE_OPERATOR = 3,MODULO_OPERATOR =4;
     String[] operators = {"+", "-", "x", "/"};
     int[][] levelMin = {
            {15, 11, 21},
            {12, 54, 10},
            {28, 5, 10},
            {2, 13, 5}};
     int[][] levelMax = {
            {10, 25, 150},
            {120, 210, 30},
            {5, 100, 15},
            {10, 50, 100}};

     Random random;
     TextView crct, wrong, question;
     ImageView response;
    String level1;
    String st,ver;
    int crctans=1,wrongans=1,chec=0,countplus,decr,countminus,total;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, enterBtn,dot;
     SharedPreferences gamePrefs;
    ImageView imageView;
    String crctanswer,totalcnt,usernam,usermail;
    private RelativeLayout rootContent;

    public static final String GAME_PREFS = "ArithmeticFile";
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user1 = session.getUserDetails();
        usernam = user1.get(SessionManager.KEY_NAME1);

        usermail = user1.get(SessionManager.KEY_EMAIL1);
        gamePrefs = getSharedPreferences(GAME_PREFS, 0);
        imageView=(ImageView)findViewById(R.id.image);
        val=(EditText)findViewById(R.id.str);
        crct=(TextView)findViewById(R.id.correct);
        wrong=(TextView)findViewById(R.id.wrng);
        question=(TextView)findViewById(R.id.question);
        cardView=(CardView)findViewById(R.id.cardView1);
        btn1 = (FButton)findViewById(R.id.btn1);
        btn2 = (FButton)findViewById(R.id.btn2);
        btn3 = (FButton)findViewById(R.id.btn3);
        btn4 = (FButton)findViewById(R.id.btn4);
        btn5 = (FButton)findViewById(R.id.btn5);
        btn6 = (FButton)findViewById(R.id.btn6);
        btn7 = (FButton)findViewById(R.id.btn7);
        btn8 = (FButton)findViewById(R.id.btn8);
        btn9 = (FButton)findViewById(R.id.btn9);
        btn0 = (FButton)findViewById(R.id.btn0);
        rootContent=(RelativeLayout) findViewById(R.id.nnmmkk);
        dot = (FButton)findViewById(R.id.dot);
        playagain = (FButton)findViewById(R.id.playagain);
        share = (FButton)findViewById(R.id.share);
        stor_ans=(TextView) findViewById(R.id.store_ans);
        totalscore=(TextView)findViewById(R.id.total);
        enterBtn = (FButton)findViewById(R.id.enter);
//        clearBtn = (FButton)findViewById(R.id.clear);
        saveStateHandler = new LovelySaveStateHandler();
        Intent intent = getIntent();
        user = intent.getStringExtra("username");
        umail = intent.getStringExtra("email");
        uid = intent.getStringExtra("uid");

        playagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartGame.this,MainActivity.class));
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent sendIntent=new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(sendIntent.EXTRA_TEXT,"This game is Assume ");
//
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);
                takeScreenshot(ScreenshotType.FULL);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val.setText(val.getText()+"1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val.setText(val.getText()+"2");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val.setText(val.getText()+"3");
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val.setText(val.getText()+"4");
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val.setText(val.getText()+"5");
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val.setText(val.getText()+"6");
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val.setText(val.getText()+"7");
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val.setText(val.getText()+"8");
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val.setText(val.getText()+"9");
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val.setText(val.getText()+"0");
            }
        });
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val.setText(val.getText()+".");
            }
        });

enterBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        val.setText("");
    }
});
        if(savedInstanceState!=null){

            level=savedInstanceState.getInt("level");
            int exScore = savedInstanceState.getInt("score");
            crct.setText("Score: "+exScore);
        }

        else{
            Bundle extras = getIntent().getExtras();
            if(extras != null)
            {
                int passedLevel = extras.getInt("level", -1);
                if(passedLevel>=0) level= passedLevel;
                // Toast.makeText(getApplicationContext(),Integer.toString(level),Toast.LENGTH_LONG).show();
            }
        }

        st=val.getText().toString();
        random = new Random();
        chooseQuestion();
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);

        textViewTime = (TextView) findViewById(R.id.textViewTime);
        val.addTextChangedListener(new TextWatcher(){

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cal_answer=stor_ans.getText().toString();
               if (cal_answer.length()==count) {

                   StartGame.this.updateValue();
               }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {

                    if(chec==0){
                        startStop();
                        chec++;

                    }



            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void takeScreenshot(ScreenshotType screenshotType) {
        Bitmap b = null;
        switch (screenshotType) {
            case FULL:
                //If Screenshot type is FULL take full page screenshot i.e our root content.
                b = ScreenshotUtils.getScreenShot(rootContent);
                break;

        }

        //If bitmap is not null
        if (b != null) {
          //  showScreenShotImage(b);//show bitmap over imageview

            File saveFile = ScreenshotUtils.getMainDirectoryName(this);//get the path to save screenshot
            File file = ScreenshotUtils.store(b, "screenshot" + screenshotType + ".jpg", saveFile);//save the screenshot to selected path
            shareScreenshot(file);//finally share screenshot
        } else
            //If bitmap is null show toast message
            Toast.makeText(this, "Screen shot taken failed", Toast.LENGTH_SHORT).show();

    }


    /*  Share Screenshot  */
    private void shareScreenshot(File file) {
        Uri uri = Uri.fromFile(file);//Convert file path into Uri for sharing
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.sharing_text));
        intent.putExtra(Intent.EXTRA_STREAM, uri);//pass uri here
        startActivity(Intent.createChooser(intent, getString(R.string.share_title)));





    }

    private void updateValue() {
        ver = val.getText().toString();
        try {
            if (Integer.parseInt(ver) == answer) {
                val.setBackgroundResource(R.color.fbutton_color_emerald);
                countplus = crctans++;
                crct.setText(Integer.toString(countplus));
                chooseQuestion();
            } else {
                if(decr==0) {
                    countminus=wrongans++;
                    wrong.setText(Integer.toString(countminus));
                    decr++;
                }
                val.setBackgroundResource(R.color.fbutton_color_pomegranate);
                ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(600);
                val.setText("");
            }

        } catch (NumberFormatException ne) {

        }




    }


    private void chooseQuestion(){
            val.setText("");
        decr=0;
        operator = random.nextInt(operators.length);
        operand1 = getOperand();
        operand2 = getOperand();
        if(operator == SUBTRACT_OPERATOR){
            while(operand2>operand1){
                operand1 = getOperand();
                operand2 = getOperand();
            }
        }
        else if(operator==DIVIDE_OPERATOR){
            while((((double)operand1/(double)operand2)%1 > 0) || (operand1==operand2))
            {
                operand1 = getOperand();
                operand2 = getOperand();
            }
        }
        switch(operator)
        {
            case ADD_OPERATOR:
                answer = operand1+operand2;
                stor_ans.setText(Integer.toString(answer));

                break;
            case SUBTRACT_OPERATOR:
                answer = operand1-operand2;
                stor_ans.setText(Integer.toString(answer));

                break;
            case MULTIPLY_OPERATOR:
                answer = operand1*operand2;
                stor_ans.setText(Integer.toString(answer));

                break;
            case DIVIDE_OPERATOR:
                answer = operand1/operand2;
                stor_ans.setText(Integer.toString(answer));

                break;
            default:
                break;
        }
        question.setText(operand1+"    "+operators[operator]+"    "+operand2);
    }


    private int getOperand(){
        return random.nextInt(levelMax[operator][level] - levelMin[operator][level] + 1)
                + levelMin[operator][level];
    }
    private void reset() {
        stopCountDownTimer();
        startCountDownTimer();
    }

    private void startStop() {
        if (timerStatus == TimerStatus.STOPPED) {

            // call to initialize the timer values
            setTimerValues();
            // call to initialize the progress bar values
            setProgressBarValues();
            // showing the reset icon
           // imageViewReset.setVisibility(View.VISIBLE);
            // changing play icon to stop icon
            //imageViewStartStop.setText("stop");
            // making edit text not editable
          //  editTextMinute.setEnabled(false);
            // changing the timer status to started
            timerStatus = TimerStatus.STARTED;

            // call to start the count down timer
            startCountDownTimer();

        } else {

            timerStatus = TimerStatus.STOPPED;
            stopCountDownTimer();

        }

    }
    private void setTimerValues() {
        int time = 1;
        timeCountInMilliSeconds = time * 60 * 1000;
    }



    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));


            }

            @Override
            public void onFinish() {

               textViewTime.setText("00:00");
                setProgressBarValues();

                total=countplus+countminus;
                cardView.setVisibility(View.INVISIBLE);
                question.setVisibility(View.INVISIBLE);
                val.setVisibility(View.INVISIBLE);
                btn1.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.INVISIBLE);
                btn4.setVisibility(View.INVISIBLE);
                btn5.setVisibility(View.INVISIBLE);
                btn6.setVisibility(View.INVISIBLE);
                btn7.setVisibility(View.INVISIBLE);
                btn8.setVisibility(View.INVISIBLE);
                btn9.setVisibility(View.INVISIBLE);
                btn0.setVisibility(View.INVISIBLE);
                dot.setVisibility(View.INVISIBLE);
                enterBtn.setVisibility(View.INVISIBLE);
                totalscore.setVisibility(View.VISIBLE);
                share.setVisibility(View.VISIBLE);
                playagain.setVisibility(View.VISIBLE);
                totalscore.setText("Total Questions Attempted  = "+total);
//                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
//                mediaPlayer.start();
//                new LovelyInfoDialog(StartGame.this)
//                        .setTopColorRes(R.color.darkBlueGrey)
//                        .setIcon(R.drawable.ic_info_outline_white_36dp)
//                        .setNotShowAgainOptionEnabled(0)
//                        .setNotShowAgainOptionChecked(true)
//                        .setTitle("Game Completed")
//                        .setMessage("Total Attempted  ="+total)
//                        .show();
                crctanswer=Integer.toString(countplus);
                totalcnt=Integer.toString(total);
                timerStatus = TimerStatus.STOPPED;
                insert_service(usernam,totalcnt,crctanswer);
            }

        }.start();
        countDownTimer.start();
    }


    private void stopCountDownTimer() {
        countDownTimer.cancel();
    }

    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }


    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;


    }

    private void insert_service(final String usernam, final String totalcnt, final String crctanswer) {

        StringRequest stringreqs = new StringRequest(Request.Method.POST, MyGlobal_Url.MYBASIC_SCOREUPDATE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean abc = jObj.getBoolean("error");




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
                uandme.put("username", usernam);
                uandme.put("total", totalcnt);
                uandme.put("crct", crctanswer);
                return uandme;
            }
        };
        AppController.getInstance().addToRequestQueue(stringreqs);

    }


}
