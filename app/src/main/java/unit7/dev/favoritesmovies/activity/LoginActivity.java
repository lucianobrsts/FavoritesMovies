package unit7.dev.favoritesmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import unit7.dev.favoritesmovies.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnLogar, btnNovo;
    private TextView txtResetSenha;

    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializaComponentes();
        eventoClick();
    }

    public void eventoClick() {
        btnNovo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
            }
        });

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();

                if (!email.isEmpty() && !senha.isEmpty()) {
                    login(email, senha);
                } else {
                    alert("Os campos email e senha são obrigatórios.");
                }
            }
        });

        txtResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ResetSenhaActivity.class);
                startActivity(i);
            }
        });
    }

    private void login(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }else {
                            alert("Email ou senha inválido.");
                        }
                    }
                });
    }

    private void alert(String s) {
        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        editEmail = (EditText) findViewById(R.id.edtLoginEmail);
        editSenha = (EditText) findViewById(R.id.edtLoginSenha);
        btnLogar = (Button) findViewById(R.id.btnLoginLogar);
        btnNovo = (Button) findViewById(R.id.btnLoginNovo);
        txtResetSenha = (TextView) findViewById(R.id.txtResetSenha);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = ConexaoActivity.getFirebaseAuth();
    }
}
