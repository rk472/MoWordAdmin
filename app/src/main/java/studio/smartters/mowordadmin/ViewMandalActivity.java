package studio.smartters.mowordadmin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import studio.smartters.mowordadmin.adapter.BoothAdapter;
import studio.smartters.mowordadmin.adapter.MandalAdapter;
import studio.smartters.mowordadmin.others.Constants;
import studio.smartters.mowordadmin.others.SurveyMan;

public class ViewMandalActivity extends AppCompatActivity {
    private RecyclerView list;
    private String id;
    public ProgressDialog p;
    private static ViewMandalActivity inst;
    public static ViewMandalActivity getInstance(){
        return inst;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_survey_man);
        inst=this;
        list=findViewById(R.id.survey_man_list);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        id=getSharedPreferences("login",MODE_PRIVATE).getString("id","0");
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        refresh("");
    }
    public void refresh(String name){
        GetDataTask gt=new GetDataTask();
        gt.execute(Constants.URL+"getMandals?id="+id);
    }
    private class GetDataTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                URLConnection con=url.openConnection();
                InputStream is=con.getInputStream();
                InputStreamReader ir=new InputStreamReader(is);
                int data=ir.read();
                String res="";
                while(data!=-1){
                    res+=(char)data;
                    data=ir.read();
                }
                return res;
            } catch (MalformedURLException e) {
                //Toast.makeText(SearchNameActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                //Toast.makeText(SearchNameActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray arr = new JSONArray(s);
                if (arr.length() == 0)
                    Snackbar.make(list, "No data found", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                List<String> id = new ArrayList<>();
                List<String> name=new ArrayList<>();
                List<String> userName=new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject json=arr.getJSONObject(i);
                    id.add(json.getString("id"));
                    name.add(json.getString("name"));
                    userName.add(json.getString("user_name"));
                    Log.e("arr", arr.getJSONObject(i).toString());
                }

                MandalAdapter d = new MandalAdapter( ViewMandalActivity.this,name,id,userName);
                list.setAdapter(d);

            } catch(JSONException e){
                Toast.makeText(ViewMandalActivity.this, s, Toast.LENGTH_SHORT).show();
            }

        }
    }
}
