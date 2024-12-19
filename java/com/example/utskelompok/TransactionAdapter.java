package com.example.utskelompok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final List<Transaction> transactionList;

    public TransactionAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        holder.descriptionTextView.setText(transaction.getDescription());
        holder.amountTextView.setText(formatCurrency(transaction.getAmount()));
        holder.timeTextView.setText(transaction.getTime());

        if (transaction.getType().equals("expense")) {
            holder.amountTextView.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.red));
        } else {
            holder.amountTextView.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.green));
        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    private String formatCurrency(int amount) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMANY);
        return "Rp " + numberFormat.format(amount);
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView, amountTextView, timeTextView;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}