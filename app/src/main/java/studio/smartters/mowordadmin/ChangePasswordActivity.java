package studio.smartters.mowordadmin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import studio.smartters.mowordadmin.others.Constants;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText ownPassText,passText,confPassText;
    private ProgressDialog p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ownPassText=findViewById(R.id.own_pass);
        passText=findViewById(R.id.pass);
        confPassText=findViewById(R.id.conf_pass);
    }

    public void changePassword(View view) {
        String ownPass=ownPassText.getText().toString();
        String pass=passText.getText().toString();
        String confPass=confPassText.getText().toString();
        if(TextUtils.isEmpty(ownPass) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(confPass)){
            Toast.makeText(this, "Fields can't be blank", Toast.LENGTH_SHORT).show();
        }else if(!pass.equals(confPass)){
            Toast.makeText(this, "Confirm the exact password to change", Toast.LENGTH_SHORT).show();
        }else if(ownPass.equals(pass)){
            Toast.makeText(this, "Password can't be changed as You have given the same password", Toast.LENGTH_SHORT).show();
        }else{
            String id=getSharedPreferences("login",MODE_PRIVATE).getString("id","0");
            p=new ProgressDialog(this);
            p.setCancelable(false);
            p.setCanceledOnTouchOutside(false);
            p.setTitle("Please wait");
            p.setMessage("Please wait while we are changing your password");
            p.show();
            ChangeTask ct=new ChangeTask();
            ct.execute(Constants.URL+"changeAdminPassword?id="+id+"&newPass="+pass+"&oldPass="+ownPass);
        }

    }
    @SuppressLint("StaticFieldLeak")
    private class ChangeTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                URLConnection con=url.openConnection();
                InputStream is=con.getInputStream();
                InputStreamReader ir=new InputStreamReader(is);
                int data=ir.read();
                StringBuilder res= new StringBuilder();
                while(data!=-1){
                    res.append((char) data);
                    data=ir.read();
                }
                return res.toString();
            } catch (IOException e) {
                return "Can't reach server";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            p.dismiss();
            Toast.makeText(ChangePasswordActivity.this, s, Toast.LENGTH_SHORT).show();
            if(s.equalsIgnoreCase("Successfully changed")){
                finish();
            }
        }
    }
}
