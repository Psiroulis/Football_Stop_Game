package gr.redpepper.footballrunningtime;

import android.app.Activity;
import android.os.Bundle;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends Activity {

    LoginButton FbLoginButton;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CallbackManager manager = CallbackManager.Factory.create();

        FbLoginButton =  findViewById(R.id.login_button);
        FbLoginButton.setReadPermissions(Arrays.asList(EMAIL));

        // Callback registration
        FbLoginButton.registerCallback(manager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });





    }
}
