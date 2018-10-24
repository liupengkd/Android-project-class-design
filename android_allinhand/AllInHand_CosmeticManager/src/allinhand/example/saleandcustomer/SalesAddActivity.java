package allinhand.example.saleandcustomer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 添加销售单
 * 
 * @author 刘晴
 * 
 */
public class SalesAddActivity extends Activity {

	Intent intent = null;
	TextView tvtitle;
	EditText ettotal;
	EditText etID;
	EditText etDate;
	EditText etSalesmanID;
	AutoCompleteTextView actvCustomersname;
	// 销售详细明细
	List<JSONObject> deldtlist = null;
	// 所有的客户名
	List<String> customernamelist = new ArrayList<String>();
	// 销售1，销售退货－1
	int prop = 1;
	// 原数量
	int pdnum = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sales_add);

		tvtitle = (TextView) findViewById(R.id.tvtitle);
		ettotal = (EditText) findViewById(R.id.ettotal);
		etID = (EditText) findViewById(R.id.etID);
		etDate = (EditText) findViewById(R.id.etDate);
		etSalesmanID = (EditText) findViewById(R.id.etSalesmanID);
		actvCustomersname = (AutoCompleteTextView) findViewById(R.id.actvCustomersname);
		// actvCustomersname配置adapter
		getAllCustomer();
		ArrayAdapter<String> customeradapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, customernamelist);
		actvCustomersname.setAdapter(customeradapter);

		intent = getIntent();
		tvtitle.setText(intent.getStringExtra("title"));
		if (tvtitle.getText().toString().equals("销售退货")) {
			pdnum = Integer.parseInt(intent.getStringExtra("pdnum"));
		}
		// 订单编号
		etID.setText(createBH(intent.getStringExtra("title")));
		// 时间
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());
		etDate.setText(formatter.format(curDate));
		// 填充销售员
		AuthorityChange authorityChange = (AuthorityChange) getApplication();
		String username = authorityChange.getUsername();
		etSalesmanID.setText(username);
		// 客户名
		actvCustomersname.setText(intent.getStringExtra("customer"));
		// 总金额
		ettotal.setText("0.00");
		// 获取订单详细
		getAllDeliveryDetail();
		if (intent.getStringExtra("dtlist") != null
				|| intent.getStringExtra("salesman") != null) {
			// 填充销售员
			etSalesmanID.setText(intent.getStringExtra("salesman").toString());
			// 获取客户名
			actvCustomersname.setText(intent.getStringExtra("customer")
					.toString());
			if (tvtitle.getText().equals("销售退货")) {
				actvCustomersname.setEnabled(false);
			}
			// 获取商品总金额
			ettotal.setText(intent.getStringExtra("total"));
		}
		// 销售1，销售退货－1
		if (tvtitle.getText().equals("销售退货")) {
			prop = -1;
		}
	}

	// 添加销售明细
	public void go_detail(View v) {
		intent = new Intent(SalesAddActivity.this,
				SalesDetailListActivity.class);
		intent.putExtra("title", tvtitle.getText());
		intent.putExtra("ddbh", etID.getText().toString());
		intent.putExtra("dtlist", new JSONArray(deldtlist).toString());
		intent.putExtra("salesman", etSalesmanID.getText().toString());
		intent.putExtra("customer", actvCustomersname.getText().toString());
		intent.putExtra("pdnum", pdnum + "");
		startActivity(intent);
	}

	// 获取订单详细
	private void getAllDeliveryDetail() {
		String deldtJSON = intent.getStringExtra("dtlist");
		deldtlist = new ArrayList<JSONObject>();
		if (deldtJSON != null && !deldtJSON.equals("")) {
			try {
				JSONArray ja = new JSONArray(deldtJSON);
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = new JSONObject(ja.getString(i));
					JSONObject job = new JSONObject();
					job.put("productID", jo.optString("productID"));
					job.put("pdnum", String.valueOf(jo.optInt("pdnum")));
					job.put("pdprice", String.valueOf(jo.optDouble("pdprice")));
					job.put("pdamount",
							String.valueOf(jo.optDouble("pdamount")));
					deldtlist.add(job);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 添加销售单
	public void go_master(View v) {
		if (deldtlist.size() > 0) {
			try {
				String uri = "http://10.0.2.2:8080/CosmeticService/addDelivery.do";
				HttpPost hp = new HttpPost(uri);
				HttpClient hc = new DefaultHttpClient();
				JSONObject jo = new JSONObject();
				jo.put("dmID", etID.getText().toString());
				jo.put("dmProp", prop);
				jo.put("dmDate", etDate.getText().toString());
				jo.put("dmSalesman", etSalesmanID.getText().toString());
				jo.put("dmCustomer", actvCustomersname.getText().toString());
				jo.put("dmtotal", ettotal.getText().toString());
				jo.put("deldtlist", new JSONArray(deldtlist).toString());
				// 添加参数
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("dm_json", jo.toString()));
				hp.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
				// 发送
				HttpResponse hr = hc.execute(hp);
				// 接收
				if (hr.getStatusLine().getStatusCode() == 200) {
					String str = EntityUtils.toString(hr.getEntity());
					if (str.trim().equals("success")) {
						Toast.makeText(getApplicationContext(), "销售单信息添加成功",
								Toast.LENGTH_SHORT).show();
						// 跳转页面
						intent = new Intent(SalesAddActivity.this,
								SalesMasterListActivity.class);

						intent.putExtra("title", tvtitle.getText());
						startActivity(intent);
					} else {
						Toast.makeText(getApplicationContext(), "销售单信息添加失败",
								Toast.LENGTH_SHORT).show();
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			Toast.makeText(getApplicationContext(), "不能添加空销售单，请添加销售明细。",
					Toast.LENGTH_SHORT).show();
			return;
		}

	}

	// 获取所有客户名
	public void getAllCustomer() {
		String uri = "http://10.0.2.2:8080/CosmeticService/SelectAllCustomer.do";
		HttpGet hg = new HttpGet(uri);
		HttpClient hc = new DefaultHttpClient();
		try {
			// 发送
			HttpResponse hr = hc.execute(hg);
			if (hr.getStatusLine().getStatusCode() == 200) {
				JSONArray ja = new JSONArray(EntityUtils.toString(hr
						.getEntity()));
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = ja.optJSONObject(i);
					// 客户名
					customernamelist.add(jo.optString("customername"));
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

	// 自动生成采购编号
	public String createBH(String title) {
		String ddbh = null;
		String uri = null;
		int dp = 1;
		if (title.equals("销售退货")) {
			dp = -1;
			uri = "http://10.0.2.2:8080/CosmeticService/getAllDelivery.do?dp="
					+ dp;
		} else {
			uri = "http://10.0.2.2:8080/CosmeticService/getAllDelivery.do?dp="
					+ dp;
		}

		HttpGet hg = new HttpGet(uri);
		HttpClient hc = new DefaultHttpClient();
		try {
			HttpResponse hr = hc.execute(hg);

			// 接收
			if (hr.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(hr.getEntity());

				if (result.equals("failed")) {
					if (title.equals("销售退货")) {
						ddbh = "XSTH0001";
					} else {
						ddbh = "XSCK0001";
					}
				} else {
					JSONArray jarray = new JSONArray(result);
					JSONObject jo = jarray.optJSONObject(0);
					int bh = Integer.parseInt(jo.optString("deliveryid")
							.substring(4));
					bh++;
					if ((bh + "").length() == 1) {
						ddbh = jo.optString("deliveryid").substring(0, 4)
								+ "000" + bh;
					}
					if ((bh + "").length() == 2) {
						ddbh = jo.optString("deliveryid").substring(0, 4)
								+ "00" + bh;
					}
					if ((bh + "").length() == 3) {
						ddbh = jo.optString("deliveryid").substring(0, 4) + "0"
								+ bh;
					}
					if ((bh + "").length() == 4) {
						ddbh = jo.optString("deliveryid").substring(0, 4) + bh;
					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ddbh;
	}

	// 返回
	public void btnback(View view) {
		if (tvtitle.getText().toString().equals("销售出库")) {
			// 跳转页面
			intent = new Intent(SalesAddActivity.this,
					SalesMasterListActivity.class);

			intent.putExtra("title", tvtitle.getText());
			startActivity(intent);
		} else {
			finish();
		}
	}

	// 返回首页
	public void btnhome(View v) {
		if (deldtlist.size() > 0) {
			Dialog dialog = new AlertDialog.Builder(SalesAddActivity.this)
					.setTitle("明细尚未提交，返回首页将会丢失，是否返回首页？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									fanhui();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {

								}
							}).create();
			dialog.show();
		} else {
			fanhui();
		}
	}

	// 返回首页
	public void fanhui() {
		Intent intent = new Intent(SalesAddActivity.this, MainActivity.class);
		startActivity(intent);
	}
}
