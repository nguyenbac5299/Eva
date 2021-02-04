package com.example.eva.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eva.R;
import com.example.eva.callback.OnItemClickListener;
import com.example.eva.callback.OnSwitchChangeListener;
import com.example.eva.model.Remind;

import java.util.List;

public class RemindAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Remind> mListRemind;
    private OnItemClickListener IOnItemClickListener;
    private OnSwitchChangeListener IOnSwitchListener;

    public RemindAdapter(List<Remind> listRemind, OnItemClickListener clickListener, OnSwitchChangeListener switchListener) {

        IOnItemClickListener =clickListener;
        IOnSwitchListener = switchListener;
        this.mListRemind = listRemind;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewRemind = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_remind, parent, false);
        return new RemindViewHolder(viewRemind);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RemindViewHolder viewHolder = (RemindViewHolder) holder;
        Remind remind = mListRemind.get(position);


        viewHolder.imageRemind.setImageResource(remind.getImage());
        viewHolder.textTitleRemind.setText(remind.getTitle());
        viewHolder.textContentRemind.setText(remind.getContent());
        viewHolder.switchRemind.setChecked(remind.isChooseSwitch());

        viewHolder.switchRemind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mListRemind.get(viewHolder.getAdapterPosition()).setChooseSwitch(isChecked);
                IOnSwitchListener.switchChange(viewHolder.getAdapterPosition(), isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListRemind.size();
    }

    class RemindViewHolder extends RecyclerView.ViewHolder {
        ImageView imageRemind;
        Switch switchRemind;
        TextView textTitleRemind;
        TextView textContentRemind;

        public RemindViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IOnItemClickListener.onItemClick(getAdapterPosition());
                }
            });
            imageRemind = itemView.findViewById(R.id.image_remind);
            switchRemind = itemView.findViewById(R.id.switch_remind);
            textTitleRemind = itemView.findViewById(R.id.text_title_remind);
            textContentRemind = itemView.findViewById(R.id.text_content_remind);

        }
    }
}
