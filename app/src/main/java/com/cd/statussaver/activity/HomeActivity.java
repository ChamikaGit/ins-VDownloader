package com.cd.statussaver.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.cd.statussaver.R;
import com.cd.statussaver.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    HomeActivity activity;
    ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        activity = this;
        initView();
    }

    private void initView() {
        binding.relWhatsappContainer.setOnClickListener(this);
        binding.relGalleryContainer.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions(0);
        }
    }

    private boolean checkPermissions(int type) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(activity, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) (activity),
                    listPermissionsNeeded.toArray(new
                            String[listPermissionsNeeded.size()]), type);
            return false;
        } else {
            if (type == 102) {
                callWhatsAppActivity();
            }
            if (type == 103) {
                callGalleryActivity();
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.relWhatsappContainer:
                if (Build.VERSION.SDK_INT >= 23) {
                    checkPermissions(102);
                } else {
                    callWhatsAppActivity();
                }
                break;

            case R.id.relGalleryContainer:
                if (Build.VERSION.SDK_INT >= 23) {
                    checkPermissions(103);
                } else {
                    callGalleryActivity();
                }
                break;
        }
    }

    private void callGalleryActivity() {
        Intent i = new Intent(activity, GalleryActivity.class);
        startActivity(i);
    }

    private void callWhatsAppActivity() {
        Intent i = new Intent(activity, WhatsappActivity.class);
        startActivity(i);
    }
}