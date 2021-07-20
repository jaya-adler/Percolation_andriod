package com.example.percolation.ui.slideshow;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.percolation.R;
import com.example.percolation.ui.PercolationStats;

public class SlideshowFragment extends Fragment {
    private EditText trail,grid_size;
    private TextView result_mean,result_std,result_conf;
    Context context;
    Button shw_thre;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        context=root.getContext();
        trail = root.findViewById(R.id.no_trails);
        grid_size=root.findViewById(R.id.grid_size);
        result_mean=root.findViewById(R.id.meanouput);
        result_std=root.findViewById(R.id.stdouput);
        result_conf=root.findViewById(R.id.confouput);
        shw_thre=root.findViewById(R.id.chk_threshold);
        shw_thre.setOnClickListener(v -> {
            String tr=trail.getText().toString();String gr=grid_size.getText().toString();
            if(tr.length()==0) {
                trail.setError("Enter valid number of trails !");
            }
            else if(gr.length()==0) {
                grid_size.setError("Enter valid  grid size !");
            }
            else {
                int trails=Integer.parseInt(tr);
                int size=Integer.parseInt(gr);
                PercolationStats stats = new PercolationStats(size,trails);
                Double res_mean=stats.mean();
                Double res_std=stats.stddev();
                String conf="["+String.valueOf(stats.confidenceLo())+","+String.valueOf(stats.confidenceHi())+"]";
                result_mean.setText(String.valueOf(res_mean));
                result_std.setText(String.valueOf(res_std));
                result_conf.setText(conf);

            }
        });
        return root;
    }
}