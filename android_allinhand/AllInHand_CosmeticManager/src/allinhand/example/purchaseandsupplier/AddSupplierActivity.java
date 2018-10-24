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
        supID = (EditText)findViewById(R.id.ed_supplier1); // ȡ��EditText���
        supName = (EditText)findViewById(R.id.ed_supplier2); // ȡ��EditText���
        supTelephone = (EditText)findViewById(R.id.ed_supplier3);// ȡ��EditText���
        supAddress = (EditText)findViewById(R.id.ed_supplier4);// ȡ��EditText���
        
        
        Intent it = getIntent();
    	supID.setFocusable(false); 
		values = it.getStringExtra("value"); 
        if (values.equals("1")) {  //�ж� ��ӻ����޸�
        	
        	String id = it.getStringExtra("id");
        	String name = it.getStringExtra("name");
        	String telephone = it.getStringExtra("telephone");
        	String address = it.getStringExtra("address");
        	
        	supID.setText(id);
        	supName.setText(name);
        	supTelephone.setText(telephone);
        	supAddress.setText(address);
        	
			
		}else if (values.equals("2")) {
			createSupID(); //�Զ����� ��Ӧ��ID
			
		}
        
        
        
        
    }
    
  //�ύ��Ϣ
    public void finish(View v){
    	
    	// ��֤�ǿ�
    	if (!supName.getText().toString().trim().equals("")
    			&&!supTelephone.getText().toString().trim().equals("")
    			&&!supAddress.getText().toString().trim().equals("")) {
			
		
    	//�ж����޸Ļ������
    	if (values.equals("1")) { 
    		updateSupplier();
    		
		}else if (values.equals("2")) {
			
			addSupplier();
		}
    	}else {
    		Toast.makeText(getApplicationContext(), "������Ϣ����Ϊ�գ�",
					Toast.LENGTH_SHORT).show();
		}
    }
    
  //�����Ϣ
    public void empty(View v){
    	
    	supAddress.setText(null);
    	supName.setText(null);
    	supTelephone.setText(null);
    }
  //�Զ����빩Ӧ��ID
    int id;
    public void createSupID(){
    	String url = "http://10.0.2.2:8080/CosmeticService/SelectAllSupplier.do";
    	// HttpGet���Ӷ������ÿͻ����ύ��ʽ
    			HttpGet httpGet = new HttpGet(url);
    			// ȡ��HttpClient����
    			HttpClient client = new DefaultHttpClient();
    			try {
    				// ��ȡHttpResponse����
    				HttpResponse response = client.execute(httpGet);
    				// ���������ȷ�ӷ���������

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
    
  //��ӹ�Ӧ��
    public  void addSupplier(){
    	String url = "http://10.0.2.2:8080/CosmeticService/AddSupplier.do";
    	//ȡ��HttpClient����
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
					Toast.makeText(getApplicationContext(), "��Ӧ����Ϣ��ӳɹ�",
							Toast.LENGTH_SHORT).show();
					Intent in = new Intent(AddSupplierActivity.this,
							SupplierActivity.class);
					startActivity(in);
//					supID = null;
//					supName = null;
//					supTelephone = null;
//					supAddress = null;
				}else if (str.trim().equals("doublename")) {
					Toast.makeText(getApplicationContext(), "��Ӧ�������Ѵ���",
							Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(getApplicationContext(), "��Ӧ����Ϣ���ʧ��!",
							Toast.LENGTH_SHORT).show();
				}
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
    
    
  //�޸Ĺ�Ӧ����Ϣ
    public void updateSupplier(){
    	String url ="http://10.0.2.2:8080/CosmeticService/updateSupplier.do";
    	
    	//��ȡHttpClient����
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
					Toast.makeText(getApplicationContext(), "��Ӧ���޸ĳɹ�", 
							Toast.LENGTH_SHORT).show();
					Intent in = new Intent(AddSupplierActivity.this,
							SupplierActivity.class);
					startActivity(in);
					
				}else {
					Toast.makeText(getApplicationContext(), "��Ӧ���޸�ʧ��", 
							Toast.LENGTH_SHORT).show();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    //���ذ�ť�¼�
    public void back(View view){
    	Intent intent=new Intent(AddSupplierActivity.this,MainActivity.class);
    	startActivity(intent);
    }
}
