package id.ac.uny.unyofficial.indeks_pengumuman;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import id.ac.uny.unyofficial.R;

/**
 * Created by WIN 8 on 10/10/2017.
 */

public class PengumumanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tv_title,tv_date;
    public PengumumanAdapter.OnClickListener onClickListener;

    public PengumumanHolder(View itemView,PengumumanAdapter.OnClickListener onClickListener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.onClickListener = onClickListener;
        tv_title =itemView.findViewById(R.id.title);
        tv_date = itemView.findViewById(R.id.date);
    }

    @Override
    public void onClick(View view) {
        if(onClickListener != null){
            onClickListener.onClick(getAdapterPosition());
        }
    }
    public void bind(PengumumanModel model) {
        tv_title.setText(model.getTitle());
        tv_date.setText(model.getDate());
    }

}
