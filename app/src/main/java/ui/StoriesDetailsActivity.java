package ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.showbox.showbox.R;

import java.io.Serializable;

import model.NewsDTO;

/**
 * Created by Vlade Ilievski on 10/19/2016.
 */

public class StoriesDetailsActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar progressBarStories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stories_details_layout);
        NewsDTO newsDTO = null;
        progressBarStories=(ProgressBar)findViewById(R.id.progressBarStories);
        progressBarStories.setVisibility(View.VISIBLE);
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().getSerializable("news_dto") != null) {
            Serializable seriazable = getIntent().getExtras().getSerializable("news_dto");
            if (seriazable instanceof NewsDTO) {
                newsDTO = (NewsDTO) getIntent().getExtras().getSerializable("news_dto");
                progressBarStories.setVisibility(View.GONE);
            } else {
                finish();
            }
        }

        String url = newsDTO.getOriginalLink();
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);

    }
}
