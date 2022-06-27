package huunhan.hn.com.cuahangthietbionline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import huunhan.hn.com.cuahangthietbionline.R;
import huunhan.hn.com.cuahangthietbionline.model.Sanpham;

public class LaptopAdapter extends BaseAdapter {
    ArrayList<Sanpham> arrayListLaptop;
    Context context;

    public LaptopAdapter(ArrayList<Sanpham> arrayListLaptop, Context context) {
        this.arrayListLaptop = arrayListLaptop;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListLaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListLaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView tvTenLaptop, tvGialaptop, tvMotalaptop;
        ImageView ivLaptop;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.dong_laptop, null);
            viewHolder.ivLaptop = convertView.findViewById(R.id.ivLaptop);
            viewHolder.tvTenLaptop = convertView.findViewById(R.id.tvTenLaptop);
            viewHolder.tvGialaptop = convertView.findViewById(R.id.tvGialaptop);
            viewHolder.tvMotalaptop = convertView.findViewById(R.id.tvMotalaptop);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.tvTenLaptop.setText(sanpham.getTensp());
        viewHolder.tvMotalaptop.setText(sanpham.getMotasp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGialaptop.setText(decimalFormat.format(sanpham.getGiasp()));
        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.ivLaptop);

        return convertView;
    }
}
