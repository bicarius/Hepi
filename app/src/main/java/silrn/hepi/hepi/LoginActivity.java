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
import silrn.hepi.hepi.Sessions.SessionManager;

public class LoginActivity extends AppCompatActivity {

    SessionManager session;
    Button sign_in;
    TextView register;
    EditText editTextUsername,  editTextPassword;
    ProgressBar progressBar;

    public static final String URL_LOGIN = "https://sirlawren.com/hepi/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(0, 0);

        session = new SessionManager(this);

        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        register = findViewById(R.id.register);
        sign_in = findViewById(R.id.login_btn);

        sign_in.setOnClickListener(this::sign_in);
        register.setOnClickListener(this::registration);

        /*register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration(view);
            }
        });*/

    }

    public void sign_in(View view) {
        final String username, password;
        username = String.valueOf(editTextUsername.getText());
        password = String.valueOf(editTextPassword.getText());

        if (!username.equals("") && !password.equals("")){
            progressBar.setVisibility(View.VISIBLE);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                String[] field = new String[2];
                field[0] = "username";
                field[1] = "password";

                String[] data = new String[2];
                data[0] = username;
                data[1] = password;
                InsertData populateData = new InsertData(URL_LOGIN, "POST", field, data);
                if (populateData.startPut()) {
                    if (populateData.onComplete()) {
                        progressBar.setVisibility(View.GONE);
                        String result = populateData.getResult();
                        if (result.equals("Login Success")){
                            session.createLoginSession(username);

                            Intent intent = new Intent(this, MainActivity.class);
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
            Toast.makeText(this,"All fields are required!", Toast.LENGTH_SHORT).show();
        }
    }

    public void registration(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
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