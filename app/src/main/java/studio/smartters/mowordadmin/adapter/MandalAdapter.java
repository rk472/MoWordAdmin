package studio.smartters.mowordadmin.adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import studio.smartters.mowordadmin.R;
import studio.smartters.mowordadmin.others.SurveyMan;
import studio.smartters.mowordadmin.viewHolder.BoothHolder;
import studio.smartters.mowordadmin.viewHolder.MandalViewHolder;

public class MandalAdapter extends  RecyclerView.Adapter<BoothHolder>{
    private final AppCompatActivity a;
    private final List<String> name,id,userName;

    public MandalAdapter(AppCompatActivity a, List<String> name,List<String> id,List<String> userName){
        this.a=a;
        this.name=name;
        this.id=id;
        this.userName=userName;
    }
    @NonNull
    @Override
    public BoothHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(a).inflate(R.layout.mandal_row,parent,false);
        return new BoothHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BoothHolder holder, int position) {
        holder.setName(name.get(position));
        holder.setClickNew(id.get(position),a);
        holder.setInVisible();
        holder.edit(id.get(position),userName.get(position),a);
    }

    @Override
    public int getItemCount() {
        return id.size();
    }
}
