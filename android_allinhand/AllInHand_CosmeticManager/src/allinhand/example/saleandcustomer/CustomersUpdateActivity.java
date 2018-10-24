package allinhand.example.saleandcustomer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import allinhand.example.cosmeticmanager.AuthorityChange;
import allinhand.example.cosmeticmanager.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.Toast;

public class CustomersUpdateActivity extends Activity {
	private EditText edCusID = null;
	private EditText edName = null;
	private EditText edPhone = null;
	private EditText edAddres = null;
	private TableRow tablerow = null;
	private String values;
	private String url;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_iucustomers);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		edCusID = (EditText) findViewById(R.id.EDCusID);
		edName = (EditText) findViewById(R.id.EDCusName);
		edPhone = (EditText) findViewById(R.id.EDCusPhone);
		edAddres = (EditText) findViewById(R.id.EDCusAddress);
		tablerow = (TableRow) findViewById(R.id.fristTableRow);
		Intent it = getIntent();
		values = it.getStringExtra("value");
		if (values.equals("1")) {
			edCusID.setEnabled(false);
			edCusID.setFocusable(false);
			String id = it.getStringExtra("customerid");
			String name = it.getStringExtra("customername");
			String phone = it.getStringExtra("telephone");
			String Address = it.getStringExtra("customeraddress");
			edCusID.setText(id);
			edName.setText(name);
			edPhone.setText(phone);
			edAddres.setText(Address);
		} else if (values.equals("2")) {
			edCusID.setEnabled(true);
			tablerow.setVisibility(View.GONE);
		}
	}

	public void btnback(View view) {
		Intent intent = new Intent(CustomersUpdateActivity.this,
				CustomersActivity.class);
		startActivity(intent);
	}

	public void CusClear(View v) {
		edAddres.setText(null);
		edPhone.setText(null);
		edName.setText(null);
	}
	//提交按钮点击事件
	public void CommGood(View view) {

		if (!edName.getText().toString().trim().equals("")
				&& !edPhone.getText().toString().trim().equals("")
				&& !edAddres.getText().toString().trim().equals("")) {
			if (values.equals("1")) {
				UpdateCustomer();
			} else if (values.equals("2")) {
				AddCcustomer();
			}
		} else {
			Toast.makeText(getApplicationContext(), "以上信息不能为空！",
					Toast.LENGTH_SHORT).show();
		}

	}

	// 添加客户资料
	public void AddCcustomer() {
		url = "http://10.0.2.2:8080/CosmeticService/AddCustomer.do";
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("customername", edName.getText().toString().trim());
			map.put("telephone", edPhone.getText().toString().trim());
			map.put("customeraddress", edAddres.getText().toString().trim());
			JSONObject jo = new JSONObject(map);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("addcus_json", jo.toString()));
			post.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse hr = client.execute(post);
			if (hr.getStatusLine().getStatusCode() == 200) {
				String str = EntityUtils.toString(hr.getEntity());
				if (str.trim().equals("success")) {
					Toast.makeText(getApplicationContext(), "添加客户成功！",
							Toast.LENGTH_SHORT).show();
					edName.setText(null);
					edAddres.setText(null);
					edPhone.setText(null);
				} else {
					Toast.makeText(getApplicationContext(), "添加客户失败！",
							Toast.LENGTH_SHORT).show();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 修改客户资料
	public void UpdateCustomer() {
		url = "http://10.0.2.2:8080/CosmeticService/UpdateCustomer.do";
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("customerid", edCusID.getText().toString().trim());
			map.put("customername", edName.getText().toString().trim());
			map.put("telephone", edPhone.getText().toString().trim());
			map.put("customeraddress", edAddres.getText().toString().trim());

			JSONObject jo = new JSONObject(map);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("Updatecus_json", jo.toString()));
			post.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse hr = client.execute(post);
			if (hr.getStatusLine().getStatusCode() == 200) {
				String str = EntityUtils.toString(hr.getEntity());
				if (str.trim().equals("success")) {
					Toast.makeText(getApplicationContext(), "客户修改成功",
							Toast.LENGTH_SHORT).show();
					Intent in = new Intent(CustomersUpdateActivity.this,
							CustomersActivity.class);
					startActivity(in);
				} else {
					Toast.makeText(getApplicationContext(), "客户修改失败！",
							Toast.LENGTH_SHORT).show();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
