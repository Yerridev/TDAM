package com.example.yerripc1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.yerripc1.Interface.APIYerri;
import com.example.yerripc1.Models.RptaObtenerUsuarios;
import com.example.yerripc1.Models.Usuarios;
import com.example.yerripc1.databinding.FragmentSecondBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(v ->
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment)
        );

        String username = SecondFragmentArgs.fromBundle(getArguments()).getUsername();
        binding.txtUsuario.setText(username);

        obtenerUsuarios();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void obtenerUsuarios(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://yerri.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIYerri apiYerri = retrofit.create(APIYerri.class);
        Call <RptaObtenerUsuarios> call = apiYerri.obtenerUsuarios();
        call.enqueue(new Callback<RptaObtenerUsuarios>() {
            @Override
            public void onResponse(Call<RptaObtenerUsuarios> call, Response<RptaObtenerUsuarios> response) {
                if(!response.isSuccessful()){
                    binding.txtlistado.setText("Código: " + response.code());
                }

                RptaObtenerUsuarios rptaObtenerUsuarios = response.body();
                List<Usuarios> listaUsuarios =rptaObtenerUsuarios.getData();
                for (Usuarios usuarios: listaUsuarios){
                    String contenido = "";
                    contenido += "id: " + String.valueOf(usuarios.getId()) + "\n";
                    contenido += "email: " + usuarios.getEmail() + "\n";
                    contenido += "password: " + usuarios.getPassword() + "\n";
                    binding.txtlistado.append(contenido);
                }
            }

            @Override
            public void onFailure(Call<RptaObtenerUsuarios> call, Throwable throwable) {

            }
        });
    }


}