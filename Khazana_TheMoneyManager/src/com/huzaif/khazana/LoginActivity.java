package com.huzaif.khazana;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.huzaif.khazana.MESSAGE";
	LinearLayout l1,l2,l3;
	EditText emailidreg, firstnamereg, lastnamereg,pin1reg,pin2reg,emailidlogin11,pinlogin11;
	String emailidreg1, firstnamereg1, lastnamereg1,pin1reg1,pin2reg1,emailidlogin1,pinlogin1;
	String userID=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		//displayToast("Please enter your login credentials");
		
		/*userID="1";
		//displayToast("DB ROW = "+userID);
		Intent i=new Intent(this,MenuActivity.class);
		i.putExtra("loggedInUser", userID);
		startActivity(i);
		displayToast("User ID: "+userID);
		*/
	
	}
	
	public void showregister(View view){
		
		setContentView(R.layout.activity_login);
		
		l2= (LinearLayout) findViewById(R.id.tablelayoutregister);
		l2.setVisibility(View.VISIBLE);
		
		l3= (LinearLayout) findViewById(R.id.tablelayoutlogin);
		l3.setVisibility(View.GONE);
		
		displayToast("Please enter your details to register as a new user");
	}	
	
	public void showLogin(View view){
		
		setContentView(R.layout.activity_login);
		
		l2= (LinearLayout) findViewById(R.id.tablelayoutregister);
		l2.setVisibility(View.GONE);
		
		l3= (LinearLayout) findViewById(R.id.tablelayoutlogin);
		l3.setVisibility(View.VISIBLE);
		
		displayToast("Please enter your credentials");
	}	

	public void logincheck(View view){
		
		try{
			
		emailidlogin11=(EditText) findViewById(R.id.emailidlogin);
		pinlogin11=(EditText) findViewById(R.id.passwordlogin);
		
		emailidlogin1=emailidlogin11.getText().toString();
		pinlogin1=pinlogin11.getText().toString();
		
		if (emailidlogin1.equals("") ){
			displayToast("Enter Username");
  		return;
    	}
		
    	if (pinlogin1.equals("") ){
    		displayToast("Enter PIN");
  		return;
    	}
				
		DatabaseHandler dblogin = new DatabaseHandler(this);		
		userID=dblogin.loginCheckDB(emailidlogin1,pinlogin1);
		
		if(userID==null){
			displayToast("Unsucessful... :( ");
		}else{
			//displayToast("Success.... :) ");
			displayToast("DB ROW = "+userID);
			Intent i=new Intent(this,MenuActivity.class);
			i.putExtra("loggedInUser", userID);
			startActivity(i);
			displayToast("User ID: "+userID);
		}
		
		}catch(Exception e){
			String error=e.toString();
			Dialog d=new Dialog(this);
			d.setTitle("Unsuccessfull");
			d.setCanceledOnTouchOutside(true);
			TextView tv=new TextView(this);
			tv.setText(error);
			d.setContentView(tv);
			d.show();			
		}
		
	}	
	
	public void registerNewUser(View view){
		Boolean notempty=true;
		Boolean pinmatch=false;
		
		emailidreg=(EditText) findViewById(R.id.emailidregister);
		firstnamereg=(EditText) findViewById(R.id.firstnameregister);
		lastnamereg=(EditText) findViewById(R.id.lastnameregister);
		pin1reg=(EditText) findViewById(R.id.loginpasswordregister);
		pin2reg=(EditText) findViewById(R.id.confirmloginpasswordregister);
		
		emailidreg1 = emailidreg.getText().toString();
		firstnamereg1=firstnamereg.getText().toString();
		lastnamereg1=lastnamereg.getText().toString();
		pin1reg1=pin1reg.getText().toString();
		pin2reg1=pin2reg.getText().toString();
		
		if(emailidreg1.equals("")){
			displayToast("Enter Company Email ID");
			notempty=false;
			return;
		}
		 
		if(firstnamereg1.equals("")){
			displayToast("Enter First Name");
			notempty=false;
			return;
		}
		
		if( lastnamereg1.equals("")){
			displayToast("Enter Last Name");
			notempty=false;
			return;
		}
		
		if(  pin1reg1.equals("") ){
			displayToast("Enter Login PIN");
			notempty=false;
			return;
		}
		
		if( pin2reg1.equals("")){
			displayToast("Confirm PIN");
			notempty=false;
			return;
		}
		
		if(pin1reg1.equals(pin2reg1)){
			pinmatch=true;
		}else 
			displayToast("PIN's Do not match");

		if(notempty==true && pinmatch==true){
					DatabaseHandler dbregister = new DatabaseHandler(this);
			        // Register New User
					String dateDB=dbregister.addUser(emailidreg1,firstnamereg1, lastnamereg1,pin1reg1);
					displayToast("pinmatch: "+pinmatch+" "+"notempty: "+notempty);
					displayToast("Values entered...."+dateDB);
					setContentView(R.layout.activity_login);
					l1= (LinearLayout) findViewById(R.id.tablelayoutlogin);
					l2= (LinearLayout) findViewById(R.id.tablelayoutregister);
					l1.setVisibility(View.VISIBLE);
					l2.setVisibility(View.INVISIBLE);
			}else{
				displayToast("pinmatch: "+pinmatch+" "+"notempty: "+notempty);
				displayToast("Error on page....");				
			} 
	}
	
	
	/*public void getdatabase(View view)
	{
		String loggedUserId="1";
		Intent i=new Intent(this,DatabaseData.class);
		i.putExtra("loggedUserId",loggedUserId);
		startActivity(i);
				
	} */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
