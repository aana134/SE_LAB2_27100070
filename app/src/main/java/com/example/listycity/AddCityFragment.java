package com.example.listycity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import androidx.fragment.app.DialogFragment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;


public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
        void editCity();
    }

    private AddCityDialogListener listener;
    private City city;

    public AddCityFragment() { }

    public AddCityFragment(City city) {
        this.city = city;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (city != null) {
            editCityName.setText(city.getName());
            editProvinceName.setText(city.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view)
                .setTitle("Add/edit city")
                .setNegativeButton("CANCEL", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String name = editCityName.getText().toString();
                    String province = editProvinceName.getText().toString();

                    if (city == null)
                    {
                        listener.addCity(new City(name, province));
                    }
                    else
                    {
                        city.setName(name);
                        city.setProvince(province);
                        listener.editCity();
                    }
                });

        return builder.create();
    }
}