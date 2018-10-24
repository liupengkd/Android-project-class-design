package allinhand.example.SQLite;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class DBHelper extends SQLiteOpenHelper{

	//���ݿ�汾��
    private static final int DATABASE_VERSION=4;

    //���ݿ�����
    private static final String DATABASE_NAME="manager.db";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

	@Override
    public void onCreate(SQLiteDatabase db) {
        //�������ݱ�

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
        //����ɱ���ڣ�ɾ�����������ݽ�����ʧ
        db.execSQL("DROP TABLE IF EXISTS "+ Deliverydetail.TABLE);

        //�ٴδ�����
        onCreate(db);
    }
	
}
