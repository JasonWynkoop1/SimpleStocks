package com.cs360.simplestocks.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.androidtutorialshub.loginregister.R;
import com.cs360.simplestocks.helpers.InputValidation;
import com.cs360.simplestocks.model.User;
import com.cs360.simplestocks.sql.SQLiteDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

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
    private SQLiteDatabaseHelper mSQLiteDatabaseHelper;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initializeViews();
        intializeListeners();
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
    private void intializeListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initializeObjects() {
        inputValidation = new InputValidation(activity);
        mSQLiteDatabaseHelper = new SQLiteDatabaseHelper(activity);
        user = new User();

    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void writeDataToDatabase() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, mTextInputLayout, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, mTextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, mTextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, mTextInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                mTextInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }

        if (!mSQLiteDatabaseHelper.checkIfUserExists(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            mSQLiteDatabaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(mNestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


            //Send the user back to login with new account
            Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(loginActivity);

        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(mNestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }
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
