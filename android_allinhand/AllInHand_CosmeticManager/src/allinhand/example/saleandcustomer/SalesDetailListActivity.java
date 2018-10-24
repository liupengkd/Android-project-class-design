package allinhand.example.saleandcustomer;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ������ϸ�б�
 * 
 * @author ����
 * 
 */
public class SalesDetailListActivity extends Activity {

	Intent intent = null;
	TextView tvtitle;
	ListView detList;
	TextView tvtotal;
	TextView tvddbh;

	SimpleAdapter adapter = null;
	// ��Ʒ�ܽ��(����*����)
	Double amount = 0.00;
	// ������Ʒ�ܽ��
	Double total = 0.00;
	// ���۵�������Ա�Ϳͻ���
	String salesman = "";
	String customer = "";
	// �˻�����
	int productnum = 0;
	// ������ϸ
	private List<Map<String, String>> dtlist = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sales_detail_list);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		detList = (ListView) findViewById(R.id.lv);
		tvtitle = (TextView) findViewById(R.id.tvtitle);
		tvtotal = (TextView) findViewById(R.id.tvtotal);
		tvddbh = (TextView) findViewById(R.id.tvddbh);

		intent = getIntent();
		tvtitle.setText(intent.getStringExtra("title"));
		tvddbh.setText(intent.getStringExtra("ddbh"));
		tvtotal.setText("0.00");
		// �ж��������˻�
		if (tvtitle.getText().toString().equals("�����˻�")) {
			// Ҫ�˵���Ʒԭ����
			productnum = Integer.parseInt(intent.getStringExtra("pdnum")
					.toString());
		}
		// ���������ϸ
		insert();
		tvtotal.setText(total + "");
		salesman = intent.getStringExtra("salesman");
		customer = intent.getStringExtra("customer");
	}

	// ������۵���ϸ
	public void go_detailadd(View v) {
		intent = new Intent(SalesDetailListActivity.this,
				SalesDetailAddActivity.class);
		intent.putExtra("title", tvtitle.getText());
		intent.putExtra("dtlist", new JSONArray(dtlist).toString());
		intent.putExtra("salesman", salesman);
		intent.putExtra("customer", customer);
		intent.putExtra("pdnum", productnum + "");
		intent.putExtra("ddbh", tvddbh.getText().toString());
		startActivity(intent);
	}

	// �ύ������ϸ
	public void go_addmaster(View v) {
		if (dtlist.size() > 0) {

			// ��ת
			intent = new Intent(SalesDetailListActivity.this,
					SalesAddActivity.class);

			intent.putExtra("title", tvtitle.getText());
			intent.putExtra("dtlist", new JSONArray(dtlist).toString());
			intent.putExtra("total", tvtotal.getText().toString());
			intent.putExtra("salesman", salesman);
			intent.putExtra("customer", customer);
			intent.putExtra("pdnum", productnum + "");
			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(), "��ϸ����Ϊ�գ��������ϸ��",
					Toast.LENGTH_SHORT).show();
		}
	}

	// ���������ϸ����
	public void insert() {
		dtlist = new ArrayList<Map<String, String>>();
		// ����������ݵ�list
		String result = intent.getStringExtra("dtlist");
		if (result != null && !result.equals("")) {
			try {
				// ����
				JSONArray ja = new JSONArray(result);
				for (int x = 0; x < ja.length(); x++) {
					JSONObject jo = new JSONObject(ja.getString(x));
					Map<String, String> mp = new HashMap<String, String>(); // ����Map���ϣ�����ÿһ������
					mp.put("productID", jo.optString("productID"));
					mp.put("pdnum", String.valueOf(jo.optInt("pdnum")));
					mp.put("pdprice", String.valueOf(jo.optDouble("pdprice")));
					amount = jo.optInt("pdnum") * jo.optDouble("pdprice");
					total += amount;
					mp.put("pdamount", String.valueOf(amount));
					dtlist.add(mp);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		adapter = new SimpleAdapter(this, this.dtlist, R.layout.product_list,
				new String[] { "productID", "pdnum", "pdprice", "pdamount" } // Map�е�key������
				, new int[] { R.id.product, R.id.count, R.id.price, R.id.sum });
		// �󶨵�listview
		detList.setAdapter(adapter);
		detList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// ��ϸ
				intent = new Intent(SalesDetailListActivity.this,
						SalesDetailAddActivity.class);
				intent.putExtra("title", tvtitle.getText());
				intent.putExtra("productID", dtlist.get(arg2).get("productID"));
				intent.putExtra("pdnum", dtlist.get(arg2).get("pdnum"));
				intent.putExtra("pdprice", dtlist.get(arg2).get("pdprice"));
				intent.putExtra("pdamount", dtlist.get(arg2).get("pdamount"));
				startActivity(intent);
			}
		});
		detList.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				Dialog dialog = new AlertDialog.Builder(
						SalesDetailListActivity.this)
						.setTitle("ȷ��ɾ����")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										dtlist.remove(arg2);
										adapter = new SimpleAdapter(
												SalesDetailListActivity.this,
												dtlist, R.layout.product_list,
												new String[] { "productID",
														"pdnum", "pdprice",
														"pdamount" } // Map�е�key������
												, new int[] { R.id.product,
														R.id.count, R.id.price,
														R.id.sum });
										// �󶨵�listview
										detList.setAdapter(adapter);
									}
								})
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).create();
				dialog.show();
				return false;
			}
		});
	}

	public void btnback(View view) {
		if (tvtitle.getText().toString().equals("���۳���")) {
			// ��ת
			intent = new Intent(SalesDetailListActivity.this,
					SalesAddActivity.class);

			intent.putExtra("title", tvtitle.getText());
			intent.putExtra("dtlist", new JSONArray(dtlist).toString());
			intent.putExtra("total", tvtotal.getText().toString());
			intent.putExtra("salesman", salesman);
			intent.putExtra("customer", customer);
			intent.putExtra("pdnum", productnum + "");
			startActivity(intent);
		} else {
			finish();
		}
	}

	//������ҳ
	public void btnhome(View v) {
		if (dtlist.size() > 0) {
			Dialog dialog = new AlertDialog.Builder(
					SalesDetailListActivity.this)
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
		} else {
			fanhui();
		}
	}

	// ������ҳ
	public void fanhui() {
		Intent intent = new Intent(SalesDetailListActivity.this,
				MainActivity.class);
		startActivity(intent);
	}
}
