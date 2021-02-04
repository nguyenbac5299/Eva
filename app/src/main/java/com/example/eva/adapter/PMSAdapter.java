package com.example.eva.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eva.R;
import com.example.eva.activity.PMSActivity;
import com.example.eva.callback.OnItemClickListener;
import com.example.eva.model.ItemPMS;

import java.util.List;

public class PMSAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ItemPMS> mListPMS;
    private OnItemClickListener IListener;
    private int mID;


    public PMSAdapter(int ID, List<ItemPMS> listPMS, OnItemClickListener listener) {
        mListPMS = listPMS;
        IListener = listener;
        mID = ID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewPMS = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pms, parent, false);
        return new PMSViewHolder(viewPMS);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PMSViewHolder pmsViewHolder = (PMSViewHolder) holder;
        ItemPMS itemPMS = mListPMS.get(position);
        pmsViewHolder.mImageImage.setImageResource(itemPMS.getImage());
        pmsViewHolder.mTextView.setText(itemPMS.getText());
        pmsViewHolder.mImageSelected.setImageResource(R.drawable.ic_selected_green);
        if (itemPMS.isStatus()) {
            pmsViewHolder.mImageSelected.setVisibility(View.VISIBLE);
        } else
            pmsViewHolder.mImageSelected.setVisibility(View.GONE);

        pmsViewHolder.mImageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mID == PMSActivity.ID_MENSTRUATION||mID==PMSActivity.ID_SEX||mID==PMSActivity.ID_CHARGE) {
                    boolean status= itemPMS.isStatus();

                    for(ItemPMS itemPMSMenstruation : mListPMS){
                        itemPMSMenstruation.setStatus(false);
                    }
                    itemPMS.setStatus(!status);

                }else if(mID== PMSActivity.ID_SPORTY){
                    boolean status= itemPMS.isStatus();
                    if(position==0){
                        for(ItemPMS itemPMSSporty : mListPMS){
                            itemPMSSporty.setStatus(false);
                        }
                        itemPMS.setStatus(!status);
                    }else {
                        itemPMS.setStatus(!itemPMS.isStatus());
                        mListPMS.get(0).setStatus(false);
                    }
                }
                else {
                    itemPMS.setStatus(!itemPMS.isStatus());
                }
                IListener.onItemClick(1);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListPMS.size();
    }

    class PMSViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageImage;
        ImageView mImageSelected;
        TextView mTextView;

        public PMSViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageImage = itemView.findViewById(R.id.image_image);
            mImageSelected = itemView.findViewById(R.id.image_selected);
            mTextView = itemView.findViewById(R.id.text_image);
        }
    }
}
