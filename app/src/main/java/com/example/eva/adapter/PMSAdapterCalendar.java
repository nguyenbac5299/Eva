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

import java.util.ArrayList;
import java.util.List;

public class PMSAdapterCalendar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ItemPMS> mListPMS;
    private OnItemClickListener IListener;

    public PMSAdapterCalendar(List<ItemPMS> listPMS, OnItemClickListener listener) {
        IListener=listener;
        mListPMS=new ArrayList<>();
        for(ItemPMS itemPMS: listPMS){
            if(itemPMS.isStatus()){
                mListPMS.add(itemPMS);
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewPMS = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pms_calendar, parent, false);
        return new PMSAdapterCalendar.PMSViewHolder(viewPMS);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PMSAdapterCalendar.PMSViewHolder pmsViewHolder = (PMSAdapterCalendar.PMSViewHolder) holder;
        ItemPMS itemPMS = mListPMS.get(position);

        pmsViewHolder.mImageImage.setImageResource(itemPMS.getImage());
        pmsViewHolder.mTextView.setText(itemPMS.getText());
        pmsViewHolder.mImageImage.setVisibility(View.VISIBLE);
        pmsViewHolder.mTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mListPMS.size();
    }

    class PMSViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageImage;
        TextView mTextView;

        public PMSViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageImage = itemView.findViewById(R.id.image_image_calendar);
            mTextView = itemView.findViewById(R.id.text_image_calendar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IListener.onItemClick(1);
                }
            });
        }
    }
}
