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
        //获取当前日期的控件
        currentDate=(TextView)findViewById(R.id.currentDate);
        //为当前日期控件的内容设置为系统当前时间
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Date curDate=new Date(System.currentTimeMillis());
        currentDate.setText(formatter.format(curDate));
        autoLoseSpillId();
        
    }

    //返回按钮事件
    public void btnback(View view){
    	Intent intent=new Intent(StockTakingActivity.this,StockTakingListActivity.class);
    	startActivity(intent);
    }
    //清空按钮和提交按钮的事件
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		//清空按钮
		case R.id.stock_Taking_clear:
			clearTxt();
			break;
		//提交按钮事件
		case R.id.stock_Taking_Add:
			String idString=takId.getText().toString();
			String productIdString=productId.getText().toString();
			String countString=count.getText().toString();
			String date=currentDate.getText().toString();
			//获取HttpClient
			HttpClient client=new DefaultHttpClient();
			//要访问的URL
			String url="http://10.0.2.2:8080/CosmeticService/addLoseSpill.do";
			//POST提交数据
			HttpPost post=new HttpPost(url);
			JSONObject jo=new JSONObject();
			try {
				jo.put("loseId", idString);
				jo.put("productId", productIdString);
				jo.put("count", countString);
				jo.put("date",date );
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
					if (result.trim().equals("success")) {
						Toast.makeText(this,"盘点成功",Toast.LENGTH_LONG).show();
						clearTxt();
						autoLoseSpillId();
					}else if (result.trim().equals("exist")) {
						Toast.makeText(this,"盘点编号已存在",Toast.LENGTH_LONG).show();
					}else if (result.trim().equals("notexist")) {
						Toast.makeText(this,"商品不存在",Toast.LENGTH_LONG).show();
					}else {
						Toast.makeText(this,"盘点失败",Toast.LENGTH_LONG).show();
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		}
	}
	//清空文本框中的数据
	public void clearTxt(){
		productId.setText("");
		count.setText("");
	}
	//自动生成盘 点编号
		public void autoLoseSpillId(){
			String url = "http://10.0.2.2:8080/CosmeticService/getAutoLoseSpillId.do";
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
