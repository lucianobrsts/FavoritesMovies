package unit7.dev.favoritesmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import unit7.dev.favoritesmovies.R;

public class CadastroActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnRegistrar, btnVoltar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializaComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();
                if (!email.isEmpty() && !senha.isEmpty()) {
                    if (senha.length() >= 6) {
                        criarUser(email, senha);
                    } else {
                        alert("Senha de 6 dígitos.");
                    }
                } else {
                    alert("Os campos email e senha são obrigatórios.");
                }
            }
        });
    }

    private void criarUser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            alert("Usuário cadastrado com sucesso.");
                            Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            alert("Email já cadastrado ou sem conexão.");
                        }
                    }
                });
    }

    private void alert(String msg) {
        Toast.makeText(CadastroActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        editEmail = (EditText) findViewById(R.id.editCadastroEmail);
        editSenha = (EditText) findViewById(R.id.editCadastroSenha);
        btnRegistrar = (Button) findViewById(R.id.btnCadastroRegistrar);
        btnVoltar = (Button) findViewById(R.id.btnCadastroVoltar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = ConexaoActivity.getFirebaseAuth();
    }
}
