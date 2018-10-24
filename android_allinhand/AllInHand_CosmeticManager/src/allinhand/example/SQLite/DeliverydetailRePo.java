package allinhand.example.SQLite;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DeliverydetailRePo {

	private DBHelper dbHelper;

    public DeliverydetailRePo(Context context){
        dbHelper=new DBHelper(context);
    }

    public int insert(Deliverydetail deliverydetail){
        //打开连接，写入数据
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(Deliverydetail.KEY_ID,deliverydetail.delivery_ID);
        values.put(Deliverydetail.KEY_Pid,deliverydetail.product_ID);
        values.put(Deliverydetail.KEY_Price, deliverydetail.sales_price);
        values.put(Deliverydetail.KEY_Quantity,deliverydetail.sales_quantity);

        //
        long Deliverydetail_id=db.insert(Deliverydetail.TABLE,null,values);
        db.close();
        return (int)Deliverydetail_id;
    }

    public void delete(int Deliverydetail_id){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        db.delete(Deliverydetail.TABLE,Deliverydetail.KEY_ID+"=?", new String[]{String.valueOf(Deliverydetail_id)});
        db.close();
    }
    
    

    public ArrayList<HashMap<String, String>> getDeliveryDetailList(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        String selectQuery="SELECT "+
                Deliverydetail.KEY_ID+","+
                Deliverydetail.KEY_Pid+","+
                Deliverydetail.KEY_Price+"," +
                Deliverydetail.KEY_Quantity+
                " FROM "+Deliverydetail.TABLE;
        ArrayList<HashMap<String,String>> deliverydetailList=new ArrayList<HashMap<String, String>>();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String,String> deliverydetail=new HashMap<String,String>();
                deliverydetail.put("id",cursor.getString(cursor.getColumnIndex(Deliverydetail.KEY_ID)));
                deliverydetail.put("Pid",cursor.getString(cursor.getColumnIndex(Deliverydetail.KEY_Pid)));
                deliverydetailList.add(deliverydetail);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return deliverydetailList;
    }

    public Deliverydetail getDeliverydetailById(int Id){
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        String selectQuery="SELECT "+
        		 Deliverydetail.KEY_ID+","+
                 Deliverydetail.KEY_Pid+","+
                 Deliverydetail.KEY_Price+"," +
                 Deliverydetail.KEY_Quantity+
                 " FROM "+Deliverydetail.TABLE
                + " WHERE " +
                Deliverydetail.KEY_ID + "=?";
        int iCount=0;
        Deliverydetail deliverydetail=new Deliverydetail();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(Id)});
        if(cursor.moveToFirst()){
            do{
                deliverydetail.delivery_ID=cursor.getString(cursor.getColumnIndex(Deliverydetail.KEY_ID));
                deliverydetail.product_ID =cursor.getString(cursor.getColumnIndex(Deliverydetail.KEY_Pid));
                deliverydetail.sales_price =cursor.getFloat(cursor.getColumnIndex(Deliverydetail.KEY_Price));
                deliverydetail.sales_quantity =cursor.getInt(cursor.getColumnIndex(Deliverydetail.KEY_Quantity));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return deliverydetail;
    }
}
