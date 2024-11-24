package com.example.courseapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CertificateFragment extends Fragment {
    private Button btnWinCertificate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_certificate, container, false);
        btnWinCertificate = view.findViewById(R.id.btn_win_certificate);

        btnWinCertificate.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CertificateActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
