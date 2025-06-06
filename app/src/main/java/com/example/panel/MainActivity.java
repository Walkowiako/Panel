package com.example.konfiguracja;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> settingNames;
    ArrayList<Integer> settingValues;
    ArrayList<String> settingUnits;
    ArrayList<String> displayItemsForListView;

    ListView settingsListView;
    TextView editingLabelTextView;
    SeekBar valueSeekBar;
    TextView seekBarValueTextView;

    ArrayAdapter<String> adapter;

    int selectedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicjalizacja danych
        settingNames = new ArrayList<>();
        settingValues = new ArrayList<>();
        settingUnits = new ArrayList<>();
        displayItemsForListView = new ArrayList<>();

        // Dodaj przykładowe dane
        settingNames.add("Jasność Ekranu");
        settingValues.add(50);
        settingUnits.add("%");

        settingNames.add("Głośność Dźwięków");
        settingValues.add(80);
        settingUnits.add("%");

        settingNames.add("Czas Automatycznej Blokady");
        settingValues.add(30);
        settingUnits.add("s");

        // Generowanie listy do wyświetlenia
        for (int i = 0; i < settingNames.size(); i++) {
            String item = settingNames.get(i) + ": " + settingValues.get(i) + settingUnits.get(i);
            displayItemsForListView.add(item);
        }

        // Adapter i ListView
        settingsListView = findViewById(R.id.settingsListView);
        adapter = new ArrayAdapter<>(this, R.layout.list_item_setting, R.id.itemTextView, displayItemsForListView);
        settingsListView.setAdapter(adapter);

        // Pobierz pozostałe elementy UI
        editingLabelTextView = findViewById(R.id.editingLabelTextView);
        valueSeekBar = findViewById(R.id.valueSeekBar);
        seekBarValueTextView = findViewById(R.id.seekBarValueTextView);

        // Ustawienia początkowe
        seekBarValueTextView.setText("Wartość: 0");
        valueSeekBar.setEnabled(false);

        // Obsługa kliknięcia na ListView
        settingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemPosition = position;
                String selectedName = settingNames.get(position);
                int selectedValue = settingValues.get(position);

              
            }
        });

        // Obsługa SeekBar
        valueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && selectedItemPosition != -1) {
                    seekBarValueTextView.setText("Wartość: " + progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Opcjonalne
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (selectedItemPosition != -1) {
                    int newValue = seekBar.getProgress();
                    settingValues.set(selectedItemPosition, newValue);
                    String updatedItem = settingNames.get(selectedItemPosition) + ": " + newValue + settingUnits.get(selectedItemPosition);
                    displayItemsForListView.set(selectedItemPosition, updatedItem);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
