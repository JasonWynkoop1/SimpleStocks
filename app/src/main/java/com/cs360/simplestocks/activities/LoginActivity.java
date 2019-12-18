package com.cs360.simplestocks.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cs360.simplestocks.helpers.InputValidation;
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

/**
 * Login class that takes care of the login process
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private TextInputLayout emailTextLayout;
    private TextInputLayout passwordTextLayout;

    private TextInputEditText emailTextInputField;
    private TextInputEditText passwordTextInputField;
    private static final String TAG = "Login";

    private AppCompatButton loginButtonClickListener;

    private AppCompatTextView registerButtonClickListener;
    private AppCompatTextView contactUsClickListener;

    private InputValidation inputValidation;

    private static final int STORAGE_PERMISSION_CODE = 101;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    /**
     *things that happen when the view is created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        /*if (CheckInternetConnection.chekcingNetworkConnection(getApplicationContext())) {

            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
        }*/

        initializeViews();
        initializeListeners();
        initializeObjects();


    }

    /**
     * Check if the user has checked the stay logged in button or not.
     * If true, load homepage
     * Else, load login
     */
    public void onStart() {
        super.onStart();
        //check if user is signed it (non null) and update UI accordingly
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            Log.i(TAG, "onStart: Current user ----->" + currentUser.getDisplayName());
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
            } else {
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
                verifyInput();
                signIn(emailTextInputField.getText().toString().trim(), passwordTextInputField.getText().toString().trim());
                //mStatusTextView.setText("");
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
        emailTextLayout = findViewById(R.id.TEXT_INPUT_LAYOUT_EMAIL);
        passwordTextLayout = findViewById(R.id.textInputLayoutPassword);
        emailTextInputField = findViewById(R.id.textInputEditTextEmail);
        passwordTextInputField = findViewById(R.id.textInputEditTextPassword);
        loginButtonClickListener = findViewById(R.id.appCompatButtonLogin);
        registerButtonClickListener = findViewById(R.id.TEXT_VIEW_LINK_REGISTER);
        contactUsClickListener = findViewById(R.id.TEXT_VIEW_LINK_CONTACT_US);
    }

    /**
     * initializing listeners
     */
    private void initializeListeners(){
        loginButtonClickListener.setOnClickListener(this);
        registerButtonClickListener.setOnClickListener(this);
        contactUsClickListener.setOnClickListener(this);
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
     *Calls the inputValidation class to check the text fields for input
     */
    private void verifyInput(){
        if (!inputValidation.checkForUserInput(emailTextInputField, emailTextLayout, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.checkForValidEmail(emailTextInputField, emailTextLayout, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.checkForUserInput(passwordTextInputField, passwordTextLayout, getString(R.string.error_message_password))) {
            return;
        }


    }


    /**
     * Starts a sign-in process through Firebase using the email and password
     * entered into the text views
     *
     * @param email
     * @param password
     */
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        //showProgressDialog();
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        if (!user.isEmailVerified()) {
                            Toast.makeText(LoginActivity.this, "You are not verified yet!  Please check your email for the verification link.",
                                    Toast.LENGTH_LONG).show();
                            emptyInputEditText();
                        } else {
                            goToHomepageActivity();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        // mStatusTextView.setText(R.string.auth_failed);
                        emptyInputEditText();
                    }

                    hideProgressDialog();
                });
    }


    /**
     * Creates a homepage intent and loads it after
     * checking permissions
     */
    public void goToHomepageActivity() {
        Intent homeIntent = new Intent(activity, HomepageActivity.class);
        homeIntent.putExtra("EMAIL", Objects.requireNonNull(emailTextInputField.getText()).toString().trim());
        homeIntent.putExtra("uid", currentUser.getUid());
        emptyInputEditText();
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        startActivity(homeIntent);

    }

    /**
     * Clear all edit text fields
     *
     * email and password fields
     */
    private void emptyInputEditText() {
        emailTextInputField.setText(null);
        passwordTextInputField.setText(null);
    }
}
