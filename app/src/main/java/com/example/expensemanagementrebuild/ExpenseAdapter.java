package com.example.expensemanagementrebuild;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {
    private Context context;
    private List<ExpenseModel> expenseModelList;

    public ExpenseAdapter(Context context) {
        this.context = context;
        expenseModelList= new ArrayList<>();

    }
    public void add(ExpenseModel expenseModel){
        expenseModelList.add(expenseModel);
        notifyDataSetChanged();
    }
    public void clear(){
        expenseModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExpenseModel expenseModel = expenseModelList.get(position);
        holder.tvNote.setText(expenseModel.getNote());
        holder.tvCategory.setText(expenseModel.getCategory());
        holder.tvAmount.setText(String.valueOf(expenseModel.getAmount()));
    }

    @Override
    public int getItemCount() {
        return expenseModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNote, tvCategory, tvAmount, tvDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNote=itemView.findViewById(R.id.tvNote);
            tvCategory=itemView.findViewById(R.id.tvCategory);
            tvAmount=itemView.findViewById(R.id.tvAmount);
            tvDate=itemView.findViewById(R.id.tvDate);
        }
    }
}
