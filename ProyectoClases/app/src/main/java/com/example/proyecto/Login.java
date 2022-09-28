package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.proyecto.Model.User;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.UserService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private TextView newregister;
    private EditText correo;
    private EditText contra;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        newregister=findViewById(R.id.newregister);
        correo = findViewById(R.id.correo);
        contra = findViewById(R.id.contra);
        login = findViewById(R.id.login);


        newregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }


        });

    }
    private void logear(String correo, String contra) {
        try {
            UserService userService = Api.getRetrofitInstance().create(UserService.class);
            Call<User> call = userService.postLogin(correo, contra);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user.ok ){
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(Login.this, user.msg, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Login.this, "Error en conexion",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch(Exception e){
            System.out.println(e);
        }

    }


}