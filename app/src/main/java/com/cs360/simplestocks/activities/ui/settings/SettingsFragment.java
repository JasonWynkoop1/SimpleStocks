package com.cs360.simplestocks.activities.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.simplestocks.loginregister.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        final TextView signOutButton = root.findViewById(R.id.sign_out_button);
        settingsViewModel.getText().observe(this, s -> {

        });
        return root;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                FirebaseAuth.getInstance().signOut();
        }
    }

}