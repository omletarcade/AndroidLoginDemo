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

    Button loginButton, copyButton;
    private TextView omletUserTokenTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login);
        copyButton = findViewById(R.id.copy);
        copyButton.setVisibility(View.GONE);
        omletUserTokenTextView = findViewById(R.id.omlet_user_token);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getOmletUserTokenIntent = new Intent(MainActivity.this, WebLoginActivity.class);
                startActivityForResult(getOmletUserTokenIntent, REQUEST_GET_OMLET_USER_TOKEN);
            }
        });
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Omlet User Token", omletUserTokenTextView.getText());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(MainActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == REQUEST_GET_OMLET_USER_TOKEN) {
            omletUserTokenTextView.setText(data.getStringExtra(WebLoginActivity.EXTRA_OMLET_USER_TOKEN));
            copyButton.setVisibility(View.VISIBLE);
        }
    }
}
