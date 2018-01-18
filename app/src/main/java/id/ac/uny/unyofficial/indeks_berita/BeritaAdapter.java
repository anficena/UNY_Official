package id.ac.uny.unyofficial.indeks_berita;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.ac.uny.unyofficial.R;
import id.ac.uny.unyofficial.indeks_pengumuman.PengumumanAdapter;
import id.ac.uny.unyofficial.indeks_pengumuman.PengumumanHolder;
import id.ac.uny.unyofficial.indeks_pengumuman.PengumumanModel;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaHolder> {
    public List<BeritaModel> models;
    private OnClickListener onClickListener;

    public BeritaAdapter(List<BeritaModel> models) {
        this.models = models;
    }

    public void setModels(List<BeritaModel> models) {
        this.models = models;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public BeritaHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_berita,parent,false);
        BeritaHolder holder = new BeritaHolder(v,onClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(BeritaHolder holder, int position) {
        holder.bind(models.get(position));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }


    public interface OnClickListener{
        void onClick(int adapterPosition);
    }
}
