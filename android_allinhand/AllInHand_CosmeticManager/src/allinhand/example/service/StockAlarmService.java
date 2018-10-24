package allinhand.example.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import allinhand.example.stockandproduct.StockWarnActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class StockAlarmService extends Service {
	private NotificationManager notificationManager;
	private Notification notification;
	private static final int NOTIFICATION_ID=1;
	private boolean stopservice=true;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		new Thread(new Runnable() {

			public void run() {
				while (stopservice) {
					try {
						getCheckProductAlarm();
						Thread.sleep(3600 * 1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		stopservice=false;
		super.onDestroy();
		
		
	}
	public void showAlarm(){
		notification=new Notification(android.R.drawable.ic_notification_overlay,"进销存系统中有商品的数量低于或高于警报值！",System.currentTimeMillis());
		PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,new Intent(getApplicationContext(),StockWarnActivity.class),PendingIntent.FLAG_ONE_SHOT);
		notification.defaults=Notification.DEFAULT_ALL;
		notification.flags=Notification.FLAG_AUTO_CANCEL;
		notification.setLatestEventInfo(getApplicationContext(),"警报", "库存中有商品的多于或少于警报值！",pendingIntent);
		notificationManager.notify(1,notification);
	}
	public void getCheckProductAlarm(){
		String url="http://10.0.2.2:8080/CosmeticService/StockAlarm.do";
    	HttpGet hg = new HttpGet(url);
    	HttpClient hc = new DefaultHttpClient();
    	try {
			HttpResponse hr = hc.execute(hg);
			if(hr.getStatusLine().getStatusCode()==200){
				String slist = EntityUtils.toString(hr.getEntity());
				JSONArray ja = new JSONArray(slist);
				if(ja.length()>0){
					showAlarm();
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
