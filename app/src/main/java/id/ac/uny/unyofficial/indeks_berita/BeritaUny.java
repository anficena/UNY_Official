package id.ac.uny.unyofficial.indeks_berita;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import id.ac.uny.unyofficial.Constant;
import id.ac.uny.unyofficial.R;
import id.ac.uny.unyofficial.indeks_pengumuman.DetailPengumuman;

import static android.content.Context.MODE_PRIVATE;
import static id.ac.uny.unyofficial.Constant.TAG;

public class BeritaUny extends Fragment {
    private RecyclerView recyclerView;
    SharedPreferences preferences;
    private BeritaAdapter beritaAdapter;
    public ArrayList<BeritaModel> models;
    String url = "https://www.uny.ac.id/index-berita/";
    String path_url;
    String detail_url;
    private ProgressDialog mProgressDialog;
    private String total;
    private int page;
    private boolean isLoading;
    private LinearLayoutManager layoutManager;
    private OnFragmentInteractionListener mListener;

    public BeritaUny() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = 1;
        isLoading = false;
        new JsoupBerita(page).execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_berita_uny, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_news);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(beritaAdapter);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Berita");
        return  rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    protected void updateList(ArrayList<BeritaModel> list) {
        if(beritaAdapter == null) {
            beritaAdapter = new BeritaAdapter(list);
            recyclerView.setAdapter(beritaAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            BeritaUny.JsoupBerita loadpage = new BeritaUny.JsoupBerita(++page);
                            loadpage.execute();
                        }
                    }
                }
            });
        } else {
            beritaAdapter.models.addAll(list);
            beritaAdapter.notifyDataSetChanged();
        }
    }


    public class JsoupBerita extends AsyncTask<Void,Void,Void>{
        int page;

        public JsoupBerita(int page) {
            this.page = page;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setTitle("Berita");
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
                    models = new ArrayList<BeritaModel>();
                    for (Element row : table.select("tr")){
                        for (Element td : row.select("td")){
                            Elements image = td.select("div.field-content img[typeof=foaf:Image]");
                            String url = image.attr("src");
                            Elements strong = td.select("strong.field-content a");
                            String title = strong.text();
                            String path_url = strong.attr("href");
                            String main_url = "https://www.uny.ac.id";
                            detail_url = main_url.concat(path_url);
                            Elements br = td.select("span.field-content");
                            String date = br.text();
                            Elements p = td.select("div.field-content p[style]");
                            String deskripsi = p.text();
                            models.add(new BeritaModel(title,url,date,deskripsi,detail_url));
                        }
//                        Elements strong = row.select("td");
//                        Elements image = doc.select("div.field-content img[typeof=foaf:Image]");
//                        String url = image.attr("src");
//                        Log.d("url", "doInBackground: ");
//                        //String url ="https://www.uny.ac.id/sites/www.uny.ac.id/files/styles/thumbberita/public/sites/www.uny.ac.id/files/berita/MAB_5081.JPG?itok=xPix_ywM";
//
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
            beritaAdapter.setOnClickListener(new BeritaAdapter.OnClickListener() {
                @Override
                public void onClick(int adapterPosition) {
                    Log.d("adapter", "onClick: "+adapterPosition);
                    Toast.makeText(getContext(), models.get(adapterPosition).getTitle(), Toast.LENGTH_SHORT).show();
//                    preferences = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = preferences.edit();
//                    editor.putString(Constant.TAG,detail_url);
//                    editor.apply();
//                    preferences= getSharedPreferences("A", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor  editor = preferences.edit();
//                    editor.putString("detail_url",detail_url);
//                    editor.commit();
                    Intent intent = new Intent(getContext(),DetailBerita.class);
                    intent.putExtra("title",models.get(adapterPosition).getTitle());
                    intent.putExtra("date",models.get(adapterPosition).getDate());
                    intent.putExtra("path",models.get(adapterPosition).getDetail());
                    startActivity(intent);
                }
            });
            isLoading = false;
        }
    }
}
