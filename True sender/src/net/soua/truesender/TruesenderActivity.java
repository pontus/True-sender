package net.soua.truesender;

import android.text.TextWatcher;
import android.util.Log;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.widget.EditText;
import android.widget.TextView;

import android.content.SharedPreferences;
import android.content.DialogInterface;

import android.os.AsyncTask;
import android.text.Editable;

public class TruesenderActivity extends Activity {
	/** Called when the activity is first created. */

	private static final String TAG = "True Sender";
	protected ProgressDialog dialog;
	protected String send_error;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		EditText message = (EditText) findViewById(R.id.message);
		message.addTextChangedListener(new CharCounter());
		

		checkFirstRun();
	}

	
	
	class CharCounter implements TextWatcher {
		public void afterTextChanged(Editable s) {
			EditText message = (EditText) findViewById(R.id.message);
			int count = message.getText().length();
			TextView chars = (TextView) findViewById(R.id.charcount);
			chars.setText(160-count+"");
		}

		public void beforeTextChanged(CharSequence s, int a, int b, int c) {
		
		}
		public void onTextChanged(CharSequence s,int a,int b,int c) {
			
		}
	}
	
	
	
	
	
	
	
	class SendTask extends AsyncTask<String, Void, Integer> {
		@Override
		protected Integer doInBackground(String... msg) {

			Log.i(TAG, "Sending message");

			SharedPreferences settings = getSharedPreferences(
					getString(R.string.app_name), MODE_PRIVATE);

			String operatorName = settings.getString("operator", "");

			String password = settings.getString("password+"+operatorName, "");
			String username = settings.getString("username+"+operatorName, "");
			String source = settings.getString("sourcenumber+"+operatorName, "");
			
			String classname = "net.soua.truesender."
					+ operatorName.replaceAll("\\s", "") + "Operator";

			Log.i(TAG, "Using operator " + operatorName);

			try {

				OperatorInterface operator = (OperatorInterface) Class.forName(
						classname).newInstance();
				
				operator.passwordAuth(username, password);
				operator.send(msg[0], source, msg[1]);
				
			} catch (ClassNotFoundException e) {
				Log.e(TAG, "ClassNotFoundException for operator "
						+ operatorName + " in doInBackground (sendtask)");
				send_error = "Weird, class not found. Class was " + classname
						+ ". Corrupt installation/preferences?";
				return 1;

			} catch (IllegalAccessException e) {
				Log.e(TAG, "IllegalaccessException for operator "
						+ operatorName + " in doInBackground (sendtask)");
				send_error = "Weird, illegal access. Corrupt installation/preferences?";
				return 1;
			} catch (InstantiationException e) {
				Log.e(TAG, "InstantiationException for operator "
						+ operatorName + " in doInBackground (sendtask)");
				send_error = "Weird, instantiation error. Corrupt installation/preferences?";
				return 1;
			}

			return 0;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (result > 0) {
				new AlertDialog.Builder(TruesenderActivity.this)
						.setMessage(
								getString(R.string.send_failed) + send_error)
						.setTitle(getString(R.string.app_name))
						.setCancelable(true)
						.setNeutralButton("Ok",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								})

						.show();

			}

			dialog.dismiss();
		}
	}

	public void onSend(View v) {

		if (!settingsOk()) {
			startActivity(new Intent(TruesenderActivity.this,
					TruesenderSettingsActivity.class));
			return;
		}

		EditText message = (EditText) findViewById(R.id.message);
		EditText destination = (EditText) findViewById(R.id.destination);

		dialog = ProgressDialog.show(this, "", "Sending. Please wait...", true);

		new SendTask().execute(message.getText().toString(), 
				destination.getText().toString());

	}

	public void onSettings(View v) {
		startActivity(new Intent(TruesenderActivity.this,
				TruesenderSettingsActivity.class));
	}

	private boolean settingsOk() {
		Log.i(TAG, "Settings Ok?");
		SharedPreferences settings = getSharedPreferences(
				getString(R.string.app_name), MODE_PRIVATE);

		// If operator is set, all is well
		return (!settings.getString("operator", "").equals(""));
	}

	private void checkFirstRun() {

		Log.i(TAG, "Checking for first run.");
		if (settingsOk())
			return;

		Log.i(TAG, "First run, saying hi and sending to settings.");

		new AlertDialog.Builder(this)
				.setMessage(getString(R.string.long_welcome))
				.setTitle(getString(R.string.welcome)).setCancelable(true)
				.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						startActivity(new Intent(TruesenderActivity.this,
								TruesenderSettingsActivity.class));
					}
				}).show();

	}
}