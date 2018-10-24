package allinhand.example.SQLite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DBHelper extends SQLiteOpenHelper{

	//数据库版本号
    private static final int DATABASE_VERSION=4;

    //数据库名称
    private static final String DATABASE_NAME="manager.db";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

	@Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据表

        String CREATE_TABLE_DELIVERYDETAIL="CREATE TABLE "+Deliverydetail.TABLE+"("
        		+ Deliverydetail.KEY_ID + " VARCHAR PRIMARY KEY AUTOINCREMENT ,"
        		+ Deliverydetail.KEY_Pid + " VARCHAR,"
        		+ Deliverydetail.KEY_Price + " REAL,"
        		+ Deliverydetail.KEY_Quantity+" INTEGER)";
        db.execSQL(CREATE_TABLE_DELIVERYDETAIL);
        String CREATE_TABLE_DELIVERY_MASTER="CREATE TABLE "+Deliverymaster.TABLE+"("
        		+ Deliverydemaster.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //如果旧表存在，删除，所以数据将会消失
        db.execSQL("DROP TABLE IF EXISTS "+ Deliverydetail.TABLE);

        //再次创建表
        onCreate(db);
    }
	
}
