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

public class pengembangan_karir extends AppCompatActivity {
    private WebView webView;
    String html;
    String url = "http://ppk.lppmp.uny.ac.id/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penembangan_karir);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_karir);
//      toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Pengembangan Karir");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = (WebView) findViewById(R.id.view_karir);
        webView.getSettings().setJavaScriptEnabled(true);

        new pengembangan_karir.JsoupsKarir().execute();
    }

    public class JsoupsKarir extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect(url).get();
                Elements head = doc.select("head");
                Elements content = doc.select("div#content.main");
                Elements vacancy = content.select("div.span7.vacancy");
                Elements img = vacancy.select("img");
                Elements h2 = vacancy.select("h2");
                for (Element imgs : img) {
                    if (!imgs.equals("")){
                        imgs.remove();
                    }
                }
                for (Element h2s : h2){
                    if (!h2s.equals("")){
                        h2s.remove();
                    }
                }
                String head_tag = head.outerHtml();
                String vacancy_job = vacancy.outerHtml();
                html= head_tag + vacancy_job;

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
