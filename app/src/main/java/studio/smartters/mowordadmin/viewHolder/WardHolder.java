package studio.smartters.mowordadmin.viewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import studio.smartters.mowordadmin.AllBoothActivity;
import studio.smartters.mowordadmin.Dialog.CreateDialogPanchayat;
import studio.smartters.mowordadmin.Dialog.EditDialogBooth;
import studio.smartters.mowordadmin.Dialog.EditDialogWard;
import studio.smartters.mowordadmin.R;

public class WardHolder extends RecyclerView.ViewHolder {
    private TextView nameText;
    private View v;
    public WardHolder(View itemView) {
        super(itemView);
        nameText=itemView.findViewById(R.id.panchayat_name);
        v=itemView;
    }
    public void setName(String name){
        nameText.setText(name);
    }
    public void setClick(final String id, final Context a){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(a, AllBoothActivity.class);
                i.putExtra("id",id);
                a.startActivity(i);
            }
        });

    }
}
