package com.shiva1234r.ebank;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerAdapter extends FirebaseRecyclerAdapter<Model, RecyclerAdapter.ViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RecyclerAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull final Model model) {
        holder.name.setText(model.getName());
        holder.phone.setText(model.getPhone());
        holder.balance.setText(("Rs."+ model.getBalance()+"/-"));
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                Intent intent = new Intent(activity.getApplicationContext(), userDetailsActivity.class);

                intent.putExtra("userName", model.getName());
                intent.putExtra("userEmail", model.getEmail());
                intent.putExtra("userPhone", model.getPhone());
                intent.putExtra("userBank", model.getBank());
                intent.putExtra("userBalance", ""+model.getBalance());
                intent.putExtra("userId", model.getUserId());
                activity.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);

        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, phone, balance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            balance = itemView.findViewById(R.id.balance);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {


        }
    }
}
