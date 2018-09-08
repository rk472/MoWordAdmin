package studio.smartters.mowordadmin.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import studio.smartters.mowordadmin.AllWardActivity;
import studio.smartters.mowordadmin.Dialog.EditDialogBooth;
import studio.smartters.mowordadmin.MainActivity;
import studio.smartters.mowordadmin.R;
import studio.smartters.mowordadmin.SearchByBoothActivity;
import studio.smartters.mowordadmin.SearchNameActivity;

public class BoothHolder extends RecyclerView.ViewHolder {
    private TextView nameText;
    private ImageView edit;
    private View v;
    public BoothHolder(View itemView) {
        super(itemView);
        nameText=itemView.findViewById(R.id.booth_name);
        edit=itemView.findViewById(R.id.edit_booth_btn);
        v=itemView;
    }
    public void setInVisible(){
        edit.setVisibility(View.GONE);
    }
    public void setName(String name){
        nameText.setText(name);
    }
    public void edit(final String id, final Context c){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialogBooth dialog= new EditDialogBooth(c,id);
                dialog.show();
            }
        });
    }
    public void setClickNew(final String id, final Context c){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(c, AllWardActivity.class);
                i.putExtra("id",id);
                c.startActivity(i);
            }
        });
    }
    public void setClick(final String id, final Context c){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(c, SearchNameActivity.class);
                i.putExtra("id",id);
                c.startActivity(i);
                
            }
        });

    }
}
