package com.example.omletlogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_GET_OMLET_USER_TOKEN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getOmletUserTokenIntent = new Intent(MainActivity.this, WebLoginActivity.class);
                startActivityForResult(getOmletUserTokenIntent, REQUEST_GET_OMLET_USER_TOKEN);
            }
        });
        findViewById(R.id.copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Omlet User Token", ((TextView)findViewById(R.id.omlet_user_token)).getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_GET_OMLET_USER_TOKEN) {
            ((TextView)findViewById(R.id.omlet_user_token)).setText(data.getStringExtra(WebLoginActivity.EXTRA_OMLET_USER_TOKEN));
            findViewById(R.id.success_text).setVisibility(View.VISIBLE);
            findViewById(R.id.omlet_user_token).setVisibility(View.VISIBLE);
            findViewById(R.id.copy).setVisibility(View.VISIBLE);
        }
    }
}
