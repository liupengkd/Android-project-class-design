package allinhand.example.purchaseandsupplier;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseRu_addActivity extends Activity {

	Intent intent;

	TextView title;
	Button submit;
	TextView txtbh;
	TextView txtdate;
	TextView txttotal;

	Spinner spisupplier;
	List<String> list = new ArrayList<String>();
	ArrayAdapter<String> adapter = null;

	String pru = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.purchaseru_add);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		title = (TextView) findViewById(R.id.title);
		submit = (Button) findViewById(R.id.submit);
		txtbh = (TextView) findViewById(R.id.txtbh);
		txtdate = (TextView) findViewById(R.id.txtdate);
		txttotal = (TextView) findViewById(R.id.txttotal);
		spisupplier = (Spinner) findViewById(R.id.spiSupplier);

		intent = getIntent();
		title.setText(intent.getStringExtra("title"));

		getSupplierName();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		spisupplier.setAdapter(adapter);
		if (intent.getStringExtra("purchaseid").equals("1")) {
			getPastDetail();
			txtbh.setText(intent.getStringExtra("pru"));
			// 时间
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date curDate = new Date(System.currentTimeMillis());
			txtdate.setText(formatter.format(curDate));
			txttotal.setText(total.toString());

			spisupplier.setSelection(
					supMap.get(intent.getStringExtra("supplier")), true);
			spisupplier.setEnabled(false);
		} else {
			// 判断是添加还是查看
			if (intent.getStringExtra("purchaseid").equals("")) {
				pru = intent.getStringExtra("pruid");
				// 编号
				txtbh.setText(createBH(title.getText().toString()));
				// 时间
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date curDate = new Date(System.currentTimeMillis());
				txtdate.setText(formatter.format(curDate));
				if (title.getText().equals("采购退货")) {
					// 供应商
					spisupplier
							.setSelection(supMap.get(intent
									.getStringExtra("supplier")), true);
					spisupplier.setEnabled(false);
				}
			} else {
				// 编号
				txtbh.setText(intent.getStringExtra("purchaseid"));
				// 通过编号得到一条记录
				getOneOrder();
				// 时间
				
				txtdate.setText(order.get(0).get("purchasedate"));
				// 总金额
				
				txttotal.setText(order.get(0).get("subtotal"));
				// 供应商
				
				spisupplier.setSelection(
						supMap.get(order.get(0).get("supplier")), true);

				spisupplier.setEnabled(false);
				submit.setVisibility(View.GONE);
			}
		}

	}

	Double total = 0.0;

	// 得到上个页面传过来的明细并设置总金额
	public void getPastDetail() {
		String detail = intent.getStringExtra("detailFromAdd");
		try {
			JSONArray ja = new JSONArray(detail);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = new JSONObject(ja.getString(i));
				JSONObject jj = new JSONObject();
				jj.put("product", jo.optString("product"));
				jj.put("productid", jo.optString("productid"));
				jj.put("number", jo.optString("number"));
				jj.put("price", jo.optString("price"));
				jj.put("sum", jo.optString("sum"));
				total = total + jo.optDouble("sum");
				detailFromAdd.add(jj);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 通过productname得到id

	List<Map<String, String>> order = new ArrayList<Map<String, String>>();
	List<Map<String, String>> detail = new ArrayList<Map<String, String>>();

	// 通过purcaseid得到一条记录及明细
	public void getOneOrder() {
		String uri = null;
		if (title.getText().equals("采购退货")) {
			uri = "http://10.0.2.2:8080/CosmeticService/purchasegetonebyidtui.do";
		} else {
			uri = "http://10.0.2.2:8080/CosmeticService/purchasegetonebyidru.do";
		}

		HttpPost hp = new HttpPost(uri);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("purchaseid", intent
				.getStringExtra("purchaseid")));
		HttpClient hc = new DefaultHttpClient();
		try {
			hp.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse hr = hc.execute(hp);

			if (hr.getStatusLine().getStatusCode() == 200) {
				JSONObject jo = new JSONObject(EntityUtils.toString(hr
						.getEntity()));

				Map<String, String> purchasemap = new HashMap<String, String>();
				purchasemap.put("purchaseid",
						String.valueOf(jo.optString("purchaseid")));
				purchasemap.put("purchasedate", jo.optString("purchasedate"));
				purchasemap.put("supplier",
						getOneSupplierName(jo.optString("supplierid")));
				purchasemap.put("purchaseproperty",
						jo.optString("purchaseproperty"));
				purchasemap.put("subtotal", jo.optString("subtotal"));
				order.add(purchasemap);

				JSONArray js = new JSONArray(jo.optString("allDetail"));

				for (int j = 0; j < js.length(); j++) {

					Map<String, String> purchaseDetail = new HashMap<String, String>();
					JSONObject ob = new JSONObject(js.optJSONObject(j)
							.toString());
					
					purchaseDetail.put("product",
							getOneProductName(ob.optString("productid")));
					purchaseDetail.put("purchasequantity",
							String.valueOf(ob.optInt("purchasequantity")));
					purchaseDetail.put("purchaseunitprice",
							ob.optString("purchaseunitprice"));
					detail.add(purchaseDetail);
				}

			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
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

	// 自动生成采购编号
	public String createBH(String title) {
		String ddbh = null;
		String uri = null;
		if (title.equals("采购退货")) {
			uri = "http://10.0.2.2:8080/CosmeticService/purchasegetalltui.do";
		} else {
			uri = "http://10.0.2.2:8080/CosmeticService/purchasegetallru.do";
		}
		HttpPost hp = new HttpPost(uri);
		HttpClient hc = new DefaultHttpClient();
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("purchaseid", ""));
		try {
			hp.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse hr = hc.execute(hp);
			if (hr.getStatusLine().getStatusCode() == 200) {
				String result = EntityUtils.toString(hr.getEntity());
				if (result.equals("fail")) {
					if (title.equals("采购退货")) {
						ddbh = "CGTH0001";
					} else {
						ddbh = "CGRK0001";
					}
				} else {
					JSONArray jarray = new JSONArray(result);
					JSONObject jo = jarray.optJSONObject(0);
					int bh = Integer.parseInt(jo.optString("purchaseid")
							.substring(4));
					bh++;
					if ((bh + "").length() == 1) {
						ddbh = jo.optString("purchaseid").substring(0, 4)
								+ "000" + bh;
					}
					if ((bh + "").length() == 2) {
						ddbh = jo.optString("purchaseid").substring(0, 4)
								+ "00" + bh;
					}
					if ((bh + "").length() == 3) {
						ddbh = jo.optString("purchaseid").substring(0, 4) + "0"
								+ bh;
					}
					if ((bh + "").length() == 4) {
						ddbh = jo.optString("purchaseid").substring(0, 4) + bh;
					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ddbh;
	}

	Map<String, Integer> supMap = new HashMap<String, Integer>();
	Map<String, Integer> supMapid = new HashMap<String, Integer>();

	// 查询所有供应商名称
	private void getSupplierName() {
		// TODO Auto-generated method stub
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
				String nameandid = EntityUtils.toString(response.getEntity());
				JSONArray jsonArray = new JSONArray(nameandid);
				int j = 0;
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);
					// 判断此供应商有无商品
					if (getProductBySupplier(jo.optString("suppliername"))) {
						list.add(String.valueOf(jo.optString("suppliername")));
						supMap.put(jo.optString("suppliername"), j);
						supMapid.put(jo.optString("supplierid"), j);
						j++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据供应商得到商品
	public boolean getProductBySupplier(String supplierid) {
		String url = "http://10.0.2.2:8080/CosmeticService/getProductByNameID.do";
		// HttpPost连接对象，设置客户端提交方式
		HttpPost httpPost = new HttpPost(url);
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("nameid", supplierid));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String losespillList = EntityUtils.toString(response
						.getEntity());
				if (losespillList.equals("faild")) {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return true;
	}

	// 明细
	List<JSONObject> detailFromAdd = new ArrayList<JSONObject>();

	public void detail(View v) {

		intent = new Intent(PurchaseRu_addActivity.this,
				PurchaseRuMingXiActivity.class);
		intent.putExtra("title", title.getText().toString());
		intent.putExtra("bh", txtbh.getText().toString());
		intent.putExtra("total", txttotal.getText().toString());
		intent.putExtra("date", txtdate.getText().toString());
		JSONArray ja = null;
		// 判断是添加还是查看
		if (submit.getVisibility() != View.GONE) {
			if (title.getText().equals("采购退货")) {
				// 传入库单号
				intent.putExtra("pru", pru);
				// 传供应商
				intent.putExtra("supplier", spisupplier.getSelectedItem()
						.toString());
			} else {
				// 传供应商
				intent.putExtra("supplier", spisupplier.getSelectedItem()
						.toString());

			}

			ja = new JSONArray(detailFromAdd);
		} else {
			// 查看
			intent.putExtra("supplier", "");
			ja = new JSONArray(detail);
		}
		intent.putExtra("list", ja.toString());
		startActivity(intent);
	}

	public void delete(View v) {

	}

	public void update(View v) {

	}

	// 提交
	public void submit(View v) {
		if (detailFromAdd.size() > 0) {
			String pp = "";
			if (title.getText().equals("采购退货")) {
				pp = "-1";
			} else {
				pp = "1";
			}
			try {
				String uri = "http://10.0.2.2:8080/CosmeticService/addPurchase.do";
				HttpPost hp = new HttpPost(uri);
				HttpClient hc = new DefaultHttpClient();
				JSONObject jo = new JSONObject();
				jo.put("pid", txtbh.getText().toString());
				jo.put("pdate", txtdate.getText().toString());
				jo.put("supplierid", getOneSupplierid(spisupplier
						.getSelectedItem().toString()));
				jo.put("purchasep", pp);
				jo.put("sub", txttotal.getText().toString());
				jo.put("detaillist", new JSONArray(detailFromAdd).toString());
				// 添加参数
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				param.add(new BasicNameValuePair("p_json", jo.toString()));
				hp.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
				// 发送
				HttpResponse hr = hc.execute(hp);
				// 接收
				if (hr.getStatusLine().getStatusCode() == 200) {
					String str = EntityUtils.toString(hr.getEntity());
					if (str.trim().equals("success")) {
						Toast.makeText(getApplicationContext(), "采购单信息添加成功",
								Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(getApplicationContext(), "采购信息添加失败",
								Toast.LENGTH_SHORT).show();
					}
					// 跳转页面
					intent = new Intent(PurchaseRu_addActivity.this,
							PurchaseRuActivity.class);
					intent.putExtra("title", title.getText().toString());
					startActivity(intent);
				} else {
					Toast.makeText(getApplicationContext(), "404或500错误",
							Toast.LENGTH_SHORT).show();
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			Toast.makeText(getApplicationContext(), "明细为空，添加失败",
					Toast.LENGTH_SHORT).show();
		}

	}

	// 通过suppliername得到supplierid
	public String getOneSupplierid(String suppliername) {
		String name = "";
		JSONObject jo = new JSONObject();

		String url = "http://10.0.2.2:8080/CosmeticService/GetSupplierOneName.do";
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			jo.put("name", suppliername);
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

	
	public void btnback(View view) {
		finish();
	}
	public void btnhome(View v){
		if(detailFromAdd.size()>0){
			Dialog dialog = new AlertDialog.Builder(
					PurchaseRu_addActivity.this)
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
		}else{
			fanhui();
		}
		
	}
	//返回首页
	public void fanhui(){
		Intent intent = new Intent(PurchaseRu_addActivity.this,MainActivity.class);
		startActivity(intent);
	}
}
