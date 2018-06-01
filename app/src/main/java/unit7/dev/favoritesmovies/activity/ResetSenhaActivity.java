package unit7.dev.favoritesmovies.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import unit7.dev.favoritesmovies.R;

public class ResetSenhaActivity extends AppCompatActivity {

    private EditText editEmail;
    private Button btnResetSenha;

    private FirebaseAuth auth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_senha);
        inicializaComponentes();
        eventoClick();
    }

    private void eventoClick() {
        btnResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                if(!email.isEmpty()) {
                    resetSenha(email);
                }else {
                    alert("Email é obrigratório.");
                }
            }
        });
    }

    private void resetSenha(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(ResetSenhaActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            alert("Um email foi enviado para alterar sua senha.");
                            finish();
                        } else {
                            alert("Email não registrado.");
                        }
                    }
                });
    }

    private void alert(String s) {
        Toast.makeText(ResetSenhaActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        editEmail = (EditText) findViewById(R.id.editResetEmail);
        btnResetSenha = (Button) findViewById(R.id.btnResetSenha);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = ConexaoActivity.getFirebaseAuth();
    }
}
