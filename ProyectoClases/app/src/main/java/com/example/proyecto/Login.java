package com.example.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.proyecto.Model.User;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.UserService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private TextView newregister;
    private EditText correo;
    private EditText contra;
    private Button login;

    SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        newregister=findViewById(R.id.newregister);
        correo = findViewById(R.id.correo);
        contra = findViewById(R.id.contra);
        login = findViewById(R.id.login);

        final LoadingDialog loadingDialog = new LoadingDialog(Login.this);

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("_id", null);
        if (id != null){
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        }


        newregister.setOnClickListener(new View.OnClickListener() {@Override
            public void onClick(View view) {
                final LoadingDialog loadingDialog = new LoadingDialog(Login.this);
                startActivity(new Intent(Login.this, Register.class));
            loadingDialog.startLoading();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.dismissDialog();
                }
            }, 3000);

            }
        });


    }
    public void login(View view){
        final LoadingDialog loadingDialog = new LoadingDialog(Login.this);

        if (correo.getText().toString().isEmpty() && contra.getText().toString().isEmpty()){
            // objeto de vista que despliega elemementos emergentes en la IU
            Toast.makeText(Login.this, "Ingresar correo y contraseña",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        logear(
                correo.getText().toString(),
                contra.getText().toString()
        );
        loadingDialog.startLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 3000);


    }
    private void logear(String correo, String contra) {
        try {
            UserService userService = Api.getRetrofitInstance().create(UserService.class);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Call<User> call = userService.postLogin(correo, contra);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user != null ){
                        editor.putString("_id", user.get_id());
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);

                    } else{
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                        dialog.setView(R.layout.loginerror);
                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = dialog.create();
                        alertDialog.show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                    dialog.setView(R.layout.loginerror);
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }
            });
        } catch(Exception e){
            System.out.println(e);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("_id", null);
        if (id != null){
            finish();
        }
    }
}