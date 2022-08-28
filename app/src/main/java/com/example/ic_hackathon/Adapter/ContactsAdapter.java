package com.example.ic_hackathon.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ic_hackathon.Model.ContactsModels;
import com.example.ic_hackathon.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.vieholder>{

    ArrayList<ContactsModels> list;

    public ContactsAdapter(ArrayList<ContactsModels> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public vieholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row_view, parent, false);
        return new vieholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vieholder holder, int position) {

        ContactsModels models = list.get(position);

        Glide.with(holder.pic.getContext()).load(models.getPic()).into(holder.pic);
        holder.name.setText(models.getName());
        holder.number.setText(models.getNumber());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class vieholder extends RecyclerView.ViewHolder{

        TextView name, number;
        ImageView pic;

        public vieholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.contact_name);
            number = itemView.findViewById(R.id.contact_number);
            pic = itemView.findViewById(R.id.contact_image);
        }
    }
}
