package net.soua.truesender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.widget.TextView;
import android.widget.EditText;

import android.content.SharedPreferences;
import android.content.DialogInterface;

import android.os.AsyncTask;

public class TruesenderActivity extends Activity {
    /** Called when the activity is first created. */
	
	
	protected ProgressDialog dialog;
	protected String send_error;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        checkFirstRun();

        
    }

 
    
    
        public void onSend(View v) {
        	
        	if (!settingsOk())
        	{
        		startActivity(new Intent(TruesenderActivity.this, TruesenderSettingsActivity.class));
        		return;
        	}
        	
	    	SharedPreferences settings = getSharedPreferences( getString(R.string.app_name), MODE_PRIVATE);
        	
           	EditText message = (EditText) findViewById(R.id.message);
        	
           	dialog = ProgressDialog.show(this, "", 
                    "Sending. Please wait...", true);
           	
        	
            class SendTask extends AsyncTask<String, Void, Integer> {
                protected Integer doInBackground(String... msg) {
                	try { 
                		Thread.sleep(4000);
                	}
                	catch(InterruptedException e)
                	{
                		
                	}
                	return 1;
                }

                protected void onPostExecute(Integer result) {
                    //if (result > 0)
                    {
                 	   new AlertDialog.Builder(TruesenderActivity.this)
             	      .setMessage(getString(R.string.send_failed)+send_error)
             	      .setTitle(getString(R.string.app_name))
             	      .setCancelable(true)   
             	      .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
             	           public void onClick(DialogInterface dialog, int id) {
             	                dialog.cancel();
             	           }})
             	      
             	      .show();
	
                    }
                    
                    	
                    dialog.dismiss();
                }}
                
            	new SendTask().execute(message.getText().toString());

            
            }

        	
        


        
        public void onSettings(View v) {
        		startActivity(new Intent(TruesenderActivity.this, TruesenderSettingsActivity.class));
        }


        private boolean settingsOk() {
	    	SharedPreferences settings = getSharedPreferences( getString(R.string.app_name), MODE_PRIVATE);

	    	// If operator is set, all is well
	    	return (!settings.getString("operator","").equals(""));
	    		
                
        }

        
        
        private void checkFirstRun() {

        	if (settingsOk())
        			return;
        	
    	   new AlertDialog.Builder(this)
        	      .setMessage(getString(R.string.long_welcome))
        	      .setTitle(getString(R.string.welcome))
        	      .setCancelable(true)   
        	      .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
        	           public void onClick(DialogInterface dialog, int id) {
        	                dialog.cancel();
        	        		startActivity(new Intent(TruesenderActivity.this, TruesenderSettingsActivity.class));
        	           }})
        	      
        	      .show();
        	   
        }
       

}