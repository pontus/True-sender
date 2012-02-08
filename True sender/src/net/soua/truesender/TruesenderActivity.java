package net.soua.truesender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.EditText;

import android.content.SharedPreferences;

import android.widget.ArrayAdapter;


public class TruesenderActivity extends Activity {
    /** Called when the activity is first created. */
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

        

        Button button = (Button) findViewById(R.id.send_button);

        
    }

 
    
    
        public void onSend(View v) {
        	
        	SharedPreferences settings = getSharedPreferences( getString(R.string.app_name), MODE_PRIVATE);
        	
           	EditText message = (EditText) findViewById(R.id.message);
        	
 
        	TextView t = (TextView) findViewById(R.id.header);
        	t.setText(message.getText());
 
        	
       	
        }

        public void onSettings(View v) {
        		startActivity(new Intent(TruesenderActivity.this, TruesenderSettingsActivity.class));
        }


}