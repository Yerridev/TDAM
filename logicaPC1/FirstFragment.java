package com.example.yerripc1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.yerripc1.databinding.FragmentFirstBinding;

import java.util.Random;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        binding.buttonFirst.setOnClickListener(v ->
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
//        );
        Random random = new Random();
        int numeroAleatorio = 1000 + random.nextInt(9000);
        String cadenaAleatorio = String.valueOf(numeroAleatorio);

        Toast.makeText(getContext(), cadenaAleatorio, Toast.LENGTH_LONG).show();

        binding.buttonFirst.setOnClickListener(v -> {
            String username = binding.txtUserInput.getText().toString();
            String valorNumero = binding.txtNumberImput.getText().toString();

            if( username.isEmpty() || valorNumero.isEmpty()){
                Toast.makeText(getContext(), "Ingresa Valores", Toast.LENGTH_SHORT).show();
                return;
            }

            if(valorNumero.equals(cadenaAleatorio)){
                FirstFragmentDirections.ActionFirstFragmentToSecondFragment action =
                        FirstFragmentDirections.actionFirstFragmentToSecondFragment(username);

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(action);
            } else {
                Toast.makeText(getContext(), "Acceso denegado", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}