package com.huzaif.khazana;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class ViewStatementActivity extends Activity {

	String loggedUserId;
	private static final String TAG = "MyApp";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_view_statement);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Intent mIntent = getIntent();
		loggedUserId=mIntent.getStringExtra("loggedInUser");
		displayToast("View Statement loggedUserId: "+loggedUserId);
		
		getStatement();
		
	}
	
	
	public void getStatement()
	{	
		DatabaseHandler db=new DatabaseHandler(this);
		String dbDt=" ";
		int rowCount=db.getRowCountStatement();
		displayToast("rowCount: "+rowCount);
		TextView dbdata=(TextView) findViewById(R.id.getData11);
		
		try{
		String  dbData [] = db.getStatementTableRow(loggedUserId);
		//Log.v(TAG, "Exception Raised "+dbData);
		//displayToast(dbData);
		for(int i=0;i<rowCount;i++){
			dbDt=dbDt.concat(dbData[i].concat("\n"));
			}
		dbdata.setText(dbDt);
		}catch (Exception e){
			Log.v(TAG, "Exception Raised "+e.getStackTrace());
			Log.v(TAG, "Exception Raised "+e.getLocalizedMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_statement, menu);
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return false;
		}
		return super.onOptionsItemSelected(item);
	}

	public void displayToast(String msg){
		Toast toast= Toast.makeText(getApplicationContext(), 
				msg, Toast.LENGTH_SHORT);  
				toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
	}
}
