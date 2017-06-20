package eu.kudan.ar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class TitleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        requestPermissions();
    }

    public void startMarkerActivity(View view)
    {
        Intent intent = new Intent(this,MarkerActivity.class);
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            permissionsNotSelected();
            requestPermissions();
        }
        else
        {
            startActivity(intent);
        }
    }
    public void startMakerlessFloorActivity(View view)
    {
        Intent intent = new Intent(this,MarkerlessActivity.class);
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            permissionsNotSelected();
            requestPermissions();
        }
        else
        {
            startActivity(intent);
        }
    }

    public void startMarkerlessWallActivity(View view)
    {
        Intent intent = new Intent(this,Markerless_Wall.class);
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            permissionsNotSelected();
            requestPermissions();
        }
        else
        {
            startActivity(intent);
        }
    }

    public void startSimultaneousActivity(View view)
    {
        Intent intent = new Intent(this,SimultaneousDetectionActivity.class);
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            permissionsNotSelected();
            requestPermissions();
        }
        else
        {
            startActivity(intent);
        }
    }

    public void requestPermissions()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},111);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case 111: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                }
                else
                {
                    permissionsNotSelected();
                }
            }
        }
    }

    private void permissionsNotSelected()
    {
        Toast.makeText(this,"You must enable permissions in settings",Toast.LENGTH_LONG).show();

    }
}
