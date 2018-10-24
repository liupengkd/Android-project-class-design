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
 * ���۵��б�
 * @author ����
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
	// ���۶������
	List<String> list = new ArrayList<String>();
	// ��Ʒ���ۡ�����
	String productname = null;
	//��ʶ
	int a = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sales_master_list);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		// ��ȡ���ֿؼ�
		tvtitle = (TextView) findViewById(R.id.tvtitle);
		btnaddorder = (Button) findViewById(R.id.btnaddorder);
		btnorder = (Button) findViewById(R.id.btnorder);
		expandListView = (ExpandableListView) findViewById(R.id.lv);
		actvdeliveryid = (AutoCompleteTextView) findViewById(R.id.actvdeliveryid);

		intent = getIntent();
		tvtitle.setText(intent.getStringExtra("title"));

		// �ж������۳��⻹�������˻�
		change_page(tvtitle.getText().toString());

		// actvdeliveryid����adapter
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

	// actvdeliveryidʧȥ�������list����ʾ���
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

	// ���۵�ÿ����Ʒ�Ĳ�������ת��������Ʒ��ϸҳ��
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		intent = new Intent(SalesMasterListActivity.this,
				SalesDetailAddActivity.class);
		//�����˻����½���ϸa=1
		intent.putExtra("title", tvtitle.getText().toString());
		intent.putExtra("ddbh",masterlist.get(groupPosition).get("deliveryid"));
		intent.putExtra("productname",detaillist.get(groupPosition).get(childPosition).get("productname"));
		intent.putExtra("pdnum",detaillist.get(groupPosition).get(childPosition).get("salesquantity"));
		intent.putExtra("salesman", masterlist.get(groupPosition).get("salesmanid"));
		intent.putExtra("customer", masterlist.get(groupPosition).get("customerid"));
		//�����˻��Ĳ鿴�����۳���Ĳ鿴a=0
		if (a == 0) {
			intent.putExtra("productID",detaillist.get(groupPosition).get(childPosition).get("productname"));
			intent.putExtra("pdprice",detaillist.get(groupPosition).get(childPosition).get("productprice"));
		}
		startActivity(intent);
		return false;
	}

	// ������۵�
	public void go_salesadd(View v) {
		a = 1;
		// ���İ�ťͼƬ
		btnorder.setBackgroundResource(R.drawable.button_bg_defocused_left);
		btnaddorder.setBackgroundResource(R.drawable.button_bg_normal_right);
		// �ж������۳��⻹�������˻�
		if (tvtitle.getText().equals("���۳���")) {
			// ��ת���������ҳ��
			intent = new Intent(SalesMasterListActivity.this,
					SalesAddActivity.class);
			// ��ȡtitleֵ
			intent.putExtra("title", tvtitle.getText());
			startActivity(intent);
		} else {
			//�½������˻�����ʾ���۳��ⶩ��
			list.clear();
			getAllDelivery("���۳���", "");
			// actvdeliveryid����adapter
			ArrayAdapter<String> idadapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, list);
			actvdeliveryid.setAdapter(idadapter);
		}
	}

	// �����嵥
	public void go_master(View v) {
		// �ж������۳��⻹�������˻�
		change_page(tvtitle.getText().toString());
	}

	// ����
	public void btnback(View view) {
		// ��ת��main
		intent = new Intent(SalesMasterListActivity.this, MainActivity.class);
		startActivity(intent);
	}

	// �ж������۳��⻹�������˻�
	public void change_page(String title) {
		a = 0;
		// ���İ�ťͼƬ
		btnorder.setBackgroundResource(R.drawable.button_bg_normal_left);
		btnaddorder.setBackgroundResource(R.drawable.button_bg_defocused_right);
		getAllDelivery(title, "");
		// �����˻�
		if (title.equals("�����˻�")) {
			btnorder.setText("�˻��嵥");
		} else {
			// ���۳���
			btnorder.setText("�����嵥");
		}
	}

	// �����ݰ���ExpandableListView
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

	// ��ʾ����������list
	public void getAllDelivery(String title, String deliveryid) {
		masterlist.clear();
		detaillist.clear();
		String uri = null;
		int dp = 1;
		
		if (deliveryid.length()==0) {
			if (title.equals("�����˻�")) {
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
			// ����
			if (hr.getStatusLine().getStatusCode() == 200) {
				//��ѯ�������۵�
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
						// ��ϸ
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

							// ������ϸ
							dtlist.add(detailmap);
						}
						// ÿ�����۵���������ϸ
						detaillist.add(dtlist);
						// �������۵�
						masterlist.add(mastermap);
						insertintoExpandableListView();
					}
				} else {
					//�������۵��Ų�ѯ���۵�
					JSONObject jo = new JSONObject(EntityUtils.toString(hr
							.getEntity()));
					// list.add(jo.optString("deliveryid"));
					Map<String, String> mastermap = new HashMap<String, String>();
					mastermap.put("deliveryid", jo.optString("deliveryid"));
					mastermap.put("deliverydate", jo.optString("deliverydate"));
					mastermap.put("salesmanid",getSalesManName(jo.optString("salesmanid")));
					mastermap.put("customerid",getCustomerName(jo.optString("customerid")));
					JSONArray js = new JSONArray(jo.optString("deldtList"));
					// ��ϸ
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

						// ������ϸ
						dtlist.add(detailmap);
					}
					// ÿ�����۵���������ϸ
					detaillist.add(dtlist);
					// �������۵�
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

	// ��������Ա�����ʾ����
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

	// ���ݿͻ������ʾ����
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

	// ������Ʒ�����ʾ��Ʒ��
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
