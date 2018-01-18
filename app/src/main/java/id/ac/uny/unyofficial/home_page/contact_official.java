package id.ac.uny.unyofficial.home_page;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import id.ac.uny.unyofficial.R;

public class contact_official extends AppCompatActivity {
    String html;
    WebView webView;
    String url = "https://www.uny.ac.id/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_official);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_contact);
//      toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Kontak Kami");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = (WebView) findViewById(R.id.view_contact);
        webView.getSettings().setJavaScriptEnabled(true);

        new contact_official.JsoupsContact().execute();
    }

    public class JsoupsContact extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect(url).get();
                Elements head = doc.select("head");
                Elements footer = doc.select("div#footer_wrapper");
                Elements contact = footer.select("div.coloumn");
                Elements sosmed = footer.select("div.social-icons");
                String head_tag = head.outerHtml();
                String content = footer.outerHtml();
                String sosmeds = sosmed.outerHtml();
                html= head_tag + content ;

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
