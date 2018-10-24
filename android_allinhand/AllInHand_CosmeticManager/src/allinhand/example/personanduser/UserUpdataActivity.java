package allinhand.example.personanduser;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
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
import allinhand.example.cosmeticmanager.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class UserUpdataActivity extends Activity {
	TableRow row1;
	TableRow row4;
	TableRow row5;
	TextView zhu;
	private EditText EdUserID;//�û����
	private EditText EdUserName;//�û���
	private Spinner EdUserRole;//�û���ɫ
	private EditText EdUserPass;//����
	private EditText EdUserPassT;//ȷ������
	private int role = 0;
	Intent intent = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_updata);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		EdUserID = (EditText) findViewById(R.id.EdUserID);
		EdUserName = (EditText) findViewById(R.id.EdUserName);
		EdUserPass = (EditText) findViewById(R.id.EdUserPass);
		EdUserRole = (Spinner) findViewById(R.id.EdUserRole);
		EdUserPassT = (EditText) findViewById(R.id.EdUserPassT);
		zhu=(TextView)findViewById(R.id.zhu);
		EdUserPass.setText("123456");
		EdUserID.setFocusable(false);
		EdUserID.setEnabled(false);
		
		autoUserId();
		row1 = (TableRow) findViewById(R.id.row1);
		row4 = (TableRow) findViewById(R.id.row4);
		row5 = (TableRow) findViewById(R.id.row5);
		row5.setVisibility(View.GONE);
		List<Dict> list = new ArrayList<Dict>();
		list.add(new Dict(0, "����Ա"));
		list.add(new Dict(1, "����Ա"));
		list.add(new Dict(2, "�ɹ�Ա"));
		list.add(new Dict(3, "���Ա"));
		ArrayAdapter<Dict> dataAdapter = new ArrayAdapter<Dict>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		EdUserRole.setAdapter(dataAdapter);
		EdUserRole.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				role = ((Dict) EdUserRole.getSelectedItem()).getId();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		intent = getIntent();
		String addorupdate = intent.getStringExtra("value");
		String authority = intent.getStringExtra("authority");
		//�޸��û�
		if (addorupdate.equals("1")) {
			EdUserID.setEnabled(false);
			EdUserName.setEnabled(false);
			EdUserID.setFocusable(false);
			EdUserName.setFocusable(false);
			row4.setVisibility(View.GONE);
			EdUserPassT.setEnabled(false);
			zhu.setVisibility(View.GONE);
			EdUserPassT.setVisibility(View.GONE);
			EdUserID.setText(intent.getStringExtra("userId"));
			EdUserName.setText(intent.getStringExtra("name"));
			EdUserRole.setSelection(Integer.parseInt(authority) - 1, true);
			EdUserPass.setText(intent.getStringExtra("pwd"));
		}
	}
	//�ύ��ť�¼�
	public void UserGood(View v) {
		intent = getIntent();
		String userName = EdUserName.getText().toString();
		String userId = EdUserID.getText().toString();
		String password = EdUserPass.getText().toString();
		//String surepassword=EdUserPassT.getText().toString();
		String addorupdate = intent.getStringExtra("value");
		if(addorupdate.equals("1")){
			userId=intent.getStringExtra("userId");
		}
		if (addorupdate.equals("1")) {
			// ��ȡHttpClient
			HttpClient client = new DefaultHttpClient();
			// Ҫ���ʵ�URL
			String url = "http://10.0.2.2:8080/CosmeticService/updateUserInfo.do";
			// POST�ύ����
			HttpPost post = new HttpPost(url);
			JSONObject jo = new JSONObject();
			try {
				jo.put("userId", intent.getStringExtra("userId"));
				jo.put("userName", userName);
				jo.put("roles", role);
				// �ͻ����ύ���ݼ���
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("stockJson", jo
						.toString()));
				// ���ͻ������ݷ�װ��ʵ����
				HttpEntity entity = new UrlEncodedFormEntity(params,
						"utf-8");
				// ��ʵ����ӵ�������
				post.setEntity(entity);
				// ִ�����������������
				HttpResponse response = client.execute(post);
				// �жϽ�����������д���
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String result = EntityUtils.toString(response
							.getEntity());
					if (result.trim().equals("success")) {
						Toast.makeText(this, "�޸ĳɹ�", Toast.LENGTH_SHORT)
								.show();
						
					} else {
						Toast.makeText(this, "�޸�ʧ��", Toast.LENGTH_SHORT)
								.show();
					}
				}
				Intent intent=new Intent(UserUpdataActivity.this,UsersActivity.class);
				startActivity(intent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (password.equals("")||userId.equals("")||userName.equals("")){
				Toast.makeText(this, "�뽫��Ϣ������", Toast.LENGTH_SHORT).show();
			}else{
				
				// ��ȡHttpClient
				HttpClient client = new DefaultHttpClient();
				// Ҫ���ʵ�URL
				String url = "http://10.0.2.2:8080/CosmeticService/addUsers.do";
				// POST�ύ����
				HttpPost post = new HttpPost(url);
				JSONObject jo = new JSONObject();
				try {
					jo.put("userId", userId);
					jo.put("userName", userName);
					jo.put("password", password);
					jo.put("roles", role);
					// �ͻ����ύ���ݼ���
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("stockJson", jo
							.toString()));
					// ���ͻ������ݷ�װ��ʵ����
					HttpEntity entity = new UrlEncodedFormEntity(params,
							"utf-8");
					// ��ʵ����ӵ�������
					post.setEntity(entity);
					// ִ�����������������
					HttpResponse response = client.execute(post);
					// �жϽ�����������д���
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						String result = EntityUtils.toString(response
								.getEntity());
						if (result.trim().equals("success")) {
							Toast.makeText(this, "��ӳɹ�", Toast.LENGTH_SHORT)
									.show();
							Intent intent=new Intent(this,UsersActivity.class);
							startActivity(intent);
						} else if (result.trim().equals("idexist")) {
							Toast.makeText(this, "����Ѵ���", Toast.LENGTH_SHORT)
									.show();
						} else if (result.trim().equals("nameexist")) {
							Toast.makeText(this, "�û����Ѵ���", Toast.LENGTH_SHORT)
							.show();
						} else if (result.trim().equals("double")) {
							Toast.makeText(this, "��ź��û����Ѵ���", Toast.LENGTH_SHORT)
							.show();
						} else {
							Toast.makeText(this, "���ʧ��", Toast.LENGTH_SHORT)
									.show();
						}
						
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	//��հ�ť�¼�
	public void UserClear(View v) {
		intent = getIntent();
		if(!intent.getStringExtra("value").equals("1")){
			EdUserName.setText("");
		}
		
	}
	//���ذ�ť�¼�
	public void btnback(View v) {
		Intent intent=new Intent(UserUpdataActivity.this,UsersActivity.class);
		startActivity(intent);
		
	}
	//�Զ������û����
	public void autoUserId(){
		String url = "http://10.0.2.2:8080/CosmeticService/getAutoUserId.do";
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
				String userid="";
				if (json.trim().equals("fail")) {
					userid="0001";
				}else {
					JSONArray jsonArray = new JSONArray(json);
					
					JSONObject jo = jsonArray.optJSONObject(0);
					
					int id =Integer.parseInt(jo.optString("userid")) ;
					id=id+1;
					userid=id+"";
					if (userid.length()==1) {
						userid="000"+userid;
					}
					if (userid.length()==2) {
						userid="00"+userid;
					}
					if (userid.length()==3) {
						userid="0"+userid;
					}
				}
				
					EdUserID.setText(userid);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
