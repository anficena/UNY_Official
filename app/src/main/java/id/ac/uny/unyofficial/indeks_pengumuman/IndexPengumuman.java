package id.ac.uny.unyofficial.indeks_pengumuman;


import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import id.ac.uny.unyofficial.R;



public class IndexPengumuman extends AppCompatActivity {
    private RecyclerView rv_ipengumuman;
    private PengumumanAdapter adapter;
    ArrayList<PengumumanModel>models;
    String url = "https://www.uny.ac.id/index-pengumuman/";
    String detail_url;
    private ProgressDialog mProgressDialog;
    private String total;
    private int page;
    private boolean isLoading;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_pengumuman);
        page = 1;
        isLoading = false;

        new JsoupPengumuman(page).execute();
        rv_ipengumuman = (RecyclerView) findViewById(R.id.rv_pengumuman);
        layoutManager = new LinearLayoutManager(IndexPengumuman.this, LinearLayoutManager.VERTICAL, false);
        rv_ipengumuman.setLayoutManager(layoutManager);
        rv_ipengumuman.setAdapter(adapter);
    }

    protected void updateList(ArrayList<PengumumanModel> list) {
        if(adapter == null) {
            adapter = new PengumumanAdapter(list);
            rv_ipengumuman.setAdapter(adapter);
            rv_ipengumuman.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleitems = layoutManager.findFirstVisibleItemPosition();
                    String var = "pv " + pastVisibleitems + " vi "+visibleItemCount+" ti "+totalItemCount;
//                    Toast.makeText(IndexPengumuman.this, var, Toast.LENGTH_SHORT).show();
                    Log.d("vsr", "onScrolled: "+var);
                    if(pastVisibleitems + visibleItemCount >= totalItemCount){

                        if(!isLoading) {
                            JsoupPengumuman loadpage = new JsoupPengumuman(++page);
                            loadpage.execute();
                        }
                    }
                }
            });
        } else {
            adapter.models.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }


    public class JsoupPengumuman extends AsyncTask<Void,Void,Void>{
        int page;

        public JsoupPengumuman(int page) {
            this.page = page;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(IndexPengumuman.this);
            mProgressDialog.setTitle("Pengumuman");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(isLoading) {
                return null;
            }
            isLoading = true;
            try {
                Document doc = Jsoup.connect(url).data("page", String.valueOf(page-1)).get();
                for(Element table : doc.select("tbody")){
                    models = new ArrayList<PengumumanModel>();
                    for (Element row : table.select("tr")){
                        Elements td = row.select("td");
                        String date = td.get(2).text().trim();
                        Log.d("match", "doInBackground: "+date);
                        Matcher matcher = Pattern.compile("Post date.*?, (\\w+) (\\d+)",
                                Pattern.CASE_INSENSITIVE).matcher(date);
                        Log.d("if", "doInBackground: "+"test");
                        if(matcher.find()){
                            String arg_month = matcher.group(1);
                            Log.d("month", "doInBackground: " + arg_month);
                            String arg_num = matcher.group(2);
                            arg_num = String.format("%2s",arg_num).replace(" ","0");
                            Log.d("num", "doInBackground: "+ arg_num);
                            String month  = arg_month.substring(0,3);
                            Log.d("month2", "doInBackground: "+month);
                            total = arg_num+" "+month;
                        }
                        String title = td.get(1).text();
                        Elements a = td.get(1).select("a");
                        String url = a.attr("href");
                        String main_url = "https://www.uny.ac.id";
                        detail_url = main_url.concat(url);
                        models.add(new PengumumanModel(title,total,detail_url));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                isLoading = false;
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateList(models);
            mProgressDialog.dismiss();
            adapter.setOnClickListener(new PengumumanAdapter.OnClickListener() {
                @Override
                public void onClick(int adapterPosition) {
                    Log.d("adapter", "onClick: "+adapterPosition);
//                    Toast.makeText(IndexPengumuman.this, models.get(adapterPosition).getUrl(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(IndexPengumuman.this,DetailPengumuman.class);
                    intent.putExtra("title",models.get(adapterPosition).getTitle());
                    intent.putExtra("date",models.get(adapterPosition).getDate());
                    intent.putExtra("detail_link",models.get(adapterPosition).getDetail_link());
                    startActivity(intent);

                }
            });
            isLoading = false;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
