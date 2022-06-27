package huunhan.hn.com.cuahangthietbionline.adapter;

import android.content.Context;
import android.text.TextUtils;
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

public class DienthoaiAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sanpham> arrayListDienThoai;

    public DienthoaiAdapter(Context context, ArrayList<Sanpham> arrayListDienThoai) {
        this.context = context;
        this.arrayListDienThoai = arrayListDienThoai;
    }

    @Override
    public int getCount() {
        return arrayListDienThoai.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListDienThoai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView tvTenDienThoai, tvGiaDienThoai, tvMotaDienThoai;
        ImageView ivDienThoai;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_dienthoai, null);
            viewHolder.tvGiaDienThoai = convertView.findViewById(R.id.tvGiadienthoai);
            viewHolder.tvTenDienThoai = convertView.findViewById(R.id.tvDienThoai);
            viewHolder.tvMotaDienThoai = convertView.findViewById(R.id.tvMotadienthoai);
            viewHolder.ivDienThoai = convertView.findViewById(R.id.ivDienthoai);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);

        viewHolder.tvMotaDienThoai.setText(sanpham.getMotasp());
        viewHolder.tvMotaDienThoai.setMaxLines(2);
        viewHolder.tvMotaDienThoai.setEllipsize(TextUtils.TruncateAt.END);

        viewHolder.tvTenDienThoai.setText(sanpham.getTensp());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiaDienThoai.setText("Gia: " + decimalFormat.format(sanpham.getGiasp()) + " Dong");

        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.ivDienThoai);
        return convertView;
    }
}
