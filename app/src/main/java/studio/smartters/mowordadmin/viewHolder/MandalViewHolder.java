package studio.smartters.mowordadmin.viewHolder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import studio.smartters.mowordadmin.Dialog.EditPasswordMandal;
import studio.smartters.mowordadmin.PersonDataActivity;
import studio.smartters.mowordadmin.R;
import studio.smartters.mowordadmin.ViewDataActivity;

public class MandalViewHolder extends RecyclerView.ViewHolder {
    private TextView nameText, areaText,totalText;
    private ImageButton callButton,editButton;
    private View v;
    public MandalViewHolder(View itemView) {
        super(itemView);
        v = itemView;
        nameText = v.findViewById(R.id.survey_man_name);
        areaText = v.findViewById(R.id.survey_man_area);
        callButton = v.findViewById(R.id.survey_man_call);
        totalText=v.findViewById(R.id.survey_man_no);
        editButton=v.findViewById(R.id.edit_mandal_btn);
    }
    public void setName(String name) {
        nameText.setText(name);
    }

    public void setArea(String name) {
        areaText.setText(name);
    }
    public void setNo(String total){
        totalText.setText(total);
    }

    public void setCall(final String number, final AppCompatActivity a) {
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(a, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                a.startActivity(i);
            }
        });
    }
    public void setClick(final String id, final AppCompatActivity a){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(a,ViewDataActivity.class);
                i.putExtra("id",id);
                a.startActivity(i);
            }
        });

    }
    public void setEdit(final String id,final AppCompatActivity a,final String userName){
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EditPasswordMandal(a,id,userName).show();
            }
        });
    }
}
