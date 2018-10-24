package allinhand.example.stockandproduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import allinhand.example.cosmeticmanager.AuthorityChange;
import allinhand.example.cosmeticmanager.MainActivity;
import allinhand.example.cosmeticmanager.R;
import allinhand.example.purchaseandsupplier.PurchaseRuActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class StockWarnActivity extends Activity {
	Button buyBtn;
	ListView listView;
	List<Map<String, String>> alarmList=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_stock_warn);
        buyBtn=(Button)findViewById(R.id.buyproduct);
        AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
        listView=(ListView)findViewById(R.id.warnList);
		this.getAlarmProduct();

    }
    //返回方法
   
    Intent intent=null;
    public void btnback(View view){
    	finish();
    }
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
    	finish();
		return super.onKeyDown(keyCode, event);
	}
	//得到所有的库存预警商品
    public void getAlarmProduct(){
    	alarmList=new ArrayList<Map<String,String>>();
    	String url="http://10.0.2.2:8080/CosmeticService/StockAlarm.do";
    	//HttpGet连接对象，设置客户端提交方式
    	HttpGet httpGet=new HttpGet(url);
    	//取得HttpClient对象
    	HttpClient client=new DefaultHttpClient();
    	try {
			//获取HttpResponse对象
    		HttpResponse response=client.execute(httpGet);
    		//如果数据正确从服务器返回
    		if (response.getStatusLine().getStatusCode()==HttpStatus.SC_OK) {
				String stockList=EntityUtils.toString(response.getEntity());
				JSONArray jsonArray=new JSONArray(stockList);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i); 
					Map<String, String> samap = new HashMap<String, String>();
					samap.put("goodsname", String.valueOf(jo.optString("productname")));
					samap.put("goodcount",String.valueOf(jo.getInt("quantity")));
					samap.put("mincount", String.valueOf(jo.optInt("safeStock")));
					samap.put("maxcount",String.valueOf(jo.optInt("maxSafeStock")));
					samap.put("supplier",String.valueOf(jo.optString("suppliername")));
					alarmList.add(samap);
			    }
    		}
    		}catch (Exception e) {
				e.printStackTrace();
			}
    	//将数据填充到适配器中
    	SimpleAdapter sa = new SimpleAdapter(this, alarmList,
				R.layout.stockwarnlist, new String[] { "goodsname", "goodcount",
						"mincount", "maxcount","supplier"}, new int[] { R.id.warn_name,
						R.id.stock_count, R.id.little_count, R.id.max_count,R.id.supplier_Name});
		listView.setAdapter(sa);
    }
    //进行采购按钮事件
    public void buyProduct(View v){
    	AuthorityChange authorityChange=(AuthorityChange)getApplication();
		int auth=authorityChange.getAuthoritytype();
		if(auth==2){
			Intent intent=new Intent(StockWarnActivity.this,PurchaseRuActivity.class);
			intent.putExtra("title", "采购入库");
	    	startActivity(intent);
		}
    	
    }
}

