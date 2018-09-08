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

import studio.smartters.mowordadmin.Fragment.ViewBoothFragment;
import studio.smartters.mowordadmin.adapter.BoothAdapter;
import studio.smartters.mowordadmin.others.Constants;

public class AllBoothActivity extends AppCompatActivity {
    RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_booth);
        list=findViewById(R.id.all_booth_list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        refresh();
    }
    void refresh(){
        if(Constants.isNetworkAvailable(this)) {
            String ward=getIntent().getExtras().getString("id");
            BoothTask pt=new BoothTask();
            pt.execute("http://205.147.101.127:8084/MoWord/getBooths?ward="+ward);
            //ln.setVisibility(View.GONE);
        }else{
            //ln.setVisibility(View.VISIBLE);
        }
    }
    private class BoothTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                URLConnection con = url.openConnection();
                InputStream is = con.getInputStream();
                InputStreamReader ir = new InputStreamReader(is);
                int data = ir.read();
                String res = "";
                while (data != -1) {
                    res += (char) data;
                    data = ir.read();
                }
                return res;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            List<String> boothId = new ArrayList<>();
            List<String> boothName = new ArrayList<>();
            try {
                JSONArray arr = new JSONArray(s);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject json = arr.getJSONObject(i);
                    boothId.add(json.getString("id"));
                    boothName.add(json.getString("name"));
                }
                BoothAdapter a = new BoothAdapter(AllBoothActivity.this, boothName, boothId);
                list.setAdapter(a);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
}
