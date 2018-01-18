package id.ac.uny.unyofficial.indeks_pengumuman;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import id.ac.uny.unyofficial.R;
import id.ac.uny.unyofficial.indeks_berita.BeritaAdapter;
import id.ac.uny.unyofficial.indeks_berita.BeritaModel;
import id.ac.uny.unyofficial.indeks_berita.BeritaUny;
import id.ac.uny.unyofficial.indeks_berita.DetailBerita;


/**
 * Created by WIN 8 on 07/10/2017.
 */

public class DetailPengumuman extends AppCompatActivity {
    String path, title, date, desc, path_img;
    TextView tv_detail_title_p, tv_detail_date_p, tv_detail_desc_p;
    ImageView iv_detail_image_p;
    String main_url, img_detail, url;
    WebView webView;
    String html ;
    Document doc ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pengumuman);

        new DetailPengumuman.JsoupsDetailPengumuman().execute();

        Bundle extras = getIntent().getExtras();
        path = extras.getString("path");
        title = extras.getString("title");
        date = extras.getString("date");
         url = extras.getString("path");


        tv_detail_title_p = (TextView) findViewById(R.id.detail_title_p);
        tv_detail_date_p = (TextView) findViewById(R.id.detail_date_p);
//        iv_detail_image_p = (ImageView) findViewById(R.id.detail_image_p);
        webView = (WebView) findViewById(R.id.view_pengumuman);
        webView.getSettings().setJavaScriptEnabled(false);

    }

    public class JsoupsDetailPengumuman extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect(path).get();
                Elements head = doc.select("head");
                Elements content = doc.select("section#post-content");
                Elements main = content.select("img");
//                Element imgs = main.select("img").first();
                    for(Element img : main) {
                        if (!img.equals("")){
                            String imgss = img.select("img").attr("src");
                            String main_url = "https://www.uny.ac.id";
                            String path_img = main_url.concat(imgss);
                            img.attr("src", path_img);
                        }
                    }
                    String head_tag = head.outerHtml();
                    html=head_tag + (content.outerHtml());
//                Document document = Jsoup.connect(path).get();
//                for (Element detail : document.select("div.field-item")){
//                    for (Element image : detail.select("div.field-item.even[property] p")) {
//                           Elements img = image.select("img");
//                           main_url = "https://www.uny.ac.id";
//                           path_img = img.attr("src");
//                          img_detail = main_url.concat(path_img);
//                    }
//                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            tv_detail_title_p.setText(html);
//            tv_detail_date_p.setText(html);
            webView.loadDataWithBaseURL(null,html,
                    "text/html", "utf-8", null);
//            webView.getSettings().setLoadWithOverviewMode(true);
//            webView.getSettings().setUseWideViewPort(true);
//            Picasso.with(getApplicationContext())
//                    .load(img_detail)
//                    .fit()
//                    .into(iv_detail_image_p);
        }
    }

}
