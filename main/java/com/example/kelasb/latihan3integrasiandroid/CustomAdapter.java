package com.example.kelasb.latihan3integrasiandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private ArrayList<Student> data;
    private static CustomAdapterListener onClickListener;

    public CustomAdapter(CustomAdapterListener listener) {
        onClickListener = listener;
    }

    public void setAdapterData(ArrayList<Student> data) {
        this.data = data;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtVwNama.setText(this.data.get(position).getName());
        holder.txtVwAngkatan.setText(String.valueOf(this.data.get(position).getYear()));
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public ArrayList<Student> getData() {
        return this.data;
    }

    public void remove(int position) {
        this.data.remove(position);
        notifyItemRemoved(position);
    }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView txtVwNama;
            private TextView txtVwAngkatan;
            private ImageButton imgBtnDelete;
            private ImageButton imgBtnEdit;

            public ViewHolder(View view) {
                super(view);
                txtVwNama = view.findViewById(R.id.txtName);
                txtVwAngkatan = view.findViewById(R.id.txtYear);
                imgBtnDelete = view.findViewById(R.id.imgBtnDelete);
                imgBtnEdit = view.findViewById(R.id.imgBtnEdit);
                imgBtnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListener.editBtnViewOnClick(view, getAdapterPosition());
                    }
                });
                imgBtnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickListener.deleteBtnViewOnClick(view, getAdapterPosition());
                    }
                });
            }
        }
    }


