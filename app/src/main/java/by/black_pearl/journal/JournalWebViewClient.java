package by.black_pearl.journal;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by BLACK_Pearl.
 */

public class JournalWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
