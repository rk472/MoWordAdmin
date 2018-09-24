package studio.smartters.mowordadmin;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

import studio.smartters.mowordadmin.Fragment.ViewWardFragment;
import studio.smartters.mowordadmin.adapter.WardAdapter;
import studio.smartters.mowordadmin.others.Constants;

public class AllWardActivity extends AppCompatActivity {
    private RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ward);
        list=findViewById(R.id.ward_list);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        list.setHasFixedSize(true);
        refresh();
    }
    void refresh(){
        if(Constants.isNetworkAvailable(this)) {
            String id = getIntent().getExtras().getString("id");
            GetWordTask gt = new GetWordTask();
            gt.execute(Constants.URL + "getWards?mandal=" + id);
            //ln.setVisibility(View.GONE);
        }else{
            //ln.setVisibility(View.VISIBLE);
        }
    }
    private class GetWordTask extends AsyncTask<String,Void,String> {

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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray arr=new JSONArray(s);
                List<String> names=new ArrayList<>();
                List<String> ids=new ArrayList<>();
                for(int i=0;i<arr.length();i++){
                    JSONObject json=arr.getJSONObject(i);
                    names.add(json.getString("name"));
                    ids.add(json.getString("id"));
                    WardAdapter a=new WardAdapter(AllWardActivity.this,names,ids);
                    list.setAdapter(a);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
