package allinhand.example.personanduser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import allinhand.example.cosmeticmanager.MainActivity;
import allinhand.example.cosmeticmanager.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class UsersActivity extends Activity {
	private ListView listview;
	private AutoCompleteTextView selectuser;
	private List<Map<String, String>> list = new ArrayList<Map<String, String>>(); // 定义显示的内容包装
	private SimpleAdapter simpleAdapter = null; // 进行数据的转换操作
	List items = new ArrayList();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_users);
		AuthorityChange.AuthorityChangegetIntance().AddActivity(this);
		selectuser = (AutoCompleteTextView) findViewById(R.id.UserSelectTxt);
		getUserNameOrId();
		selectuser.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (selectuser.getText().toString().equals("")) {

					getAllUsers();
				} else {
					getUserByNameorId();
				}

			}
		});
		final Builder b = new AlertDialog.Builder(this);
		listview = (ListView) findViewById(R.id.UsersListview);
		getAllUsers();
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				b.setTitle("请选择！");// 标题
				b.setItems(new String[] { "修改用户", "初始化密码" },
						new OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								switch (which) {

								case 0:// 修改该用户信息
									Intent intent = new Intent(
											UsersActivity.this,
											UserUpdataActivity.class);
									String authority = list.get(arg2)
											.get("authority").toString();
									intent.putExtra("authority", authority);
									intent.putExtra("name",
											list.get(arg2).get("username")
													.toString());
									intent.putExtra("userId", list.get(arg2)
											.get("userId").toString());
									intent.putExtra("pwd",
											list.get(arg2).get("pwd")
													.toString());
									intent.putExtra("value", "1");
									startActivity(intent);
									break;
								case 1:// 重置密码
									Dialog dg = new AlertDialog.Builder(
											UsersActivity.this)
											.setTitle("是否重置？")
											.setPositiveButton(
													"确定",
													new DialogInterface.OnClickListener() {

														public void onClick(
																DialogInterface dialog,
																int which) {
															String userId = list
																	.get(arg2)
																	.get("userId");
															String url = "http://10.0.2.2:8080/CosmeticService/resetPassword.do";
															// HttpPost连接对象，设置客户端提交方式
															HttpPost httpPost = new HttpPost(
																	url);
															// 取得HttpClient对象
															HttpClient client = new DefaultHttpClient();
															List<NameValuePair> param = new ArrayList<NameValuePair>();
															param.add(new BasicNameValuePair(
																	"userId",
																	userId));
															try {
																httpPost.setEntity(new UrlEncodedFormEntity(
																		param,
																		"utf-8"));
																HttpResponse response = client
																		.execute(httpPost);
																if (response
																		.getStatusLine()
																		.getStatusCode() == HttpStatus.SC_OK) {
																	String resetString = EntityUtils
																			.toString(response
																					.getEntity());
																	if (resetString
																			.trim()
																			.equals("success")) {
																		Toast.makeText(
																				UsersActivity.this,
																				"重置密码成功",
																				Toast.LENGTH_LONG)
																				.show();
																	} else {
																		Toast.makeText(
																				UsersActivity.this,
																				"重置密码失败",
																				Toast.LENGTH_LONG)
																				.show();
																	}
																}
															} catch (Exception e) {
																// TODO: handle
																// exception
																e.printStackTrace();
															}
														}
													})
											.setNegativeButton(
													"取消",
													new DialogInterface.OnClickListener() {

														public void onClick(
																DialogInterface dialog,
																int which) {

														}
													}).create();
									dg.show();
									break;
								default:
									break;
								}
							}
						});
				b.create().show();

			}
		});
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				Dialog dialog = new AlertDialog.Builder(UsersActivity.this)
						.setTitle("是否删除？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										String userId = list.get(arg2).get(
												"userId");
										String url = "http://10.0.2.2:8080/CosmeticService/deleteUser.do";
										// HttpPost连接对象，设置客户端提交方式
										HttpPost httpPost = new HttpPost(url);
										// 取得HttpClient对象
										HttpClient client = new DefaultHttpClient();
										List<NameValuePair> param = new ArrayList<NameValuePair>();
										param.add(new BasicNameValuePair(
												"userId", userId));
										try {
											httpPost.setEntity(new UrlEncodedFormEntity(
													param, "utf-8"));
											HttpResponse response = client
													.execute(httpPost);
											if (response.getStatusLine()
													.getStatusCode() == HttpStatus.SC_OK) {
												String deleteString = EntityUtils.toString(response.getEntity());
												//System.out.println(deleteString);
												if (deleteString.trim().equals("success")) {
													Toast.makeText(UsersActivity.this,"删除成功",Toast.LENGTH_LONG).show();
												} else if (deleteString.trim()
														.equals("notdelete")) {
													Toast.makeText(
															UsersActivity.this,
															"该用户不能删除",
															Toast.LENGTH_LONG)
															.show();
												} else {
													Toast.makeText(
															UsersActivity.this,
															"删除失败",
															Toast.LENGTH_LONG)
															.show();
												}
												Intent intent = new Intent(
														UsersActivity.this,
														UsersActivity.class);
												startActivity(intent);
											}
										} catch (Exception e) {
											// TODO: handle exception
											e.printStackTrace();
										}

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {

									}
								}).create();
				dialog.show();
				return false;
			}
		});
	}

	public void btnback(View v) {
		Intent intent=new Intent(UsersActivity.this,MainActivity.class);
		startActivity(intent);
	}

	public void AddUser(View v) {
		Intent intent = new Intent(UsersActivity.this, UserUpdataActivity.class);
		intent.putExtra("value", "2");
		startActivity(intent);
	}

	// 得到所有的用户信息
	public void getAllUsers() {
		list.clear();
		String url = "http://10.0.2.2:8080/CosmeticService/getAllUsers.do";
		// HttpGet连接对象，设置客户端提交方式
		HttpGet httpGet = new HttpGet(url);
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		try {
			// 获取HttpResponse对象
			HttpResponse response = client.execute(httpGet);
			// 如果数据正确从服务器返回
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String losespillList = EntityUtils.toString(response
						.getEntity());
				JSONArray jsonArray = new JSONArray(losespillList);
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObject = jsonArray.optJSONObject(i);
					Map<String, String> userMap = new HashMap<String, String>();
					userMap.put("userId",
							String.valueOf(jsonObject.optString("userid")));
					userMap.put("username",
							String.valueOf(jsonObject.optString("username")));
					userMap.put("roles", String.valueOf(jsonObject
							.optString("authorityType")));
					userMap.put("authority",
							String.valueOf(jsonObject.getInt("userauthority")));
					userMap.put("pwd", String.valueOf(jsonObject
							.optString("passwordcode")));
					list.add(userMap);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		SimpleAdapter adapter = new SimpleAdapter(this, this.list,
				R.layout.userslistview, new String[] { "username", "roles" } // Map中的key的名称
				, new int[] { R.id.UserListName, R.id.UserListRole });

		listview.setAdapter(adapter);
	}

	// 根据用户ID或者用户名得到相应的信息

	public void getUserNameOrId() {
		String url = "http://10.0.2.2:8080/CosmeticService/getAllUsers.do";
		// HttpGet连接对象，设置客户端提交方式
		HttpGet httpGet = new HttpGet(url);
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		try {
			// 获取HttpResponse对象
			HttpResponse response = client.execute(httpGet);
			// 如果数据正确从服务器返回
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String nameandid = EntityUtils.toString(response.getEntity());
				JSONArray jsonArray = new JSONArray(nameandid);
				int j = 0;
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jo = jsonArray.optJSONObject(i);

					items.add(j, String.valueOf(jo.optString("userid")));
					j++;
					items.add(j, String.valueOf(jo.optString("username")));
					j++;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.UserSelectTxt);
		actv.setAdapter(aa);
	}

	// 根据用户名或编号得到相应的数据
	public void getUserByNameorId() {
		list.clear();
		String nameorid = selectuser.getText().toString();
		String url = "http://10.0.2.2:8080/CosmeticService/getUsersByNameOrId.do";
		// HttpPost连接对象，设置客户端提交方式
		HttpPost httpPost = new HttpPost(url);
		// 取得HttpClient对象
		HttpClient client = new DefaultHttpClient();
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		param.add(new BasicNameValuePair("name", nameorid));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
			HttpResponse response = client.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String losespillList = EntityUtils.toString(response
						.getEntity());
				JSONObject jsonObject = new JSONObject(losespillList);
				Map<String, String> items = new HashMap<String, String>();
				items.put("userId",
						String.valueOf(jsonObject.optString("userid")));
				items.put("username",
						String.valueOf(jsonObject.optString("username")));
				items.put("roles",
						String.valueOf(jsonObject.optString("authorityType")));
				items.put("authority",
						String.valueOf(jsonObject.getInt("userauthority")));
				items.put("pwd",
						String.valueOf(jsonObject.optString("passwordcode")));
				list.add(items);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		SimpleAdapter adapter = new SimpleAdapter(this, this.list,
				R.layout.userslistview, new String[] { "username", "roles" } // Map中的key的名称
				, new int[] { R.id.UserListName, R.id.UserListRole });

		listview.setAdapter(adapter);
	}
}
