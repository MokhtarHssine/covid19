package com.projet.dsi32g5_covid19;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LoginActivity extends Activity {
    EditText pass;
    static EditText userid;
    Button login;
    TextView not_reg;
    DataBaseHelper db;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    public static EditText getUserid() {
        return userid;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userid = (EditText) findViewById(R.id.eduser);
        pass = (EditText) findViewById(R.id.edpass);
        login = (Button) findViewById(R.id.login);
        not_reg = findViewById(R.id.not_reg);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Authentification with SQLite
                GlobalDefinitions dbDefintions = new GlobalDefinitions();
                db = new DataBaseHelper(LoginActivity.this, dbDefintions.DATABASE_NAME, null, dbDefintions.DATABASE_VERSION);
                String usrid = LoginActivity.userid.getText().toString();
                String password = pass.getText().toString();
                String StoredPassword = db.getRegister(usrid).password;
                if (password.equals(StoredPassword)) {
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Accueil.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Username/Password incorrect", Toast.LENGTH_LONG).show();
                    LoginActivity.userid.setText("");
                    pass.setText("");
                }
            }
        });

        not_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });


        // Authentification with google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        final GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }
            }
        });
    }

    //Début des méthodes de Google Autetification
    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if (account != null) {

                // Persisting user to db
                GlobalDefinitions dbDefintions = new GlobalDefinitions();
                db = new DataBaseHelper(LoginActivity.this, dbDefintions.DATABASE_NAME, null, dbDefintions.DATABASE_VERSION);
                if (db.getRegister(account.getEmail()) == null) {
                    RegisterData reg = new RegisterData();
                    reg.setEmailId(account.getEmail());
                    reg.setfirstName(account.getGivenName());
                    reg.setlastName(account.getFamilyName());
                    db.addRegister(reg);
                    Toast.makeText(this, "User persisted to database", Toast.LENGTH_LONG).show();
                }
                //

                Toast.makeText(this, "U Signed In successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, Accueil.class));
            } else {
                Toast.makeText(this, "U Didnt signed in", Toast.LENGTH_LONG).show();
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
    //Fin des Méthodes Google

}