package silrn.hepi.hepi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import silrn.hepi.hepi.DataHandlers.InsertData;

public class RegistrationActivity extends AppCompatActivity {

    TextView login;
    EditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    Button register_btn;
    ProgressBar progressBar;
    public static final String URL_LOGIN = "https://sirlawren.com/hepi/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        overridePendingTransition(0, 0);

        editTextUsername = findViewById(R.id.username);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.re_password);
        progressBar = findViewById(R.id.progressBar);

        login = findViewById(R.id.login);
        login.setOnClickListener(this::login);
        /*login.setOnClickListener(view -> login(view));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
            }
        });*/

        register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this::sign_up);
    }

    public void sign_up(View view) {
        String username, email, password, confirmPass;
        username = String.valueOf(editTextUsername.getText());
        email = String.valueOf(editTextEmail.getText());
        password = String.valueOf(editTextPassword.getText());
        confirmPass = String.valueOf(editTextConfirmPassword.getText());

        if (!username.equals("") && !email.equals("") && !password.equals("") && !confirmPass.equals("")){
            if (password.equals(confirmPass)){
                progressBar.setVisibility(View.VISIBLE);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[3];
                    field[0] = "username";
                    field[1] = "email";
                    field[2] = "password";

                    String[] data = new String[3];
                    data[0] = username;
                    data[1] = email;
                    data[2] = password;
                    InsertData populateData = new InsertData(URL_LOGIN, "POST", field, data);
                    if (populateData.startPut()) {
                        if (populateData.onComplete()) {
                            progressBar.setVisibility(View.GONE);
                            String result = populateData.getResult();
                            if (result.equals("Sign Up Success")){
                                Intent intent = new Intent(this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
            else {
                Toast.makeText(this,"Password and Confirm Password mismatch", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this,"All fields are required!", Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        /*moveTaskToBack(true);*/
    }
}