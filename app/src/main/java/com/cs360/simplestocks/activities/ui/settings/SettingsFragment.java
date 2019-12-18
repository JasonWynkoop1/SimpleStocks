package com.cs360.simplestocks.activities.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cs360.simplestocks.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.simplestocks.loginregister.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static com.simplestocks.loginregister.R.id.sign_out_button;


/**
 * Holds the view for the settings fragment
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Button signOutButton = view.findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(this);
        settingsViewModel.getText().observe(this, s -> {

        });
        return view;
    }

    /**
     * When sign out is clicked call this method
     *
     * @param v
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case sign_out_button:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
        }
    }

}