package com.shiva1234r.ebank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecyclerAdapter extends FirebaseRecyclerAdapter<HistoryModel, HistoryRecyclerAdapter.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HistoryRecyclerAdapter(@NonNull FirebaseRecyclerOptions<HistoryModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull HistoryModel model) {
        holder.paidAmount.setText("Rs."+model.getAmount());
        holder.paidNames.setText(model.getSenderName()+" to "+model.getReceiverName());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_row, parent,false);
        return new ViewHolder(view);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
            TextView paidAmount, paidNames;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            paidAmount = itemView.findViewById(R.id.paidAmount);
            paidNames = itemView.findViewById(R.id.paidNames);
        }
    }
}
