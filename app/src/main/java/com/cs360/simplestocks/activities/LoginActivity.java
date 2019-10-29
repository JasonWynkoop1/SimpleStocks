package com.cs360.simplestocks.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.androidtutorialshub.loginregister.R;
import com.cs360.simplestocks.helpers.InputValidation;
import com.cs360.simplestocks.sql.SQLiteDatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener{

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout mTextInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText mTextInputEditTextEmail;
    private TextInputEditText mTextInputEditPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private SQLiteDatabaseHelper mSQLiteDatabaseHelper;

    private static final int STORAGE_PERMISSION_CODE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        initializeViews();
        initializeListeners();
        initializeObjects();
    }

    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(LoginActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[] { permission },
                    requestCode);
        } /*else {
            Toast.makeText(LoginActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }*/


    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(LoginActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(LoginActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /**
     * Implemented method to listen for the click on view
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.appCompatButtonLogin:
                verifyInput();
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }

        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);


    }

    /**
     * initializing view
     */
    private void initializeViews(){
        nestedScrollView = findViewById(R.id.NESTED_SCROLL_VIEW);
        mTextInputLayoutEmail = findViewById(R.id.TEXT_INPUT_LAYOUT_EMAIL);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        mTextInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        mTextInputEditPassword = findViewById(R.id.textInputEditTextPassword);
        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);
        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);
    }

    /**
     * initializing listeners
     */
    private void initializeListeners(){
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * initializing objects
     *
     * mSQLiteDatabaseHelper
     * input validator
     */
    private void initializeObjects(){
        mSQLiteDatabaseHelper = new SQLiteDatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    private void verifyInput(){
        if (!inputValidation.isInputEditTextFilled(mTextInputEditTextEmail, mTextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.checkForValidEmail(mTextInputEditTextEmail, mTextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(mTextInputEditPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        if (mSQLiteDatabaseHelper.checkIfUserExists(mTextInputEditTextEmail.getText().toString().trim()
                , mTextInputEditPassword.getText().toString().trim())) {


            Intent accountsIntent = new Intent(activity, UsersListActivity.class);
            accountsIntent.putExtra("EMAIL", mTextInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();
        }
    }

    /**
     * Clear all edit text fields
     *
     * email and password fields
     */
    private void emptyInputEditText(){
        mTextInputEditTextEmail.setText(null);
        mTextInputEditPassword.setText(null);
    }
}
