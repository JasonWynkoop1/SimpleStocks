package com.cs360.simplestocks.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.cs360.simplestocks.adapters.UsersRecyclerAdapter;
import com.cs360.simplestocks.model.User;
import com.cs360.simplestocks.sql.UserDatabaseHelper;
import com.simplestocks.loginregister.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UsersListActivity extends AppCompatActivity {

    private AppCompatActivity activity = UsersListActivity.this;
    private AppCompatTextView textViewName;
    private RecyclerView recyclerViewUsers;
    private static List<User> listUsers;
    private static UsersRecyclerAdapter usersRecyclerAdapter;
    private static UserDatabaseHelper userDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        initViews();
        initObjects();

    }



    /**
     * This method is to initialize views
     */
    private void initViews() {
        textViewName = findViewById(R.id.textViewName);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listUsers = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(listUsers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        recyclerViewUsers.setAdapter(usersRecyclerAdapter);
        userDatabaseHelper = new UserDatabaseHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName.setText(emailFromIntent);

        //TODO: Fetch users name from SQLite database
        getDataFromSQLite();
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    @SuppressLint("StaticFieldLeak")
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            private ProgressDialog pd;

            // onPreExecute called before the doInBackgroud start for display
            // progress dialog.
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pd = ProgressDialog.show(UsersListActivity.this, "LOADING", "Loading users list...", true,
                        false); // Create and show Progress dialog
            }
            @Override
            protected Void doInBackground(Void... params) {
                listUsers.clear();
                listUsers.addAll(userDatabaseHelper.getAllUser());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                pd.dismiss();
                usersRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}