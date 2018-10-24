package allinhand.example.cosmeticmanager;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

public class AuthorityChange extends Application{
	private static AuthorityChange instance;
	private int authoritytype;
	private boolean islogin;
	private String username;
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	private List<Activity> Activitylist=new LinkedList<Activity>();
	public boolean isIslogin() {
		return islogin;
	}

	public void setIslogin(boolean islogin) {
		this.islogin = islogin;
	}

	public int getAuthoritytype() {
		return authoritytype;
	}

	public void setAuthoritytype(int authoritytype) {
		this.authoritytype = authoritytype;
	}
	public AuthorityChange(){}
	
	public static  AuthorityChange AuthorityChangegetIntance() {
		if (instance==null) {
			instance = new AuthorityChange();
		}
		return instance;
	}
	//添加Activity到Activitylist容器中
	public void AddActivity(Activity activity) {
		Activitylist.add(activity);
	}
	//遍历所有Activity并finish
	public void exit(){
		for(Activity activity:Activitylist){
			activity.finish();
		}
		System.exit(0);
	}
}
