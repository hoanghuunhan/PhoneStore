package huunhan.hn.com.cuahangthietbionline.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import huunhan.hn.com.cuahangthietbionline.R;
import huunhan.hn.com.cuahangthietbionline.model.Giohang;
import huunhan.hn.com.cuahangthietbionline.model.Sanpham;
import huunhan.hn.com.cuahangthietbionline.ultil.CheckConnection;

public class ChiTietActivity extends AppCompatActivity {

    Toolbar toolbarChitiet;
    TextView tvTensp, tvMotasp, tvGiasp;
    ImageView ivHinhanhsp;
    Button btDatmua;
    Spinner spinner;

    int id =0;
    String tenChitiet = "";
    int giaChitiet = 0;
    String hinhanhChitiet = "";
    String motaChitiet = "";
    int idsanpham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        anhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionToolbar();
            Getinformation();
            CatchEventSpinner();
            EventButton();
        }
    }

    private void EventButton() {
        btDatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangGiohang.size() >0) {
                    int sl= Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    for (int i=0; i<MainActivity.mangGiohang.size() ; i++) {
                        if (MainActivity.mangGiohang.get(i).getIdsp() == id) {
                            MainActivity.mangGiohang.get(i).setSoluongsp(MainActivity.mangGiohang.get(i).getSoluongsp() + sl);
                            if (MainActivity.mangGiohang.get(i).getSoluongsp() >= 10) {
                                MainActivity.mangGiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.mangGiohang.get(i).setGiasp(giaChitiet * MainActivity.mangGiohang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if (exists == false) {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi = soluong * giaChitiet;
                        MainActivity.mangGiohang.add(new Giohang(id,tenChitiet,giamoi,hinhanhChitiet,soluong));
                    }

                } else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi = soluong * giaChitiet;
                    MainActivity.mangGiohang.add(new Giohang(id,tenChitiet,giamoi,hinhanhChitiet,soluong));
                }
                Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[] {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(adapter);

    }

    private void Getinformation() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getId();
        tenChitiet = sanpham.getTensp();
        giaChitiet = sanpham.getGiasp();
        hinhanhChitiet = sanpham.getHinhanhsp();
        motaChitiet = sanpham.getMotasp();
        idsanpham = sanpham.getIdsanpham();

        tvTensp.setText(tenChitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGiasp.setText(decimalFormat.format(giaChitiet));
        tvMotasp.setText(motaChitiet);
        Picasso.with(getApplicationContext()).load(hinhanhChitiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(ivHinhanhsp);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolbarChitiet = findViewById(R.id.toolbarChitietsp);
        tvGiasp = findViewById(R.id.tvgiachitietsp);
        tvMotasp = findViewById(R.id.tvMotachitietsp);
        tvTensp = findViewById(R.id.tvtenchitietsp);
        ivHinhanhsp = findViewById(R.id.ivChitietsp);
        btDatmua = findViewById(R.id.btDatmua);
        spinner = findViewById(R.id.spinner);
    }
}