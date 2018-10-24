package allinhand.example.stockandproduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import allinhand.example.cosmeticmanager.AuthorityChange;
import allinhand.example.cosmeticmanager.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ProductUpdateActivity extends Activity {
	private EditText edCommID = null;
	private EditText edName = null;
	private EditText edSafetyStock = null;
	private EditText edMaxStock = null;
	private Spinner spCommSupliername = null;
	private String Supliername;
	private String values;
	private String url;
	private List<String> list = new ArrayList<String>();
	private List<String> li = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_updata_commodity);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		edCommID = (EditText) findViewById(R.id.EdCommID);
		edName = (EditText) findViewById(R.id.EdCommName);
		edSafetyStock = (EditText) findViewById(R.id.EdCommSafetyStock);
		edMaxStock = (EditText) findViewById(R.id.EdCommMaxStock);
		spCommSupliername = (Spinner) findViewById(R.id.CommSpinner);
		getSupplierName();
		Intent it = getIntent();
		values = it.getStringExtra("value");
		edCommID.setEnabled(false);
		edCommID.setFocusable(false);
		if (values.equals("1")) {
			
			String id = it.getStringExtra("id");
			String name = it.getStringExtra("name");
			String wher = it.getStringExtra("supplier");
			// li.add(it.getStringExtra("supplier"));
			// ArrayAdapter<String> supplier = new ArrayAdapter<String>(this,
			// android.R.layout.simple_list_item_1, li);
			li = null;
			String max = it.getStringExtra("max");
			String min = it.getStringExtra("min");
			edName.setText(name);
			edSafetyStock.setText(min);
			edMaxStock.setText(max);
			edCommID.setText(id);
			spCommSupliername.setSelection(supMap.get(wher), true);
		} else if (values.equals("2")) {
			autoProductId();
		}
	}

	public void btnback(View view) {
		Intent intent = new Intent(ProductUpdateActivity.this,
				ProductActivity.class);
		startActivity(intent);
	}

	
	public void CommClear(View v) {
		edName.setText(null);
		edSafetyStock.setText(null);
		edMaxStock.setText(null);
		getSupplierName();
		edCommID.setText(null);
	}
	//提交按钮点击事件
	public void CommGood(View view) {
		if (!edCommID.getText().toString().trim().equals("")
				&& !edName.getText().toString().trim().equals("")
				&& !edSafetyStock.getText().toString().trim().equals("")
				&& !edMaxStock.getText().toString().trim().equals("")) {
			if ((Integer.parseInt(edSafetyStock.getText().toString().trim()) >= (Integer
					.parseInt(edMaxStock.getText().toString().trim())))) {
				Toast.makeText(getApplicationContext(), "安全库存应小于最大库存！",
						Toast.LENGTH_SHORT).show();
			} else {
				select();
				if (values.equals("1")) {
					UpdateProduct();
				} else {
					AddProduct();
				}
			}
		} else {
			Toast.makeText(getApplicationContext(), "以上信息不能为空！",
					Toast.LENGTH_SHORT).show();
		}
	}

	// 修改商品信息
	private void UpdateProduct() {
		url = "http://10.0.2.2:8080/CosmeticService/UpdateProduct.do";
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("ProductID", edCommID.getText().toString().trim());
			map.put("ProductName", edName.getText().toString().trim());
			map.put("MinSafeStock", edSafetyStock.getText().toString().trim());
			map.put("MaxSafeStock", edMaxStock.getText().toString().trim());
			map.put("SupplierID", Supliername);

			JSONObject jo = new JSONObject(map);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("UpdatePro_json", jo.toString()));
			post.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse hr = client.execute(post);
			if (hr.getStatusLine().getStatusCode() == 200) {
				String str = EntityUtils.toString(hr.getEntity());
				if (str.trim().equals("success")) {
					Toast.makeText(getApplicationContext(), "商品修改成功",
							Toast.LENGTH_SHORT).show();
					Supliername = null;
					Intent in = new Intent(ProductUpdateActivity.this,
							ProductActivity.class);
					startActivity(in);
				} else {
					Toast.makeText(getApplicationContext(), "商品修改失败！",
							Toast.LENGTH_SHORT).show();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Map<String, Integer> supMap = new HashMap<String, Integer>();

	// 查询供应商名称
	private void getSupplierName() {
		// TODO Auto-generated method stub
		url = "http://10.0.2.2:8080/CosmeticService/SelectAllSupplier.do";
		// HttpGet连接对象，设置客户端提交方式
		HttpGet httpGet = new HttpGet(url);
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		try {
			// 获取HttpResponse对象
			HttpResponse response = client.execute(httpGet);
			// 如果数据正确从服务器返回
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String nameandid = EntityUtils.toString(response.getEntity());
				JSONArray jsonArray = new JSONArray(nameandid);
				int j = 0;
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);
					list.add(String.valueOf(jo.optString("suppliername")));
					supMap.put(jo.optString("suppliername"), i);
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, list);
				spCommSupliername.setAdapter(adapter);

				list = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 添加新商品
	private void AddProduct() {
		url = "http://10.0.2.2:8080/CosmeticService/AddProduct.do";
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("ProductID", edCommID.getText().toString().trim());
			map.put("ProductName", edName.getText().toString().trim());
			map.put("MinSafeStock", edSafetyStock.getText().toString().trim());
			map.put("MaxSafeStock", edMaxStock.getText().toString().trim());
			map.put("SupplierID", Supliername);

			JSONObject jo = new JSONObject(map);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("addpro_json", jo.toString()));
			post.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse hr = client.execute(post);
			if (hr.getStatusLine().getStatusCode() == 200) {
				String str = EntityUtils.toString(hr.getEntity());
				if (str.trim().equals("success")) {
					Toast.makeText(getApplicationContext(), "商品添加成功",
							Toast.LENGTH_SHORT).show();
					Supliername = null;
					edName.setText(null);
					edSafetyStock.setText(null);
					edMaxStock.setText(null);
					getSupplierName();
					edCommID.setText(null);
					autoProductId();
				} else {
					Toast.makeText(getApplicationContext(), "商品添加失败！",
							Toast.LENGTH_SHORT).show();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void select() {
		String Suname = spCommSupliername.getSelectedItem().toString();
		JSONObject jo = new JSONObject();

		url = "http://10.0.2.2:8080/CosmeticService/SelectSupplierOneName.do";
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			jo.put("name", Suname);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("pc_json", jo.toString()));
			post.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse hr = client.execute(post);
			if (hr.getStatusLine().getStatusCode() == 200) {
				Supliername = EntityUtils.toString(hr.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void autoProductId(){
		String url = "http://10.0.2.2:8080/CosmeticService/getAutoProductId.do";
		// HttpGet连接对象，设置客户端提交方式
		HttpGet httpGet = new HttpGet(url);
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		try {
			// 获取HttpResponse对象
			HttpResponse response = client.execute(httpGet);
			// 如果数据正确从服务器返回
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String json = EntityUtils.toString(response.getEntity());
				String proid="";
				if (json.trim().equals("fail")) {
					proid="pro0001";
				}else {
					JSONArray jsonArray = new JSONArray(json);
					
					JSONObject jo = jsonArray.optJSONObject(0);
					
					int id =Integer.parseInt(jo.optString("productid").substring(3));
					id=id+1;
					proid=id+"";
					if (proid.length()==1) {
						proid="pro000"+proid;
					}
					if (proid.length()==2) {
						proid="pro00"+proid;
					}
					if (proid.length()==3) {
						proid="pro0"+proid;
					}
				}
				
				edCommID.setText(proid);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
