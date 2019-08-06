package com.ejsfbu.app_main.DialogFragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ejsfbu.app_main.Models.Goal;
import com.ejsfbu.app_main.Models.Reward;
import com.ejsfbu.app_main.Models.User;
import com.ejsfbu.app_main.R;

import java.util.ArrayList;
import java.util.List;

public class SetUpAutoPaymentDialogFragment extends DialogFragment {

    Context context;
    Button bSetUpAutoPaymentConfirm;
    Button bSetUpAutoPaymentCancel;
    Spinner spFrequency;
    Spinner spTimesRepeated;

    public SetUpAutoPaymentDialogFragment() {

    }

    public static SetUpAutoPaymentDialogFragment newInstance(Goal goal, User user) {
        SetUpAutoPaymentDialogFragment setUpAutoPaymentDialogFragment = new SetUpAutoPaymentDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("goal", goal);
        args.putParcelable("user", user);
        setUpAutoPaymentDialogFragment.setArguments(args);
        return setUpAutoPaymentDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        return inflater.inflate(R.layout.fragment_automatic_payment, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bSetUpAutoPaymentConfirm = view.findViewById(R.id.bSetUpAutoPaymentConfirm);
        bSetUpAutoPaymentCancel = view.findViewById(R.id.bSetUpAutoPaymentCancel);
        spFrequency = view.findViewById(R.id.spFrequency);
        spTimesRepeated = view.findViewById(R.id.spTimesRepeated);

        setOnClickListeners();
        setAdapters();
    }

    public void setOnClickListeners() {
        bSetUpAutoPaymentConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        bSetUpAutoPaymentCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setAdapters(){
        ArrayList<String> numArray = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            StringBuilder num = new StringBuilder();
            num.append(i);
            numArray.add(num.toString());
        }
        ArrayAdapter<String> numAdapter =
                new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, numArray);
        numAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTimesRepeated.setAdapter(numAdapter);

        ArrayList<String> frequency = new ArrayList<>();
        frequency.add("Day");
        frequency.add("Week");
        frequency.add("Month");
        frequency.add("Year");
        ArrayAdapter<String> frequencyAdapter =
                new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, frequency);
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFrequency.setAdapter(frequencyAdapter);

    }

    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }

    public interface SetUpAutoPaymentDialogListener {
        void onFinishEditDialog(String frequency);
    }

    public void sendBackResult(String frequency) {
        SetUpAutoPaymentDialogFragment.SetUpAutoPaymentDialogListener listener = (SetUpAutoPaymentDialogFragment.SetUpAutoPaymentDialogListener) getFragmentManager()
                .findFragmentById(R.id.flMainContainer);
        listener.onFinishEditDialog(frequency);
        dismiss();
    }

}
