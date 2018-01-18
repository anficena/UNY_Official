package id.ac.uny.unyofficial.indeks_berita;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

import id.ac.uny.unyofficial.R;
import id.ac.uny.unyofficial.indeks_berita.BeritaAdapter;
import id.ac.uny.unyofficial.indeks_berita.BeritaModel;

/**
 * Created by WIN 8 on 10/10/2017.
 */

public class BeritaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView tv_title, tv_date, tv_desc, tv_read, btn_read;
    private ImageView imageView;
    public BeritaAdapter.OnClickListener onClickListener;

    public BeritaHolder(View itemView,BeritaAdapter.OnClickListener onClickListener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.onClickListener = onClickListener;
        tv_title = (TextView) itemView.findViewById(R.id.title);
        tv_date = (TextView) itemView.findViewById(R.id.date);
        tv_desc = (TextView) itemView.findViewById(R.id.deskripsi);
        imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
        btn_read = (TextView) itemView.findViewById(R.id.btn_read);
    }

    @Override
    public void onClick(View view) {
        if(onClickListener != null){
            onClickListener.onClick(getAdapterPosition());
        }
    }
    public void bind(BeritaModel model) {
        tv_title.setText(model.getTitle());
        tv_date.setText(model.getDate());
        tv_desc.setText(model.getArtikel());
        Picasso.with(itemView.getContext()).load(model.getImage()).fit().into(imageView);
    }
}
