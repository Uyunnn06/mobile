package com.example.utskelompok;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SummaryFragment extends Fragment {

    private TextView totalIncomeTextView, totalExpenseTextView, balanceTextView;
    private RecyclerView summaryRecyclerView;
    private SummaryAdapter summaryAdapter;
    private SQLiteOpenHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        // Initialize TextViews and RecyclerView
        totalIncomeTextView = view.findViewById(R.id.totalIncomeTextView);
        totalExpenseTextView = view.findViewById(R.id.totalExpenseTextView);
        balanceTextView = view.findViewById(R.id.balanceTextView);
        summaryRecyclerView = view.findViewById(R.id.summaryRecyclerView);

        // Initialize RecyclerView
        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize database helper
        dbHelper = new SQLiteOpenHelper(getContext(), "budget_smart.db", null, 2) {  // Increased database version to 2
            @Override
            public void onCreate(SQLiteDatabase db) {
                String CREATE_TABLE_INCOME = "CREATE TABLE IF NOT EXISTS income_table ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "description TEXT, "
                        + "amount INTEGER, "
                        + "time TEXT)";
                db.execSQL(CREATE_TABLE_INCOME);

                String CREATE_TABLE_EXPENSE = "CREATE TABLE IF NOT EXISTS expense_table ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "description TEXT, "
                        + "amount INTEGER, "
                        + "time TEXT)";
                db.execSQL(CREATE_TABLE_EXPENSE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                if (oldVersion < 2) {
                    // Drop existing tables and recreate them
                    db.execSQL("DROP TABLE IF EXISTS income_table");
                    db.execSQL("DROP TABLE IF EXISTS expense_table");
                    onCreate(db);  // Recreate tables
                }
            }
        };

        // Load the summary data
        loadSummaryData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload the summary data to reflect any changes
        loadSummaryData();
    }

    private void loadSummaryData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Query total income
        Cursor incomeCursor = db.rawQuery("SELECT SUM(amount) FROM income_table", null);
        if (incomeCursor != null && incomeCursor.moveToFirst()) {
            int totalIncome = incomeCursor.getInt(0);  // The sum of the income amounts
            totalIncomeTextView.setText(formatCurrency(totalIncome));
            incomeCursor.close();
        } else {
            Log.e("SummaryFragment", "Error fetching total income data.");
            totalIncomeTextView.setText(formatCurrency(0));
        }

        // Query total expense
        Cursor expenseCursor = db.rawQuery("SELECT SUM(amount) FROM expense_table", null);
        if (expenseCursor != null && expenseCursor.moveToFirst()) {
            int totalExpense = expenseCursor.getInt(0);  // The sum of the expense amounts
            totalExpenseTextView.setText(formatCurrency(totalExpense));
            expenseCursor.close();
        } else {
            Log.e("SummaryFragment", "Error fetching total expense data.");
            totalExpenseTextView.setText(formatCurrency(0));
        }

        // Calculate balance (total income - total expense)
        int totalIncome = getTotalAmount("income_table");
        int totalExpense = getTotalAmount("expense_table");
        int balance = totalIncome - totalExpense;
        balanceTextView.setText(formatCurrency(balance));

        // Load data into RecyclerView (summary data)
        loadSummaryRecyclerView();
    }

    private void loadSummaryRecyclerView() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<SummaryItem> summaryItems = new ArrayList<>();

        // Fetch income data for RecyclerView
        Cursor incomeCursor = db.rawQuery("SELECT description, amount FROM income_table", null);
        if (incomeCursor != null && incomeCursor.moveToFirst()) {
            int descriptionIndex = incomeCursor.getColumnIndex("description");
            int amountIndex = incomeCursor.getColumnIndex("amount");

            // Debugging: Log the column indices to ensure they're correct
            Log.d("SummaryFragment", "Description column index: " + descriptionIndex);
            Log.d("SummaryFragment", "Amount column index: " + amountIndex);

            if (descriptionIndex != -1 && amountIndex != -1) {
                do {
                    String description = incomeCursor.getString(descriptionIndex);
                    String amount = incomeCursor.getString(amountIndex);
                    summaryItems.add(new SummaryItem(description, amount));
                } while (incomeCursor.moveToNext());
            } else {
                Log.e("SummaryFragment", "Column 'description' or 'amount' not found.");
            }
            incomeCursor.close();
        } else {
            Log.e("SummaryFragment", "Error fetching income data for RecyclerView.");
        }

        // Fetch expense data for RecyclerView
        Cursor expenseCursor = db.rawQuery("SELECT description, amount FROM expense_table", null);
        if (expenseCursor != null && expenseCursor.moveToFirst()) {
            int descriptionIndex = expenseCursor.getColumnIndex("description");
            int amountIndex = expenseCursor.getColumnIndex("amount");

            // Debugging: Log the column indices to ensure they're correct
            Log.d("SummaryFragment", "Description column index: " + descriptionIndex);
            Log.d("SummaryFragment", "Amount column index: " + amountIndex);

            if (descriptionIndex != -1 && amountIndex != -1) {
                do {
                    String description = expenseCursor.getString(descriptionIndex);
                    String amount = expenseCursor.getString(amountIndex);
                    summaryItems.add(new SummaryItem(description, amount));
                } while (expenseCursor.moveToNext());
            } else {
                Log.e("SummaryFragment", "Column 'description' or 'amount' not found.");
            }
            expenseCursor.close();
        } else {
            Log.e("SummaryFragment", "Error fetching expense data for RecyclerView.");
        }

        // Set the adapter for RecyclerView
        summaryAdapter = new SummaryAdapter(summaryItems);
        summaryRecyclerView.setAdapter(summaryAdapter);
    }

    // Helper method to format currency in "Rp" format
    private String formatCurrency(int amount) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        return numberFormat.format(amount);
    }

    // Helper method to get total amount from a table (income or expense)
    private int getTotalAmount(String tableName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(amount) FROM " + tableName, null);
        int totalAmount = 0;
        if (cursor != null && cursor.moveToFirst()) {
            totalAmount = cursor.getInt(0);  // The sum of the amounts
            cursor.close();
        }
        return totalAmount;
    }
}