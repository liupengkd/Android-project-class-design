package allinhand.example.stockandproduct;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StockTakingActivity extends Activity implements OnClickListener{
	TextView currentDate;
	Button clearButton;
	EditText takId;
	EditText productId;
	EditText count;
	Button addButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_stock_taking);
        AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
        takId=(EditText)findViewById(R.id.takings);
        productId=(EditText)findViewById(R.id.autoTaking);
        count=(EditText)findViewById(R.id.counting);
        clearButton=(Button)findViewById(R.id.stock_Taking_clear);
        clearButton.setOnClickListener(this);
        addButton=(Button)findViewById(R.id.stock_Taking_Add);
        addButton.setOnClickListener(this);
        takId.setFocusable(false);
        takId.setEnabled(false);
        //��ȡ��ǰ���ڵĿؼ�
        currentDate=(TextView)findViewById(R.id.currentDate);
        //Ϊ��ǰ���ڿؼ�����������Ϊϵͳ��ǰʱ��
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Date curDate=new Date(System.currentTimeMillis());
        currentDate.setText(formatter.format(curDate));
        autoLoseSpillId();
        
    }

    //���ذ�ť�¼�
    public void btnback(View view){
    	Intent intent=new Intent(StockTakingActivity.this,StockTakingListActivity.class);
    	startActivity(intent);
    }
    //��հ�ť���ύ��ť���¼�
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		//��հ�ť
		case R.id.stock_Taking_clear:
			clearTxt();
			break;
		//�ύ��ť�¼�
		case R.id.stock_Taking_Add:
			String idString=takId.getText().toString();
			String productIdString=productId.getText().toString();
			String countString=count.getText().toString();
			String date=currentDate.getText().toString();
			//��ȡHttpClient
			HttpClient client=new DefaultHttpClient();
			//Ҫ���ʵ�URL
			String url="http://10.0.2.2:8080/CosmeticService/addLoseSpill.do";
			//POST�ύ����
			HttpPost post=new HttpPost(url);
			JSONObject jo=new JSONObject();
			try {
				jo.put("loseId", idString);
				jo.put("productId", productIdString);
				jo.put("count", countString);
				jo.put("date",date );
				//�ͻ����ύ���ݼ���
				List<NameValuePair> params=new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("stockJson", jo.toString()));
				//���ͻ������ݷ�װ��ʵ����
				HttpEntity entity=new UrlEncodedFormEntity(params,"utf-8");
				//��ʵ����ӵ�������
				post.setEntity(entity);
				//ִ�����������������
				HttpResponse response=client.execute(post);
				//�жϽ�����������д���
				if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
					String result=EntityUtils.toString(response.getEntity());
					if (result.trim().equals("success")) {
						Toast.makeText(this,"�̵�ɹ�",Toast.LENGTH_LONG).show();
						clearTxt();
						autoLoseSpillId();
					}else if (result.trim().equals("exist")) {
						Toast.makeText(this,"�̵����Ѵ���",Toast.LENGTH_LONG).show();
					}else if (result.trim().equals("notexist")) {
						Toast.makeText(this,"��Ʒ������",Toast.LENGTH_LONG).show();
					}else {
						Toast.makeText(this,"�̵�ʧ��",Toast.LENGTH_LONG).show();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		}
	}
	//����ı����е�����
	public void clearTxt(){
		productId.setText("");
		count.setText("");
	}
	//�Զ������� ����
		public void autoLoseSpillId(){
			String url = "http://10.0.2.2:8080/CosmeticService/getAutoLoseSpillId.do";
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
					String losespillid="";
					if (json.trim().equals("fail")) {
						losespillid="0001";
					}else {
						JSONArray jsonArray = new JSONArray(json);
						JSONObject jo = jsonArray.optJSONObject(0);
						
						int id =Integer.parseInt(jo.optString("losespillid")) ;
						id=id+1;
						losespillid=id+"";
						if (losespillid.length()==1) {
							losespillid="000"+losespillid;
						}
						if (losespillid.length()==2) {
							losespillid="00"+losespillid;
						}
						if (losespillid.length()==3) {
							losespillid="0"+losespillid;
						}
					}
					
						takId.setText(losespillid);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
}
