package com.example.passwordgenerator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainViewModel extends AndroidViewModel {

    MutableLiveData<String> generatedPassword = new MutableLiveData<>();

    List<Integer> numsOfChars = new ArrayList<>();

    public LiveData<String> getGeneratedPassword() {
        return generatedPassword;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);

        for (int i=65; i<91; i++) {
            numsOfChars.add(i);
        }
        for (int i=97; i<123; i++) {
            numsOfChars.add(i);
        }
    }


    public void generatePassword(int length, boolean hasNumbers, boolean hasSpecialSymbols) {
        if (hasNumbers) {
            for (int i=48; i<58; i++) {
                numsOfChars.add(i);
            }
        }
        if (hasSpecialSymbols) {
            numsOfChars.add(35);
            numsOfChars.add(36);
            numsOfChars.add(37);
            numsOfChars.add(41);
            numsOfChars.add(42);
            numsOfChars.add(64);
        }
        StringBuilder password = new StringBuilder();
        char temp;
        int index;
        for (int i=0; i<length; i++){
            index = numsOfChars.get((int)(Math.random()*numsOfChars.size()));
            temp = (char) index;
            password.append(temp);
        }
        generatedPassword.setValue(password.toString());
    }
}
