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
import org.json.JSONException;
import org.json.JSONObject;

import allinhand.example.cosmeticmanager.AuthorityChange;
import allinhand.example.cosmeticmanager.MainActivity;
import allinhand.example.cosmeticmanager.R;
import allinhand.example.saleandcustomer.SalesDetailAddActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ProductActivity extends Activity {

	private ListView listview;
	Intent it = null;
	private AutoCompleteTextView EdiCommodity;
	LinearLayout linearlayout1;
	Button CommodityList;
	Button AddCommodity;
	ArrayList<Map<String, String>> dtlist = null;
	List items = new ArrayList();
	String salesman = "";
	String customer = "";
	String ddbh = "";
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>(); // ������ʾ�����ݰ�װ
	private SimpleAdapter simpleAdapter = null; // �������ݵ�ת������

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.commodity);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		it = getIntent();
		salesman = it.getStringExtra("salesman");
		customer = it.getStringExtra("customer");
		ddbh = it.getStringExtra("ddbh");

		listview = (ListView) findViewById(R.id.commodity_listview);
		EdiCommodity = (AutoCompleteTextView) findViewById(R.id.EdiCommodity);
		linearlayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
		CommodityList = (Button) findViewById(R.id.CommodityList);
		AddCommodity = (Button) findViewById(R.id.AddCommodity);

		// �ж��Ƿ�Ϊ������ת��ҳ��
		if (it.getStringExtra("title") != null) {
			// ����view
			linearlayout1.setVisibility(View.GONE);
			AddCommodity.setVisibility(View.GONE);
			CommodityList.setVisibility(View.GONE);
		} else {
			// ��ʾview
			linearlayout1.setVisibility(View.VISIBLE);
			AddCommodity.setVisibility(View.VISIBLE);
			CommodityList.setVisibility(View.VISIBLE);
		}
		getAllProduct();
		getProductNameOrId();
		getSupplierName();
		// ����AutoCompleteTextView
		EdiCommodity.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (EdiCommodity.getText().toString().equals("")) {
					getAllProduct();
				} else {
					getProductByNameID();
				}

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

		});

		// ��������¼�
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String id = list.get(arg2).get("e");
				String name = list.get(arg2).get("a");
				String suppname = list.get(arg2).get("d");
				String max = list.get(arg2).get("b");
				String min = list.get(arg2).get("c");
				String price = list.get(arg2).get("f");
				Intent intent = null;
				if (it.getStringExtra("title") != null) {
					// ���������

					// ��ȡ���ݣ���ֵ����ҳ��
					dtlist = new ArrayList<Map<String, String>>();
					String result = it.getStringExtra("dtlist");
					if (result != null && !result.equals("")) {
						try {
							JSONArray ja = new JSONArray(result);
							for (int x = 0; x < ja.length(); x++) {
								JSONObject jo = new JSONObject(ja.getString(x));
								Map<String, String> mp = new HashMap<String, String>(); // ����Map���ϣ�����ÿһ������
								mp.put("productID", jo.optString("productID"));
								mp.put("pdnum",
										String.valueOf(jo.optInt("pdnum")));
								mp.put("pdprice",
										String.valueOf(jo.optDouble("pdprice")));
								mp.put("pdamount", String.valueOf(jo
										.optDouble("pdamount")));
								dtlist.add(mp);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					intent = new Intent(ProductActivity.this,
							SalesDetailAddActivity.class);
					intent.putExtra("title", it.getStringExtra("title"));
					intent.putExtra("proid", id);
					intent.putExtra("proname", name);
					intent.putExtra("proprice", price);
					intent.putExtra("salesman", salesman);
					intent.putExtra("customer", customer);
					intent.putExtra("ddbh", ddbh);
					intent.putExtra("dtlist", new JSONArray(dtlist).toString());
				} else {
					intent = new Intent(ProductActivity.this,
							ProductUpdateActivity.class);
					intent.putExtra("value", "1");
					intent.putExtra("id", id);
					intent.putExtra("name", name);
					intent.putExtra("supplier", suppname);
					intent.putExtra("max", max);
					intent.putExtra("min", min);
				}

				startActivity(intent);
			}
		});
		// ���������¼�
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (it.getStringExtra("title") == null) {
					// ��ȡ��Ʒ���
					String empname = list.get(arg2).get("productname");
					final String proid = list.get(arg2).get("e");
					Dialog dialog = new AlertDialog.Builder(
							ProductActivity.this)
							.setTitle("�Ƿ�ɾ����")
							.setPositiveButton("ȷ��",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int which) {
											try {
												String uri = "http://10.0.2.2:8080/CosmeticService/deleteProduct.do?proid="
														+ proid;
												HttpGet hp = new HttpGet(uri);

												HttpClient hc = new DefaultHttpClient();

												HttpResponse hr = hc
														.execute(hp);
												if (hr.getStatusLine()
														.getStatusCode() == 200) {

													String str = EntityUtils.toString(hr
															.getEntity());

													if (str.trim().equals(
															"success")) {
														Toast.makeText(
																getApplicationContext(),
																"ɾ���ɹ�",
																Toast.LENGTH_SHORT)
																.show();
														Intent intent = new Intent(
																ProductActivity.this,
																ProductActivity.class);
														startActivity(intent);
													} else if (str
															.trim()
															.equals("notdelete")) {
														Toast.makeText(
																getApplicationContext(),
																"������ɾ��",
																Toast.LENGTH_SHORT)
																.show();
													} else {
														Toast.makeText(
																getApplicationContext(),
																"ɾ��ʧ��",
																Toast.LENGTH_SHORT)
																.show();
													}
												}
											} catch (Exception ex) {
												ex.printStackTrace();
											}
										}
									})
							.setNegativeButton("ȡ��",
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									}).create();
					dialog.show();
				}
				return false;
			}	
		});

	}

	// ��ȡ������Ʒ��Ϣ
	private void getAllProduct() {
		// TODO Auto-generated method stub
		list = new ArrayList<Map<String, String>>();
		String url = "http://10.0.2.2:8080/CosmeticService/SelectAllProduct.do";
		// HttpGet���Ӷ������ÿͻ����ύ��ʽ
		HttpGet httpGet = new HttpGet(url);
		// ȡ��HttpClient����
		HttpClient client = new DefaultHttpClient();
		try {
			// ��ȡHttpResponse����
			HttpResponse response = client.execute(httpGet);
			// ���������ȷ�ӷ���������
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String nameandid = EntityUtils.toString(response.getEntity());
				JSONArray jsonArray = new JSONArray(nameandid);
				int j = 0;
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);
					Map<String, String> map = new HashMap<String, String>();
					map.put("a", String.valueOf(jo.optString("productname")));
					map.put("b", String.valueOf(jo.optString("maxSafeStock")));
					map.put("c", String.valueOf(jo.optString("safeStock")));
					map.put("d", String.valueOf(jo.optString("suppliername")));
					map.put("e", String.valueOf(jo.optString("productid")));
					map.put("f", String.valueOf(jo.optDouble("productprice")));
					this.list.add(map);
				}

				SimpleAdapter adapter = new SimpleAdapter(this, this.list,
						R.layout.commodity_listview, new String[] { "a", "b",
								"c", "d", "e", "f" } // Map�е�key������
						, new int[] { R.id.CommName, R.id.CommMax,
								R.id.CommLittle, R.id.Commsupplier,
								R.id.CommID, R.id.Commprice });
				listview.setAdapter(adapter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��autotext����
	public void getProductNameOrId() {
		String url = "http://10.0.2.2:8080/CosmeticService/SelectAllProduct.do";
		// HttpGet���Ӷ������ÿͻ����ύ��ʽ
		HttpGet httpGet = new HttpGet(url);
		// ȡ��HttpClient����
		HttpClient client = new DefaultHttpClient();
		try {
			// ��ȡHttpResponse����
			HttpResponse response = client.execute(httpGet);
			// ���������ȷ�ӷ���������
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String nameandid = EntityUtils.toString(response.getEntity());
				JSONArray jsonArray = new JSONArray(nameandid);
				int j = 0;
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);

					items.add(j, String.valueOf(jo.optString("productname")));
					j++;
					items.add(j, String.valueOf(jo.optString("productid")));
					j++;

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		EdiCommodity.setAdapter(aa);
	}

	// ��ѯ��Ʒ��Ϣ ������Ʒ���ƻ��Ż�Ӧ��
	public void getProductByNameID() {
		list.clear();
		String nameorid = EdiCommodity.getText().toString();
		String url = "http://10.0.2.2:8080/CosmeticService/getProductByNameID.do";
		// HttpPost���Ӷ������ÿͻ����ύ��ʽ
		HttpPost httpPost = new HttpPost(url);
		// ȡ��HttpClient����
		HttpClient client = new DefaultHttpClient();
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("nameid", nameorid));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String losespillList = EntityUtils.toString(response
						.getEntity());
				JSONArray jsonArray = new JSONArray(losespillList);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);
					Map<String, String> items = new HashMap<String, String>();
					items.put("a", String.valueOf(jo.optString("productname")));
					items.put("b", String.valueOf(jo.optString("maxSafeStock")));
					items.put("c", String.valueOf(jo.optString("safeStock")));
					items.put("d", String.valueOf(jo.optString("suppliername")));
					items.put("e", String.valueOf(jo.optString("productid")));
					items.put("f", String.valueOf(jo.optDouble("productprice")));
					list.add(items);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		SimpleAdapter adapter = new SimpleAdapter(this, this.list,
				R.layout.commodity_listview, new String[] { "a", "b", "c", "d",
						"e", "f" } // Map�е�key������
				, new int[] { R.id.CommName, R.id.CommMax, R.id.CommLittle,
						R.id.Commsupplier, R.id.CommID, R.id.Commprice });
		listview.setAdapter(adapter);
	}

	// ��ѯ��Ӧ������
	private void getSupplierName() {
		// TODO Auto-generated method stub
		String url = "http://10.0.2.2:8080/CosmeticService/SelectAllSupplier.do";
		// HttpGet���Ӷ������ÿͻ����ύ��ʽ
		HttpGet httpGet = new HttpGet(url);
		// ȡ��HttpClient����
		HttpClient client = new DefaultHttpClient();
		try {
			// ��ȡHttpResponse����
			HttpResponse response = client.execute(httpGet);
			// ���������ȷ�ӷ���������
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String nameandid = EntityUtils.toString(response.getEntity());
				JSONArray jsonArray = new JSONArray(nameandid);
				int j = 0;
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);
					items.add(j, String.valueOf(jo.optString("suppliername")));
					j++;
				}
				ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, items);
				EdiCommodity.setAdapter(aa);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �����Ʒ��ť
	public void Addcommodity(View v) {
		Intent intent = new Intent(ProductActivity.this,
				ProductUpdateActivity.class);
		intent.putExtra("value", "2");
		startActivity(intent);
	}

	// ���ذ�ť
	public void btnback(View view) {
		Intent intent = new Intent();
		if (it.getStringExtra("title") != null) {
			intent = new Intent(ProductActivity.this,
					SalesDetailAddActivity.class);
			intent.putExtra("title", it.getStringExtra("title"));
			intent.putExtra("salesman", salesman);
			intent.putExtra("customer", customer);
			intent.putExtra("ddbh", ddbh);
		} else {
			intent = new Intent(ProductActivity.this, MainActivity.class);
		}
		startActivity(intent);
	}
}
