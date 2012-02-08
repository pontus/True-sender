package net.soua.truesender;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;

public class TruesenderSettingsActivity extends Activity {

	protected SharedPreferences settings;
	
    protected ArrayAdapter<CharSequence> operatorAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        this.operatorAdapter = ArrayAdapter.createFromResource(this, R.array.operators,
                android.R.layout.simple_spinner_dropdown_item);
        
        
        settings = getSharedPreferences( getString(R.string.app_name), MODE_PRIVATE);
        ((Spinner) findViewById(R.id.spinner)).setAdapter(this.operatorAdapter);
        
         
        ((EditText) findViewById(R.id.username)).setText(settings.getString("username", "username"));
 		((EditText) findViewById(R.id.password)).setText(settings.getString("password", "password"));
        ((EditText) findViewById(R.id.sourcenumber)).setText(settings.getString("sourcenumber", "+46"));
    }

    


    public void onSave(View v) {
    		
    	
    	SharedPreferences.Editor e = settings.edit();
    	
       	      	    	
    	e.putString("username", ((EditText) findViewById(R.id.username)).getText().toString());
    	e.putString("password", ((EditText) findViewById(R.id.password)).getText().toString());
    	e.putString("source", ((EditText) findViewById(R.id.sourcenumber)).getText().toString());
    	
    	e.commit();
    	
    	
    	finish();
    }
    
    
    public void onCancel(View v) {
    		
    	finish();
    }
    
    
}
