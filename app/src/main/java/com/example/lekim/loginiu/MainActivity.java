package com.example.lekim.loginiu;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lekim.loginiu.model.ResObj;
import com.example.lekim.loginiu.remote.ApiUtils;
import com.example.lekim.loginiu.remote.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText editUsername;
    EditText editPassword;
    UserService userService;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = (EditText) findViewById(R.id.account);
        editPassword = (EditText) findViewById(R.id.password);
        userService = ApiUtils.getUserService();
        // Find the View that shows the Sign In
        signIn = (Button) findViewById(R.id.sign_in);

        // Set a onClick Listener on that View
        signIn.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the Sign In Button is clicked on.
            @Override
            public void onClick(View view) {

                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();

                // Validate form
                if(validateLogin(username, password)){
                    // Do login
                    doLogin(username, password);
                }
            }
        });
    }

    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(final String username,final String password){
        Call call = userService.login(username, password);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){
                    ResObj resObj = (ResObj) response.body();
                    if(resObj.getMessage().equals("true")) {
                        // Login start SignIn activity
                        Intent intent = new Intent(MainActivity.this, SignIn.class);
                        intent.putExtra("username", username);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "Log in is success!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error! Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clickFacebook (View view) {
        Uri webpage = Uri.parse("https://www.facebook.com/pogofdev.net/");
        Intent gotoFacebook = new Intent(Intent.ACTION_VIEW, webpage);
        if (gotoFacebook.resolveActivity(getPackageManager()) != null) {
            startActivity(gotoFacebook);
        }
    }

    public void clickPhoneNumber (View view) {
        String number = "0964592444";
        Intent toDial = new Intent(Intent.ACTION_DIAL);
        toDial.setData(Uri.parse("tel: " +number));
        if (toDial.resolveActivity(getPackageManager()) != null) {
            startActivity(toDial);
        }
    }
}