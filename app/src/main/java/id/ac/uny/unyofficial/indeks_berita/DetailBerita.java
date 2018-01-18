package id.ac.uny.unyofficial.indeks_berita;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import id.ac.uny.unyofficial.Constant;
import id.ac.uny.unyofficial.R;

public class DetailBerita extends AppCompatActivity {


    private static final String PREF_NAME = "detail_url";
    String path, title, date, desc, path_img;
//    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
//    private SharedPreferences pref = PreferenceManager
//            .getDefaultSharedPreferences(getApplicationContext());
    //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//    SharedPreferences pref=getApplication().getSharedPreferences("detail_ link", MODE_PRIVATE);
//    String detail_berita = (pref.getString("detail_url", null));
    //String url =pref.getString(Constant.TAG,"");


    TextView tv_detail_title, tv_detail_date, tv_detail_desc;
    ImageView iv_detail_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_berita);

        new JsoupsDetailBerita().execute();

        Bundle extras = getIntent().getExtras();
        path = extras.getString("path");
        title = extras.getString("title");
        date = extras.getString("date");
//        String url_main = "https://www.uny.ac.id";
//        url =url_main.concat(path);

        tv_detail_title = (TextView) findViewById(R.id.detail_title);
        tv_detail_date = (TextView) findViewById(R.id.detail_date);
        tv_detail_desc = (TextView) findViewById(R.id.detail_desc);
        iv_detail_image = (ImageView) findViewById(R.id.detail_image);

    }

    public class JsoupsDetailBerita extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document document = Jsoup.connect(path).get();
                Elements image = document.select("div.field-item.even img[typeof=foaf:Image]");
                path_img = image.attr("src");
                for (Element detail : document.select("div.field-item.even[property]")){
                    Elements p = detail.select("p");
                    desc = p.text();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tv_detail_title.setText(title);
            tv_detail_date.setText(date);
            Picasso.with(getApplicationContext())
                    .load(path_img)
                    .fit()
                    .into(iv_detail_image);
            tv_detail_desc.setText(desc);
        }
    }
}
