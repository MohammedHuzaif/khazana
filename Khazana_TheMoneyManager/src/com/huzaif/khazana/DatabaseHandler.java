package com.huzaif.khazana;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	 
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Khazana";
    private static final String TABLE_USER = "USERREGISTER";
    private static final String TABLE_STATEMENT="STATEMENT";
 
    private static final String KEY_USERID = "id";
    private static final String KEY_EMAILID = "emailid";
    private static final String KEY_FIRSTNAME="firstname";
    private static final String KEY_LASTNAME="lastname";
    private static final String KEY_PIN="pin";
    private static final String KEY_DATECREATED = "datecreated";
    private static final String KEY_DATEEDITED = "dateedited";
    
    private static final String KEY_ID="rownumber";
    private static final String KEY_DATE = "date";
    private static final String KEY_DESCRIPTION="transactiondetails";
    private static final String KEY_TRANSACTION_TYPE="transactiontype";
    private static final String KEY_DEBITAMOUNT="debitamount";
    private static final String KEY_CREDITAMOUNT="creditamount";
    private static final String KEY_BALANCE="balance";
    private static final String KEY_TRANSACTION_COUNT="transactioncount";
    private static final String KEY_TURNOVER="turnover";
    
   public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
       String USER_REGISTER_TABLE = 
    		   	"CREATE TABLE " + TABLE_USER + "("
                + KEY_USERID + " INTEGER," 
                + KEY_EMAILID + " VARCHAR,"
                + KEY_FIRSTNAME + " VARCHAR," 
                + KEY_LASTNAME + " VARCHAR," 
                + KEY_PIN + " VARCHAR," 
                + KEY_DATECREATED + " VARCHAR," 
                + KEY_DATEEDITED + " VARCHAR" +")";
       
        db.execSQL(USER_REGISTER_TABLE); 
        
        String STATEMENT_TABLE = "CREATE TABLE " + TABLE_STATEMENT  + "("
                + KEY_ID  + " INTEGER," 
                + KEY_USERID + " INTEGER," 
                + KEY_DATE  + " VARCHAR,"
                + KEY_DESCRIPTION  + " VARCHAR," 
                + KEY_TRANSACTION_TYPE  + " VARCHAR," 
                + KEY_DEBITAMOUNT  + " INTEGER,"
                + KEY_CREDITAMOUNT  + " INTEGER,"
                + KEY_BALANCE + "INTEGER"
                + " )";
        
        db.execSQL(STATEMENT_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATEMENT );
        // Create tables again
        onCreate(db); 
    }
    
    String addUser(String email, String firstname, String lastname, String pin) {
    	int rowCount=0;
    	int rowcount=0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor allrows  = db.rawQuery("SELECT * FROM "+  TABLE_USER, null);
 	   	rowCount=allrows.getCount();
 	   	rowcount=rowCount+1;
 	   	
 	   	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String dateDB = sdf.format(new Date());
		       
        db.execSQL("INSERT INTO "+TABLE_USER+" VALUES " +
        "("+rowcount+"," +"'"+email+"','"+firstname+"','"+lastname+"','"+pin+"','"+dateDB+"','"+dateDB+"')");
        
        db.close(); // Closing database connection
        return dateDB;
    }    
    
    String loginCheckDB(String emailidlogin2, String pinlogin2) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String dbdata = null;

        Cursor cursor  = db.rawQuery("SELECT * FROM "+  TABLE_USER, null);
        		
		int iUserEMail = cursor.getColumnIndex(KEY_EMAILID);
		int iPin = cursor.getColumnIndex(KEY_PIN);
		
		for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
			if(emailidlogin2.equals(cursor.getString(iUserEMail)) && pinlogin2.equals(cursor.getString(iPin))){
				  String ID = cursor.getString(cursor.getColumnIndex(KEY_USERID));
			      //String EMAIL= cursor.getString(cursor.getColumnIndex(KEY_EMAILID));
			      //String FIRSTNAME= cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME));
			      //String LASTNAME= cursor.getString(cursor.getColumnIndex(KEY_LASTNAME));
			      //String PIN=cursor.getString(cursor.getColumnIndex(KEY_PIN));
			      //String blankspace=" ";
			      
			      //dbdata=ID.concat(blankspace.concat(EMAIL.concat(blankspace.concat(FIRSTNAME.concat(blankspace.concat(LASTNAME.concat(blankspace).concat(PIN)))))));   
				  	dbdata=ID;
			        db.close();
			        
			       return dbdata;
			}
			}
		
        db.close();
        
       return dbdata;
    }
    
    public String [] getUserProfile(String userID){
    	SQLiteDatabase db = this.getReadableDatabase();
    	String []user=new String[]{"userid","email ID","firstname","lastname"};
        Cursor cursor  = db.rawQuery("SELECT * FROM "+  TABLE_USER, null);
        		
		int iUserID = cursor.getColumnIndex(KEY_USERID);
		
		for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
			if(userID.equals(cursor.getString(iUserID))){
				  user[0] = (String) cursor.getString(cursor.getColumnIndex(KEY_USERID));
			      user[1]= cursor.getString(cursor.getColumnIndex(KEY_EMAILID));
			      user[2]= cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME));
			      user[3]= cursor.getString(cursor.getColumnIndex(KEY_LASTNAME));
			      
			      db.close();
			      return user;
			}
		}    	
    	return user;
    }
    
   public String[] getUserDatabase()
   {	int i=0;  
	   SQLiteDatabase db = this.getReadableDatabase(); 

	   Cursor allrows  = db.rawQuery("SELECT * FROM "+  TABLE_USER, null);
	   String [] dbdata = new String[allrows.getCount()];
	   String ID="empty";
	   try{
	   
       if(allrows.moveToFirst()){
           do{     
        	   ID = allrows.getString(0);
               String EMAIL= allrows.getString(allrows.getColumnIndex(KEY_EMAILID));
               String FIRSTNAME= allrows.getString(allrows.getColumnIndex(KEY_FIRSTNAME));
               String LASTNAME= allrows.getString(allrows.getColumnIndex(KEY_LASTNAME));
               String PIN=allrows.getString(allrows.getColumnIndex(KEY_PIN));
               String blankspace=" ";
               dbdata[i]=ID.concat(blankspace.concat(EMAIL.concat(blankspace.concat(FIRSTNAME.concat(blankspace.concat(LASTNAME.concat(blankspace).concat(PIN)))))));   
               i++;
           }
           while(allrows.moveToNext());
       	}
       db.close();
	   }catch(Exception e){
   		System.out.println("Error encountered."+e);
	   }
	return dbdata;    
   }
   
   public int getUserRowCount(){
	   int rowcount=0;
	   SQLiteDatabase db = this.getReadableDatabase();
       Cursor allrows  = db.rawQuery("SELECT * FROM "+  TABLE_USER, null);
	   rowcount=allrows.getCount();	   
	   return rowcount;
   }
   
   public String saveStatementRow(String loggedUserId, String DateTime, String transactionDescription,String transactionType,int debitAmount, int creditAmount){
	   	int rowCount=0, newBalance=0;
		String dbdataTransaction = null;
		Cursor allrows;		
			  try{
		       SQLiteDatabase db = this.getWritableDatabase();
		       allrows  = db.rawQuery("SELECT * FROM "+  TABLE_STATEMENT , null);	   	
		       rowCount=allrows.getCount(); 
			   
			   allrows.moveToLast();
		       
		       db.execSQL("INSERT INTO "+TABLE_STATEMENT +" VALUES " +"("
		    		   +rowCount+"," +"'"
		    		   +loggedUserId+"','"
		    		   +DateTime+"','"
		    		   +transactionDescription+"','"
		    		   +transactionType+"','"
		    		   +debitAmount+"','"
		    		   +creditAmount+"','"
		    		   +newBalance
		    		   +"')");
			   
			   	String ID				= allrows.getString(allrows.getColumnIndex(KEY_ID));
			    String USERID			= allrows.getString(allrows.getColumnIndex(KEY_USERID));
		 	    String DATE				= allrows.getString(allrows.getColumnIndex(KEY_DATE ));
		  	    String DESCRIPTION		= allrows.getString(allrows.getColumnIndex(KEY_DESCRIPTION ));
		  	    String TRANSACTION_TYPE	= allrows.getString(allrows.getColumnIndex(KEY_TRANSACTION_TYPE ));
		  	    String DEBITAMOUNT		= allrows.getString(allrows.getColumnIndex(KEY_DEBITAMOUNT ));
		  	    String CREDITAMOUNT		= allrows.getString(allrows.getColumnIndex(KEY_CREDITAMOUNT ));
		  	    String BALANCE			= allrows.getString(allrows.getColumnIndex(KEY_BALANCE));
		  	    String blankspace		= " ";
		  	    dbdataTransaction=ID.concat(blankspace.concat(USERID.concat(blankspace
		       		.concat(DATE.concat(blankspace.concat(DESCRIPTION.concat(blankspace.concat(TRANSACTION_TYPE.concat(blankspace
		       		.concat(DEBITAMOUNT.concat(blankspace.concat(CREDITAMOUNT.concat(blankspace.concat(BALANCE))))))))))))));  
				    
		       db.close(); // Closing database connection
			   	}catch (Exception e){
					System.out.println("Error encountered."+e);
			}
	  return dbdataTransaction;	   
   }
   
   public String [] getStatementTableRow(String UserID){
	   Cursor allrows1;	   
	   String ID="empty";
	   String error=null;
	   SQLiteDatabase db = this.getReadableDatabase();
	   String [] dbdataStatement={null};
	   
	   try{
	   int i=0;
	   allrows1 = db.rawQuery("SELECT * FROM "+  TABLE_STATEMENT  + " WHERE "+ KEY_USERID +" = "+ UserID, null);
	   dbdataStatement = new String[allrows1.getCount()];

	   if(allrows1.moveToFirst()){
           do{     
        		ID						= allrows1.getString(allrows1.getColumnIndex(KEY_ID));
        	    String USERID			= allrows1.getString(allrows1.getColumnIndex(KEY_USERID));
         	    String DATE				= allrows1.getString(allrows1.getColumnIndex(KEY_DATE ));
          	    String DESCRIPTION		= allrows1.getString(allrows1.getColumnIndex(KEY_DESCRIPTION ));
          	    String TRANSACTION_TYPE	= allrows1.getString(allrows1.getColumnIndex(KEY_TRANSACTION_TYPE ));
          	    String DEBITAMOUNT		= allrows1.getString(allrows1.getColumnIndex(KEY_DEBITAMOUNT ));
          	    String CREDITAMOUNT		= allrows1.getString(allrows1.getColumnIndex(KEY_CREDITAMOUNT ));
          	    String BALANCE			= allrows1.getString(allrows1.getColumnIndex(KEY_BALANCE));
          	    String blankspace		= " ";
          	    dbdataStatement[i]=ID.concat(blankspace.concat(USERID.concat(blankspace
               		.concat(DATE.concat(blankspace.concat(DESCRIPTION.concat(blankspace.concat(TRANSACTION_TYPE.concat(blankspace
               		.concat(DEBITAMOUNT.concat(blankspace.concat(CREDITAMOUNT.concat(blankspace.concat(BALANCE))))))))))))));    
	            i++;
           }
           while(allrows1.moveToNext());
       	}
       
   }
   
	catch(Exception e){
		
		System.out.println("Error encountered."+e);
		error=e.getLocalizedMessage();
		} 
	   db.close();
	   return dbdataStatement;
   }
   
