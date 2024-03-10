package com.example.hw2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hw2.Interfaces.CallBack_Player;
import com.example.hw2.Model.Player;
import com.example.hw2.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {


    private Context context;
    private ArrayList<Player> players;
    private CallBack_Player playerCallback;

    public PlayerAdapter(Context context, ArrayList<Player> players) {
        this.context = context;
        this.players = players;
    }

    public PlayerAdapter setPlayerCallback(CallBack_Player playerCallback) {
        this.playerCallback = playerCallback;
        return this;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_score_item, parent,false);
        PlayerViewHolder playerViewHolder = new PlayerViewHolder(view);
        return playerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = getItem(position);
        holder.player_LBL_rank.setText("#"+String.valueOf(position + 1));
        holder.player_LBL_name.setText(player.getName());
        holder.player_LBL_score.setText(String.valueOf(player.getScore()));



    }

    @Override
    public int getItemCount() {
        return players == null ? 0 : players.size();
    }

    private Player getItem(int position){
        return players.get(position);
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder{

        private MaterialTextView player_LBL_rank;
        private MaterialTextView player_LBL_name;
        private MaterialTextView player_LBL_score;
        private ShapeableImageView player_IMG_location;



        public PlayerViewHolder(@NonNull View itemView){
            super(itemView);
            player_LBL_rank = itemView.findViewById(R.id.player_LBL_rank);
            player_LBL_name = itemView.findViewById(R.id.player_LBL_name);
            player_LBL_score = itemView.findViewById(R.id.player_LBL_score);
            player_IMG_location = itemView.findViewById(R.id.player_IMG_location);


        }

    }
}
