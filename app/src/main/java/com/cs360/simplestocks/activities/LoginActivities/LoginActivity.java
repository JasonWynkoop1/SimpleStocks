package com.cs360.simplestocks.activities.LoginActivities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cs360.simplestocks.activities.BaseActivity;
import com.cs360.simplestocks.activities.ContactUsActivity;
import com.cs360.simplestocks.activities.HomepageActivity;
import com.cs360.simplestocks.activities.RegisterActivity;
import com.cs360.simplestocks.activities.UsersListActivity;
import com.cs360.simplestocks.helpers.InputValidation;
import com.cs360.simplestocks.utilities.CheckInternetConnection;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.simplestocks.loginregister.R;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private TextInputLayout mTextInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText mTextInputEditTextEmail;
    private TextInputEditText mTextInputEditPassword;
    private static final String TAG = "Login";

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;
    private AppCompatTextView textViewLinkContactUs;

    private InputValidation inputValidation;

    private static final int STORAGE_PERMISSION_CODE = 101;
    private TextView mStatusTextView;
    private FirebaseAuth mAuth;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        if (CheckInternetConnection.chekcingNetworkConnection(getApplicationContext())) {

            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
        }

        initializeViews();
        initializeListeners();
        initializeObjects();


    }

    public void onStart() {
        super.onStart();
        System.out.println("START START START");

        //check if user is signed it (non null) and update UI accordingly
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Log.i(TAG, "onStart: " + currentUser);
            //mAuth.addAuthStateListener(authStateListener);
        } else {
            Log.i(TAG, "onStart: user is not currently logged in");
        }

    }

    /**
     * Function to check and request permission.
     *
     * @param permission - permission
     * @param requestCode - code being requested
     */
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(LoginActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[] { permission },
                    requestCode);
        }

    }


    /**
     * This function is called when the user accepts or decline the permission.
     * Request Code is used to check which permission called this function.
     * This request code is provided when the user is prompt for permission.
     * @param requestCode - what permission
     * @param permissions - permission to check against
     * @param grantResults - grant results code
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
     * @param v - current view
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.appCompatButtonLogin:
                signIn(mTextInputEditTextEmail.getText().toString().trim(), mTextInputEditPassword.getText().toString().trim());
                //verifyInput();
                break;
            case R.id.TEXT_VIEW_LINK_REGISTER:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.TEXT_VIEW_LINK_CONTACT_US:
                Intent intentContactUs = new Intent(getApplicationContext(), ContactUsActivity.class);
                startActivity(intentContactUs);
        }

    }

    /**
     * initializing view
     */
    private void initializeViews(){
        mTextInputLayoutEmail = findViewById(R.id.TEXT_INPUT_LAYOUT_EMAIL);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        mTextInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        mTextInputEditPassword = findViewById(R.id.textInputEditTextPassword);
        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);
        textViewLinkRegister = findViewById(R.id.TEXT_VIEW_LINK_REGISTER);
        textViewLinkContactUs = findViewById(R.id.TEXT_VIEW_LINK_CONTACT_US);
        mStatusTextView = findViewById(R.id.mStatusTextView);
    }

    /**
     * initializing listeners
     */
    private void initializeListeners(){
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
        textViewLinkContactUs.setOnClickListener(this);
    }

    /**
     * initializing objects
     *
     * userDatabaseHelper
     * input validator
     */
    private void initializeObjects(){
        inputValidation = new InputValidation(activity);
    }

    /**
     *
     */
    private void verifyInput(){
        if (!inputValidation.checkForUserInput(mTextInputEditTextEmail, mTextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.checkForValidEmail(mTextInputEditTextEmail, mTextInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.checkForUserInput(mTextInputEditPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

        signIn(mTextInputEditTextEmail.getText().toString().trim(), mTextInputEditPassword.getText().toString().trim());

    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        goToHomepageActivity();
                        //updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }

                    // [START_EXCLUDE]
                    if (!task.isSuccessful()) {
                        mStatusTextView.setText(R.string.auth_failed);
                    }
                    hideProgressDialog();
                    // [END_EXCLUDE]
                });
        // [END sign_in_with_email]
    }

    /**
     * Method to load admin dashboard
     */
    public void goToAdminDashboard() {
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        Intent accountsIntent = new Intent(activity, UsersListActivity.class);
        accountsIntent.putExtra("EMAIL", Objects.requireNonNull(mTextInputEditTextEmail.getText()).toString().trim());
        emptyInputEditText();
        startActivity(accountsIntent);
    }


    public void goToHomepageActivity() {
        Intent accountsIntent = new Intent(activity, HomepageActivity.class);
        accountsIntent.putExtra("EMAIL", Objects.requireNonNull(mTextInputEditTextEmail.getText()).toString().trim());
        emptyInputEditText();
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        startActivity(accountsIntent);
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
