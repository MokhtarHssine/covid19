package com.projet.dsi32g5_covid19;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
    EditText pass;
    EditText user;
    Button login;
    TextView not_reg;
    DataBaseHelper db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.eduser);
        pass = (EditText) findViewById(R.id.edpass);
        login = (Button) findViewById(R.id.login);
        not_reg = findViewById(R.id.not_reg);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                GlobalDefinitions dbDefintions = new GlobalDefinitions();
                db = new DataBaseHelper(LoginActivity.this, dbDefintions.DATABASE_NAME , null, dbDefintions.DATABASE_VERSION);
                String username = user.getText().toString();
                String password = pass.getText().toString();
                String StoredPassword = db.getRegister(username);
                if (password.equals(StoredPassword)) {
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Username/Password incorrect", Toast.LENGTH_LONG).show();
                    user.setText("");
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
    }
}