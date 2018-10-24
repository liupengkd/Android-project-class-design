package allinhand.example.personanduser;

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

import allinhand.example.cosmeticmanager.AuthorityChange;
import allinhand.example.cosmeticmanager.MainActivity;
import allinhand.example.cosmeticmanager.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PersonActivity extends Activity {
	Button achieveButton;
	EditText newpwdtxt;
	EditText surepwd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.personset);
        AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
        newpwdtxt=(EditText)findViewById(R.id.newpwd);
        surepwd=(EditText)findViewById(R.id.surepwd);
        //获取配置文件中的提交按钮
        achieveButton=(Button)findViewById(R.id.achieve);
        //设置提交按钮的监听器
        achieveButton.setOnClickListener(new OnClickListener() {
			//提交按钮的点击事件
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//修改个人密码
				if (!newpwdtxt.getText().toString().equals(surepwd.getText().toString())) {
					Toast.makeText(PersonActivity.this, "密码不一致", Toast.LENGTH_SHORT).show();
				}else if (newpwdtxt.getText().toString().equals("")||surepwd.getText().toString().equals("")) {
					Toast.makeText(PersonActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
				}
				else {
					// 获取HttpClient
					HttpClient client = new DefaultHttpClient();
					// 要访问的URL
					String url = "http://10.0.2.2:8080/CosmeticService/updatePassword.do";
					// POST提交数据
					HttpPost post = new HttpPost(url);
					JSONObject jo = new JSONObject();
					try {
						AuthorityChange auth=(AuthorityChange)getApplication();
						jo.put("userId", auth.getUserId());
						jo.put("pwd", newpwdtxt.getText().toString());
						// 客户端提交数据集合
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("stockJson", jo
								.toString()));
						// 将客户端数据封装到实体中
						HttpEntity entity = new UrlEncodedFormEntity(params,
								"utf-8");
						// 将实体添加到请求中
						post.setEntity(entity);
						// 执行请求与服务器交互
						HttpResponse response = client.execute(post);
						// 判断交互结果，进行处理
						if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
							String result = EntityUtils.toString(response
									.getEntity());
							if (result.trim().equals("success")) {
								Toast.makeText(PersonActivity.this, "修改成功", Toast.LENGTH_SHORT)
										.show();
							} else {
								Toast.makeText(PersonActivity.this, "修改失败", Toast.LENGTH_SHORT)
										.show();
							}
							Intent intent2=new Intent(PersonActivity.this,MainActivity.class);
							startActivity(intent2);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
    }
    //返回按钮的事件
    public void btnback(View view){
    	finish();
    }
}
