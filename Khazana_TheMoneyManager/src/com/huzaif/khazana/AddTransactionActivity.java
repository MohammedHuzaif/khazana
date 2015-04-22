package com.huzaif.khazana;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddTransactionActivity extends Activity {
		
		private RadioGroup radioGroup;
		private RadioButton radioButton;
		private DatePicker dp;
		private TimePicker tp;
		EditText amount, transactionDetails;
		String amount11, transactionDetails11;
		DatabaseHandler db=new DatabaseHandler(this);
		String loggedUserId=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_transaction);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		Intent mIntent = getIntent();
		loggedUserId=mIntent.getStringExtra("loggedInUser");
		displayToast("AddTransaction loggedUserId: "+loggedUserId);
	}
	
	public void addTransaction(View view){
		int debitAmount=0, creditAmount=0;
		String savedData=null;
		try{		
		
		 dp = (DatePicker) findViewById(R.id.datePicker1);
	     tp = (TimePicker) findViewById(R.id.timePicker1);
	        
	                String Date = dp.getYear() + "-" + (dp.getMonth() + 1) + "-" + dp.getDayOfMonth() ;
	                String Time = tp.getCurrentHour() + ":" + tp.getCurrentMinute();
	                //displayToast(Time);
	                //displayToast(Date);
	                String DateTime=Date.concat(" ").concat(Time);
	                //displayToast("DateTime: "+DateTime);
		
					radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
					int selectedId = radioGroup.getCheckedRadioButtonId();
				    radioButton = (RadioButton) findViewById(selectedId);
					
				    String radioValue=radioButton.getText().toString();
					//displayToast("radioValue: "+radioValue);
		
					amount = (EditText)findViewById(R.id.amount1);
					amount11=amount.getText().toString();
					//displayToast("amount11: "+amount11);
					
					transactionDetails = (EditText)findViewById(R.id.transactionDescription1);
					transactionDetails11=transactionDetails.getText().toString();
					//displayToast("transactionDetails11: "+transactionDetails11);
					
					if(radioValue.equals("CREDIT")){
						debitAmount=0;
						creditAmount=Integer.parseInt(amount11);
					}else{
						if(radioValue.equals("DEBIT")){
						creditAmount=0;
						debitAmount=Integer.parseInt(amount11);
						}
					}
						
					//displayToast("debitAmount: "+Integer.toString(debitAmount));
					//displayToast("creditAmount: "+Integer.toString(creditAmount));
					
					savedData=db.saveStatementRow(loggedUserId,DateTime,transactionDetails11,radioValue,debitAmount, creditAmount);
					//setContentView(R.layout.activity_add_transaction);
					displayToast("Added: "+savedData);
		}catch(Exception e){
			displayToast("Added: "+savedData);
		}finally{
			//setContentView(R.layout.activity_add_transaction);
			//displayToast("All fields are mandatory");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_transaction, menu);
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
