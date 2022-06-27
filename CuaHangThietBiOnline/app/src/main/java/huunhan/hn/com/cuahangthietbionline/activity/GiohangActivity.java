package huunhan.hn.com.cuahangthietbionline.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import huunhan.hn.com.cuahangthietbionline.R;
import huunhan.hn.com.cuahangthietbionline.adapter.GiohangAdapter;
import huunhan.hn.com.cuahangthietbionline.model.Giohang;

public class GiohangActivity extends AppCompatActivity {

    ListView listViewGiohang;
    Toolbar toolbarGiohang;
    TextView tvThongbao;
    static TextView tvTongtien;
    Button btThanhtoan, btTieptucmuahang;
    GiohangAdapter giohangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);

        anhXa();
        ActionToolbar();
        CheckData();
        EventUltil();
        CatOnItemListview();
        EventButton();
    }

    private void EventButton() {
        btTieptucmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mangGiohang.size() >0) {
                    Intent intent = new Intent(getApplicationContext(), ClientInfoActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(GiohangActivity.this, "Gio hang cua ban chua co san pham nao", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void CatOnItemListview() {
        listViewGiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("Xac nhan xoa san pham")
                        .setMessage("Ban co chac muon xoa san pham nay?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.mangGiohang.size() <= 0) {
                            tvThongbao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.mangGiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if (MainActivity.mangGiohang.size() <= 0) {
                                tvThongbao.setVisibility(View.VISIBLE);
                            } else {
                                tvThongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangAdapter.notifyDataSetChanged();
                        EventUltil();
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
    }

    public static void EventUltil() {
        long tongTien = 0;
        for (int i=0; i<MainActivity.mangGiohang.size(); i++) {
            tongTien += MainActivity.mangGiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongtien.setText(decimalFormat.format(tongTien) + " Đ");
    }

    private void CheckData() {
        if (MainActivity.mangGiohang.size() <= 0) {
            giohangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.VISIBLE);
            listViewGiohang.setVisibility(View.INVISIBLE);
        } else {
            giohangAdapter.notifyDataSetChanged();
            tvThongbao.setVisibility(View.INVISIBLE);
            listViewGiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarGiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        listViewGiohang = findViewById(R.id.listview_giohang);
        toolbarGiohang = findViewById(R.id.toolbar_giohang);
        tvThongbao = findViewById(R.id.tvThongbao);
        tvTongtien = findViewById(R.id.tvTongtien);
        btThanhtoan = findViewById(R.id.btThanhtoan);
        btTieptucmuahang = findViewById(R.id.btTieptucmuahang);
        giohangAdapter = new GiohangAdapter(getApplicationContext(), MainActivity.mangGiohang);
        listViewGiohang.setAdapter(giohangAdapter);
    }
}