package huunhan.hn.com.cuahangthietbionline.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

import huunhan.hn.com.cuahangthietbionline.R;
import huunhan.hn.com.cuahangthietbionline.ultil.CheckConnection;
import huunhan.hn.com.cuahangthietbionline.ultil.Server;

public class ClientInfoActivity extends AppCompatActivity {

    EditText edtTenkhachhang, edtSDT, edtEmail;
    Button btXacnhan, btReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);

        anhXa();
        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            EventButton();
        } else {
            Toast.makeText(this, "Kiem tra lai ket noi mang", Toast.LENGTH_SHORT).show();
        }
    }

    private void EventButton() {
        btXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = edtTenkhachhang.getText().toString().trim();
                final String sdt = edtSDT.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                if (ten.length() >0 && email.length() >0 && sdt.length() >0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.DuongdanDonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String madonhang) {
                            Log.d("madonhang", madonhang);
                            if (Integer.parseInt(madonhang) > 0) {
                                RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.DuongdanChitietDonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")) {
                                            MainActivity.mangGiohang.clear();
                                            Toast.makeText(ClientInfoActivity.this, "Ban da them du lieu gio hang thanh cong", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(ClientInfoActivity.this, "Moi ban tiep tuc mua hang", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(ClientInfoActivity.this, "Du lieu gio hang ban da bi loi", Toast.LENGTH_SHORT).show();
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
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i=0; i<MainActivity.mangGiohang.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("giasanpham",MainActivity.mangGiohang.get(i).getGiasp());
                                                jsonObject.put("masanpham",MainActivity.mangGiohang.get(i).getIdsp());
                                                jsonObject.put("soluongsanpham",MainActivity.mangGiohang.get(i).getSoluongsp());
                                                jsonObject.put("tensanpham",MainActivity.mangGiohang.get(i).getTensp());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String,String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                requestQueue1.add(stringRequest1);
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
                            HashMap<String, String> params = new HashMap<String,String>();
                            params.put("tenkhachhang", ten);
                            params.put("sodienthoai", sdt);
                            params.put("email", email);
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(ClientInfoActivity.this, "Vui long kiem tra lai thong tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void anhXa() {
        edtTenkhachhang = findViewById(R.id.edtTenClient);
        edtEmail = findViewById(R.id.edtEmailClient);
        edtSDT = findViewById(R.id.edtPhoneClient);
        btXacnhan = findViewById(R.id.btXacnhan);
        btReturn = findViewById(R.id.btReturn);
    }
}