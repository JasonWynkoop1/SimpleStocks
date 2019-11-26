package com.cs360.simplestocks.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs360.simplestocks.model.User;
import com.simplestocks.loginregister.R;

import java.util.List;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;

    public UsersRecyclerAdapter(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewName.setText(listUsers.get(position).getName());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.textViewPassword.setText(listUsers.get(position).getPassword());
    }


    public int getItemCount() {
        Log.i(UsersRecyclerAdapter.class.getSimpleName(),"" + listUsers.size());
        return listUsers.size();
    }


    /**

     /**
     * ViewHolder class
     */
    class UserViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewName;
        AppCompatTextView textViewEmail;
        AppCompatTextView textViewPassword;

        UserViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.textViewName);
            textViewEmail = view.findViewById(R.id.textViewEmail);
            textViewPassword = view.findViewById(R.id.textViewPassword);
        }
    }


}
