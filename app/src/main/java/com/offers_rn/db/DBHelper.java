package com.offers_rn.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.offers_rn.menulist.StarredMessage;
import com.offers_rn.parseobject.Jobs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract.Data;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "UserDB.db";
	public static final String OFFERS_TYPE = "Type";
	public static final String OFFERS_OBJECTID= "ObjectID";
	public static final String OFFERS_DEADLINE = "Deadline";
	public static final String OFFERS_COMPANY = "Company";
	public static final String OFFERS_JOB_TITLE = "JobTitle";
	
	public static final String STAR_STRING ="Star_string";
	public static final String STAR_CLASS="Star_class";
	public static final String STAR_TIMESTAMP = "timestamp";
	
	public static final String TABLE_NAME = "offers";
	public static final String TABLE_BLACKLIST = "offers_blacklist";
	public static final String TABLE_STARLIST = "offers_starmessage";
	public static final String KEY_ID = "_id";
	
	public static final String CREATE_TABLE = 
            "CREATE TABLE " + TABLE_NAME + " (" + 
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            OFFERS_TYPE + " TEXT NOT NULL, " +
            OFFERS_OBJECTID + " TEXT NOT NULL, " +
            OFFERS_DEADLINE + " TEXT NOT NULL, " +
            OFFERS_COMPANY + " TEXT NOT NULL, " +
            OFFERS_JOB_TITLE + " TEXT NOT NULL)";
	
	public static final String CREATE_BLACKLIST = 
            "CREATE TABLE " + TABLE_BLACKLIST + " (" + 
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            OFFERS_TYPE + " TEXT NOT NULL, " +
            OFFERS_OBJECTID + " TEXT NOT NULL, " +
            OFFERS_DEADLINE + " TEXT NOT NULL, " +
            OFFERS_COMPANY + " TEXT NOT NULL, " +
            OFFERS_JOB_TITLE + " TEXT NOT NULL)";
	
	
	public static final String CREATE_STARLIST = 
            "CREATE TABLE " + TABLE_STARLIST + " (" + 
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            STAR_STRING + " TEXT NOT NULL, " +
            STAR_CLASS+" TEXT NOT NULL,"+
            STAR_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)";

/*	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		//super(context, name, factory, version);
		  super(context,DATABASE_NAME,null,1);
		// TODO Auto-generated constructor stub
	} */
	
	public DBHelper(Context context) {
		//super(context, name, factory, version);
		  super(context,DATABASE_NAME,null,1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//onCreate() is only run when the database file did not exist
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE);
		db.execSQL(CREATE_BLACKLIST);
		db.execSQL(CREATE_STARLIST);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		//onUpgrade() is only called when the database file exists but the stored version number is lower than requested in constructor. The onUpgrade() should update the table schema to the requested version.
		
		db.execSQL("DROP TABLE IF EXISTS offers");
	    onCreate(db);
		
	}
	
	public boolean insertOffers (String table_name, String type, String ObjectID, String Deadline, String Company,String Job_Title)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      contentValues.put(OFFERS_TYPE, type);
	      contentValues.put(OFFERS_OBJECTID, ObjectID);
	      contentValues.put(OFFERS_DEADLINE, Deadline);
	      contentValues.put(OFFERS_COMPANY, Company);
	      contentValues.put(OFFERS_JOB_TITLE, Job_Title);
	      db.insert(table_name, null, contentValues);
	      return true;
	   }
	
	public boolean deleteOffers (String table_name, String ObjectID)
	   {
	      SQLiteDatabase db = this.getWritableDatabase();
	      String where = OFFERS_OBJECTID + "='" + ObjectID+"'";
	        // �R�����w�s����ƨæ^�ǧR���O�_���\
	        return db.delete(table_name, where , null) > 0;
	   }
	
	public boolean insert_StarMessage(String star_string,String class_name){
		SQLiteDatabase db = this.getWritableDatabase();
	    ContentValues contentValues = new ContentValues();
	    contentValues.put(STAR_STRING, star_string);
	    contentValues.put(STAR_CLASS, class_name);
	    db.insert(TABLE_STARLIST, null, contentValues);
		return true;
	}
	
	public boolean checkExist(String ObjectID){
		SQLiteDatabase db = this.getReadableDatabase();
	    Cursor res =  db.rawQuery( "select * from offers where "+OFFERS_OBJECTID+'='+"'"+ObjectID+"'", null );
	    if(res.getCount() <= 0){
            res.close();
            return false;
        }
    res.close();
    return true;
	}
	
	public ArrayList<StarredMessage> getAllStarMessages(){
		
		  ArrayList<StarredMessage> array_list = new ArrayList<StarredMessage>();
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from "+TABLE_STARLIST, null );
	      res.moveToFirst();
	      
	      while(res.isAfterLast() == false){
		    	StarredMessage tempmsg = new StarredMessage(res.getString(res.getColumnIndex(STAR_STRING)),res.getString(res.getColumnIndex(STAR_CLASS)));
		    	
		        array_list.add(tempmsg);
		        res.moveToNext();
		      }
		   return array_list;
		
	}
	
	public ArrayList<Jobs> getAllOffers(String table_name)
	   {
	      ArrayList<Jobs> array_list = new ArrayList<Jobs>();
	      
	      //hp = new HashMap();
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from "+table_name, null );
	      res.moveToFirst();
	      
	      while(res.isAfterLast() == false){
	    	 Jobs newjob = new Jobs();
	    	 newjob.SetCompany(res.getString(res.getColumnIndex(OFFERS_COMPANY)));
	    	 newjob.SetJobTitle(res.getString(res.getColumnIndex(OFFERS_JOB_TITLE)));
	    	 newjob.setObjectId(res.getString(res.getColumnIndex(OFFERS_OBJECTID)));
	    	 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    	 Date date;
			 try {
				date = sdf.parse(res.getString(res.getColumnIndex(OFFERS_DEADLINE)));
				newjob.setDeadline(date);
				Log.v("log",date.toString());
			 } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }
	    	 
	    	 newjob.setType(res.getString(res.getColumnIndex(OFFERS_TYPE)));
	         array_list.add(newjob);
	         res.moveToNext();
	      }
	   return array_list;
	   }

}
