package allinhand.example.purchaseandsupplier;

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
import allinhand.example.cosmeticmanager.MainActivity;
import allinhand.example.cosmeticmanager.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SupplierActivity extends Activity {

	
	private ListView listView = null;
	private Button btn = null;
	private AutoCompleteTextView act;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	private List<String> actList;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.supplier);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		// 取得ListView组件
		listView = (ListView) findViewById(R.id.list_supplier);
		btn = (Button) findViewById(R.id.btn_newsupplier);
		act = (AutoCompleteTextView) findViewById(R.id.act_query);
		// 获取供应商所有信息
		getAllSupplier();
		
		//绑定Auto……
		getSupplierName();
		
		//监听新建供应商按钮
		this.btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SupplierActivity.this,
						AddSupplierActivity.class);
				intent.putExtra("value", "2");
				startActivity(intent);

			}
		});
		
		// 监听自动完成文本框
		act.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (act.getText().toString().equals("")) { //如果auto为空的
					
					getAllSupplier();
				} else {
					
					getSupplierByName();
				}

			}
		});

		// 监听listview点击事件
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String id = list.get(arg2).get("a");
				String name = list.get(arg2).get("b");
				String telephone = list.get(arg2).get("c");
				String address = list.get(arg2).get("d");
				Intent intent = new Intent(SupplierActivity.this,
						AddSupplierActivity.class);
				intent.putExtra("value", "1");
				intent.putExtra("id", id);
				intent.putExtra("name", name);
				intent.putExtra("telephone", telephone);
				intent.putExtra("address", address);

				startActivity(intent);

			}
		});

		// 监听listview长按事件
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				// 得到供应商编号
				final String supid = list.get(arg2).get("a");
				Dialog dialog = new AlertDialog.Builder(SupplierActivity.this)
						.setTitle("是否删除该条供应商信息？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										try {
											String uri = "http://10.0.2.2:8080/CosmeticService/deleteSupplier.do?supid="
													+ supid;
											HttpGet hg = new HttpGet(uri);

											HttpClient hc = new DefaultHttpClient();

											HttpResponse hr = hc.execute(hg);

											if (hr.getStatusLine()
													.getStatusCode() == 200) {

												String str = EntityUtils
														.toString(hr
																.getEntity());

												if (str.trim()
														.equals("SUCCESS")) {
													Toast.makeText(
															getApplicationContext(),
															"删除成功",
															Toast.LENGTH_SHORT)
															.show();

													Intent intent=new Intent(SupplierActivity.this,SupplierActivity.class);
													startActivity(intent);

												}else if(str.trim()
														.equals("notdelete")){
													Toast.makeText(
															getApplicationContext(),
															"不能删除，此供应商数据被引用 ",
															Toast.LENGTH_SHORT)
															.show();
												}
													else {
															Toast.makeText(
															getApplicationContext(),
															"删除失败",
															Toast.LENGTH_SHORT)
															.show();
												}
											}
										} catch (Exception e) {
											// TODO: handle exception
											e.printStackTrace();
										}

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								}).create();
				dialog.show();
				return false;
			}
		});

		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	

	// 获取供应商所有信息
	private void getAllSupplier() {
		list = new ArrayList<Map<String, String>>();
		String url = "http://10.0.2.2:8080/CosmeticService/SelectAllSupplier.do";
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
				JSONArray jsonArray = new JSONArray(json);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);
					Map<String, String> map = new HashMap<String, String>(); // 定义Map集合保存数据
					map.put("a", String.valueOf(jo.optString("supplierid")));
					map.put("b", String.valueOf(jo.optString("suppliername")));
					map.put("c", String.valueOf(jo.optString("telephone")));
					map.put("d", String.valueOf(jo.optString("companyaddress")));

					this.list.add(map); // 保存所有的数据行

				}

				SimpleAdapter adapter = new SimpleAdapter(this, this.list,
						R.layout.supplier_list, new String[] { "a", "b", "c",
								"d" }// Map中的Key的名称
						, new int[] { R.id.list_num, R.id.list_name,
								R.id.list_phone, R.id.list_address }); // supplier_list中定义组件的ID
				listView.setAdapter(adapter); // 为ListView组件设置内容

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//根据供应商名称获取供应商信息
	
	public void getSupplierByName(){
		list = new ArrayList<Map<String,String>>();
		String supname = act.getText().toString().trim();
		
		String url = "http://10.0.2.2:8080/CosmeticService/getSupplierByName.do";
		//HttpPost 连接对象，设置客户端提交方式
		HttpPost httpPost = new HttpPost(url);
		//取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("supname", supname));
		
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(param,"utf-8"));
			// 获取HttpResponse对象
			HttpResponse response = client.execute(httpPost);
			// 如果数据正确从服务器返回
			if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				String json = EntityUtils.toString(response.getEntity());
				
				JSONArray jsonArray = new JSONArray(json);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);
					Map<String, String> map = new HashMap<String, String>(); // 定义Map集合保存数据
					map.put("a", String.valueOf(jo.optString("supplierid")));
					map.put("b", String.valueOf(jo.optString("suppliername")));
					map.put("c", String.valueOf(jo.optString("telephone")));
					map.put("d", String.valueOf(jo.optString("companyaddress")));

					this.list.add(map); // 保存所有的数据行
				
			}
				SimpleAdapter adapter = new SimpleAdapter(this,this.list,
						R.layout.supplier_list,
						new String[]{"a","b","c","d"},// Map中的Key的名称
						new int[] {R.id.list_num, R.id.list_name,  // supplier_list中定义组件的ID
								R.id.list_phone, R.id.list_address });
				listView.setAdapter(adapter);// 为ListView组件设置内容
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
			
	}

	//获取所有供应商名称
	public void getSupplierName(){
		
		actList = new ArrayList<String>();
		String url = "http://10.0.2.2:8080/CosmeticService/SelectAllSupplier.do";
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
				JSONArray jsonArray = new JSONArray(json);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);
				
					actList.add(String.valueOf(jo.optString("suppliername"))) ;
					

				}

				
				

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,actList);
		act.setAdapter(aa);
	}
	
	public void back(View view) {
		Intent intent=new Intent(SupplierActivity.this,MainActivity.class);
		startActivity(intent);
	}
	
}
