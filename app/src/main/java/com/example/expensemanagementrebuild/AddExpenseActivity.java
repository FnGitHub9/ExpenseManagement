package com.example.expensemanagementrebuild;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensemanagementrebuild.databinding.ActivityAddExpenseBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {
    ActivityAddExpenseBinding binding;
    private String type;
    private ExpenseModel expenseModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddExpenseBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();

        type = getIntent().getStringExtra("type");
        expenseModel = (ExpenseModel) getIntent().getSerializableExtra("model");
        if (type==null){
            type=expenseModel.getType();
            binding.amount.setText(String.valueOf(expenseModel.getAmount()));
            binding.category.setText(expenseModel.getCategory());
            binding.note.setText(expenseModel.getNote());
        }



        if (type.equals("Income")){
            binding.RBincome.setChecked(true);
        }else {
            binding.RBexpense.setChecked(true);
        }

        binding.RBincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Income";
            }
        });
        binding.RBexpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "Expense";
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        if (expenseModel==null){
            menuInflater.inflate(R.menu.add_menu,menu);
        }else{
            menuInflater.inflate(R.menu.update_menu,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.saveExpense){
            if (type!=null){
                createExpense();
            }else{
                updateExpense();
            }
            return true;
        }
        if (id==R.id.deleteExpense){
            deleteExpense();
        }
        return false;
    }

    private void deleteExpense() {
        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseModel.getExpenseId())
                .delete();
        finish();
    }

    private void createExpense() {
        String expenseId= UUID.randomUUID().toString();
        String amount = binding.amount.getText().toString();
        String note = binding.note.getText().toString();
        String category = binding.category.getText().toString();
        boolean rBincomeChecked =binding.RBincome.isChecked();

        if (rBincomeChecked){
            type="Income";
        }else {
            type="Expense";
        }

        if (amount.trim().length()==0){
            binding.amount.setError("Invalid Value, Please enter a valid amount");
            return;
        }
        ExpenseModel expenseModel = new ExpenseModel(expenseId,note,category,type, Long.parseLong(amount), Calendar.getInstance().getTimeInMillis(), FirebaseAuth.getInstance().getUid());

        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseId)
                .set(expenseModel);
        finish();
    }
    private void updateExpense() {
        String expenseId= expenseModel.getExpenseId();
        String amount = binding.amount.getText().toString();
        String note = binding.note.getText().toString();
        String category = binding.category.getText().toString();
        boolean rBincomeChecked =binding.RBincome.isChecked();

        if (rBincomeChecked){
            type="Income";
        }else {
            type="Expense";
        }

        if (amount.trim().length()==0){
            binding.amount.setError("Invalid Value, Please enter a valid amount");
            return;
        }
        ExpenseModel model = new ExpenseModel(expenseId,note,category,type, Long.parseLong(amount),expenseModel.getTime(), FirebaseAuth.getInstance().getUid());

        FirebaseFirestore
                .getInstance()
                .collection("expenses")
                .document(expenseId)
                .set(model);
        finish();
    }
}