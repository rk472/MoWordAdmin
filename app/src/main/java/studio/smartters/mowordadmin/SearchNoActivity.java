package studio.smartters.mowordadmin;

import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import studio.smartters.mowordadmin.others.Constants;

public class SearchNoActivity extends AppCompatActivity {
    private EditText etSearch;
    private String id;
    private RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_no);
        etSearch = findViewById(R.id.search_no_text);
        list=findViewById(R.id.search_no_list);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        id=getSharedPreferences("login",MODE_PRIVATE).getString("id","0");
        refresh("");
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                refresh(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    void refresh(String name){
        GetDataTask gt=new GetDataTask();
        gt.execute(Constants.URL+"getAllDataByAdminByNumber?id="+id+"&phone="+name);
    }
    public void clearText(View view) {
        etSearch.setText("");
    }
    public void goBack(View view) {
        finish();
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
                //Toast.makeText(SearchNoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                //Toast.makeText(SearchNoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray arr = new JSONArray(s);
                if (arr.length() == 0)
                    Snackbar.make(etSearch, "No data found", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                List<JSONObject> jsonList = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    jsonList.add(arr.getJSONObject(i));
                    Log.e("arr", arr.getJSONObject(i).toString());
                }

                DataAdapter d = new DataAdapter(jsonList, SearchNoActivity.this);
                list.setAdapter(d);

            } catch(JSONException e){
                Toast.makeText(SearchNoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
