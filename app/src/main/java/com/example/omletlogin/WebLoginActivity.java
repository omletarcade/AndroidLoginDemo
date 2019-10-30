package com.example.omletlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebLoginActivity extends AppCompatActivity {
    public static final String EXTRA_OMLET_USER_TOKEN = "extra_omlet_user_token";

    private static final String LOGIN_URL = "https://tpapi-idp-s.stage.omapi.net/login";
    private static final String GAME_UID = "FD06580EB5BC6200AE886EB84042BCDB";
    private static final String GAME_NAME = "Omlet%20Pew%20Pew";
    private static final String GAME_DEV = "Omlet%20Arcade";
    private static final String GAME_ICON = "https://i.imgur.com/JDoIU8N.png";

    private static final String CALLBACK_PREFIX = "interceptme://get-omlet-token/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onLoadResource(WebView view, String url) {
                //Intercept the custom callback prefix
                if(url.startsWith(CALLBACK_PREFIX)) {
                    Uri uri = Uri.parse(url);
                    String omletUserToken = uri.getLastPathSegment();

                    /* SAVE THIS OMLET USER TOKEN ON YOUR SERVER */

                    Intent userTokenDataIntent = new Intent();
                    userTokenDataIntent.putExtra(EXTRA_OMLET_USER_TOKEN, omletUserToken);
                    setResult(RESULT_OK, userTokenDataIntent);
                    finish();
                    return;
                }
                super.onLoadResource(view, url);
            }
        });

        webView.loadUrl(LOGIN_URL + "?gameuid="  + GAME_UID
                + "&gamename=" + GAME_NAME
                + "&gamedev="  + GAME_DEV
                + "&gameicon=" + GAME_ICON
                + "&gamecb="   + CALLBACK_PREFIX);
    }
}