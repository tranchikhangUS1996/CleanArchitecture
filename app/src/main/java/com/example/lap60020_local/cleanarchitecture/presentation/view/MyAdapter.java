package com.example.lap60020_local.cleanarchitecture.presentation.view;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lap60020_local.cleanarchitecture.R;
import com.example.lap60020_local.cleanarchitecture.data.Movie;
import com.example.lap60020_local.cleanarchitecture.data.MyApiClient;
import com.example.lap60020_local.cleanarchitecture.presentation.presenter.IListView;
import com.example.lap60020_local.cleanarchitecture.presentation.presenter.ListPresenter;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IListView {


    List<Movie> Movies;
    private Context context;
    LinearLayoutManager linearLayoutManager;
    ListPresenter listPresenter;
    private int loadingPos = 0;

    public MyAdapter(Context context, RecyclerView recyclerView, ListPresenter presenter) {
        this.context = context;
        listPresenter = presenter;
        // attach view with presenter
        presenter.setView(this);
        Movies = new ArrayList<>();
        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.setAdapter(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int last = linearLayoutManager.findLastVisibleItemPosition();
                listPresenter.loadMoreData(last);
            }
        });
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0) {
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.loading_layout,parent,false);
            return new LoadingHolder(v);
        } else {
            CardView cardView = (CardView) LayoutInflater.from(context)
                    .inflate(R.layout.card_view, parent, false);
            return new MyHolder(cardView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderinput, int position) {
        Movie movie = Movies.get(position);
        if(holderinput instanceof MyHolder) {
            MyHolder holder = (MyHolder) holderinput;
            holder.movieTitle.setText(movie.getTitle());
            holder.release.setText(movie.getReleaseDate());
            String over = movie.getOverview();
            int length = over.length();
            try {
                if(length > 100) {
                    String description = over.substring(0, 100 - 1).concat("...");
                    holder.overView.setText(description);
                } else {
                    holder.overView.setText(over);
                }
            } catch(Exception e) {
                Log.d("Error",e.getMessage()
                        + movie.getTitle()
                        + " length = "
                        + String.valueOf(over.length()));
            }
            holder.rate.setText("Rate: " + String.valueOf(movie.getVoteAverage()));
            //
            String imagePath = MyApiClient.IMAGE_PATH + movie.getPosterPath();
                    GlideApp.with(context).load(Uri.parse(imagePath))
                    .placeholder(R.drawable.placeholder)
                    .into(holder.poster);
            // sets clicklistener for each items
            holder.card.setOnClickListener(new MyClickListener(movie.getId()));
        }
    }

    @Override
    public int getItemCount() {
        return Movies.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(Movies.get(position) == null) return 0;
        return 1;
    }

    @Override
    public void receiveData(List<Movie> users) {
        Movies = users;
    }

    @Override
    public void receiveMoreData(List<Movie> users) {
        Movies.addAll(users);
    }

    @Override
    public void showLoading() {
        Movies.add(null);
        loadingPos = Movies.size()-1;
        notifyItemInserted(Movies.size()-1);
    }

    @Override
    public void removeLoading() {
        Movies.remove(loadingPos);
        notifyItemRemoved(Movies.size());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(context,"Error!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show(int pos) {
        notifyDataSetChanged();
        linearLayoutManager.scrollToPosition(pos);
    }

    @Override
    public void updateList(int pos) {
        notifyDataSetChanged();
        linearLayoutManager.scrollToPosition(pos);
    }

    class LoadingHolder extends RecyclerView.ViewHolder{
        LoadingHolder(View itemView) {
            super(itemView);
            ProgressBar progressBar = itemView.findViewById(R.id.loading_progressbar);
            progressBar.setIndeterminate(true);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder{

        public ImageView poster;
        TextView release;
        TextView movieTitle;
        public TextView overView;
        public CardView card;
        public TextView rate;

        MyHolder(CardView itemView) {
            super(itemView);
            card =  itemView;
            poster = itemView.findViewById(R.id.poster);
            release = itemView.findViewById(R.id.date);
            movieTitle = itemView.findViewById(R.id.title);
            overView = itemView.findViewById(R.id.overView);
            rate = itemView.findViewById(R.id.rate);
        }
    }

}
