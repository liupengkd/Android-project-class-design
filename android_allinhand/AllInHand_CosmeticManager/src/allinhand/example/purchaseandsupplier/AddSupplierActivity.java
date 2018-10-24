package allinhand.example.purchaseandsupplier;

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
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class AddSupplierActivity extends Activity {

	private EditText supID = null;
	private EditText supName = null;
	private EditText supTelephone = null;
	private EditText supAddress = null;
	
	private String values;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_supplier);
        AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
        supID = (EditText)findViewById(R.id.ed_supplier1); // 取得EditText组件
        supName = (EditText)findViewById(R.id.ed_supplier2); // 取得EditText组件
        supTelephone = (EditText)findViewById(R.id.ed_supplier3);// 取得EditText组件
        supAddress = (EditText)findViewById(R.id.ed_supplier4);// 取得EditText组件
        
        
        Intent it = getIntent();
    	supID.setFocusable(false); 
		values = it.getStringExtra("value"); 
        if (values.equals("1")) {  //判断 添加还是修改
        	
        	String id = it.getStringExtra("id");
        	String name = it.getStringExtra("name");
        	String telephone = it.getStringExtra("telephone");
        	String address = it.getStringExtra("address");
        	
        	supID.setText(id);
        	supName.setText(name);
        	supTelephone.setText(telephone);
        	supAddress.setText(address);
        	
			
		}else if (values.equals("2")) {
			createSupID(); //自动生成 供应商ID
			
		}
        
        
        
        
    }
    
  //提交信息
    public void finish(View v){
    	
    	// 验证非空
    	if (!supName.getText().toString().trim().equals("")
    			&&!supTelephone.getText().toString().trim().equals("")
    			&&!supAddress.getText().toString().trim().equals("")) {
			
		
    	//判断是修改还是添加
    	if (values.equals("1")) { 
    		updateSupplier();
    		
		}else if (values.equals("2")) {
			
			addSupplier();
		}
    	}else {
    		Toast.makeText(getApplicationContext(), "以上信息不能为空！",
					Toast.LENGTH_SHORT).show();
		}
    }
    
  //清空信息
    public void empty(View v){
    	
    	supAddress.setText(null);
    	supName.setText(null);
    	supTelephone.setText(null);
    }
  //自动插入供应商ID
    int id;
    public void createSupID(){
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

    					String json = EntityUtils.toString(response.getEntity());
    					if (json.trim().equals("failed")) {
							id=1;
						}
    					else {
    						JSONArray jsonArray = new JSONArray(json);
        					
    						JSONObject jo = jsonArray.optJSONObject(0);
    						
    					    id =Integer.parseInt(jo.optString("supplierid")) ;
    						id=id+1;
    						
						}
    					
    						supID.setText(id+"");

    						
    				}
    			}catch(Exception e){
    				e.printStackTrace();
    			}
    					
    }
    
  //添加供应商
    public  void addSupplier(){
    	String url = "http://10.0.2.2:8080/CosmeticService/AddSupplier.do";
    	//取得HttpClient对象
    	HttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	
    	try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("SupplierID", supID.getText().toString().trim());
			map.put("SupplierName", supName.getText().toString().trim());
			map.put("Telephone", supTelephone.getText().toString().trim());
			map.put("Companyaddress", supAddress.getText().toString().trim());
			
			JSONObject jo = new JSONObject(map);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("addsup_json", jo.toString()));
			post.setEntity(new UrlEncodedFormEntity(param,"utf-8"));
			
			HttpResponse hr = client.execute(post);
			if (hr.getStatusLine().getStatusCode() == 200) {
				String str = EntityUtils.toString(hr.getEntity());
				if (str.trim().equals("success")) {
					Toast.makeText(getApplicationContext(), "供应商信息添加成功",
							Toast.LENGTH_SHORT).show();
					Intent in = new Intent(AddSupplierActivity.this,
							SupplierActivity.class);
					startActivity(in);
//					supID = null;
//					supName = null;
//					supTelephone = null;
//					supAddress = null;
				}else if (str.trim().equals("doublename")) {
					Toast.makeText(getApplicationContext(), "供应商名称已存在",
							Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(getApplicationContext(), "供应商信息添加失败!",
							Toast.LENGTH_SHORT).show();
				}
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    
    
  //修改供应商信息
    public void updateSupplier(){
    	String url ="http://10.0.2.2:8080/CosmeticService/updateSupplier.do";
    	
    	//获取HttpClient对象
    	HttpClient client = new DefaultHttpClient();
    	HttpPost post = new HttpPost(url);
    	
    	try {
			Map<String, String>  map = new HashMap<String, String>();
			map.put("SupplierID", supID.getText().toString().trim());
			map.put("SupplierName", supName.getText().toString().trim());
			map.put("Telephone", supTelephone.getText().toString().trim());
			map.put("Companyaddress", supAddress.getText().toString().trim());
			JSONObject jo = new JSONObject(map);
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			param.add(new BasicNameValuePair("updatesup_json", jo.toString()));
			
			post.setEntity(new UrlEncodedFormEntity(param,"utf-8"));
			HttpResponse hr =client.execute(post);
			if (hr.getStatusLine().getStatusCode() == 200) {
				String str = EntityUtils.toString(hr.getEntity());
				if (str.trim().equals("SUCCESS")) {
					Toast.makeText(getApplicationContext(), "供应商修改成功", 
							Toast.LENGTH_SHORT).show();
					Intent in = new Intent(AddSupplierActivity.this,
							SupplierActivity.class);
					startActivity(in);
					
				}else {
					Toast.makeText(getApplicationContext(), "供应商修改失败", 
							Toast.LENGTH_SHORT).show();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    //返回按钮事件
    public void back(View view){
    	Intent intent=new Intent(AddSupplierActivity.this,MainActivity.class);
    	startActivity(intent);
    }
}
