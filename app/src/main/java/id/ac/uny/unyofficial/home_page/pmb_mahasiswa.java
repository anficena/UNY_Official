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

public class pmb_mahasiswa extends AppCompatActivity {
    String html;
    WebView webView;
    String url = "http://pmb.uny.ac.id/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmb_mahasiswa);

        new pmb_mahasiswa.JsoupsPmb().execute();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pmb);
//      toolbar.setLogo(R.mipmap.ic_launcher);
        toolbar.setTitle("Penerimaan Mahasiswa");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = (WebView) findViewById(R.id.view_pmb);
        webView.getSettings().setJavaScriptEnabled(true);



//        setTitle("Penerimaan Mahasiswa");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_pmb);
//        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.drawable.ic_launcher);
//        toolbar.setLogoDescription("Penerimaan Mahasiswa");
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));


    }
    public class JsoupsPmb extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect(url).get();
                Elements head = doc.select("head");
                Elements home_cta = doc.select("div#home-cta");
                Elements remove = home_cta.select("div.row.content-box");
                for(Element element : remove){
                        element.remove();
                }
//                Elements autowide = home_cta.select("div.autowide");
//                Elements module = home_cta.select("div.module");

//                for(Element modules : module) {
//                    if (!img.equals("")){
//                        String imgss = img.select("img").attr("src");
//                        String main_url = "https://www.uny.ac.id";
//                        String path_img = main_url.concat(imgss);
//                        img.attr("src", path_img);
//                    }
//                }
                String head_tag = head.outerHtml();
                html= head_tag + (home_cta.outerHtml());

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
