package unit7.dev.favoritesmovies.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import unit7.dev.favoritesmovies.R;

public class PerfilActivity extends AppCompatActivity {

    private TextView textEmail, textId;
    private Button btnLogOut;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        inicializaComponentes();
        eventoClick();
    }

    private void eventoClick() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexaoActivity.logout();
                finish();
            }
        });
    }

    private void inicializaComponentes() {
        textEmail = (TextView) findViewById(R.id.textPerfilEmail);
        textId = (TextView) findViewById(R.id.textPerfilId);
        btnLogOut = (Button) findViewById(R.id.btnPerfilLogout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = ConexaoActivity.getFirebaseAuth();
        user = ConexaoActivity.getFirebaseUser();
        verificaUser();
    }

    private void verificaUser() {
        if(user == null) {
            finish();
        } else {
            textEmail.setText("Email: " + user.getEmail());
            textId.setText("ID: " + user.getUid());
        }
    }
}
