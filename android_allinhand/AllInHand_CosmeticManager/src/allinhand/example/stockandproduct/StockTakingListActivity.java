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
	private List<Map<String, String>> list=new ArrayList<Map<String,String>>(); // 定义显示的内容包装
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

    //返回按钮事件
    public void btnback(View view){
    	Intent intent=new Intent(StockTakingListActivity.this,MainActivity.class);
    	startActivity(intent);
    }
    //进行盘点按钮事件
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
    	//HttpGet连接对象，设置客户端提交方式
    	HttpGet httpGet=new HttpGet(url);
    	//取得HttpClient对象
    	HttpClient client=new DefaultHttpClient();
    	try {
			//获取HttpResponse对象
    		HttpResponse response=client.execute(httpGet);
    		//如果数据正确从服务器返回
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
		//HttpGet连接对象，设置客户端提交方式
		HttpGet httpGet=new HttpGet(url);
		//取得HttpClient对象
		HttpClient client=new DefaultHttpClient();
		try {
			//获取HttpResponse对象
			HttpResponse response=client.execute(httpGet);
			//如果数据正确从服务器返回
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
		//HttpPost连接对象，设置客户端提交方式
		HttpPost httpPost=new HttpPost(url);
		//取得HttpClient对象
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
	//将服务器端返回的值添加到list中
	public void addList(JSONObject jsonObject){
		Map<String,String> items=new HashMap<String, String>();
		items.put("losespillId", String.valueOf(jsonObject.optString("losespillid")));
		items.put("productname", String.valueOf(jsonObject.opt("productname")));
		items.put("type", String.valueOf(jsonObject.optString("type")));
		items.put("stockcount", String.valueOf(jsonObject.optInt("counts")));
		items.put("checkdate", String.valueOf(jsonObject.optString("checkdate")));
		list.add(items);
	}
	//将list中的数据填充到适配器中，并填充到listview中
	public void addListView(){
		SimpleAdapter adapter = new SimpleAdapter(this, this.list,
				R.layout.stocktaklist, new String[] {"losespillId", "productname", "type",
						"stockcount" ,"checkdate"} // Map中的key的名称
				, new int[] {R.id.stocktaklist_Id, R.id.stocktaklist_name,R.id.stocktype,R.id.stocktaklist_count,R.id.stocktaklistdate});
		
        listView.setAdapter(adapter);
	}
}
