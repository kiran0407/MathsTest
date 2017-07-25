package kiran541.ench.com.mathstest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HighScore extends AppCompatActivity {
    SessionManager session;
    String usernam,usermail,user,email,myurl,count;
    ListView scorelist;
    TextView urank;
    int coun=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        user = intent.getStringExtra("username");
        email = intent.getStringExtra("email");
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user1 = session.getUserDetails();
        usernam = user1.get(SessionManager.KEY_NAME1);

        usermail = user1.get(SessionManager.KEY_EMAIL1);
        if (!session.isLoggedIn()) {
            session.createLoginSession(user, email,false);
            Intent intent1 = new Intent(HighScore.this, Signin.class);
            startActivity(intent1);
            finish();
        }
        scorelist=(ListView)findViewById(R.id.scorelist);
        urank=(TextView)findViewById(R.id.r1);
        myurl=MyGlobal_Url.MYBASIC_SCOREDETAILS+"?username="+usernam;
        new kilomilo().execute(myurl);

//        myurl=inputurl.VALIDATE+"?slat="+latLng.latitude+"&slng="+latLng.longitude;
//        // new kilomilo().execute(inputurl.VALIDATE);
//        new kilomilo().execute(myurl);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public class MovieAdap extends ArrayAdapter {
        private List<Scores> movieModelList;
        private int resource;
        private int selectedPosition = -1;
        Context context;
        private LayoutInflater inflater;
        MovieAdap(Context context, int resource, List<Scores> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context =context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getViewTypeCount() {
            return 1;
        }
        @Override
        public int getItemViewType(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder  ;
            if(convertView == null){
                convertView = inflater.inflate(resource,null);
                holder = new ViewHolder();
                //  holder.logo=(ImageView) convertView.findViewById(R.id.teamlogo);
                holder.rank1=(TextView) convertView.findViewById(R.id.rank);
                holder.uname1=(TextView) convertView.findViewById(R.id.uname);
                holder.crct1=(TextView) convertView.findViewById(R.id.crct);
                holder.total1=(TextView) convertView.findViewById(R.id.total1);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            //  holder.checkBox.setTag(position);
            Scores cc=movieModelList.get(position);
           // count=Integer.toString(coun);
            holder.rank1.setText(cc.getRank());
            holder.uname1.setText(cc.getName());
            holder.crct1.setText(cc.getPoints());
            holder.total1.setText(cc.getTotal());
            if(usernam.contains(cc.getName())){
               urank.setText(cc.getRank());
                holder.uname1.setText(cc.getName()+"*");

            }
            //holder.tname.setText(getItem(position).getName());


//            Toast.makeText(getApplicationContext(),cc.getName(),Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(),cc.getPoints(),Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(),cc.getTotal(),Toast.LENGTH_LONG).show();
            // Picasso.with(context).load(cc.getTeamlogo()).fit().error(R.drawable.footballlogo).fit().into(holder.logo);
            //   Glide.with(context).load(cc.getTeamlogo()).into(holder.logo);
            return convertView;
        }

        class ViewHolder{


            public TextView rank1,uname1,crct1,total1;


        }
    }
    public class kilomilo extends AsyncTask<String,String, List<Scores>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected List<Scores> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("result");
                List<Scores> milokilo = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    Scores catego = gson.fromJson(finalObject.toString(), Scores.class);
                    milokilo.add(catego);
                }
                return milokilo;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(final List<Scores> movieMode) {
            super.onPostExecute(movieMode);
            if (movieMode!=null)
            {
                MovieAdap adapter = new MovieAdap(getApplicationContext(), R.layout.scorelist, movieMode);
                scorelist.setAdapter(adapter);
                scorelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Scores item = movieMode.get(position);
//                        Intent intent=new Intent(AllStudents.this,Selection.class);
//                      //  intent.putExtra("pname",user1);
//


                    }
                });
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Check your internet connection",Toast.LENGTH_SHORT).show();
            }
        }
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
