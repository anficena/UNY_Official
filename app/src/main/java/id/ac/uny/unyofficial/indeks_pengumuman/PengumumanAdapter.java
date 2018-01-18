package id.ac.uny.unyofficial.indeks_pengumuman;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.ac.uny.unyofficial.R;

/**
 * Created by WIN 8 on 07/10/2017.
 */

public class PengumumanAdapter extends RecyclerView.Adapter<PengumumanHolder> {
    private OnClickListener onClickListener;
    public List<PengumumanModel>models;

    public PengumumanAdapter(List<PengumumanModel> models) {
        this.models = models;
    }

    public void setModels(List<PengumumanModel> models) {
        this.models = models;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    @Override
    public PengumumanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pengumuman, parent, false);
        PengumumanHolder holder = new PengumumanHolder(v,onClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(PengumumanHolder holder, int position) {
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
