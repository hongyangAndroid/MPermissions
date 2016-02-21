package com.zhy.permission_sample;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

public class MainActivity extends AppCompatActivity
{

    private Button mBtnSdcard, mBtnCallPhone;
    private static final int REQUECT_CODE_SDCARD = 2;
    private static final int REQUECT_CODE_CALL_PHONE = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnSdcard = (Button) findViewById(R.id.id_btn_sdcard);
        mBtnCallPhone = (Button) findViewById(R.id.id_btn_callphone);

        mBtnSdcard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_SDCARD, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        });

        mBtnCallPhone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_CALL_PHONE, Manifest.permission.CALL_PHONE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @PermissionGrant(REQUECT_CODE_SDCARD)
    public void requestSdcardSuccess()
    {
        Toast.makeText(this, "GRANT ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(REQUECT_CODE_SDCARD)
    public void requestSdcardFailed()
    {
        Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }


    @PermissionGrant(REQUECT_CODE_CALL_PHONE)
    public void requestCallPhoneSuccess()
    {
        Toast.makeText(this, "GRANT ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }

    @PermissionDenied(REQUECT_CODE_CALL_PHONE)
    public void requestCallPhoneFailed()
    {
        Toast.makeText(this, "DENY ACCESS SDCARD!", Toast.LENGTH_SHORT).show();
    }


}
