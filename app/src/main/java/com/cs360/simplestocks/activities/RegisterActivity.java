package com.cs360.simplestocks.activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cs360.simplestocks.helpers.InputValidation;
import com.cs360.simplestocks.helpers.UpdateUser;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.simplestocks.loginregister.R;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "Register";
    private final AppCompatActivity activity = RegisterActivity.this;

    private TextInputLayout mTextInputLayout;
    private TextInputLayout mTextInputLayoutEmail;
    private TextInputLayout mTextInputLayoutPassword;
    private TextInputLayout mTextInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private UpdateUser updateUser;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mAuth = FirebaseAuth.getInstance();

        initializeViews();
        initializeListeners();
        initializeObjects();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.APP_COMPAT_BUTTON_REGISTER:
                verifyUserInput();
                createAccount(textInputEditTextEmail.getText().toString().trim(), textInputEditTextPassword.getText().toString().trim());
                break;

            case R.id.APP_COMPAT_TEXT_VIEW_LOGIN_LINK:
                finish();
                break;
        }
    }

    /**
     * This method is to initialize views
     */
    private void initializeViews() {

        mTextInputLayout = findViewById(R.id.TEXT_INPUT_LAYOUT_NAME);
        mTextInputLayoutEmail = findViewById(R.id.TEXT_INPUT_LAYOUT_EMAIL);
        mTextInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        mTextInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextName = findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister = findViewById(R.id.APP_COMPAT_BUTTON_REGISTER);

        appCompatTextViewLoginLink = findViewById(R.id.APP_COMPAT_TEXT_VIEW_LOGIN_LINK);

    }

    /**
     * This method is to initialize listeners
     */
    private void initializeListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initializeObjects() {
        inputValidation = new InputValidation(activity);
        updateUser = new UpdateUser();
    }

    /**
     * Create an account using the entered email and password.
     * Also saves the name as the display name in the firebase database
     *
     * @param email
     * @param password
     */
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        showProgressDialog();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        updateUser.updateDisplayName(user, textInputEditTextName.getText().toString());
                        sendEmailVerification();
                        updateUser.updateProfile(user);

                        Intent intentLogin = new Intent(activity, LoginActivity.class);
                        startActivity(intentLogin);

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "" + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        emptyInputEditText();
                    }

                    hideProgressDialog();
                });
    }


    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void verifyUserInput() {
        if (!inputValidation.checkForUserInput(textInputEditTextName, mTextInputLayout, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.checkForUserInput(textInputEditTextEmail, mTextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.checkForValidEmail(textInputEditTextEmail, mTextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.checkForUserInput(textInputEditTextPassword, mTextInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.doPasswordsMatch(textInputEditTextPassword, textInputEditTextConfirmPassword,
                mTextInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }

    }


    /**
     * Sending an email verification.  If the user doesn't verify
     * their email then they cannot login
     */
    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, task -> {
                    // [START_EXCLUDE]
                    if (task.isSuccessful()) {
                        Toast.makeText(activity,
                                "Verification email sent to " + user.getEmail(),
                                Toast.LENGTH_LONG).show();
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.getException());
                        Toast.makeText(RegisterActivity.this,
                                "Failed to send verification email.",
                                Toast.LENGTH_LONG).show();
                    }
                    // [END_EXCLUDE]
                });
        // [END send_email_verification]
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText(){
        //textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}
