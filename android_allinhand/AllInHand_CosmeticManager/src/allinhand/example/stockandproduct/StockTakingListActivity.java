package allinhand.example.stockandproduct;

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
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class StockTakingListActivity extends Activity implements OnClickListener {
	Button checking;
	ListView listView;
	AutoCompleteTextView autoText;
	private List<Map<String, String>> list=new ArrayList<Map<String,String>>(); // ������ʾ�����ݰ�װ
	List items=new ArrayList();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_stock_taking_list1);
        AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
        checking=(Button)findViewById(R.id.checking);
        checking.setOnClickListener(this);	
        listView=(ListView)findViewById(R.id.stockTalking);
        autoText=(AutoCompleteTextView)findViewById(R.id.inputname);
        getProductNameOrId();
		getAllLoseSpills();
        autoText.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (autoText.getText().toString().equals("")) {
					
					getAllLoseSpills();
				}else {
					getLoseSpillByName();
				}
				
			}
		});
       
        
    }

    //���ذ�ť�¼�
    public void btnback(View view){
    	Intent intent=new Intent(StockTakingListActivity.this,MainActivity.class);
    	startActivity(intent);
    }
    //�����̵㰴ť�¼�
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=null;
		switch (v.getId()) {
		case R.id.checking:
			intent=new Intent(StockTakingListActivity.this,StockTakingActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	public void getProductNameOrId(){
		String url="http://10.0.2.2:8080/CosmeticService/getProductNameOrId.do";
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
				if (!nameandid.trim().equals("fali")) {
					JSONArray jsonArray=new JSONArray(nameandid);
					int j = 0;
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jo = jsonArray.optJSONObject(i);
						
						items.add(j,String.valueOf(jo.optString("productname")));
						j++;
						items.add(j,String.valueOf(jo.optString("productid")));
						j++;
				    }
				}
				
				
    		}
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        AutoCompleteTextView actv=(AutoCompleteTextView)findViewById(R.id.inputname);
        actv.setAdapter(aa);
	}
	public void getAllLoseSpills(){
		list.clear();
		String url="http://10.0.2.2:8080/CosmeticService/getAllLoseSpills.do";
		//HttpGet���Ӷ������ÿͻ����ύ��ʽ
		HttpGet httpGet=new HttpGet(url);
		//ȡ��HttpClient����
		HttpClient client=new DefaultHttpClient();
		try {
			//��ȡHttpResponse����
			HttpResponse response=client.execute(httpGet);
			//���������ȷ�ӷ���������
			if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				String losespillList=EntityUtils.toString(response.getEntity());
				if (!losespillList.trim().equals("fail")) {
					JSONArray jsonArray=new JSONArray(losespillList);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jsonObject=jsonArray.optJSONObject(i);
						addList(jsonObject);
						
					}
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		addListView();
	}
	public void getLoseSpillByName(){
		list.clear();
		String nameorid=autoText.getText().toString();
		String url="http://10.0.2.2:8080/CosmeticService/getLoseSpillByName.do";
		//HttpPost���Ӷ������ÿͻ����ύ��ʽ
		HttpPost httpPost=new HttpPost(url);
		//ȡ��HttpClient����
		HttpClient client=new DefaultHttpClient();
		List<NameValuePair> param=new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("name",nameorid));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(param,"utf-8"));
			HttpResponse response=client.execute(httpPost);
			if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				String losespillList=EntityUtils.toString(response.getEntity());
				if (!losespillList.trim().equals("fail")) {
					JSONObject jsonObject=new JSONObject(losespillList);
					addList(jsonObject);
					
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		addListView();
	}
	//���������˷��ص�ֵ��ӵ�list��
	public void addList(JSONObject jsonObject){
		Map<String,String> items=new HashMap<String, String>();
		items.put("losespillId", String.valueOf(jsonObject.optString("losespillid")));
		items.put("productname", String.valueOf(jsonObject.opt("productname")));
		items.put("type", String.valueOf(jsonObject.optString("type")));
		items.put("stockcount", String.valueOf(jsonObject.optInt("counts")));
		items.put("checkdate", String.valueOf(jsonObject.optString("checkdate")));
		list.add(items);
	}
	//��list�е�������䵽�������У�����䵽listview��
	public void addListView(){
		SimpleAdapter adapter = new SimpleAdapter(this, this.list,
				R.layout.stocktaklist, new String[] {"losespillId", "productname", "type",
						"stockcount" ,"checkdate"} // Map�е�key������
				, new int[] {R.id.stocktaklist_Id, R.id.stocktaklist_name,R.id.stocktype,R.id.stocktaklist_count,R.id.stocktaklistdate});
		
        listView.setAdapter(adapter);
	}
}
