package huunhan.hn.com.cuahangthietbionline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import huunhan.hn.com.cuahangthietbionline.R;
import huunhan.hn.com.cuahangthietbionline.activity.ChiTietActivity;
import huunhan.hn.com.cuahangthietbionline.model.Sanpham;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ViewHolder> {

    Context context;
    ArrayList<Sanpham> arrayListSanpham;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arrayListSanpham) {
        this.context = context;
        this.arrayListSanpham = arrayListSanpham;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sanpham sanpham = arrayListSanpham.get(position);
        holder.tvTensanpham.setText(sanpham.getTensp());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvgiasanpham.setText("Gia: " + decimalFormat.format(sanpham.getGiasp()) + " Dong");

        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.ivHinhsanpham);
    }

    @Override
    public int getItemCount() {
        return arrayListSanpham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivHinhsanpham;
        public TextView tvTensanpham, tvgiasanpham;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinhsanpham = itemView.findViewById(R.id.ivSanpham);
            tvTensanpham = itemView.findViewById(R.id.tvTensanpham);
            tvgiasanpham = itemView.findViewById(R.id.tvgiasp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("thongtinsanpham", arrayListSanpham.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
