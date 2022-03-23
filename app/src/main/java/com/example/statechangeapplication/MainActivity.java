package com.example.statechangeapplication;

import android.os.Bundle;

import com.App;
import com.example.logger.Logger;
import com.example.model.UserModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.statechangeapplication.databinding.ActivityMainBinding;
import com.stateManger.BaseOnStateChangeListener;
import com.stateManger.StateEntity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BaseOnStateChangeListener {

    Button button;
    TextView textView;
    int a = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        App.appSateChangeManger.registerObserver(this);

        UserModel userModel = App.appSateChangeManger.getUserState().cloneState();
        userModel.setAccount("jack" + a);
        App.appSateChangeManger.update(userModel);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a++;
                UserModel userModel = App.appSateChangeManger.getUserState().cloneState();
                userModel.setAccount(userModel.account + a);
                App.appSateChangeManger.update(userModel);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.appSateChangeManger.unRegisterObserver(this);
    }

    @Override
    public void onStateChangeListener(StateEntity stateEntity) {
        Logger.d("");
        UserModel userModel =(UserModel)stateEntity.getEntity();
        textView.setText(userModel.account);

    }
}