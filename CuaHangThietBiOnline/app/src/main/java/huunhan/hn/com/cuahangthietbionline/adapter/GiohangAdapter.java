package huunhan.hn.com.cuahangthietbionline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import huunhan.hn.com.cuahangthietbionline.R;
import huunhan.hn.com.cuahangthietbionline.activity.GiohangActivity;
import huunhan.hn.com.cuahangthietbionline.activity.MainActivity;
import huunhan.hn.com.cuahangthietbionline.model.Giohang;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arrayListGiohang;

    public GiohangAdapter(Context context, ArrayList<Giohang> arrayListGiohang) {
        this.context = context;
        this.arrayListGiohang = arrayListGiohang;
    }

    @Override
    public int getCount() {
        return arrayListGiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListGiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private  class Viewholder{
        TextView tvTengiohang, tvGiagiohang;
        ImageView ivGiohang;
        Button btMinus, btPlus, btValues;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder = null;
        if (convertView == null) {
            viewholder = new Viewholder();
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.dong_giohang, null);
            viewholder.tvTengiohang = convertView.findViewById(R.id.tvTengiohang);
            viewholder.tvGiagiohang = convertView.findViewById(R.id.tvGiagiohang);
            viewholder.ivGiohang = convertView.findViewById(R.id.ivGiohang);
            viewholder.btMinus = convertView.findViewById(R.id.btMinus);
            viewholder.btPlus = convertView.findViewById(R.id.btPlus);
            viewholder.btValues = convertView.findViewById(R.id.btValues);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewholder.tvTengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewholder.tvGiagiohang.setText(decimalFormat.format(giohang.getGiasp()) + " Đ");
        Picasso.with(context).load(giohang.getHinhanhsp()).placeholder(R.drawable.noimage)
                .error(R.drawable.error).into(viewholder.ivGiohang);
        viewholder.btValues.setText(giohang.getSoluongsp() + "");
        int sl = Integer.parseInt(viewholder.btValues.getText().toString());
        if (sl>= 10) {
            viewholder.btPlus.setVisibility(View.INVISIBLE);
            viewholder.btMinus.setVisibility(View.VISIBLE);
        } else if (sl <= 1) {
            viewholder.btMinus.setVisibility(View.INVISIBLE);
        } else if (sl >= 1) {
            viewholder.btPlus.setVisibility(View.VISIBLE);
            viewholder.btMinus.setVisibility(View.VISIBLE);
        }

        Viewholder finalViewholder = viewholder;
        viewholder.btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewholder.btValues.getText().toString()) + 1;
                int slht = MainActivity.mangGiohang.get(position).getSoluongsp();
                long giaht = MainActivity.mangGiohang.get(position).getGiasp();
                MainActivity.mangGiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.mangGiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewholder.tvGiagiohang.setText(decimalFormat.format(giamoinhat) + " Đ");
                GiohangActivity.EventUltil();
                if (slmoinhat > 9) {
                    finalViewholder.btPlus.setVisibility(View.INVISIBLE);
                    finalViewholder.btMinus.setVisibility(View.VISIBLE);
                    finalViewholder.btValues.setText(String.valueOf(slmoinhat));
                } else {
                    finalViewholder.btPlus.setVisibility(View.VISIBLE);
                    finalViewholder.btMinus.setVisibility(View.VISIBLE);
                    finalViewholder.btValues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewholder.btMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(finalViewholder.btValues.getText().toString()) - 1;
                int slht = MainActivity.mangGiohang.get(position).getSoluongsp();
                long giaht = MainActivity.mangGiohang.get(position).getGiasp();
                MainActivity.mangGiohang.get(position).setSoluongsp(slmoinhat);
                long giamoinhat = (giaht * slmoinhat) / slht;
                MainActivity.mangGiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewholder.tvGiagiohang.setText(decimalFormat.format(giamoinhat) + " Đ");
                GiohangActivity.EventUltil();
                if (slmoinhat < 2) {
                    finalViewholder.btPlus.setVisibility(View.VISIBLE);
                    finalViewholder.btMinus.setVisibility(View.INVISIBLE);
                    finalViewholder.btValues.setText(String.valueOf(slmoinhat));
                } else {
                    finalViewholder.btPlus.setVisibility(View.VISIBLE);
                    finalViewholder.btMinus.setVisibility(View.VISIBLE);
                    finalViewholder.btValues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        return convertView;
    }
}
