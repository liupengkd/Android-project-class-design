package allinhand.example.cosmeticmanager;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import allinhand.example.service.StockAlarmService;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{
	Button loginButton;
	EditText nameEditText;
	EditText pwdEditText;
	ImageView savepwd;
	int flag=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loginpage);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		loginButton=(Button)findViewById(R.id.login_btn_login);
		loginButton.setOnClickListener(this);
		nameEditText=(EditText)findViewById(R.id.login_edit_account);
		pwdEditText=(EditText)findViewById(R.id.login_edit_pwd);
		savepwd=(ImageView)findViewById(R.id.login_cb_savepwd);
		nameEditText.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				pwdEditText.setText("");
				savepwd.setImageResource(R.drawable.btn_check_off);
				flag=0;
				
			}
		});
		//获取当前Activity的Preferences对象
		SharedPreferences sp = getPreferences(MODE_PRIVATE);
		//从Preferences对象中获取登录信息并显示到EditText中
		nameEditText.setText(sp.getString("loginname", ""));
		pwdEditText.setText(sp.getString("password", ""));
		if (!sp.getString("password", "").equals("")) {
			savepwd.setImageResource(R.drawable.btn_check_on);
			flag=1;
		}

	}
	//记住密码事件
	public void onimgClick(View v){
		if (flag==0) {
			savepwd.setImageResource(R.drawable.btn_check_on);
			flag=1;
		}else {
			savepwd.setImageResource(R.drawable.btn_check_off);
			flag=0;
		}
		
	}
	public void onClick(View v) {
		switch (v.getId()) {
		//登录按钮事件
		case R.id.login_btn_login:
			String name=nameEditText.getText().toString();
			String pwd=pwdEditText.getText().toString();
			
			//获取HttpClient
			HttpClient client=new DefaultHttpClient();
			//要访问的URL
			String url="http://10.0.2.2:8080/CosmeticService/LoginByNameAndPwd.do";
			//POST提交数据
			HttpPost post=new HttpPost(url);
			JSONObject jo=new JSONObject();
			try {
				jo.put("name",name);
				jo.put("pwd", pwd);
				//客户端提交数据集合
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("stockJson", jo.toString()));
				//将客户端数据封装到实体中
				HttpEntity entity=new UrlEncodedFormEntity(params,"utf-8");
				//将实体添加到请求中
				post.setEntity(entity);
				//执行请求与服务器交互
				HttpResponse response=client.execute(post);
				//判断交互结果，进行处理
				if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
					String result=EntityUtils.toString(response.getEntity());
					//System.out.println(result);
					if (result.trim().equals("fail")) {
						Toast.makeText(this,"用户名或密码不正确",Toast.LENGTH_LONG).show();
						}else {
							JSONObject jb=new JSONObject(result);
							Intent intent=new Intent();
							String authorityString=String.valueOf(jb.optInt("userauthority"));
							String userId=String.valueOf(jb.optString("userid"));
							String username=String.valueOf(jb.optString("username"));
							Intent serviceIntent=new Intent();
							intent.setClass(LoginActivity.this,MainActivity.class);
							AuthorityChange auth=(AuthorityChange)getApplication();
							auth.setUserId(userId);
							startActivity(intent);
							serviceIntent.setClass(LoginActivity.this,StockAlarmService.class);
							startService(serviceIntent);
							//假如用户是管理员
							if (authorityString.equals("0")) {
								AuthorityChange authorityChange=(AuthorityChange)getApplication();
								authorityChange.setAuthoritytype(0);
								authorityChange.setIslogin(true);
							}
							//假如用户是销售员
							if (authorityString.equals("1")) {
								AuthorityChange authorityChange=(AuthorityChange)getApplication();
								authorityChange.setAuthoritytype(1);
								authorityChange.setUsername(username);
								authorityChange.setIslogin(true);
							}
							//假如用户是采购员
							if (authorityString.equals("2")) {
								AuthorityChange authorityChange=(AuthorityChange)getApplication();
								authorityChange.setAuthoritytype(2);
								authorityChange.setIslogin(true);
							}
							//假如用户是库管员
							if (authorityString.equals("3")) {
								AuthorityChange authorityChange=(AuthorityChange)getApplication();
								authorityChange.setAuthoritytype(3);
								
							}
							
							
							//获取SharedPreferences.Editor对象
							SharedPreferences.Editor editor=getPreferences(MODE_PRIVATE).edit();
							String namestr=nameEditText.getText().toString();
							editor.putString("loginname", namestr);
							if (flag==1) {
								
								String pwdstr=pwdEditText.getText().toString();
								editor.putString("password",pwdstr);
								
							}else {
								editor.remove("password");
							}
							//提交信息
							editor.commit();
							
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
			
			}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			dialog();
			return false;
		}
		return false;
	}
	protected void dialog() {
		AlertDialog.Builder builder = new Builder(LoginActivity.this);
		builder.setMessage("确定要退出吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
//						LoginActivity.this.finish();
//						System.exit(0);
						NotificationManager noManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
						noManager.cancel(1);
						stopService(new Intent(LoginActivity.this,StockAlarmService.class));
						AuthorityChange.AuthorityChangegetIntance().exit();
						
						
						
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
}
