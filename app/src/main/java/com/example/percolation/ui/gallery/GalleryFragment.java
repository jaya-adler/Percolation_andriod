package com.example.percolation.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.percolation.Percolation;
import com.example.percolation.R;

import edu.princeton.cs.algs4.StdRandom;

public class GalleryFragment extends Fragment {

    //private GalleryViewModel galleryViewModel;
    private int grid_size;
    private ImageView[][] Iview ;
    public Button start ;
    private TextView size ,prob;
    private Context context;
    Drawable[] img;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        context=root.getContext();
        img = new Drawable[5];
        img[0] = context.getResources().getDrawable(R.drawable.red);
        img[1] = context.getResources().getDrawable(R.drawable.white);
        img[2] = context.getResources().getDrawable(R.drawable.blue);
        start=  root.findViewById(R.id.chk_threshold);
        size= root.findViewById(R.id.grid_sze);
        prob =  root.findViewById(R.id.prob);
        start.setOnClickListener(v -> {
            if(size.getText().length()==0) {
                size.setError("Enter valid number ");
            }
            else {
                grid_size = Integer.parseInt(size.getText().toString());
                Iview = new ImageView[grid_size][grid_size];
                load_grid(grid_size,root);

            }

        });
        return root;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void load_grid(int grid_size, View r) {

        //Toast.makeText(this.context,"pressed", Toast.LENGTH_SHORT).show();
        LinearLayout home_grid = (LinearLayout) r.findViewById(R.id.grid_views);

        home_grid.removeAllViews();
        int gridwidth = Math.round(get_width()/grid_size);
        LinearLayout.LayoutParams gridp = new LinearLayout.LayoutParams(gridwidth,gridwidth);
        LinearLayout.LayoutParams rowp = new LinearLayout.LayoutParams(gridwidth*grid_size,gridwidth);
        //img[0]=context.getResources().getDrawable(R.mipmap.covir);
        //img[1]=context.getResources().getDrawable(R.mipmap.people_y);
        for (int i=0;i<grid_size;i++){
            LinearLayout linear_row = new LinearLayout(r.getContext());
            for (int j=0;j<grid_size;j++){
                Iview[i][j] = new ImageView(r.getContext());
                Iview[i][j].setBackground(img[2]);
                //Iview[i][j].setImageDrawable(img[1]);
                linear_row.addView(Iview[i][j],gridp);
            }
            home_grid.addView(linear_row,rowp);
        }

        percolate(grid_size);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void percolate(int grid_size) {
        Percolation newp = new Percolation(grid_size);
        while (!newp.percolates()) {
            int row = StdRandom.uniform(0,grid_size)+1;
            int col = StdRandom.uniform(0,grid_size)+1;
            newp.open(row, col);
            if(newp.isOpen(row,col)) {
                //Iview[row-1][col-1].setImageDrawable(img[0]);
                Iview[row-1][col-1].setBackground(img[1]);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(newp.isFull(row,col))  {
                            Iview[row-1][col-1].setBackground(img[0]);
                            //Iview[row-1][col-1].setImageDrawable(img[0]);
                        }
                    }
                },1);
            }
        }
        double p = (double) newp.numberOfOpenSites()/(grid_size*grid_size);
        prob.setText(String.valueOf(p));
        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private float get_width() {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        return dm.widthPixels;
    }

}