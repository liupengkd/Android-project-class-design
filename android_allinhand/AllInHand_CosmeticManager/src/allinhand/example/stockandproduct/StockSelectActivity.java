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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class StockSelectActivity extends Activity{
	ListView listView;
	AutoCompleteTextView autoText;
	List items=new ArrayList();
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>(); // 定义显示的内容包装
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_stock_select);
        AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
        listView=(ListView)findViewById(R.id.selectList);
        autoText=(AutoCompleteTextView)findViewById(R.id.autoProduct);
        getProductNameOrId();
        getALLProductList();
        //为AutoCompleteTextView添加文本改变时的监听事件
        autoText.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			public void afterTextChanged(Editable s) {
				//判断AutoCompleteTextView中的内容是否为空
				if (autoText.getText().toString().equals("")) {
					getALLProductList();
				}else{
					getProductByName();
				}
			}
		});
        
    }
   
    //返回按钮事件
    public void btnback(View view){
    	Intent intent=new Intent(StockSelectActivity.this,MainActivity.class);
    	startActivity(intent);
    }
    //得到所有的商品库存列表
    public void getALLProductList(){
    	//清空list集合
    	list.clear();
    	//URL地址                                                                                
    	String url="http://10.0.2.2:8080/CosmeticService/getALLProductList.do";
    	//获得HttpClient对象
    	HttpClient client=new DefaultHttpClient();
    	//HttpGet连接对象，设置客户端提交方式
    	HttpGet httpGet=new HttpGet(url);
    	try {
		  //获取HttpResponse对象
    		HttpResponse response=client.execute(httpGet);
    		//判断服务器端返回是否成功
    		if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				String productlist=EntityUtils.toString(response.getEntity());
				JSONArray jsonArray=new JSONArray(productlist);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo=jsonArray.optJSONObject(i);
					addList(jo);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    	addListView();
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
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        autoText.setAdapter(aa);
    }
    
    public void getProductByName(){
		list.clear();
		String nameorid=autoText.getText().toString();
		String url="http://10.0.2.2:8080/CosmeticService/getProductByName.do";
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
				JSONObject jsonObject=new JSONObject(losespillList);
				addList(jsonObject);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		addListView();
	}
    //将数据添加到list中去
    public void addList(JSONObject jsonObject){
    	Map<String,String> items=new HashMap<String, String>();
    	items.put("productid", String.valueOf(jsonObject.optString("productid")));
		items.put("productname",String.valueOf(jsonObject.optString("productname")));
		items.put("stockcount",String.valueOf(jsonObject.optInt("quantity")));
		items.put("supplier", String.valueOf(jsonObject.optString("suppliername")));
		list.add(items);
    }
    //将list中的数据填充到适配器中，并填充到listview中
    public void addListView(){
    	SimpleAdapter adapter = new SimpleAdapter(this, this.list,
				R.layout.stockselectlist, new String[] {"productid", "productname", "stockcount",
						"supplier" } // Map中的key的名称
				, new int[] {R.id.productsId, R.id.productsName,R.id.stockcounts,R.id.supplierName});
		
        listView.setAdapter(adapter);
    }
}
