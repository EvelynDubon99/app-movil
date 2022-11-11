package com.example.proyecto.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.proyecto.Model.FechaRes;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.Fechares;
import com.example.proyecto.databinding.FragmentFechaBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FechaFragment extends AppCompatDialogFragment {
    private FragmentFechaBinding binding;
    private Button fechas;
    private int dia, mes, year;
    private TextView fecha_vista, id_res, id_user;
    private SharedPreferences sharedPreferences;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = FragmentFechaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle bundle = getActivity().getIntent().getExtras();
        builder.setView(view);

        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        fechas = view.findViewById(R.id.fechas);
        id_res = view.findViewById(R.id.id_res);
        id_res.setText(bundle.getString("id"));
        id_user = view.findViewById(R.id.id_user);
        id_user.setText(id_u);
        fecha_vista = view.findViewById(R.id.fecha_visita);
        fechas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);



                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year2, int monthOfYear, int dayOfMonth) {
                        fecha_vista.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year2);
                    }

                }
                , year,mes, dia);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });


        builder.setPositiveButton("Publicar Fecha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Fechares fechares = Api.getRetrofitInstance().create(Fechares.class);
                Call<FechaRes> call = fechares.postFecha(id_user.getText().toString(),id_res.getText().toString(),fecha_vista.getText().toString());
                call.enqueue(new Callback<FechaRes>() {
                    @Override
                    public void onResponse(Call<FechaRes> call, Response<FechaRes> response) {
                        FechaRes fechaRes = response.body();
                    }

                    @Override
                    public void onFailure(Call<FechaRes> call, Throwable t) {
                    }
                });
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setView(R.layout.fechagregada);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return builder.create();

    }

}