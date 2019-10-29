package com.example.omletlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebLoginActivity extends AppCompatActivity {

    private static final String LOGIN_URL = "https://tpapi-idp-s.stage.omapi.net/login";
    private static final String GAME_UID = "TESTPUBG";
    private static final String GAME_NAME = "PUBG%20Mobile";
    private static final String GAME_DEV = "Tencent%20Games";
    private static final String GAME_ICON = "https://omlet-download.akamaized.net/blob/bG9uZ2RhbjovL09ORS9sZHByb2QtdXMvQ1VfLVBHQ3B4QmZsODBzUUFtcTBwUT09";

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

                    /* SAVE THE OMLET USER TOKEN SOMEWHERE */
                    Log.i("WebLoginActivity", "Omlet User Token: " + omletUserToken);

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