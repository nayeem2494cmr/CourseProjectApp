package com.example.courseapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ScoreFragment extends Fragment {
    private RecyclerView scoreRecyclerView;
    private List<String> scoreList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        scoreRecyclerView = view.findViewById(R.id.score_recycler_view);
        scoreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Dummy score data
        scoreList = new ArrayList<>();
        scoreList.add("John: 85");
        scoreList.add("Emma: 90");
        scoreList.add("Mike: 78");
        scoreList.add("Sarah: 88");
        scoreList.add("David: 92");

        ScoreAdapter adapter = new ScoreAdapter(scoreList);
        scoreRecyclerView.setAdapter(adapter);

        return view;
    }
}
