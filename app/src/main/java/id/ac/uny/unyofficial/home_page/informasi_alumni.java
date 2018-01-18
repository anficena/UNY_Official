package id.ac.uny.unyofficial.home_page;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import id.ac.uny.unyofficial.R;

public class informasi_alumni extends AppCompatActivity {
    String html;
    WebView webView;
    String url = "http://simfoni.uny.ac.id/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_alumni);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_alumni);
//      toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Informasi Alumni");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = (WebView) findViewById(R.id.view_alumni);
        webView.getSettings().setJavaScriptEnabled(true);

        new informasi_alumni.JsoupsAlumni().execute();
    }

    public class JsoupsAlumni extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect(url).get();
                Elements head = doc.select("head");
                Elements event = doc.select("div.col-lg-6");

                String head_tag = head.outerHtml();
                String content = event.outerHtml();
                html= head_tag + content;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            webView.loadDataWithBaseURL(null,html,
                    "text/html", "utf-8", null);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
