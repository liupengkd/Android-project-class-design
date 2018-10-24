package allinhand.example.saleandcustomer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 销售单列表
 * @author 刘晴
 *
 */
public class SalesMasterListActivity extends Activity implements
		OnChildClickListener, TextWatcher {

	Intent intent = null;
	TextView tvtitle;
	Button btnaddorder;
	Button btnorder;
	AutoCompleteTextView actvdeliveryid;
	ExpandableListView expandListView;
	List<Map<String, String>> masterlist = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dtlist = null;
	List<List<Map<String, String>>> detaillist = new ArrayList<List<Map<String, String>>>();
	// 销售订单编号
	List<String> list = new ArrayList<String>();
	// 商品单价、名称
	String productname = null;
	//标识
	int a = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sales_master_list);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		// 获取布局控件
		tvtitle = (TextView) findViewById(R.id.tvtitle);
		btnaddorder = (Button) findViewById(R.id.btnaddorder);
		btnorder = (Button) findViewById(R.id.btnorder);
		expandListView = (ExpandableListView) findViewById(R.id.lv);
		actvdeliveryid = (AutoCompleteTextView) findViewById(R.id.actvdeliveryid);

		intent = getIntent();
		tvtitle.setText(intent.getStringExtra("title"));

		// 判断是销售出库还是销售退货
		change_page(tvtitle.getText().toString());

		// actvdeliveryid配置adapter
		ArrayAdapter<String> idadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		actvdeliveryid.setAdapter(idadapter);

		actvdeliveryid.addTextChangedListener(this);
		expandListView.setOnChildClickListener(this);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		change_page(intent.getStringExtra("title"));
		super.onRestart();
	}

	// actvdeliveryid失去焦点后，在list中显示结果
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		getAllDelivery(tvtitle.getText().toString(), actvdeliveryid.getText().toString());
	}

	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	// 销售单每个商品的操作，跳转到销售商品明细页面
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		intent = new Intent(SalesMasterListActivity.this,
				SalesDetailAddActivity.class);
		//销售退货的新建明细a=1
		intent.putExtra("title", tvtitle.getText().toString());
		intent.putExtra("ddbh",masterlist.get(groupPosition).get("deliveryid"));
		intent.putExtra("productname",detaillist.get(groupPosition).get(childPosition).get("productname"));
		intent.putExtra("pdnum",detaillist.get(groupPosition).get(childPosition).get("salesquantity"));
		intent.putExtra("salesman", masterlist.get(groupPosition).get("salesmanid"));
		intent.putExtra("customer", masterlist.get(groupPosition).get("customerid"));
		//销售退货的查看和销售出库的查看a=0
		if (a == 0) {
			intent.putExtra("productID",detaillist.get(groupPosition).get(childPosition).get("productname"));
			intent.putExtra("pdprice",detaillist.get(groupPosition).get(childPosition).get("productprice"));
		}
		startActivity(intent);
		return false;
	}

	// 添加销售单
	public void go_salesadd(View v) {
		a = 1;
		// 更改按钮图片
		btnorder.setBackgroundResource(R.drawable.button_bg_defocused_left);
		btnaddorder.setBackgroundResource(R.drawable.button_bg_normal_right);
		// 判断是销售出库还是销售退货
		if (tvtitle.getText().equals("销售出库")) {
			// 跳转到添加销售页面
			intent = new Intent(SalesMasterListActivity.this,
					SalesAddActivity.class);
			// 获取title值
			intent.putExtra("title", tvtitle.getText());
			startActivity(intent);
		} else {
			//新建销售退货，显示销售出库订单
			list.clear();
			getAllDelivery("销售出库", "");
			// actvdeliveryid配置adapter
			ArrayAdapter<String> idadapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, list);
			actvdeliveryid.setAdapter(idadapter);
		}
	}

	// 销售清单
	public void go_master(View v) {
		// 判断是销售出库还是销售退货
		change_page(tvtitle.getText().toString());
	}

	// 返回
	public void btnback(View view) {
		// 跳转到main
		intent = new Intent(SalesMasterListActivity.this, MainActivity.class);
		startActivity(intent);
	}

	// 判断是销售出库还是销售退货
	public void change_page(String title) {
		a = 0;
		// 更改按钮图片
		btnorder.setBackgroundResource(R.drawable.button_bg_normal_left);
		btnaddorder.setBackgroundResource(R.drawable.button_bg_defocused_right);
		getAllDelivery(title, "");
		// 销售退货
		if (title.equals("销售退货")) {
			btnorder.setText("退货清单");
		} else {
			// 销售出库
			btnorder.setText("订单清单");
		}
	}

	// 把数据绑定在ExpandableListView
	public void insertintoExpandableListView() {
		ExpandableListAdapter adapter = new BaseExpandableListAdapter() {
			public boolean isChildSelectable(int groupPosition,
					int childPosition) {
				// TODO Auto-generated method stub
				return true;
			}

			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return true;
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
				TextView tvorderid = (TextView) v.findViewById(R.id.orderid);
				TextView tvdate = (TextView) v.findViewById(R.id.date);
				tvorderid.setText(masterlist.get(groupPosition)
						.get("deliveryid").toString());
				tvdate.setText(masterlist.get(groupPosition)
						.get("deliverydate").toString());
				return rl;
			}

			public long getGroupId(int groupPosition) {
				// TODO Auto-generated method stub
				return groupPosition;
			}

			public int getGroupCount() {
				// TODO Auto-generated method stub
				return masterlist.size();
			}

			public Object getGroup(int groupPosition) {
				// TODO Auto-generated method stub
				return masterlist.get(groupPosition);
			}

			public int getChildrenCount(int groupPosition) {
				// TODO Auto-generated method stub
				return detaillist.get(groupPosition).size();
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
				TextView tvproduct = (TextView) v.findViewById(R.id.product);
				TextView tvcount = (TextView) v.findViewById(R.id.count);
				TextView tvprice = (TextView) v.findViewById(R.id.price);
				Map<String, String> map = (Map<String, String>) getChild(
						groupPosition, childPosition);
				tvproduct.setText(map.get("productname").toString());
				tvcount.setText(map.get("salesquantity").toString());
				tvprice.setText(map.get("productprice").toString());
				return ll;
			}

			public long getChildId(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return childPosition;
			}

			public Object getChild(int groupPosition, int childPosition) {
				// TODO Auto-generated method stub
				return detaillist.get(groupPosition).get(childPosition);
			}
		};
		expandListView.setGroupIndicator(null);
		expandListView.setAdapter(adapter);
	}

	// 显示所有数据在list
	public void getAllDelivery(String title, String deliveryid) {
		masterlist.clear();
		detaillist.clear();
		String uri = null;
		int dp = 1;
		
		if (deliveryid.length()==0) {
			if (title.equals("销售退货")) {
				dp = -1;
				uri = "http://10.0.2.2:8080/CosmeticService/getAllDelivery.do?dp="
						+ dp;
			} else {
				uri = "http://10.0.2.2:8080/CosmeticService/getAllDelivery.do?dp="
						+ dp;
			}
		} else {
			uri = "http://10.0.2.2:8080/CosmeticService/getOneDelivery.do?deliveryid="
					+ deliveryid;
		}
		HttpGet hg = new HttpGet(uri);
		HttpClient hc = new DefaultHttpClient();
		try {
			HttpResponse hr = hc.execute(hg);
			// 接收
			if (hr.getStatusLine().getStatusCode() == 200) {
				//查询所有销售单
				if (deliveryid.length()==0) {
					JSONArray ja = new JSONArray(EntityUtils.toString(hr
							.getEntity()));
					for (int i = 0; i < ja.length(); i++) {
						JSONObject jo = ja.optJSONObject(i);
						list.add(jo.optString("deliveryid"));
						Map<String, String> mastermap = new HashMap<String, String>();
						mastermap.put("deliveryid", jo.optString("deliveryid"));
						mastermap.put("deliverydate",
								jo.optString("deliverydate"));
						 mastermap.put("salesmanid",getSalesManName(jo.optString("salesmanid")));
						 mastermap.put("customerid",getCustomerName(jo.optString("customerid")));
						JSONArray js = new JSONArray(jo.optString("deldtList"));
						// 明细
						List<Map<String, String>> dtlist = new ArrayList<Map<String, String>>();
						JSONObject job = new JSONObject();
						for (int x = 0; x < js.length(); x++) {
							job = new JSONObject(js.optJSONObject(x).toString());
							Map<String, String> detailmap = new HashMap<String, String>();
							detailmap.put("productid",
									job.optString("productid"));
							detailmap.put("productname",
									getProductName(job.optString("productid")));
							detailmap.put("salesquantity",
									job.optString("salesquantity"));
							detailmap.put("productprice", job.optDouble("salesprice")+"");

							// 所有明细
							dtlist.add(detailmap);
						}
						// 每个销售单的所有明细
						detaillist.add(dtlist);
						// 所有销售单
						masterlist.add(mastermap);
						insertintoExpandableListView();
					}
				} else {
					//根据销售单号查询销售单
					JSONObject jo = new JSONObject(EntityUtils.toString(hr
							.getEntity()));
					// list.add(jo.optString("deliveryid"));
					Map<String, String> mastermap = new HashMap<String, String>();
					mastermap.put("deliveryid", jo.optString("deliveryid"));
					mastermap.put("deliverydate", jo.optString("deliverydate"));
					mastermap.put("salesmanid",getSalesManName(jo.optString("salesmanid")));
					mastermap.put("customerid",getCustomerName(jo.optString("customerid")));
					JSONArray js = new JSONArray(jo.optString("deldtList"));
					// 明细
					List<Map<String, String>> dtlist = new ArrayList<Map<String, String>>();
					JSONObject job = new JSONObject();
					for (int x = 0; x < js.length(); x++) {
						job = new JSONObject(js.optJSONObject(x).toString());
						Map<String, String> detailmap = new HashMap<String, String>();
						detailmap.put("productid", job.optString("productid"));
						detailmap.put("productname",
								getProductName(job.optString("productid")));
						detailmap.put("salesquantity",
								job.optString("salesquantity"));
						detailmap.put("productprice", job.optDouble("salesprice")+"");

						// 所有明细
						dtlist.add(detailmap);
					}
					// 每个销售单的所有明细
					detaillist.add(dtlist);
					// 所有销售单
					masterlist.add(mastermap);
					insertintoExpandableListView();
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 根据销售员编号显示姓名
	public String getSalesManName(String userid) {
		String username = null;
		String uri = "http://10.0.2.2:8080/CosmeticService/getUsersByNameOrId.do?name="
				+ userid;
		HttpGet hg = new HttpGet(uri);
		HttpClient hc = new DefaultHttpClient();
		try {
			HttpResponse hr = hc.execute(hg);
			if (hr.getStatusLine().getStatusCode() == 200) {
				JSONObject jo = new JSONObject(EntityUtils.toString(hr
						.getEntity()));
				username = jo.optString("username");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return username;
	}

	// 根据客户编号显示姓名
	public String getCustomerName(String customerid) {
		String customername = null;
		String uri = "http://10.0.2.2:8080/CosmeticService/getCustomerByName.do?Byname="
				+ customerid;
		HttpGet hg = new HttpGet(uri);
		HttpClient hc = new DefaultHttpClient();
		try {
			HttpResponse hr = hc.execute(hg);
			if (hr.getStatusLine().getStatusCode() == 200) {
				JSONObject jo = new JSONObject(EntityUtils.toString(hr
						.getEntity()));
				customername = jo.optString("customername");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customername;
	}

	// 根据商品编号显示商品名
	public String getProductName(String productid) {
		String uri = "http://10.0.2.2:8080/CosmeticService/GetProductByName.do?name="
				+ productid;
		HttpGet hg = new HttpGet(uri);
		HttpClient hc = new DefaultHttpClient();
		try {
			HttpResponse hr = hc.execute(hg);
			if (hr.getStatusLine().getStatusCode() == 200) {
				JSONObject jb = new JSONObject(EntityUtils.toString(hr
						.getEntity()));
				productname = jb.optString("productname");
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productname;
	}

}