public Cursor getStatementTable(String UserID){
	   
	   int i=0;  
	   SQLiteDatabase db = this.getReadableDatabase();	   

	   Cursor allrows;	   
	   allrows = db.rawQuery("SELECT * FROM "+  TABLE_STATEMENT  + " WHERE "+ KEY_USERID +" = "+ UserID, null);
	   
	   //allrows = db.rawQuery("SELECT * FROM "+  TABLE_TIMER , null);
	   
	   String [] dbdataStatement = new String[allrows.getCount()];
	  
	   /*
	   String ID="empty";   
	   
	   try{
	   
       if(allrows.moveToFirst()){
           do{     
        	   	ID = allrows.getString(0);
        	   	String USERID= allrows.getString(allrows.getColumnIndex(KEY_USERID));
	       	    String DATE= allrows.getString(allrows.getColumnIndex(KEY_TIMERDATE));
	       	    String TASK= allrows.getString(allrows.getColumnIndex(KEY_TASK));
	       	    String START_TIME= allrows.getString(allrows.getColumnIndex(KEY_STARTTIME));
	       	    String STOP_TIME=allrows.getString(allrows.getColumnIndex(KEY_STOPTIME));
	       	    String DURATION=allrows.getString(allrows.getColumnIndex(KEY_TIMEDURATION));       	   
	            String blankspace=" , ";
	            dbdataTimer[i]=ID.concat(blankspace.concat(USERID.concat(blankspace
	            		.concat(DATE.concat(blankspace.concat(TASK.concat(blankspace.concat(START_TIME.concat(blankspace
	            		.concat(STOP_TIME.concat(blankspace.concat(DURATION))))))))))));   
	            i++;
           }
           while(allrows.moveToNext());
       	}
       
   }
   
	catch(Exception e){
		System.out.println("Error encountered."+e);
   
		} */
	   db.close();
	   return allrows;
   }

   public int getRowCountStatement(){
	   int rowcount=0;
	   SQLiteDatabase db = this.getReadableDatabase();
       Cursor allrows  = db.rawQuery("SELECT * FROM "+  TABLE_STATEMENT , null);
	   rowcount=allrows.getCount();	   
	   return rowcount;
   }

   public String[] getUserTime(String UID)
   {	int i=0;  
	   SQLiteDatabase db = this.getReadableDatabase();	   

	   Cursor allrows;
	   
	   allrows  = db.rawQuery("SELECT * FROM "+  TABLE_USER , null);
	   
	   String [] dbdata = new String[allrows.getCount()];
	   String ID="empty";
	   try{
	   
       if(allrows.moveToFirst()){
           do{     
        	   ID = allrows.getString(0);
               String EMAIL= allrows.getString(allrows.getColumnIndex(KEY_EMAILID));
               String FIRSTNAME= allrows.getString(allrows.getColumnIndex(KEY_FIRSTNAME));
               String LASTNAME= allrows.getString(allrows.getColumnIndex(KEY_LASTNAME));
               String PIN=allrows.getString(allrows.getColumnIndex(KEY_PIN));
               String blankspace=" , ";
               dbdata[i]=ID.concat(blankspace.concat(EMAIL.concat(blankspace.concat(FIRSTNAME.concat(blankspace.concat(LASTNAME.concat(blankspace).concat(PIN)))))));   
               i++;
           }
           while(allrows.moveToNext());
       	}
       db.close();
	   }catch(Exception e){
   		System.out.println("Error encountered."+e);       
	   }
	return dbdata;
    
   }
   
   public int debitOrCredit(int debitAmount, int creditAmount){
	   int rowCount=0, oldBalance=0, newBalance=0;
	   rowCount=getRowCountStatement();
	   Cursor allrows;
	   SQLiteDatabase db = this.getWritableDatabase();
	   
	   try{
       allrows  = db.rawQuery("SELECT * FROM "+  TABLE_STATEMENT , null);	   	
       rowCount=allrows.getCount();  
	   
	   if(rowCount == 0){
		   oldBalance = 0;
	   }else if(rowCount > 0 ){   
		   allrows.moveToLast();
		   String oldBal = allrows.getString(allrows.getColumnIndex(KEY_BALANCE));
		   oldBalance = Integer.parseInt(oldBal);
	   }
	   
       if(debitAmount == 0){
    	   newBalance = oldBalance + creditAmount;
       }else{
    	   if(debitAmount > oldBalance){    		 
    	   newBalance = debitAmount - oldBalance;
    	   }else
    		   newBalance = oldBalance - debitAmount ;    	   
       }
	   }catch(Exception e){
		   
	   }
	   db.close(); // Closing database connection
       return newBalance;
   }
  
	   
}
