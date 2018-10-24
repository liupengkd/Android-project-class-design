package allinhand.example.purchaseandsupplier;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PurchaseRuMingXi_addActivity extends Activity {

	Intent intent;

	TextView title;
	EditText number;
	EditText price;
	EditText total;
	LinearLayout ll;

	Spinner spiProduct;
	List<String> list = new ArrayList<String>();
	ArrayAdapter<String> adapter = null;

	// ��ϸ
	List<Map<String, String>> detailBack = new ArrayList<Map<String, String>>();
	String suppliername = "";
	String purchaseid = "";
	String pru="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.purchaserumingxi_add);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		title = (TextView) findViewById(R.id.title);
		number = (EditText) findViewById(R.id.number);
		price = (EditText) findViewById(R.id.price);
		total = (EditText) findViewById(R.id.total);
		spiProduct = (Spinner) findViewById(R.id.spproductname);
		ll = (LinearLayout) findViewById(R.id.ll);

		intent = getIntent();
		title.setText(intent.getStringExtra("title"));
		getAllProductName();
		if (intent.getStringExtra("product").equals("")) {
			// ���
			if (list.size() > 0) {
				list.clear();
			}
			if (allDetail.size() > 0) {
				allDetail.clear();
			}
			// �õ��ϸ�ҳ�洫��������ϸ
			getPastDetail();
			suppliername = intent.getStringExtra("supplier");
			purchaseid = intent.getStringExtra("purchaseid");
			if (title.getText().equals("�ɹ��˻�")) {
				pru = intent.getStringExtra("pru");
				// ͨ��id�õ�һ��������ϸ
				getDetailByPId(pru);
				String pr=allDetail.get(0).get("price");
				price.setText(pr);
				price.setEnabled(false);
				
				
			} else {
				getProductBySupplier(suppliername);

			}

			number.setText(allDetail.get(0).get("quantity"));
			
			for (int i = 0; i < allDetail.size(); i++) {
				list.add(allDetail.get(i).get("product"));
			}
			
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, list);
			adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
			spiProduct.setAdapter(adapter);
			
			changeTotal();
		} else {
			// �鿴
			
			for (int i = 0; i < proname.size(); i++) {
				list.add(proname.get(i).get("product"));
			}
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, list);
			adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
			spiProduct.setAdapter(adapter);
			spiProduct.setSelection(
					proMap.get(intent.getStringExtra("product")), true);
			number.setText(intent.getStringExtra("count"));
			price.setText(intent.getStringExtra("price"));
			total.setText(intent.getStringExtra("sum"));
			spiProduct.setEnabled(false);
			number.setEnabled(false);
			price.setEnabled(false);
			ll.setVisibility(View.GONE);
		}
		spiProduct
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						number.setText(allDetail.get(arg2).get("quantity"));
						if(title.getText().toString().equals("�ɹ����")){
							int i = 0;
							for (Map<String, String> m : detailBack) {
								if (spiProduct.getSelectedItem().toString()
										.equals(m.get("product"))) {
									i = 1;
									price.setText(m.get("price"));
									break;
								}
							}
							if (i == 0) {
								price.setText("0.0");
							}
						}
						changeTotal();

					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		number.addTextChangedListener(new TextWatcher() {

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
				changeTotal();
			}
		});

		price.addTextChangedListener(new TextWatcher() {

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
				changeTotal();
			}
		});
	}

	// �жϷǿղ�Ϊ0
	// �˻�����С�ڲɹ�������Сֵ
	public boolean IsNullOrZero() {
		if (number.getText().toString().equals("")
				|| number.getText().toString().equals(null)) {
			number.setFocusable(true);
			return false;
		}
		if (number.getText().toString().equals("0")) {
			number.setFocusable(true);
			return false;
		}
		if (price.getText().toString().equals("")
				|| price.getText().toString().equals(null)) {
			price.setFocusable(true);
			return false;
		}
		if (price.getText().toString().equals("0.0")) {
			price.setFocusable(true);
			return false;
		}
		if (price.getText().toString().equals("0.00")) {
			price.setFocusable(true);
			return false;
		}
		if (price.getText().toString().equals("0.000")) {
			price.setFocusable(true);
			return false;
		}
		
		if (title.getText().toString().equals("�ɹ��˻�")) {
			// ������allDetail���������Ƚϣ��õ�С�ģ���Ϊ�Ƚ�����
			int num1 = 0;
			int num2 = 0;
			int result = 0;
		
			//����������Ʒ����
			for (int i = 0; i < allDetail.size(); i++) {
				if (spiProduct.getSelectedItem().toString()
						.equals(allDetail.get(i).get("product"))) {
					num1 = Integer.parseInt(allDetail.get(i).get("quantity"));
					break;
				}
			}

			getAllProductName();
			//��Ʒ�����Ʒ����
			for (int i = 0; i < proname.size(); i++) {
				if (spiProduct.getSelectedItem().toString()
						.equals(proname.get(i).get("product"))) {
					num2 = Integer.parseInt(proname.get(i).get("number"));
					break;
				}
			}
			//ȡ���߽�Сֵ
			if (num1 < num2) {
				result = num1;
			}else{
				result = num2;
			}
			int num = 0;
			for (Map<String, String> map : detailBack) {
				if (map.get("product").equals(
						spiProduct.getSelectedItem().toString())) {
					num = Integer.parseInt(map.get("number"));
					detailBack.remove(map);
					break;
				}
			}
			if ((Integer.parseInt(number.getText().toString()) + num) > result) {
				return false;
			}
		}
		return true;
	}

	// �����ܽ��
	public String changeTotal() {
		String mes = "0.0";
		if (IsNullOrZero()) {
			Double result = (Integer.parseInt(number.getText().toString()) * Double
					.parseDouble(price.getText().toString()));
			BigDecimal b = new BigDecimal(result);
			Double result1 = b.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			total.setText(result1 + "");
			mes = result1 + "";
		}
		return mes;
	}

	// �õ����е���Ʒ��������
	List<Map<String, String>> proname = new ArrayList<Map<String, String>>();

	public void getAllProductName() {
		String url = "http://10.0.2.2:8080/CosmeticService/GetAllProduct.do";
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
				int x=0;
				for (int i = 0; i < jsonArray.length(); i++) {
					Map<String, String> map = new HashMap<String, String>();
					JSONObject jo = jsonArray.optJSONObject(i);
					map.put("product",
							String.valueOf(jo.optString("productname")));
					map.put("number", String.valueOf(jo.optString("quantity")));
					proname.add(map);
					if(title.getText().toString().equals("�ɹ����")||(!(intent.getStringExtra("product").equals("")))){
						proMap.put(String.valueOf(jo.optString("productname")),x);
						x++;
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �õ��ϸ�ҳ�洫��������ϸ
	public void getPastDetail() {
		String detail = intent.getStringExtra("detailFromAdd");

		try {
			JSONArray ja = new JSONArray(detail);
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = new JSONObject(ja.getString(i));
				Map<String, String> map = new HashMap<String, String>();
				map.put("product", jo.optString("product"));
				map.put("number", jo.optString("number"));
				map.put("price", jo.optString("price"));
				map.put("sum", jo.optString("sum"));
				map.put("productid", jo.optString("productid"));
				detailBack.add(map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ͨ��id�õ�һ��������ϸ
	Map<String, Integer> proMap = new HashMap<String, Integer>();
	List<Map<String, String>> allDetail = new ArrayList<Map<String, String>>();
	Map<String, String> pro = new HashMap<String, String>();

	public void getDetailByPId(String pru) {

		if(proMap.size()>0){
			proMap.clear();
		}
		String uri = "http://10.0.2.2:8080/CosmeticService/purchasegetonebyidru.do";
		HttpPost hp = new HttpPost(uri);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("purchaseid", pru));
		HttpClient hc = new DefaultHttpClient();
		try {
			hp.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse hr = hc.execute(hp);
			if (hr.getStatusLine().getStatusCode() == 200) {
				JSONObject jo = new JSONObject(EntityUtils.toString(hr
						.getEntity()));

				JSONArray js = new JSONArray(jo.optString("allDetail"));

				for (int j = 0; j < js.length(); j++) {

					Map<String, String> purchaseDetail = new HashMap<String, String>();
					JSONObject ob = new JSONObject(js.optJSONObject(j)
							.toString());
					purchaseDetail.put("product",
							getOneProductName(ob.optString("productid")));
					proMap.put(getOneProductName(ob.optString("productid")), j);
					pro.put(getOneProductName(ob.optString("productid")),
							ob.optString("productid"));
					purchaseDetail.put("quantity",
							ob.optString("purchasequantity"));
					purchaseDetail.put("price",
							ob.optString("purchaseunitprice"));
					allDetail.add(purchaseDetail);

				}
				
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ͨ��productid�õ�productname
	public String getOneProductName(String productId) {
		String name = "";
		JSONObject jo = new JSONObject();

		String url = "http://10.0.2.2:8080/CosmeticService/GetOneProductName.do";
		// ȡ��HttpClient����
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

	// ���ݹ�Ӧ�̵õ���Ʒ
	public void getProductBySupplier(String supplierid) {
		if (allDetail.size() > 0) {
			allDetail.clear();
		}
		String url = "http://10.0.2.2:8080/CosmeticService/getProductByNameID.do";
		// HttpPost���Ӷ������ÿͻ����ύ��ʽ
		HttpPost httpPost = new HttpPost(url);
		// ȡ��HttpClient����
		HttpClient client = new DefaultHttpClient();
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("nameid", supplierid));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String losespillList = EntityUtils.toString(response
						.getEntity());
				JSONArray jsonArray = new JSONArray(losespillList);

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);
					Map<String, String> map = new HashMap<String, String>();
					map.put("product",
							String.valueOf(jo.optString("productname")));
					map.put("quantity",
							String.valueOf(jo.optString("quantity")));
					// map.put("b",
					// String.valueOf(jo.optString("maxSafeStock")));
					// map.put("c", String.valueOf(jo.optString("safeStock")));
					map.put("suppliername",
							String.valueOf(jo.optString("suppliername")));
					map.put("productid",
							String.valueOf(jo.optString("productid")));
					// map.put("f",
					// String.valueOf(jo.optDouble("productprice")));
					proMap.put(getOneProductName(jo.optString("productid")), i);
					pro.put(getOneProductName(jo.optString("productid")),
							jo.optString("productid"));
					allDetail.add(map);

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// ���
	public void save(View v) {

		insert();
		intent = new Intent(PurchaseRuMingXi_addActivity.this,
				PurchaseRuMingXiActivity.class);
		intent.putExtra("title", title.getText());
		intent.putExtra("bh", purchaseid);
		intent.putExtra("list", new JSONArray(detailBack).toString());
		intent.putExtra("supplier", suppliername);	
		
		intent.putExtra("pru", pru);
		startActivity(intent);
	

	}

	// ��һ��
	int m = 0;

	public void zsave(View v) {

		if (m < allDetail.size() - 1) {
			insert();
			if (title.getText().equals("�ɹ��˻�")) {
				// ����˻���
				
				if (allDetail.size() > 0) {
					m++;
					spiProduct.setSelection(
							proMap.get(allDetail.get(m).get("product")), true);
					number.setText(allDetail.get(m).get("quantity"));
					price.setText(allDetail.get(m).get("price"));
					BigDecimal b = new BigDecimal((Integer
							.parseInt(number.getText().toString()) * Double
							.parseDouble(price.getText().toString())));
					Double result = b.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					total.setText(result+ "");

				}
			} else {
				// ��ӽ�����
				if (allDetail.size() > 0) {
					m++;
					spiProduct.setSelection(
							proMap.get(allDetail.get(m).get("product")), true);
					number.setText(allDetail.get(m).get("quantity"));
					int i = 0;
					for (Map<String, String> m : detailBack) {

						if (spiProduct.getSelectedItem().toString()
								.equals(m.get("product"))) {
							i = 1;
							price.setText(m.get("price"));
							break;
						}
					}
					if (i == 0) {
						price.setText("0.0");
					}
				}
			}

		} else {
			Toast.makeText(this, "�������һ����Ʒ�ˡ�", Toast.LENGTH_LONG);
		}
	}

	// ��ӵ�detailBack�����ϸ�ҳ��
	public void insert() {
		if (IsNullOrZero()) {
			int num = 0;

			for (Map<String, String> map : detailBack) {
				if (map.get("product").equals(
						spiProduct.getSelectedItem().toString())) {
					num = Integer.parseInt(map.get("number"));
					detailBack.remove(map);
					break;
				}
			}
			Map<String, String> mp = new HashMap<String, String>();
			mp.put("product", spiProduct.getSelectedItem().toString());
			mp.put("productid",
					pro.get(spiProduct.getSelectedItem().toString()));

			mp.put("number",
					(num + Integer.parseInt(number.getText().toString())) + "");
			mp.put("price", price.getText().toString());
			String sum = "";
			if (mp.get("price").contains(".")) {
				sum = (Integer.parseInt(mp.get("number")) * Double
						.parseDouble(mp.get("price"))) + "";
			} else {
				sum = (Integer.parseInt(mp.get("number")) * Integer.parseInt(mp
						.get("price"))) + "";
			}
			BigDecimal b = new BigDecimal(sum);
			Double result = b.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			mp.put("sum", result + "");
			detailBack.add(mp);
		} else {
			if(title.getText().toString().equals("�ɹ����")){
				Toast.makeText(this, "�ǿ��Ҳ�Ϊ��", Toast.LENGTH_LONG).show();
			}else{
				Toast.makeText(this, "�ǿ��Ҳ�Ϊ��,����С�ڿ����ɹ���ⵥ��Сֵ", Toast.LENGTH_LONG).show();
			}
			
		}

	}

	// ����
	public void btnback(View view) {
		finish();
	}
	public void btnhome(View v){
		if(detailBack.size()>0){
			Dialog dialog = new AlertDialog.Builder(
					PurchaseRuMingXi_addActivity.this)
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
		}else{
			fanhui();
		}
	}
	//������ҳ
	public void fanhui(){
		Intent intent = new Intent(PurchaseRuMingXi_addActivity.this,MainActivity.class);
		startActivity(intent);
	}
}
