package allinhand.example.purchaseandsupplier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PurchaseRuActivity extends Activity {

	Intent intent;
	TextView title;
	ListView lv;
	ExpandableListView expandListView;
	Button xjdd;
	Button dd;
	AutoCompleteTextView autextview;

	ArrayList<Map<String, String>> allMaster = new ArrayList<Map<String, String>>();

	ArrayList<List<Map<String, String>>> allDetail = new ArrayList<List<Map<String, String>>>();
	ArrayList<String> list = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.purchaseru);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		title = (TextView) findViewById(R.id.title);
		xjdd = (Button) findViewById(R.id.xjdd);
		dd = (Button) findViewById(R.id.dd);
		lv = (ListView) findViewById(R.id.lv);
		autextview = (AutoCompleteTextView) findViewById(R.id.autext);
		expandListView = (ExpandableListView) findViewById(R.id.lv);
		intent = getIntent();
		title.setText(intent.getStringExtra("title"));

		purchase();
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1, list);
//		autextview.setAdapter(adapter);

		autextview.addTextChangedListener(new TextWatcher() {

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
				init(title.getText().toString());

			}
		});

		expandListView
				.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						// TODO Auto-generated method stub
						// 新建还是查看
						if (a == 1) {
							// 新建
							intent = new Intent(PurchaseRuActivity.this,
									PurchaseRu_addActivity.class);
							intent.putExtra("title", title.getText().toString());
							intent.putExtra("supplier",
									allMaster.get(groupPosition)
											.get("supplier"));
							intent.putExtra("purchaseid", "");
							intent.putExtra(
									"pruid",
									allMaster.get(groupPosition).get(
											"purchaseid"));
							startActivity(intent);
						} else {
							// 查看
							intent = new Intent(PurchaseRuActivity.this,
									PurchaseRu_addActivity.class);
							intent.putExtra("title", title.getText().toString());
							intent.putExtra(
									"purchaseid",
									allMaster.get(groupPosition).get(
											"purchaseid"));
							startActivity(intent);
						}

						return false;
					}
				});

	}

	// 订单清单
	public void order_list(View v) {
		a=0;
		purchase();
	}

	int a = 0;

	// 新建订单
	public void new_order(View v) {
		if (title.getText().equals("采购退货")) {
			dd.setText("退货清单");
			xjdd.setBackgroundResource(R.drawable.button_bg_normal_right);
			dd.setBackgroundResource(R.drawable.button_bg_defocused_left);
			init("采购入库");
			a = 1;
		} else {
			intent = new Intent(PurchaseRuActivity.this,
					PurchaseRu_addActivity.class);
			intent.putExtra("title", title.getText());
			intent.putExtra("purchaseid", "");
			startActivity(intent);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		autextview.setAdapter(adapter);
	}

	// 返回
	public void btnback(View view) {
		Intent intent = new Intent(PurchaseRuActivity.this,MainActivity.class);
		startActivity(intent);
		PurchaseRuActivity.this.finish();
	}


	// 判断是采购退货还是采购入库，更改按钮底图，为ListView填充不同的数据
	public void purchase() {
		if (title.getText().equals("采购退货")) {
			dd.setText("退货清单");
			xjdd.setBackgroundResource(R.drawable.button_bg_defocused_right);
			dd.setBackgroundResource(R.drawable.button_bg_normal_left);
		}
		// 填充ListView数据
		init(title.getText().toString());
	}

	// 为ExpandableListView填充数据
	public void insertListView() {
		ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return allDetail.get(groupPosition).get(childPosition);

			}

			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return childPosition;
			}

			public View getChildView(int groupPosition, int childPosition,
					boolean isLastChild, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				View v = convertView;
				if (v == null) {
					LayoutInflater inflater = getLayoutInflater();
					v = inflater.inflate(R.layout.list_content, parent, false);
				}
				LinearLayout ll = (LinearLayout) v.findViewById(R.id.ll);
				TextView tv1 = (TextView) v.findViewById(R.id.product);
				TextView tv2 = (TextView) v.findViewById(R.id.count);
				TextView tv3 = (TextView) v.findViewById(R.id.price);
				tv1.setText(allDetail.get(groupPosition).get(childPosition)
						.get("product").toString());
				tv2.setText(allDetail.get(groupPosition).get(childPosition)
						.get("purchasequantity").toString());
				tv3.setText(allDetail.get(groupPosition).get(childPosition)
						.get("purchaseunitprice").toString());
				return ll;
			}

			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				return allDetail.get(groupPosition).size();

			}

			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return allMaster.get(groupPosition);
			}

			public int getGroupCount() {
				// TODO Auto-generated method stub
				return allMaster.size();
			}

			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return groupPosition;
			}

			public View getGroupView(int groupPosition, boolean isExpanded,
					View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				View v = convertView;
				if (v == null) {
					LayoutInflater inflater = getLayoutInflater();
					v = inflater.inflate(R.layout.purchase_listcontent, parent,
							false);
				}
				RelativeLayout rl = (RelativeLayout) v.findViewById(R.id.rl);
				TextView tv1 = (TextView) v.findViewById(R.id.orderid);
				TextView tv2 = (TextView) v.findViewById(R.id.supplier);
				TextView tv3 = (TextView) v.findViewById(R.id.date);
				tv1.setText(allMaster.get(groupPosition).get("purchaseid")
						.toString());
				tv2.setText(allMaster.get(groupPosition).get("supplier")
						.toString());
				tv3.setText(allMaster.get(groupPosition).get("purchasedate")
						.toString());
				return rl;
			}

			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return true;
			}

			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				// TODO Auto-generated method stub
				return true;
			}

		};

		expandListView.setGroupIndicator(null);
		expandListView.setAdapter(adapter);
	}

	// 加载时
	public void init(String title) {

		if (allMaster.size() > 0) {
			allDetail.clear();
			allMaster.clear();
		}
		if(list.size()>0){
			list.clear();
		}
		String uri = null;
		if (title.equals("采购退货")) {
			uri = "http://10.0.2.2:8080/CosmeticService/purchasegetalltui.do";
		} else {
			uri = "http://10.0.2.2:8080/CosmeticService/purchasegetallru.do";
		}

		HttpPost hp = new HttpPost(uri);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("purchaseid", autextview.getText()
				.toString()));
		HttpClient hc = new DefaultHttpClient();
		try {
			hp.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse hr = hc.execute(hp);
			base(hr);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, list);
			
			autextview.setAdapter(adapter);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		insertListView();
	}

	// AutoCompleteTextView失去焦点时
