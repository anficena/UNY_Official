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

public class presma_mahasiswa extends AppCompatActivity {
    private WebView webView;
    String url = "http://presma.uny.ac.id/";
    String html;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presma_mahasiswa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_presma);
//      toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Prestasi Mahasiswa");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = (WebView) findViewById(R.id.view_presma);
        webView.getSettings().setJavaScriptEnabled(true);

        new presma_mahasiswa.JsoupsPresma().execute();
    }

    public class JsoupsPresma extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect(url).get();
                Elements head = doc.select("head");
                Elements conainer = doc.select("div.container");
                Elements row = conainer.select("div.row");
                Elements col_6 = conainer.select("div.col-lg-6");
                Elements col_3 = conainer.select("div.col-lg-3");

                String head_tag = head.outerHtml();
                String col6 = col_6.outerHtml();
                String col3 = col_3.outerHtml();
                html= head_tag + col3 + col6;

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