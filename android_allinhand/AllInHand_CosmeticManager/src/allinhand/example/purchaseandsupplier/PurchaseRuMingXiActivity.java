package allinhand.example.purchaseandsupplier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class PurchaseRuMingXiActivity extends Activity {

	Intent intent;

	TextView title;
	ListView lv;
	TextView orderid;
	TextView total;
	Button newDetail;
	Button submit;

	String supplier = null;
	String pru = null;
	 SimpleAdapter adapter=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.purchaserumingxi);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		lv = (ListView) findViewById(R.id.lv);
		title = (TextView) findViewById(R.id.title);
		orderid = (TextView) findViewById(R.id.orderid);
		total = (TextView) findViewById(R.id.total);
		newDetail = (Button) findViewById(R.id.newDetail);
		submit = (Button) findViewById(R.id.submit);

		intent = getIntent();
		title.setText(intent.getStringExtra("title"));
		orderid.setText(intent.getStringExtra("bh"));
		total.setText(intent.getStringExtra("total"));
		if (intent.getStringExtra("supplier").equals("")) {
			// �鿴
			newDetail.setVisibility(View.GONE);
			submit.setVisibility(View.GONE);
			// ����mapֵ���������listview
			try {
				JSONArray jsonArray = new JSONArray(
						intent.getStringExtra("list"));
				
				
				for (int x = 0; x < jsonArray.length(); x++) {
					String temp = jsonArray.optString(x);
					JSONObject jb = new JSONObject(temp);
					Map<String, String> map = new HashMap<String, String>(); // ����Map���ϣ�����ÿһ������
					map.put("product", jb.optString("product"));
					map.put("number",jb.optString("purchasequantity"));
					map.put("price", jb.optString("purchaseunitprice"));
					Double result = (Integer.parseInt(jb
							.optString("purchasequantity")) * Double.parseDouble(jb
									.optString("purchaseunitprice")));
					BigDecimal b = new BigDecimal(result);
					Double result1 = b.setScale(2, BigDecimal.ROUND_HALF_UP)
							.doubleValue();
					map.put("sum",""+ result1);
					map.put("productid", jb.optString("productid"));
					this.detailBack.add(map); // ���������е�������
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			// ���
			if (title.getText().equals("�ɹ��˻�")) {
				pru = intent.getStringExtra("pru");
			}
			// ���湩Ӧ��
			supplier = intent.getStringExtra("supplier");
			getPastDetail();
			
		}

		adapter = new SimpleAdapter(this, this.detailBack,
				R.layout.product_list, new String[] { "product", "number",
						"price", "sum" } // Map�е�key������
				, new int[] { R.id.product, R.id.count, R.id.price, R.id.sum });

		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				intent = new Intent(PurchaseRuMingXiActivity.this,
						PurchaseRuMingXi_addActivity.class);
				intent.putExtra("title", title.getText());
				intent.putExtra("product", detailBack.get(arg2).get("product"));
				intent.putExtra("count", detailBack.get(arg2).get("number"));
				intent.putExtra("price", detailBack.get(arg2).get("price"));
				intent.putExtra("sum", detailBack.get(arg2).get("sum"));
				startActivity(intent);
			}
		});
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(newDetail.getVisibility()!=View.GONE){
					Dialog dialog = new AlertDialog.Builder(
							PurchaseRuMingXiActivity.this)
							.setTitle("ȷ��ɾ����")
							.setPositiveButton("ȷ��",
									new DialogInterface.OnClickListener() {

										public void onClick(DialogInterface dialog,
												int which) {
											detailBack.remove(arg2);
											adapter = new SimpleAdapter(PurchaseRuMingXiActivity.this, detailBack,
													R.layout.product_list, new String[] { "product", "number",
															"price", "sum" } // Map�е�key������
													, new int[] { R.id.product, R.id.count, R.id.price, R.id.sum });
											lv.setAdapter(adapter);
										}
									})
							.setNegativeButton("ȡ��",
									new DialogInterface.OnClickListener() {

										public void onClick(DialogInterface dialog,
												int which) {

										}
									}).create();
					dialog.show();
				}
				return false;
			}
		});
	}

	List<Map<String, String>> detailBack = new ArrayList<Map<String, String>>();

	// �½���ϸ
	public void new_detail(View v) {
		intent = new Intent(PurchaseRuMingXiActivity.this,
				PurchaseRuMingXi_addActivity.class);
		intent.putExtra("title", title.getText());
		intent.putExtra("supplier", supplier);
		intent.putExtra("product", "");
		intent.putExtra("purchaseid", orderid.getText().toString());
		if (title.getText().equals("�ɹ��˻�")) {
			intent.putExtra("pru", pru);
		}
		intent.putExtra("detailFromAdd", new JSONArray(detailBack).toString());
		startActivity(intent);
	}

	// �õ��ϸ�ҳ�洫��������ϸ,�������ܽ��
	public void getPastDetail() {
		String detail = intent.getStringExtra("list");
		try {
			JSONArray ja = new JSONArray(detail);
			double subtotal=0.0;
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = new JSONObject(ja.getString(i));
				Map<String, String> map = new HashMap<String, String>();
				map.put("product", jo.optString("product"));
				map.put("productid",jo.optString("productid"));
				map.put("number", jo.optString("number"));
				map.put("price", jo.optString("price"));
				map.put("sum", jo.optString("sum"));
				subtotal=subtotal+Double.parseDouble(map.get("sum"));
				detailBack.add(map);
			}
			total.setText(subtotal+"");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �ύ��ϸ
	public void submit(View v) {
		intent = new Intent(PurchaseRuMingXiActivity.this,
				PurchaseRu_addActivity.class);
		
		intent.putExtra("title", title.getText());
		intent.putExtra("detailFromAdd", new JSONArray(detailBack).toString());
		intent.putExtra("pru", orderid.getText().toString());
		intent.putExtra("supplier", supplier);
		intent.putExtra("purchaseid", "1");
		startActivity(intent);
		
	}

	public void btnback(View view) {
		finish();
	}
	public void btnhome(View v){
		if(detailBack.size()>0){
			Dialog dialog = new AlertDialog.Builder(
					PurchaseRuMingXiActivity.this)
					.setTitle("������δ�ύ��������ҳ���ᶪʧ���Ƿ񷵻���ҳ��")
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
		Intent intent = new Intent(PurchaseRuMingXiActivity.this,MainActivity.class);
		startActivity(intent);
	}
}
