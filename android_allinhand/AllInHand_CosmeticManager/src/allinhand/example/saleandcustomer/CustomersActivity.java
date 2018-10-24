package allinhand.example.saleandcustomer;

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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class CustomersActivity extends Activity {

	private ListView listview;
	private AutoCompleteTextView ACTV;

	private List<Map<String, String>> list = new ArrayList<Map<String, String>>(); // ������ʾ�����ݰ�װ
	private List<String> items;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customers);
        AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
        listview = (ListView)findViewById(R.id.Cutstomerlistview);
        ACTV = (AutoCompleteTextView)findViewById(R.id.SelectCustomerText);
        getAllCustomer();
        getCustomerNameOrId();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String id = list.get(arg2).get("customerid");
				String name = list.get(arg2).get("customername");
				String phone = list.get(arg2).get("telephone");
				String address = list.get(arg2).get("customeraddress");
				// TODO Auto-generated method stub
				Intent intent = new Intent(CustomersActivity.this,CustomersUpdateActivity.class);
				intent.putExtra("value", "1");
				intent.putExtra("customerid", id);
				intent.putExtra("customername", name);
				intent.putExtra("telephone", phone);
				intent.putExtra("customeraddress", address);
				startActivity(intent);
			}
		});
        //���������¼����ݱ��ɾ���ͻ�
        listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				final String cusid = list.get(arg2).get("customerid");
				Dialog dialog = new AlertDialog.Builder(CustomersActivity.this)
				.setTitle("�Ƿ�ɾ����")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						try {
							String uri = "http://10.0.2.2:8080/CosmeticService/deleteCustomer.do?cusid="
									+ cusid;
							HttpGet hp = new HttpGet(uri);

							HttpClient hc = new DefaultHttpClient();

							HttpResponse hr = hc.execute(hp);
							if (hr.getStatusLine()
									.getStatusCode() == 200) {

								String str = EntityUtils
										.toString(hr
												.getEntity());

								if (str.trim()
										.equals("success")) {
									Toast.makeText(
											getApplicationContext(),
											"ɾ���ɹ�",
											Toast.LENGTH_SHORT)
											.show();
									getAllCustomer();
								}else if(str.trim()
										.equals("notdelete"))
								{Toast.makeText(
										getApplicationContext(),
										"������ɾ����",
										Toast.LENGTH_SHORT)
										.show();}
								else {
									Toast.makeText(
											getApplicationContext(),
											"����ɾ��",
											Toast.LENGTH_SHORT)
											.show();
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).create() ;
			dialog.show() ;
			return false;
			}
		});
        //���ݿͻ�����ѯ�ͻ�
        ACTV.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (ACTV.getText().toString().equals("")) {
					getAllCustomer();
				}else{
					getCustomerByName();
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
	}
    public void AddCustomer(View v){
    	Intent intent = new Intent(CustomersActivity.this,CustomersUpdateActivity.class);
    	intent.putExtra("value", "2");
    	startActivity(intent);
    }
    public void btnback(View view){
    	Intent intent = new Intent(CustomersActivity.this,MainActivity.class);
    	startActivity(intent);
    }
    public void getAllCustomer(){
    	list = new ArrayList<Map<String, String>>();
    	String url = "http://10.0.2.2:8080/CosmeticService/SelectAllCustomer.do";
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
				for (int i = 1; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);
					Map<String, String> map = new HashMap<String, String>();
					map.put("customerid", String.valueOf(jo.optString("customerid")));
					map.put("customername", String.valueOf(jo.optString("customername")));
					map.put("telephone", String.valueOf(jo.optString("telephone")));
					map.put("customeraddress", String.valueOf(jo.optString("customeraddress")));
					this.list.add(map);
				}

				SimpleAdapter adapter = new SimpleAdapter(
						this,
						this.list,
						R.layout.customers_list,
						new String[] { "customername", "telephone", "customeraddress" } // Map�е�key������
						,
						new int[] { R.id.CusNameli,R.id.CusPhoneli,R.id.CusAdli });
				listview.setAdapter(adapter);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    public void getCustomerByName(){
    	list= new ArrayList<Map<String, String>>();
		String cusname=ACTV.getText().toString();
		String url="http://10.0.2.2:8080/CosmeticService/getCustomerByName.do";
		//HttpPost���Ӷ������ÿͻ����ύ��ʽ
		HttpPost httpPost=new HttpPost(url);
		//ȡ��HttpClient����
		HttpClient client=new DefaultHttpClient();
		List<NameValuePair> param=new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("Byname",cusname));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(param,"utf-8"));
			HttpResponse response=client.execute(httpPost);
			if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				String losespillList=EntityUtils.toString(response.getEntity());
				JSONObject jsonObject=new JSONObject(losespillList);
				Map<String,String> items=new HashMap<String, String>();
				items.put("customername",String.valueOf(jsonObject.optString("customername")));
				items.put("customerid",String.valueOf(jsonObject.optInt("customerid")));
				items.put("telephone", String.valueOf(jsonObject.optString("telephone")));
				items.put("customeraddress", String.valueOf(jsonObject.optString("customeraddress")));
				list.add(items);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		SimpleAdapter adapter = new SimpleAdapter(
				this,
				this.list,
				R.layout.customers_list,
				new String[] {"customername", "telephone", "customeraddress" } // Map�е�key������
				,
				new int[] { R.id.CusNameli,R.id.CusPhoneli,R.id.CusAdli });
		listview.setAdapter(adapter);
	}
    public void getCustomerNameOrId(){
    	items = new ArrayList<String>();
    	String url="http://10.0.2.2:8080/CosmeticService/getCustomerNameOrId.do";
    	//HttpGet���Ӷ������ÿͻ����ύ��ʽ
    	HttpGet httpGet=new HttpGet(url);
    	//ȡ��HttpClient����
    	HttpClient client=new DefaultHttpClient();
    	try {
			//��ȡHttpResponse����
    		HttpResponse response=client.execute(httpGet);
    		//���������ȷ�ӷ���������
    		if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				String nameandid=EntityUtils.toString(response.getEntity());
				JSONArray jsonArray=new JSONArray(nameandid);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);
					items.add(String.valueOf(jo.optString("customername")));
			    }
    		}
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        ACTV.setAdapter(aa);
    }

}
