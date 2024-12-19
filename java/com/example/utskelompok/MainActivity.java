package com.example.utskelompok;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set default fragment (AddExpenseFragment)
        if (savedInstanceState == null) {
            loadFragment(new AddExpenseFragment());
        }

        // Set listener for bottom navigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_add_expense) {
                    selectedFragment = new AddExpenseFragment();
                } else if (item.getItemId() == R.id.nav_add_income) {
                    selectedFragment = new AddIncomeFragment();
                } else if (item.getItemId() == R.id.nav_summary) {
                    selectedFragment = new SummaryFragment(); // Pastikan fragment ini dipilih saat Summary ditekan
                }

                return loadFragment(selectedFragment); // Memastikan fragment yang benar dimuat
            }
        });
    }

    // Method to load the selected fragment
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer, fragment);
            transaction.commit();
            return true;
        }
        return false;
    }
}