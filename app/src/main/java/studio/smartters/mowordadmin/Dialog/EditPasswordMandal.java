package studio.smartters.mowordadmin.Dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import studio.smartters.mowordadmin.Fragment.ViewBoothFragment;
import studio.smartters.mowordadmin.R;
import studio.smartters.mowordadmin.ViewMandalActivity;
import studio.smartters.mowordadmin.others.Constants;

public class EditPasswordMandal extends Dialog {
    private EditText et_name,et_pass,et_own_pass;
    private Button btn_create;
    private AppCompatActivity c;
    private ViewMandalActivity fragment=ViewMandalActivity.getInstance();
    public EditPasswordMandal(@NonNull AppCompatActivity context, final String id, final String user_name) {
        super(context);
        setContentView(R.layout.modal_password);
        et_name = findViewById(R.id.modal_name);
        et_name.setText(user_name);
        et_pass = findViewById(R.id.modal_password);
        final String myId=context.getSharedPreferences("login",Context.MODE_PRIVATE).getString("id","0");
        et_own_pass = findViewById(R.id.modal_own_password);
        btn_create = findViewById(R.id.modal_btn);
        c=context;
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=et_name.getText().toString().trim();
                String pass=et_pass.getText().toString().trim();
                String own_pass=et_own_pass.getText().toString().trim();
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(own_pass)||TextUtils.isEmpty(pass)) {
                    Toast.makeText(c, "Field's can't be empty..", Toast.LENGTH_SHORT).show();
                }else {
                    fragment.p=new ProgressDialog(c);
                    fragment.p.setTitle("Please wait");
                    fragment.p.setMessage("We are updating the Password");
                    fragment.p.setCanceledOnTouchOutside(false);
                    fragment.p.setCancelable(false);
                    fragment.p.show();
                    EditMandalTask et=new EditMandalTask();
                    et.execute(Constants.URL+"changeSubAdminPassword?myId="+myId+"&myPass="+own_pass+"&pass="+pass+"&id="+id+"&userName="+name);
                }
            }
        });
    }

    private class EditMandalTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try{
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
                return "Can't reach Server ";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            fragment.p.dismiss();
            try {
                JSONObject json=new JSONObject(s);
                if(json.getBoolean("status")) {
                    Toast.makeText(c, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    fragment.refresh("");
                    dismiss();
                }else{
                    Toast.makeText(c, "Either your password is wrong or You are giving the old password/username of the user", Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e) {
                Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
