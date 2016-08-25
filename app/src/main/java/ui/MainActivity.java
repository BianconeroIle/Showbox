package ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.showbox.showbox.R;

import util.AppUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView logoIcon;
    TextView usernameTextView;
    EditText username;
    TextView passwordTextView;
    EditText password;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoIcon = (ImageView) findViewById(R.id.logoIcon);
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);
        username = (EditText) findViewById(R.id.usernameInputText);
        passwordTextView = (TextView) findViewById(R.id.passwordTextView);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
    }



    public void validateLogin() {
        username.setError(null);
        password.setError(null);

        String u = username.getText().toString();
        String p = password.getText().toString();

        if (u.equals("")) {
            username.setText("This field is required!");
        }
        if (p.equals("")) {
            password.setText("This field is required!");
        }

        if (AppUtils.checkUserExist(u,p)) {
            openLibraryMoviesActivity();
        }
        else {
            Toast.makeText(this, "The username or password you have entered is invalid.", Toast.LENGTH_LONG).show();
        }
    }

    private void openLibraryMoviesActivity() {
        Intent intent = new Intent(MainActivity.this, LibraryActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginBtn:
                validateLogin();
                break;
        }

    }


}


