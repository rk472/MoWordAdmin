package studio.smartters.mowordadmin.viewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import studio.smartters.mowordadmin.Dialog.CreateDialogPanchayat;
import studio.smartters.mowordadmin.Dialog.EditDialogBooth;
import studio.smartters.mowordadmin.Dialog.EditDialogWard;
import studio.smartters.mowordadmin.R;

public class WardHolder extends RecyclerView.ViewHolder {
    private TextView nameText;
    private ImageView edit;
    public WardHolder(View itemView) {
        super(itemView);
        nameText=itemView.findViewById(R.id.panchayat_name);
        edit=itemView.findViewById(R.id.edit_panchayat_btn);
    }
    public void setName(String name){
        nameText.setText(name);
    }
    public void edit(final String id, final Context c){
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialogWard dialog= new EditDialogWard(c,id);
                dialog.show();
            }
        });
    }
}
