package id.ac.uny.unyofficial;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

import id.ac.uny.unyofficial.home_page.contact_official;
import id.ac.uny.unyofficial.home_page.informasi_alumni;
import id.ac.uny.unyofficial.home_page.journal_uny;
import id.ac.uny.unyofficial.home_page.pengembangan_karir;
import id.ac.uny.unyofficial.home_page.pmb_mahasiswa;
import id.ac.uny.unyofficial.home_page.presma_mahasiswa;


public class BerandaUny extends Fragment implements View.OnClickListener {
    String url = "http://www.androidbegin.com";
    private ImageView slider;
    private RecyclerView recyclerView;
    private View mView;
    private CardView cv_pmb, cv_presma, cv_journal, cv_karir, cv_alumni, cv_contact;

    private OnFragmentInteractionListener mListener;

    public BerandaUny() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_beranda_uny, container, false);
//        slider = (ImageView) v.findViewById(R.id.image_slider);
//        recyclerView = (RecyclerView) v.findViewById(R.id.rv_menu);
//        recyclerView.setNestedScrollingEnabled(false);
//        ViewCompat.setNestedScrollingEnabled(recyclerView,false);
        cv_pmb = (CardView) mView.findViewById(R.id.unycardid_2);
        cv_presma = (CardView) mView.findViewById(R.id.unycardid);
        cv_journal = (CardView) mView.findViewById(R.id.unycardid_3);
        cv_karir = (CardView) mView.findViewById(R.id.unycardid_4);
        cv_alumni = (CardView) mView.findViewById(R.id.unycardid_5);
        cv_contact = (CardView) mView.findViewById(R.id.unycardid_6);

        cv_pmb.setOnClickListener(this);
        cv_presma.setOnClickListener(this);
        cv_journal.setOnClickListener(this);
        cv_karir.setOnClickListener(this);
        cv_alumni.setOnClickListener(this);
        cv_contact.setOnClickListener(this);
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch (view.getId()){
            case R.id.unycardid_2:
                i = new Intent(getActivity(), pmb_mahasiswa.class);
                startActivity(i);
                break;
            case R.id.unycardid:
                i = new Intent(getActivity(), presma_mahasiswa.class);
                startActivity(i);
                break;
            case R.id.unycardid_3:
                i = new Intent(getActivity(), journal_uny.class);
                startActivity(i);
                break;
            case R.id.unycardid_4:
                i = new Intent(getActivity(), pengembangan_karir.class);
                startActivity(i);
                break;
            case R.id.unycardid_5:
                i = new Intent(getActivity(), informasi_alumni.class);
                startActivity(i);
                break;
            case R.id.unycardid_6:
                i = new Intent(getActivity(), contact_official.class);
                startActivity(i);
                break;
            default:break;
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }
}
