package com.example.georgekimutai.muvileo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.georgekimutai.muvileo.DetailActivity;
import com.example.georgekimutai.muvileo.Model.Muvy;
import com.example.georgekimutai.muvileo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class mAdapter extends RecyclerView.Adapter<mAdapter.MviewHolder> {
    private Context mContext;
    private List<Muvy> muvyList;


    public mAdapter(Context context,List<Muvy> muvyList){
        this.mContext=context;
        this.muvyList=muvyList;
    }
    @Override
    public mAdapter.MviewHolder onCreateViewHolder(ViewGroup viewGroup,int i){
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_card, viewGroup,false);

        return new MviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final mAdapter.MviewHolder viewHolder,int i){
        viewHolder.title.setText(muvyList.get(i).getOriginalTitle());
        String vote=Double.toString(muvyList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote);

        Picasso.get()
                .load(muvyList.get(i).getPosterPath())
                .placeholder(R.drawable.load)
                .into(viewHolder.posterimageview);


    }
    @Override
    public int getItemCount(){
        return muvyList.size();
    }

    public class MviewHolder extends RecyclerView.ViewHolder{
        public TextView title,userrating;
        public ImageView posterimageview;
        public MviewHolder(View view){
            super(view);
            title=(TextView)view.findViewById(R.id.title);
            userrating=(TextView)view.findViewById(R.id.userrating);
            posterimageview=(ImageView)view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        Muvy muvy=muvyList.get(position);
                        Intent intent=new Intent(mContext, DetailActivity.class);
                        intent.putExtra("original_title",muvyList.get(position).getOriginalTitle());
                        intent.putExtra("poster_path",muvyList.get(position).getPosterPath());
                        intent.putExtra("overview",muvyList.get(position).getOverview());
                        intent.putExtra("vote_average",Double.toString(muvyList.get(position).getVoteAverage()));
                        intent.putExtra("release_date",muvyList.get(position).getReleaseDate());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);

                    }
                }
            });
        }
    }
}
