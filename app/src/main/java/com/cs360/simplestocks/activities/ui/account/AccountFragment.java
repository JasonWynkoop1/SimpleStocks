package com.cs360.simplestocks.activities.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.simplestocks.loginregister.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class AccountFragment extends Fragment {

    private View root;
    private AccountViewModel accountViewModel;
    private TextView nameTextView;
    private TextView emailTextView;
    private TextView passwordTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        root = inflater.inflate(R.layout.fragment_account, container, false);
        nameTextView = root.findViewById(R.id.user_name_text_view);
        accountViewModel.getText().observe(this, nameTextView::setText);
        return root;
    }
}