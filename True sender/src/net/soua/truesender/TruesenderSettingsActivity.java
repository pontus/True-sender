package net.soua.truesender;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.telephony.TelephonyManager;
import android.content.Context;

public class TruesenderSettingsActivity extends Activity {

	protected SharedPreferences settings;
	
    protected ArrayAdapter<CharSequence> operatorAdapter;

    private String operator;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        this.operatorAdapter = ArrayAdapter.createFromResource(this, R.array.operators,
                android.R.layout.simple_spinner_dropdown_item);
        
        
        settings = getSharedPreferences( getString(R.string.app_name), MODE_PRIVATE);
        
        operator = settings.getString("operator", "");
        
        if (!operator.equals(""))
        {
        	//this.operatorAdapter.insert( operator.toCharArray(), 0);
        }
        
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        
        sp.setAdapter(this.operatorAdapter);
        sp.setOnItemSelectedListener(new myOnItemSelectedListener());
        
        
        String phoneNumber = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number(); 
         
        ((EditText) findViewById(R.id.username)).setText(settings.getString("username+"+operator, "username"));
 		((EditText) findViewById(R.id.password)).setText(settings.getString("password+"+operator, "password"));
        ((EditText) findViewById(R.id.sourcenumber)).setText(settings.getString("sourcenumber+"+operator, phoneNumber));
    }

    


    public void onSave(View v) {
    		
    	
    	SharedPreferences.Editor e = settings.edit();
    	
       	e.putString("operator", operator);     	    	
    	e.putString("username+"+operator, ((EditText) findViewById(R.id.username)).getText().toString());
    	e.putString("password+"+operator, ((EditText) findViewById(R.id.password)).getText().toString());
    	e.putString("sourcenumber+"+operator, ((EditText) findViewById(R.id.sourcenumber)).getText().toString());
    	
    	e.commit();
    	
    	
    	finish();
    }
    
    
    public void onCancel(View v) {
    		
    	finish();
    }
    
    
    public class myOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent,
            View view, int pos, long id) {
          operator =  parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView<?> parent) {
          // Do nothing.
        }
    }
    
}
