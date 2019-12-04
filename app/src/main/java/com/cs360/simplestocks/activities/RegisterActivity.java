package com.cs360.simplestocks.activities;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cs360.simplestocks.helpers.InputValidation;
import com.cs360.simplestocks.model.User;
import com.cs360.simplestocks.sql.UserDatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.simplestocks.loginregister.R;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "Register";
    private final AppCompatActivity activity = RegisterActivity.this;
    private NestedScrollView mNestedScrollView;

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
    private UserDatabaseHelper userDatabaseHelper;
    private User user;

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
                writeDataToDatabase();
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
        mNestedScrollView = findViewById(R.id.NESTED_SCROLL_VIEW);

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
        userDatabaseHelper = new UserDatabaseHelper(activity);
        //user = new User();

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

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateProfile(user);
                        //updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }

                    // [START_EXCLUDE]
                    hideProgressDialog();
                    // [END_EXCLUDE]
                });
        // [END create_user_with_email]
    }


    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void writeDataToDatabase() {
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

        createAccount(textInputEditTextEmail.getText().toString().trim(), textInputEditTextPassword.getText().toString().trim());

        /*if (!userDatabaseHelper.checkIfUserExists(Objects.requireNonNull(textInputEditTextEmail.getText()).toString().trim())) {

            user.setName(Objects.requireNonNull(textInputEditTextName.getText()).toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(Objects.requireNonNull(textInputEditTextPassword.getText()).toString().trim());

            userDatabaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(mNestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


            //Send the user back to login with new account
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);

        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(mNestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }*/
    }

    public void updateProfile(FirebaseUser user) {
        // [START update_profile]
        user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(textInputEditTextName.getText().toString())
                .build();

        FirebaseUser finalUser = user;
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User profile updated.");
                        Log.i(TAG, "DISPLAY NAME: " + finalUser.getDisplayName());
                    }
                });
        // [END update_profile]
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText(){
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}
