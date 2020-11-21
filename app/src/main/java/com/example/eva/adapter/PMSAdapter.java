package com.example.eva.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eva.R;
import com.example.eva.callback.OnItemClickListener;
import com.example.eva.model.ItemPMS;

import java.util.List;

public class PMSAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ItemPMS> mListPMS;
    private OnItemClickListener mListener;


    public PMSAdapter(List<ItemPMS> listPMS, OnItemClickListener listener) {
        this.mListPMS = listPMS;
        mListener=listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewPMS= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pms, parent, false);
        return new PMSViewHolder(viewPMS);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PMSViewHolder pmsViewHolder= (PMSViewHolder) holder;
        ItemPMS itemPMS= mListPMS.get(position);
        pmsViewHolder.imageImage.setImageResource(itemPMS.getImage());
        pmsViewHolder.textView.setText(itemPMS.getText());
        pmsViewHolder.imageSelected.setImageResource(R.drawable.ic_selected_green);
        if(itemPMS.isStatus()) {
            pmsViewHolder.imageSelected.setVisibility(View.VISIBLE);
        }
        else
            pmsViewHolder.imageSelected.setVisibility(View.GONE);

        pmsViewHolder.imageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemPMS.setStatus(!itemPMS.isStatus());
                mListener.onItemClick(pmsViewHolder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListPMS.size();
    }
    class PMSViewHolder extends RecyclerView.ViewHolder{

        ImageView imageImage;
        ImageView imageSelected;
        TextView textView;

        public PMSViewHolder(@NonNull View itemView) {
            super(itemView);
            imageImage=itemView.findViewById(R.id.image_image);
            imageSelected=itemView.findViewById(R.id.image_selected);
            textView=itemView.findViewById(R.id.text_image);
        }
    }
}
