package studio.smartters.mowordadmin.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import studio.smartters.mowordadmin.MainActivity;
import studio.smartters.mowordadmin.R;

public class HomeFragment extends Fragment {
    private View v;
    private AppCompatActivity main;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        main = (AppCompatActivity)getActivity();
        main.getSupportActionBar().setTitle("Home");

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ImageView imageView = v.findViewById(R.id.home_image);
        ViewTreeObserver vto = imageView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = imageView.getWidth();
                imageView.setLayoutParams(new LinearLayout.LayoutParams(width, width/2));
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        NavigationView navigationView = (NavigationView) main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
    }
}
