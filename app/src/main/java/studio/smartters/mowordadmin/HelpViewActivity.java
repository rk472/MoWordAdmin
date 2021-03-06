package studio.smartters.mowordadmin;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

import studio.smartters.mowordadmin.adapter.DataAdapter;
import studio.smartters.mowordadmin.adapter.HelpAdapter;
import studio.smartters.mowordadmin.others.Constants;

public class HelpViewActivity extends AppCompatActivity {
    private static HelpViewActivity inst;
    private RecyclerView list;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inst=this;
        list=findViewById(R.id.help_search_name_list);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        list.setHasFixedSize(true);
        id=getSharedPreferences("login",MODE_PRIVATE).getString("id","0");
        refresh();
    }
    public void refresh(){
        GetDataTask gt=new GetDataTask();
        gt.execute(Constants.URL+"getHelpByAdmin?id="+id);
    }
    public static HelpViewActivity getInstance(){
        return inst;
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
                List<JSONObject> jsonList = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    jsonList.add(arr.getJSONObject(i));
                    Log.e("arr", arr.getJSONObject(i).toString());
                }

                HelpAdapter d = new HelpAdapter(jsonList, HelpViewActivity.this);
                list.setAdapter(d);

            } catch(JSONException e){
                Toast.makeText(HelpViewActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }


}
