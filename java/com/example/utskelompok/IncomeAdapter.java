package com.example.utskelompok;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.Locale;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder> {

    private Cursor cursor;

    // Format uang menjadi format Indonesia
    private String formatCurrency(int amount) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMANY);  // Bisa menggunakan Locale.INDONESIA
        return "Rp " + numberFormat.format(amount);
    }

    public IncomeAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public IncomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout untuk item di RecyclerView
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_income, parent, false);
        return new IncomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeViewHolder holder, int position) {
        // Mengambil data dari cursor pada posisi tertentu
        if (cursor != null && cursor.moveToPosition(position)) {
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            int amount = cursor.getInt(cursor.getColumnIndexOrThrow("amount"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));

            // Menampilkan data pada TextView yang sesuai
            holder.descriptionTextView.setText(description);
            holder.amountTextView.setText(formatCurrency(amount));
            holder.timeTextView.setText(time);
        }
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;  // Mengembalikan jumlah item dalam cursor
    }

    // ViewHolder untuk setiap item di RecyclerView
    public static class IncomeViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionTextView, amountTextView, timeTextView;

        public IncomeViewHolder(View itemView) {
            super(itemView);

            // Menghubungkan TextView dengan ID yang ada di layout XML
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}