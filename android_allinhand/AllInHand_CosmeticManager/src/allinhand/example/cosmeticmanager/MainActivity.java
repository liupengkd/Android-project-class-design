package allinhand.example.cosmeticmanager;

import java.util.ArrayList;
import java.util.List;

import allinhand.example.personanduser.PersonActivity;
import allinhand.example.personanduser.UsersActivity;
import allinhand.example.purchaseandsupplier.PurchaseRuActivity;
import allinhand.example.purchaseandsupplier.SupplierActivity;
import allinhand.example.saleandcustomer.CustomersActivity;
import allinhand.example.saleandcustomer.SalesMasterListActivity;
import allinhand.example.stockandproduct.ProductActivity;
import allinhand.example.stockandproduct.StockSelectActivity;
import allinhand.example.stockandproduct.StockTakingListActivity;
import allinhand.example.stockandproduct.StockWarnActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private ViewPager viewPager;
	private List<View> listView;
	private List<View> listDots;
	ImageView product;
	ImageView users;
	ImageView person;
	ImageView supplier;
	ImageView buychu;
	ImageView buytui;
	ImageView customer;
	ImageView salechu;
	ImageView saletui;
	ImageView stocktak;
	ImageView stockwarn;
	ImageView stockselect;
	private int oldPosition;
	ImageView warehouse1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//���ر��� 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		initViewPager();
		initDots();
		//��ȡlist 
		View view1 = listView.get(0);
		View view2 = listView.get(1);
		//��ȡ��ƷͼƬ�Ŀؼ�
		product = (ImageView) view1.findViewById(R.id.productimg);
		//��ȡ�û�ͼƬ�Ŀؼ�
		users = (ImageView) view1.findViewById(R.id.user);
		//��ȡ�������õĿؼ�
		person = (ImageView) view1.findViewById(R.id.personset);
		//��ȡ��Ӧ��ͼƬ�Ŀؼ�
		supplier = (ImageView) view1.findViewById(R.id.supplier);
		//��ȡ�ɹ����ͼƬ�Ŀؼ�
		buychu = (ImageView) view1.findViewById(R.id.purchaseru);
		//��ȡ�ɹ��˻�ͼƬ�Ŀؼ�
		buytui = (ImageView) view1.findViewById(R.id.purchasetui);
		//��ȡ�˿�ͼƬ�Ŀؼ�
		customer = (ImageView) view1.findViewById(R.id.client);
		//��ȡ���۳���ͼƬ�Ŀؼ�
		salechu = (ImageView) view1.findViewById(R.id.deliverychu);
		//��ȡ�����˻�ͼƬ�Ŀؼ�
		saletui = (ImageView) view1.findViewById(R.id.deliverytui);
		//��ȡ����̵�ͼƬ�Ŀؼ�
		stocktak = (ImageView) view2.findViewById(R.id.stockpandian);
		//��ȡ���Ԥ��ͼƬ�Ŀؼ�
		stockwarn = (ImageView) view2.findViewById(R.id.stock_report);
		//��ȡ����ѯͼƬ�Ŀؼ�
		stockselect = (ImageView) view2.findViewById(R.id.query_product);
		//�����û�������Ա���ɹ�Ա�����Ա
		if (getIntentAuthority() == 1 || getIntentAuthority() == 2 || getIntentAuthority() == 3) {
			users.setImageDrawable(getResources().getDrawable(R.drawable.user2));
			product.setImageDrawable(getResources().getDrawable(R.drawable.product2));
		}
		//�����û��ǹ���Ա������Ա���ɹ�Ա
		if (getIntentAuthority()==0||getIntentAuthority()==1||getIntentAuthority()==2) {
			stocktak.setImageDrawable(getResources().getDrawable(R.drawable.warehouse2));
			stockselect.setImageDrawable(getResources().getDrawable(R.drawable.query_product2));
		}
		//�����û��ǹ���Ա������Ա�����Ա
		if (getIntentAuthority() == 0 || getIntentAuthority()==1 ||getIntentAuthority()==3) {
			
			buychu.setImageDrawable(getResources().getDrawable(
					R.drawable.purchaseru2));
			buytui.setImageDrawable(getResources().getDrawable(
					R.drawable.purchasetui2));
		}
		//�����û��ǹ���Ա���ɹ�Ա�����Ա
		if (getIntentAuthority()==0 || getIntentAuthority()==2 || getIntentAuthority()==3) {
			salechu.setImageDrawable(getResources().getDrawable(
					R.drawable.deliverychu2));
			saletui.setImageDrawable(getResources().getDrawable(
					R.drawable.deliverytui2));
		}
		//�����û�������Ա�����Ա
		if (getIntentAuthority()==1 || getIntentAuthority()==3) {
			supplier.setImageDrawable(getResources().getDrawable(R.drawable.supplier2));
		}
		//�����û��ǲɹ�Ա�����Ա
		if (getIntentAuthority()==2 || getIntentAuthority()==3) {
			customer.setImageDrawable(getResources().getDrawable(R.drawable.client2));
		}
	}
	//��ʼ��
	private void initViewPager() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		listView = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		listView.add(inflater.inflate(R.layout.lay1, null));
		listView.add(inflater.inflate(R.layout.lay2, null));
		//
		// listView.add(inflater.inflate(R.layout.lay3, null));
		viewPager.setAdapter(new MyPagerAdapter(listView));
		viewPager.setOnPageChangeListener(new MyPagerChangeListener());

	}

	Intent intent = null;
	
	public void warebtn(View v) {
		if (getIntentAuthority()==3) {
			intent = new Intent(MainActivity.this, StockTakingListActivity.class);
			startActivity(intent);
		}
		
	}

	public void warnbtn(View v) {
		intent = new Intent(MainActivity.this, StockWarnActivity.class);
		startActivity(intent);
		
	}

	public void productSelect(View v) {
		if (getIntentAuthority()==3) {
			intent = new Intent(MainActivity.this, StockSelectActivity.class);
			startActivity(intent);
		}
		
	}

	public void purchaseTotal(View v) {
		intent = new Intent(MainActivity.this, PersonActivity.class);
		startActivity(intent);
	}

	public void Goclient(View v) {
		if (getIntentAuthority()==0||getIntentAuthority()==1) {
			intent = new Intent(MainActivity.this, CustomersActivity.class);
			startActivity(intent);
		}
		
	}

	public void Goproduct(View v) {
		if (getIntentAuthority()==0) {
			intent = new Intent(MainActivity.this, ProductActivity.class);
			startActivity(intent);
		}
		
	}

	public void purchaseru(View v) {
		if (getIntentAuthority()==2) {
			intent = new Intent(MainActivity.this, PurchaseRuActivity.class);
			intent.putExtra("title", "�ɹ����");
			startActivity(intent);
		}
		
	}

	public void purchasetui(View v) {
		if (getIntentAuthority()==2) {
			intent = new Intent(MainActivity.this, PurchaseRuActivity.class);
			intent.putExtra("title", "�ɹ��˻�");
			startActivity(intent);
		}
		
	}

	public void go_master(View v) {
		if (getIntentAuthority()==1) {
			intent = new Intent(MainActivity.this, SalesMasterListActivity.class);
			intent.putExtra("title", "���۳���");
			startActivity(intent);
		}
		
	}

	public void go_mastertui(View v) {
		if (getIntentAuthority()==1) {
			intent = new Intent(MainActivity.this, SalesMasterListActivity.class);
			intent.putExtra("title", "�����˻�");
			startActivity(intent);
		}
		
	}

	public void supplierActivity(View v) {
		if (getIntentAuthority()==2 ||getIntentAuthority()==0) {
			intent = new Intent(MainActivity.this, SupplierActivity.class);
			startActivity(intent);
		}
		
	}

	public void Gouser(View v) {
		if (getIntentAuthority()==0) {
			intent = new Intent(MainActivity.this, UsersActivity.class);
			startActivity(intent);
		}
		
	}

	public void back(View view) {
		intent=new Intent(MainActivity.this,LoginActivity.class);
		startActivity(intent);
	}

	private void initDots() {
		listDots = new ArrayList<View>();
		listDots.add(findViewById(R.id.dot01));
		listDots.add(findViewById(R.id.dot02));
		// listDots.add(findViewById(R.id.dot03));
	}

	public class MyPagerChangeListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		public void onPageSelected(int position) {
			((View) listDots.get(position))
					.setBackgroundResource(R.drawable.dot_focused);
			((View) listDots.get(oldPosition))
					.setBackgroundResource(R.drawable.dot_normal);
			oldPosition = position;
		}

	}

	public class MyPagerAdapter extends PagerAdapter {

		private List<View> list;

		public MyPagerAdapter(List<View> list) {
			this.list = list;
		}

		@Override
		public void destroyItem(View view, int index, Object arg2) {
			// TODO Auto-generated method stub
			((ViewPager) view).removeView(list.get(index));
		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object instantiateItem(View view, int index) {
			((ViewPager) view).addView(list.get(index), 0);
			return list.get(index);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == (object);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

	}
	public int getIntentAuthority(){
		AuthorityChange authorityChange=(AuthorityChange)getApplication();
		int aut=authorityChange.getAuthoritytype();
		return aut;
	}

}
