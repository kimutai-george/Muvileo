package com.example.georgekimutai.muvileo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView movyName,plotSynopsis,userRating,releaseDate;
    ImageView posterImageview;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        posterImageview = (ImageView) findViewById(R.id.imageheader);
        movyName = (TextView) findViewById(R.id.title);
        plotSynopsis = (TextView) findViewById(R.id.plotsynopsis);
        userRating = (TextView) findViewById(R.id.userrating);
        releaseDate = (TextView) findViewById(R.id.releaseDate);

        getData();

        ActionBar actionBar=this.getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

   public void getData(){
       Intent getintent=getIntent();
       if (getintent.hasExtra("original_title")){
           String thumbnail=getIntent().getExtras().getString("poster_path");
           String moviename=getIntent().getExtras().getString("original_title");
           String synopsis=getIntent().getExtras().getString("overview");
           String rating=getIntent().getExtras().getString("vote_average");
           String release_date=getIntent().getExtras().getString("release_date");

           Picasso.get()
                   .load(thumbnail)
                   .placeholder(R.drawable.load)
                   .into(posterImageview);
           movyName.setText(moviename);
           plotSynopsis.setText(synopsis);
           userRating.setText(rating);
           releaseDate.setText(release_date);

       }else {
           Toast.makeText(this,"No Data",Toast.LENGTH_LONG).show();
       }

   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}

