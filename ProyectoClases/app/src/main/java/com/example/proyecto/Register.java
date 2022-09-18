package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto.Model.User;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private ImageView regresar;
    private TextView newlogin;
    private EditText nombre;
    private EditText apellido;
    private EditText correo;
    private EditText nacionalidad;
    private EditText numero;
    private EditText contra;
    private EditText confcontra;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regresar=findViewById(R.id.regresar);
        newlogin=findViewById(R.id.newlogin);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        correo = findViewById(R.id.correo);
        nacionalidad = findViewById(R.id.nacionalidad);
        numero = findViewById(R.id.numero);
        contra = findViewById(R.id.contra);
        confcontra = findViewById(R.id.confcontra);
        register = findViewById(R.id.register);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, MainActivity.class));
            }
        });
        newlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nombre.getText().toString().isEmpty() && apellido.getText().toString().isEmpty()
                        && correo.getText().toString().isEmpty() && nacionalidad.getText().toString().isEmpty()
                        && numero.getText().toString().isEmpty() && contra.getText().toString().isEmpty()
                        && confcontra.getText().toString().isEmpty()){
                    // objeto de vista que despliega elemementos emergentes en la IU
                    Toast.makeText(Register.this, "Ingresar todos los campos",
                            Toast.LENGTH_SHORT).show();
                        return;
                }
                registrar(
                        nombre.getText().toString(),
                        apellido.getText().toString(),
                        correo.getText().toString(),
                        nacionalidad.getText().toString(),
                        numero.getText().toString(),
                        contra.getText().toString(),
                        confcontra.getText().toString()
                );
            }


        });
    }

    private void registrar(String nombre, String apellido, String correo, String nacionalidad,
                           String numero, String contra, String confcontra) {
        try {
            UserService userService = Api.getRetrofitInstance().create(UserService.class);
            Call<User> call = userService.postUser(nombre, apellido, correo, nacionalidad, numero,
                    contra, confcontra);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    Toast.makeText(Register.this, response.toString(), Toast.LENGTH_SHORT).show();
                    User user = response.body();
                    if (user.ok ){
                        Intent intent = new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                    } else{
                        Toast.makeText(Register.this, user.msg, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(Register.this, "Error en conexion",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch(Exception e){
            System.out.println(e);
        }
    }
}