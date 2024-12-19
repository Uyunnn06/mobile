package com.example.utskelompok;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private final ArrayList<AddExpenseFragment.ExpenseItem> expenseList;

    // Format uang menjadi format Indonesia
    private String formatCurrency(int amount) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMANY);  // Bisa menggunakan Locale.INDONESIA
        return "Rp " + numberFormat.format(amount);
    }

    public ExpenseAdapter(ArrayList<AddExpenseFragment.ExpenseItem> expenseList) {
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout untuk item di RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        // Mengambil item di posisi tertentu
        AddExpenseFragment.ExpenseItem expense = expenseList.get(position);

        // Menampilkan data pada TextView yang sesuai
        holder.descriptionTextView.setText(expense.description);
        holder.amountTextView.setText(formatCurrency(expense.amount));
        holder.timeTextView.setText(expense.time);
    }

    @Override
    public int getItemCount() {
        return expenseList.size();  // Mengembalikan jumlah item dalam list
    }

    // ViewHolder untuk setiap item di RecyclerView
    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView, amountTextView, timeTextView;

        public ExpenseViewHolder(View itemView) {
            super(itemView);

            // Menghubungkan TextView dengan ID yang ada di layout XML
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}