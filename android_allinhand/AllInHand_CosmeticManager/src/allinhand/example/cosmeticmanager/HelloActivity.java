package allinhand.example.cosmeticmanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class HelloActivity extends Activity {

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		HelloActivity.this.finish();
		myThread.stop();
		
	}

	private Thread myThread;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_hello);
        AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
        	myThread = new Thread(){
    		
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Intent intent = new Intent(HelloActivity.this,MainActivity.class);
				startActivity(intent);
			}
    	};

    	myThread.start();
    	
    }
}
