package allinhand.example.saleandcustomer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import allinhand.example.stockandproduct.ProductActivity;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ���������ϸ
 * 
 * @author ����
 * 
 */
public class SalesDetailAddActivity extends Activity {

	Intent intent = null;
	TextView tvtitle;
	TextView tvkcnum;
	EditText etnum;
	EditText etkcnum;
	EditText etprice;
	EditText etamount;

	String spname;
	Button btnok;
	Button btnnext;
	Button btnproname;
	Spinner spproname;
	// ������ϸ
	ArrayList<Map<String, String>> dtlist = null;
	// ����һ��������Ʒ��
	List<Map<String, String>> splist = null;
	// �˻�ʱ���˻����ŵ�������Ʒ��
	List<String> spproductnamelist = null;
	Map<String, Integer> spproductnamemap = null;
	// ������
	int spinnerIndex;

	// ���
	Double amount = 0.00;
	String salesman = "";
	String customer = "";
	// ���ơ����
	String productname = null;
	int kcnum = 0;
	// �������
	String ddbh = null;
	// �˻�����
	int productnum = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sales_detail_add);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		// ��ȡ���ֿؼ�
		tvtitle = (TextView) findViewById(R.id.tvtitle);
		tvkcnum = (TextView) findViewById(R.id.tvkcnum);
		etnum = (EditText) findViewById(R.id.etnum);
		etkcnum = (EditText) findViewById(R.id.etkcnum);
		etprice = (EditText) findViewById(R.id.etprice);
		etamount = (EditText) findViewById(R.id.etamount);
		btnok = (Button) findViewById(R.id.btnok);
		btnnext = (Button) findViewById(R.id.btnnext);
		btnproname = (Button) findViewById(R.id.btnproductname);
		spproname = (Spinner) findViewById(R.id.spproductname);
		// ����ʼֵ
		btnproname.setText("��ѡ����Ʒ");
		etprice.setText("0.00");
		spproname.setVisibility(View.GONE);

		// �޸�����
		etnum.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (IsNullOrZero()) {
					amount = Integer.parseInt(etnum.getText().toString())
							* Double.parseDouble(etprice.getText().toString());
					// etamount.setText(String.valueOf(amount));
					BigDecimal b = new BigDecimal(amount);
					Double result = b.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					etamount.setText(result + "");
				}
			}
		});

		intent = getIntent();
		tvtitle.setText(intent.getStringExtra("title"));
		salesman = intent.getStringExtra("salesman");
		customer = intent.getStringExtra("customer");
		ddbh = intent.getStringExtra("ddbh");

		// �ж��������˻�
		if (tvtitle.getText().toString().equals("�����˻�")) {
			// Ҫ�˵���Ʒԭ����
			productnum = Integer.parseInt(intent.getStringExtra("pdnum")
					.toString());
			// spinner��ֵ
			getProductNameByDeiveryID();
		}
		// �鿴������ϸ��Ʒ����
		if (intent.getStringExtra("productID") != null) {
			// �ж������۳���鿴
			if (tvtitle.getText().toString().equals("���۳���")) {
				btnproname.setText(intent.getStringExtra("productID"));
				etprice.setText(intent.getStringExtra("pdprice"));
				getProductName(btnproname.getText().toString());
				etkcnum.setText(kcnum + "");
			} else {
				// // �ж��������˻���ϸ�鿴
				spproname.setSelection(spproductnamemap.get(intent
						.getStringExtra("productID")), true);
			}
			etnum.setText(intent.getStringExtra("pdnum"));
			amount = Integer.parseInt(etnum.getText().toString())
					* Double.parseDouble(etprice.getText().toString());
			// etamount.setText(String.valueOf(amount));
			BigDecimal b = new BigDecimal(amount);
			Double result = b.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			etamount.setText(result + "");
			// ������ɡ�next
			btnproname.setEnabled(false);
			spproname.setEnabled(false);
			etnum.setEnabled(false);
			btnok.setVisibility(View.GONE);
			btnnext.setVisibility(View.GONE);
		} else {
			// ������۵���Ʒ�б�
			insertDetail();
		}

	}

	// ����˻��������Ʒ��spinner,�������۱��
	public void getProductNameByDeiveryID() {
		// ����ť
		btnproname.setVisibility(View.GONE);
		spproname.setVisibility(View.VISIBLE);
		etkcnum.setVisibility(View.GONE);
		tvkcnum.setVisibility(View.GONE);

		spproname.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				spinnerIndex = arg2;
				Double price = Double.parseDouble(splist.get(arg2).get(
						"productprice"));
				BigDecimal b = new BigDecimal(price);
				Double result = b.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				etprice.setText(result + "");
				amount = Integer.parseInt(etnum.getText().toString())
						* Double.parseDouble(etprice.getText().toString());
				// etamount.setText(String.valueOf(amount));

				b = new BigDecimal(amount);
				result = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				etamount.setText(result + "");
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		// �������۱�Ų�ѯ������ϸ�е�������Ʒ��
		getDetailByDeliveryID();
		// spinner��ֵ
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spproductnamelist);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spproname.setAdapter(adapter);
		// ����������Ʒ��
		// ����Ʒ������ʼֵ
		if (intent.getStringExtra("productname") != null) {
			spproname.setSelection(
					spproductnamemap.get(intent.getStringExtra("productname")),
					true);
		}
		etnum.setText(intent.getStringExtra("pdnum"));
	}

	// �������۱�Ų�ѯ������ϸ
	public void getDetailByDeliveryID() {
		// ���б��Ϊdeliverid��������ϸ��Ϣ
		splist = new ArrayList<Map<String, String>>();
		// ������Ʒ��
		spproductnamelist = new ArrayList<String>();
		// ��Ʒ��map
		spproductnamemap = new HashMap<String, Integer>();
		String deliveryid = intent.getStringExtra("ddbh");
		String uri = "http://10.0.2.2:8080/CosmeticService/getAllDeliveryDetail.do?deliveryid="
				+ deliveryid;
		HttpGet hg = new HttpGet(uri);
		HttpClient hc = new DefaultHttpClient();
		try {
			// ����
			HttpResponse hr = hc.execute(hg);
			// ����
			if (hr.getStatusLine().getStatusCode() == 200) {
				JSONArray ja = new JSONArray(EntityUtils.toString(hr
						.getEntity()));
				for (int i = 0; i < ja.length(); i++) {
					JSONObject jo = ja.getJSONObject(i);
					Map<String, String> mp = new HashMap<String, String>();
					mp.put("deliveryid", jo.optString("deliveryid"));
					mp.put("productid",
							getProductName(jo.optString("productid")));
					mp.put("salesquantity", jo.optString("salesquantity"));
					mp.put("productprice", jo.optDouble("salesprice") + "");
					splist.add(mp);
					spproductnamelist.add(getProductName(jo
							.optString("productid")));
					spproductnamemap.put(
							getProductName(jo.optString("productid")), i);
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

	// ���������ϸ��Ʒ�б�
	public void go_detaillist(View v) {
		// ����������ݵ�list
		if (tvtitle.getText().toString().equals("���۳���")) {
			if (btnproname.getText().toString() != null
					&& btnproname.getText().toString() != ""
					&& btnproname.getText().toString().trim() != "��ѡ����Ʒ") {
				//������ݵ�list
				insert();
			} else {
				Toast.makeText(getApplicationContext(), "��ѡ����Ʒ��",
						Toast.LENGTH_SHORT).show();
				a = 0;
				return;
			}
		} else {
			//������ݵ�list
			insert();
		}
		if (a == 1) {
			// ��תҳ��
			intent = new Intent(SalesDetailAddActivity.this,
					SalesDetailListActivity.class);
			intent.putExtra("title", tvtitle.getText());
			intent.putExtra("amount", amount);
			intent.putExtra("salesman", salesman);
			intent.putExtra("customer", customer);
			if (tvtitle.getText().equals("�����˻�")) {
				intent.putExtra("salesman", intent.getStringExtra("salesman"));
				intent.putExtra("customer", intent.getStringExtra("customer"));
				intent.putExtra("pdnum", productnum + "");
			}
			intent.putExtra("ddbh", ddbh);
			intent.putExtra("dtlist", new JSONArray(dtlist).toString());
			startActivity(intent);
		}
	}

	// �����һ��������Ʒ��ϸ
	public void go_detailadd(View v) {
		if (tvtitle.getText().toString().equals("���۳���")) {
			if (btnproname.getText().toString() != null
					&& btnproname.getText().toString() != ""
					&& btnproname.getText().toString().trim() != "��ѡ����Ʒ") {
				//������ݵ�list
				insert();
			} else {
				Toast.makeText(getApplicationContext(), "��ѡ����Ʒ��",
						Toast.LENGTH_SHORT).show();
				a = 0;
				return;
			}
		} else {
			//������ݵ�list
			insert();
		}
		if (a == 1) {
			if (tvtitle.getText().equals("�����˻�")) {
				etnum.setText("1");
				amount = Integer.parseInt(etnum.getText().toString())
						* Double.parseDouble(etprice.getText().toString());
				// etamount.setText(String.valueOf(amount));
				BigDecimal b = new BigDecimal(amount);
				Double result = b.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				etamount.setText(result + "");
			} else {
				// ��գ�Next
				btnproname.setText("��ѡ����Ʒ");
				etkcnum.setText("0");
				etnum.setText("1");
				etprice.setText("0.00");
				etamount.setText("0.00");
			}
		}
	}

	int a = 1;

	// ������ݵ�list
	public void insert() {
		// �ж���Ʒ�����Ƿ�Ϊ�պ�0
		if (IsNullOrZero()) {
			a = 1;
			// ��Ʒ����
			int num = 0;
			for (Map<String, String> mp : dtlist) {
				// �ж��Ƿ��Ѿ��ӹ�����Ʒ
				if (mp.get("productID").equals(btnproname.getText())
						|| mp.get("productID").equals(
								spproname.getSelectedItem())) {
					num = Integer.parseInt(mp.get("pdnum"));
					// �ж��������˻�
					/*
					 * if (tvtitle.getText().equals("�����˻�")) { num = -num; }
					 */
					dtlist.remove(mp);
					break;
				}
			}
			String name = btnproname.getText().toString();
			num = num + Integer.parseInt(etnum.getText().toString());
			// �ж��������˻�
			if (tvtitle.getText().equals("�����˻�")) {
				// �ж�num�Ƿ���ڴ����������۵�����Ʒ��ϸ������
				if (productnum < num) {
					Toast.makeText(getApplicationContext(),
							"�˻������ѳ����������������������룡", Toast.LENGTH_SHORT).show();
					etnum.setText(intent.getStringExtra("pdnum"));
					etnum.setFocusable(true);
					a = 0;
					return;
				}
				// num = -num;
				name = spproname.getSelectedItem().toString();
			} else {
				if (num > Integer.parseInt(etkcnum.getText().toString())) {
					Toast.makeText(getApplicationContext(),
							"���������ѳ�����Ʒ������������������룡", Toast.LENGTH_SHORT).show();
					etnum.setFocusable(true);
					a = 0;
					return;
				}
			}

			HashMap<String, String> map = new HashMap<String, String>();
			map.put("productID", name);
			map.put("pdnum", num + "");
			map.put("pdprice", etprice.getText().toString());
			map.put("pdamount", etamount.getText().toString());
			dtlist.add(map);
		} else {
			Toast.makeText(getApplicationContext(), "������������Ϊ0�����������룡",
					Toast.LENGTH_SHORT).show();
			a = 0;
			return;
		}
	}

	public void btnback(View view) {
		if (intent.getStringExtra("productID") == null
				&& tvtitle.getText().toString().equals("���۳���")) {
			// ��תҳ��
			intent = new Intent(SalesDetailAddActivity.this,
					SalesDetailListActivity.class);
			intent.putExtra("title", tvtitle.getText());
			intent.putExtra("amount", amount);
			intent.putExtra("salesman", salesman);
			intent.putExtra("customer", customer);
			if (tvtitle.getText().equals("�����˻�")) {
				intent.putExtra("salesman", intent.getStringExtra("salesman"));
				intent.putExtra("customer", intent.getStringExtra("customer"));
				intent.putExtra("pdnum", productnum + "");
			}
			intent.putExtra("ddbh", ddbh);
			intent.putExtra("dtlist", new JSONArray(dtlist).toString());
			startActivity(intent);
		} else {
			intent = new Intent(SalesDetailAddActivity.this,
					SalesMasterListActivity.class);
			intent.putExtra("title", tvtitle.getText().toString());
			startActivity(intent);
		}
	}

	// ��ȡ��Ʒ
	public void go_productlist(View v) {
		intent = new Intent(SalesDetailAddActivity.this, ProductActivity.class);
		intent.putExtra("title", tvtitle.getText());
		intent.putExtra("salesman", salesman);
		intent.putExtra("customer", customer);
		intent.putExtra("ddbh", ddbh);
		intent.putExtra("dtlist", new JSONArray(dtlist).toString());
		startActivity(intent);
	}

	// ������۵���Ʒ�б�
	public void insertDetail() {
		dtlist = new ArrayList<Map<String, String>>();
		// ����������ݵ�list
		String result = intent.getStringExtra("dtlist");
		if (result != null && !result.equals("")) {
			try {
				JSONArray ja = new JSONArray(result);
				for (int x = 0; x < ja.length(); x++) {
					JSONObject jo = new JSONObject(ja.getString(x));
					Map<String, String> mp = new HashMap<String, String>(); // ����Map���ϣ�����ÿһ������
					mp.put("productID", jo.optString("productID"));
					mp.put("pdnum", String.valueOf(jo.optInt("pdnum")));
					mp.put("pdprice", String.valueOf(jo.optDouble("pdprice")));
					mp.put("pdamount", String.valueOf(jo.optDouble("pdamount")));
					dtlist.add(mp);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (intent.getStringExtra("proprice") != null) {
			// ����Ʒ�������ۣ���ֵ
			btnproname.setText(intent.getStringExtra("proname"));
			Double price = Double.parseDouble(intent.getStringExtra("proprice")
					.toString());
			price = price + price * 0.5;
			BigDecimal b = new BigDecimal(price);
			Double result2 = b.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			etprice.setText(result2 + "");
			amount = Integer.parseInt(etnum.getText().toString())
					* Double.parseDouble(etprice.getText().toString());
			// etamount.setText(String.valueOf(amount));
			b = new BigDecimal(amount);
			Double result1 = b.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			etamount.setText(result1 + "");
			getProductName(btnproname.getText().toString());
			etkcnum.setText(kcnum + "");
		}
	}

	// ������Ʒ�����ʾ��Ʒ��
	public String getProductName(String productid) {
		String uri = "http://10.0.2.2:8080/CosmeticService/GetProductByName.do";
		HttpPost hg = new HttpPost(uri);
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("name", productid));

		HttpClient hc = new DefaultHttpClient();
		try {
			hg.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse hr = hc.execute(hg);
			if (hr.getStatusLine().getStatusCode() == 200) {
				JSONObject jb = new JSONObject(EntityUtils.toString(hr
						.getEntity()));
				productname = jb.optString("productname");
				kcnum = Integer.parseInt(jb.optString("quantity"));
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

	// �ж������Ƿ�Ϊ��
	public boolean IsNullOrZero() {
		if (etnum.getText().toString().equals("")
				|| etnum.getText().toString().equals(null)) {
			etnum.setFocusable(true);
			return false;
		}
		if (etnum.getText().toString().equals("0")) {
			etnum.setFocusable(true);
			return false;
		}
		return true;
	}

	// ������ҳ
	public void btnhome(View v) {
		if (dtlist.size() > 0) {
			Dialog dialog = new AlertDialog.Builder(SalesDetailAddActivity.this)
					.setTitle("��ϸ��δ�ύ��������ҳ���ᶪʧ���Ƿ񷵻���ҳ��")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									fanhui();
								}
							})
					.setNegativeButton("ȡ��",
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

	// ������ҳ
	public void fanhui() {
		Intent intent = new Intent(SalesDetailAddActivity.this,
				MainActivity.class);
		startActivity(intent);
	}
}
