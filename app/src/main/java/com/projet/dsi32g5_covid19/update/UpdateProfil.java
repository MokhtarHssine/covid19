package com.projet.dsi32g5_covid19.update;


import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.projet.dsi32g5_covid19.Accueil;
import com.projet.dsi32g5_covid19.DataBaseHelper;
import com.projet.dsi32g5_covid19.GlobalDefinitions;
import com.projet.dsi32g5_covid19.LoginActivity;
import com.projet.dsi32g5_covid19.R;
import com.projet.dsi32g5_covid19.SelectDate;

//Classe pour modifier les informations de l'utilisateur
public class UpdateProfil extends Fragment {

    private static TextView dateNaissance;
    public static TextView getDateNaissance() {
        return dateNaissance;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GlobalDefinitions dbDefinitions = new GlobalDefinitions();
        DataBaseHelper db = new DataBaseHelper(getContext(), dbDefinitions.DATABASE_NAME, null, dbDefinitions.DATABASE_VERSION);

        final View root = inflater.inflate(R.layout.fragement_mise_jour_profile, container, false);
        final LoginActivity logActivity = new LoginActivity();

        //String userid = logActivity.getUserid().getText().toString();
        String nomSelected = db.getRegister( logActivity.getUserid().getText().toString()).getlastName();
        String prenomSelected = db.getRegister( logActivity.getUserid().getText().toString()).getfirstName();
        String dateNaiss = db.getRegister( logActivity.getUserid().getText().toString()).getDateNaiss();

        final EditText nom = (EditText) root.findViewById(R.id.modiflastname);
        nom.setText(nomSelected);
        final EditText prenom = (EditText) root.findViewById(R.id.modiffirstname);
        prenom.setText(prenomSelected);
        dateNaissance = (TextView) root.findViewById(R.id.date_naiss);
        dateNaissance.setText(dateNaiss);

        Button modif = root.findViewById(R.id.btnmodif);
        modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ContentValues cv = new ContentValues();
                cv.put(db.getKeylastName(), nom.getText().toString()); //These Fields should be your String values of actual column names
                cv.put(db.getKeyFirstName(), prenom.getText().toString());
                cv.put(db.getKeyDate(), dateNaissance.getText().toString());
                db.updateData(logActivity.getUserid().getText().toString(), nom.getText().toString(), prenom.getText().toString(), dateNaissance.getText().toString());
                Toast.makeText(root.getContext(), "info modifié", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getActivity().getApplicationContext(), Accueil.class));
            }
        });

        dateNaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                DialogFragment newFragment = new SelectDate();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });
        return root;
    }
}


