package net.soua.truesender;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;
import android.view.View;
import android.widget.Button;

import android.widget.ArrayAdapter;


public class TruesenderActivity extends Activity {
    /** Called when the activity is first created. */
	

    protected ArrayAdapter<CharSequence> operatorAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

        this.operatorAdapter = ArrayAdapter.createFromResource(this, R.array.operators,
                android.R.layout.simple_spinner_dropdown_item);
        
        
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setAdapter(this.operatorAdapter);
        

        Button button = (Button) findViewById(R.id.send_button);

        
    }

 
    
    
        public void onSend(View v) {
            // Perform action on click
        }

        public void onSettings(View v) {
            // Perform action on click
        }


}