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

public class journal_uny extends AppCompatActivity {
    private WebView webView;
    String url = "https://journal.uny.ac.id/";
    String html;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_uny);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_journal);
        toolbar.setTitle("Journal UNY");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = (WebView) findViewById(R.id.view_journal);
        webView.getSettings().setJavaScriptEnabled(true);

        new journal_uny.JsoupsJournal().execute();
    }

    public class JsoupsJournal extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect(url).get();
                Elements head = doc.select("head");
                Elements main = doc.select("div#main");
                Elements nav = main.select("div#navbar");
                Elements  breadcrumb = main.select("div#breadcrumb");
                for (Element remove_nav : nav) {
                   remove_nav.remove();
                }
                for (Element remove_breadcrumb : breadcrumb) {
                    remove_breadcrumb.remove();
                }
                String head_tag = head.outerHtml();
                String content = main.outerHtml();

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
