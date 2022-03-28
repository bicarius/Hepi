package silrn.hepi.hepi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import silrn.hepi.hepi.Sessions.SessionManager;

public class MainActivity extends AppCompatActivity {

    SessionManager session;
    TextView logout, hepiUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0, 0);

        session = new SessionManager(this);
        hepiUser = findViewById(R.id.user);
        /*
         * Call this function to check user login
         * Redirects the user to login fragment
         * If not logged in
         * */
        session.checkLogin();
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        String username = user.get(SessionManager.KEY_USERNAME);
        // displaying user data
        hepiUser.setText(Html.fromHtml("<u>"+ username + "<u>"));

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this::logout);
    }

    public void logout(View view) {
        session.logoutUser();
    }
    @Override
    public void onBackPressed(){
        /*moveTaskToBack(true);*/
    }
}