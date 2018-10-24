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
		//隐藏标题 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		initViewPager();
		initDots();
		//获取list 
		View view1 = listView.get(0);
		View view2 = listView.get(1);
		//获取商品图片的控件
		product = (ImageView) view1.findViewById(R.id.productimg);
		//获取用户图片的控件
		users = (ImageView) view1.findViewById(R.id.user);
		//获取个人设置的控件
		person = (ImageView) view1.findViewById(R.id.personset);
		//获取供应商图片的控件
		supplier = (ImageView) view1.findViewById(R.id.supplier);
		//获取采购入库图片的控件
		buychu = (ImageView) view1.findViewById(R.id.purchaseru);
		//获取采购退货图片的控件
		buytui = (ImageView) view1.findViewById(R.id.purchasetui);
		//获取顾客图片的控件
		customer = (ImageView) view1.findViewById(R.id.client);
		//获取销售出货图片的控件
		salechu = (ImageView) view1.findViewById(R.id.deliverychu);
		//获取销售退货图片的控件
		saletui = (ImageView) view1.findViewById(R.id.deliverytui);
		//获取库存盘点图片的控件
		stocktak = (ImageView) view2.findViewById(R.id.stockpandian);
		//获取库存预警图片的控件
		stockwarn = (ImageView) view2.findViewById(R.id.stock_report);
		//获取库存查询图片的控件
		stockselect = (ImageView) view2.findViewById(R.id.query_product);
		//假如用户是销售员、采购员、库管员
		if (getIntentAuthority() == 1 || getIntentAuthority() == 2 || getIntentAuthority() == 3) {
			users.setImageDrawable(getResources().getDrawable(R.drawable.user2));
			product.setImageDrawable(getResources().getDrawable(R.drawable.product2));
		}
		//假如用户是管理员、销售员、采购员
		if (getIntentAuthority()==0||getIntentAuthority()==1||getIntentAuthority()==2) {
			stocktak.setImageDrawable(getResources().getDrawable(R.drawable.warehouse2));
			stockselect.setImageDrawable(getResources().getDrawable(R.drawable.query_product2));
		}
		//假如用户是管理员、销售员、库管员
		if (getIntentAuthority() == 0 || getIntentAuthority()==1 ||getIntentAuthority()==3) {
			
			buychu.setImageDrawable(getResources().getDrawable(
					R.drawable.purchaseru2));
			buytui.setImageDrawable(getResources().getDrawable(
					R.drawable.purchasetui2));
		}
		//假如用户是管理员、采购员、库管员
		if (getIntentAuthority()==0 || getIntentAuthority()==2 || getIntentAuthority()==3) {
			salechu.setImageDrawable(getResources().getDrawable(
					R.drawable.deliverychu2));
			saletui.setImageDrawable(getResources().getDrawable(
					R.drawable.deliverytui2));
		}
		//假如用户是销售员、库管员
		if (getIntentAuthority()==1 || getIntentAuthority()==3) {
			supplier.setImageDrawable(getResources().getDrawable(R.drawable.supplier2));
		}
		//假如用户是采购员、库管员
		if (getIntentAuthority()==2 || getIntentAuthority()==3) {
			customer.setImageDrawable(getResources().getDrawable(R.drawable.client2));
		}
	}
	//初始化
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
			intent.putExtra("title", "采购入库");
			startActivity(intent);
		}
		
	}

	public void purchasetui(View v) {
		if (getIntentAuthority()==2) {
			intent = new Intent(MainActivity.this, PurchaseRuActivity.class);
			intent.putExtra("title", "采购退货");
			startActivity(intent);
		}
		
	}

	public void go_master(View v) {
		if (getIntentAuthority()==1) {
			intent = new Intent(MainActivity.this, SalesMasterListActivity.class);
			intent.putExtra("title", "销售出库");
			startActivity(intent);
		}
		
	}

	public void go_mastertui(View v) {
		if (getIntentAuthority()==1) {
			intent = new Intent(MainActivity.this, SalesMasterListActivity.class);
			intent.putExtra("title", "销售退货");
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
