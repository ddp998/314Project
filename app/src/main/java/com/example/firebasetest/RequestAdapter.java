package com.example.firebasetest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {

    Context context;
    ArrayList<ActiveRequest> activeRequests;

    public RequestAdapter(Context c , ArrayList<ActiveRequest> p)
    {
        context = c;
        activeRequests = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.requestcardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ActiveRequest currentItem = activeRequests.get(position);

        holder.clientId.setText(currentItem.getClientId());
        holder.latitude.setText(currentItem.getLocationLat());
        holder.longitude.setText(currentItem.getLocationLong());


    }

    @Override
    public int getItemCount() {
        return activeRequests.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView clientId, latitude, longitude;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            clientId = itemView.findViewById(R.id.clientId);
            latitude = itemView.findViewById(R.id.latitude);
            longitude = itemView.findViewById(R.id.longitude);
        }
    }
}