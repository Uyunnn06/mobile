package com.example.utskelompok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {

    private List<SummaryItem> summaryList;

    public SummaryAdapter(List<SummaryItem> summaryList) {
        this.summaryList = summaryList;
    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_summary, parent, false);
        return new SummaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {
        SummaryItem summaryItem = summaryList.get(position);
        holder.descriptionTextView.setText(summaryItem.getDescription());
        holder.amountTextView.setText(summaryItem.getAmount());
    }

    @Override
    public int getItemCount() {
        return summaryList.size();
    }

    public static class SummaryViewHolder extends RecyclerView.ViewHolder {

        TextView descriptionTextView, amountTextView;

        public SummaryViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
        }
    }
}