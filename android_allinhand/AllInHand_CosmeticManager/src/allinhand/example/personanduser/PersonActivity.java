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
        //��ȡ�����ļ��е��ύ��ť
        achieveButton=(Button)findViewById(R.id.achieve);
        //�����ύ��ť�ļ�����
        achieveButton.setOnClickListener(new OnClickListener() {
			//�ύ��ť�ĵ���¼�
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//�޸ĸ�������
				if (!newpwdtxt.getText().toString().equals(surepwd.getText().toString())) {
					Toast.makeText(PersonActivity.this, "���벻һ��", Toast.LENGTH_SHORT).show();
				}else if (newpwdtxt.getText().toString().equals("")||surepwd.getText().toString().equals("")) {
					Toast.makeText(PersonActivity.this, "����Ϊ��", Toast.LENGTH_SHORT).show();
				}
				else {
					// ��ȡHttpClient
					HttpClient client = new DefaultHttpClient();
					// Ҫ���ʵ�URL
					String url = "http://10.0.2.2:8080/CosmeticService/updatePassword.do";
					// POST�ύ����
					HttpPost post = new HttpPost(url);
					JSONObject jo = new JSONObject();
					try {
						AuthorityChange auth=(AuthorityChange)getApplication();
						jo.put("userId", auth.getUserId());
						jo.put("pwd", newpwdtxt.getText().toString());
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
								Toast.makeText(PersonActivity.this, "�޸ĳɹ�", Toast.LENGTH_SHORT)
										.show();
							} else {
								Toast.makeText(PersonActivity.this, "�޸�ʧ��", Toast.LENGTH_SHORT)
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
    //���ذ�ť���¼�
    public void btnback(View view){
    	finish();
    }
}
