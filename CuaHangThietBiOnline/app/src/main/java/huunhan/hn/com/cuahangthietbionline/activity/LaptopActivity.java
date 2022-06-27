package huunhan.hn.com.cuahangthietbionline.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import huunhan.hn.com.cuahangthietbionline.R;
import huunhan.hn.com.cuahangthietbionline.adapter.LaptopAdapter;
import huunhan.hn.com.cuahangthietbionline.model.Sanpham;
import huunhan.hn.com.cuahangthietbionline.ultil.CheckConnection;
import huunhan.hn.com.cuahangthietbionline.ultil.Server;

public class LaptopActivity extends AppCompatActivity {

    Toolbar toolbarLaptop;
    ListView listViewLaptop;
    ArrayList<Sanpham> mangLaptop;
    LaptopAdapter laptopAdapter;
    int idLaptop = 0;
    int page = 1;
    View footerView;
    boolean isLoading = false;
    boolean limitData = false;
    MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);

        anhXa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionToolbar();
            GetIDLoaiSp();
            GetData(page);
            LoadMoredata();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_giohang, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_giohang) {
            Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadMoredata() {
        listViewLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChiTietActivity.class);
                intent.putExtra("thongtinsanpham", mangLaptop.get(position));
                startActivity(intent);
            }
        });
        listViewLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitData == false) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void GetData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.DuongdanDienthoai + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String TenLaptop = "";
                int GiaLaptop = 0;
                String HinhanhLP = "";
                String MotaLP = "";
                int Idsplt = 0;
                if (response != null && response.length() != 2) {
                    listViewLaptop.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0; i< jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            TenLaptop = jsonObject.getString("tensp");
                            GiaLaptop = jsonObject.getInt("giasp");
                            HinhanhLP = jsonObject.getString("hinhanhsp");
                            MotaLP = jsonObject.getString("motasp");
                            Idsplt = jsonObject.getInt("idsanpham");
                            mangLaptop.add(new Sanpham(id,TenLaptop,GiaLaptop,HinhanhLP,MotaLP,Idsplt));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    laptopAdapter.notifyDataSetChanged();
                } else {
                    limitData = true;
                    listViewLaptop.removeFooterView(footerView);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<String,String>();
                params.put("idsanpham", String.valueOf(idLaptop));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetIDLoaiSp() {
        idLaptop = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giatriloaisanpham", idLaptop + "");

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolbarLaptop = findViewById(R.id.toolbar_laptop);
        listViewLaptop = findViewById(R.id.listview_laptop);
        mangLaptop = new ArrayList<>();
        laptopAdapter = new LaptopAdapter(mangLaptop, getApplicationContext());
        listViewLaptop.setAdapter(laptopAdapter);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        footerView = inflater.inflate(R.layout.progressbar, null);
        myHandler = new MyHandler();
    }
    private class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    listViewLaptop.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private class ThreadData extends Thread {
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }
}