//	public void inittwo(String title) {
//		if (allMaster.size() > 0) {
//			allMaster.clear();
//		}
//		if (allDetail.size() > 0) {
//			allDetail.clear();
//		}
//		String uri = null;
//		if (title.equals("采购退货")) {
//			uri = "http://10.0.2.2:8080/CosmeticService/purchasegetonebyidtui.do";
//		} else {
//			uri = "http://10.0.2.2:8080/CosmeticService/purchasegetonebyidru.do";
//		}
//
//		HttpPost hp = new HttpPost(uri);
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("purchaseid", autextview.getText()
//				.toString()));
//		HttpClient hc = new DefaultHttpClient();
//		try {
//			hp.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
//			HttpResponse hr = hc.execute(hp);
//
//			if (hr.getStatusLine().getStatusCode() == 200) {
//				JSONObject jo = new JSONObject(EntityUtils.toString(hr
//						.getEntity()));
//				System.out.println(jo);
//				list.add(String.valueOf(jo.optString("purchaseid")));
//				Map<String, String> purchasemap = new HashMap<String, String>();
//				purchasemap.put("purchaseid",
//						String.valueOf(jo.optString("purchaseid")));
//				purchasemap.put("purchasedate", jo.optString("purchasedate"));
//
//				purchasemap.put("supplier",
//						getOneSupplierName(jo.optString("supplierid")));
//				purchasemap.put("purchaseproperty",
//						jo.optString("purchaseproperty"));
//				purchasemap.put("subtotal", jo.optString("subtotal"));
//
//				allMaster.add(purchasemap);
//
//				JSONArray js = new JSONArray(jo.optString("allDetail"));
//				// Toast.makeText(this, js.toString(), Toast.LENGTH_LONG)
//				// .show();
//				List<Map<String, String>> pd = new ArrayList<Map<String, String>>();
//
//				for (int j = 0; j < js.length(); j++) {
//
//					Map<String, String> purchaseDetail = new HashMap<String, String>();
//					JSONObject ob = new JSONObject(js.optJSONObject(j)
//							.toString());
//					purchaseDetail.put("product",
//							getOneProductName(ob.optString("productid")));
//					purchaseDetail.put("purchasequantity",
//							ob.optString("purchasequantity"));
//					purchaseDetail.put("purchaseunitprice",
//							ob.optString("purchaseunitprice"));
//					pd.add(purchaseDetail);
//
//				}
//
//				allDetail.add(pd);

//			}
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		insertListView();
//	}

	// 将单子及明细充入list
	public void base(HttpResponse hr) {

		if (hr.getStatusLine().getStatusCode() == 200) {

			try {
				JSONArray jarray = new JSONArray(EntityUtils.toString(hr
						.getEntity()));
				for (int i = 0; i < jarray.length(); i++) {
					JSONObject jo = jarray.optJSONObject(i);
					list.add(String.valueOf(jo.optString("purchaseid")));
					
					Map<String, String> purchasemap = new HashMap<String, String>();
					purchasemap.put("purchaseid",
							String.valueOf(jo.optString("purchaseid")));
					purchasemap.put("purchasedate",
							jo.optString("purchasedate"));
					purchasemap.put("supplier",
							getOneSupplierName(jo.optString("supplierid")));
					purchasemap.put("purchaseproperty",
							jo.optString("purchaseproperty"));
					purchasemap.put("subtotal", jo.optString("subtotal"));
					JSONArray js = new JSONArray(jo.optString("allDetail"));
					List<Map<String, String>> purchaseDetailmap = new ArrayList<Map<String, String>>();

					for (int j = 0; j < js.length(); j++) {

						Map<String, String> purchaseDetail = new HashMap<String, String>();
						JSONObject ob = new JSONObject(js.optJSONObject(j)
								.toString());
						purchaseDetail.put("product",
								getOneProductName(ob.optString("productid")));
						purchaseDetail.put("purchasequantity",
								ob.optString("purchasequantity"));
						
						purchaseDetail.put("purchaseunitprice",
								String.valueOf(ob.optDouble("purchaseunitprice")));
						purchaseDetailmap.add(purchaseDetail);
					}

					allDetail.add(purchaseDetailmap);
					allMaster.add(purchasemap);

				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 通过supplierid得到suppliername
	public String getOneSupplierName(String supplierId) {
		String name = "";
		JSONObject jo = new JSONObject();

		String url = "http://10.0.2.2:8080/CosmeticService/GetSupplierOneNames.do";
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			jo.put("id", supplierId);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("pc_json", jo.toString()));
			post.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse hr = client.execute(post);
			if (hr.getStatusLine().getStatusCode() == 200) {
				name = EntityUtils.toString(hr.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return name;
	}

	// 通过productid得到productname
	public String getOneProductName(String productId) {
		String name = "";
		JSONObject jo = new JSONObject();

		String url = "http://10.0.2.2:8080/CosmeticService/GetOneProductName.do";
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			jo.put("id", productId);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("pc_json", jo.toString()));
			post.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse hr = client.execute(post);
			if (hr.getStatusLine().getStatusCode() == 200) {
				name = EntityUtils.toString(hr.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return name;
	}
}
