package com.example.passwordgenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textViewGeneratedPassword;
    private TextView textViewCopy;
    private TextView textViewCurrentLength;
    private CheckBox checkBoxSpecialSymbols;
    private CheckBox checkBoxNumbers;
    private SeekBar seekBarLength;
    private Button buttonGenerate;

    private boolean hasNumbers = false;
    private boolean hasSpecialSymbols = false;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        initViews();
        textViewCurrentLength.setText("0");
    }

    private void initViews(){
        textViewGeneratedPassword = findViewById(R.id.tvGeneratedPassword);
        textViewCopy = findViewById(R.id.textViewCopy);
        textViewCurrentLength = findViewById(R.id.textViewCurrentLength);
        checkBoxSpecialSymbols = findViewById(R.id.checkBoxSpecialSymbols);
        checkBoxNumbers = findViewById(R.id.checkBoxNumbers);
        seekBarLength = findViewById(R.id.seekBarLength);
        buttonGenerate = findViewById(R.id.buttonGenerate);

        seekBarLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewCurrentLength.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                return;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                return;
            }
        });

        textViewCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("", textViewGeneratedPassword.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this, R.string.saved, Toast.LENGTH_SHORT).show();
            }
        });

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxNumbers.isChecked()) hasNumbers = true;
                if (checkBoxSpecialSymbols.isChecked()) hasSpecialSymbols = true;
                viewModel.generatePassword(Integer.parseInt(textViewCurrentLength.getText().toString()), hasNumbers, hasSpecialSymbols);
                viewModel.getGeneratedPassword().observe(MainActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        textViewGeneratedPassword.setText(s);
                    }
                });
            }
        });
    }
}