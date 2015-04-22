package com.huzaif.khazana;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class MenuActivity extends Activity {

	String loggedUserId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Intent mIntent = getIntent();
		loggedUserId=mIntent.getStringExtra("loggedInUser");
		displayToast("MenuActivity loggedUserId: "+loggedUserId);
	}
	
	public void newTransaction(View view){
		displayToast("New Transaction");
		Intent i=new Intent(this,AddTransactionActivity.class);
		i.putExtra("loggedInUser", loggedUserId);
		startActivity(i);
	}
	
	public void viewStatement(View view){
		Intent i=new Intent(this,ViewStatementActivity.class);
		i.putExtra("loggedInUser", loggedUserId);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
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
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		return false;
	}	

	public void displayToast(String msg){
		Toast toast= Toast.makeText(getApplicationContext(), 
				msg, Toast.LENGTH_SHORT);  
				toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
	}

}
