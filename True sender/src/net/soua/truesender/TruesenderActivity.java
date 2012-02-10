package net.soua.truesender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.AlertDialog;
import android.app.ProgressDialog;

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

	class SendTask extends AsyncTask<String, Void, Integer> {
		@Override
		protected Integer doInBackground(String... msg) {

			SharedPreferences settings = getSharedPreferences(
					getString(R.string.app_name), MODE_PRIVATE);

			String operatorName = settings.getString("operator", "");
			String classname = "net.soua.truesender."
					+ operatorName.replaceAll("\\s", "") + "Operator";

			try {

				OperatorInterface operator = (OperatorInterface) Class.forName(
						classname).newInstance();
			} catch (ClassNotFoundException e) {
				send_error = "Weird, class not found. Class was "+classname+". Corrupt installation/preferences?";
				return 1;

			} catch (IllegalAccessException e) {
				send_error = "Weird, illegal access. Corrupt installation/preferences?";
				return 1;
			} catch (InstantiationException e) {
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

		SharedPreferences settings = getSharedPreferences(
				getString(R.string.app_name), MODE_PRIVATE);

		EditText message = (EditText) findViewById(R.id.message);

		dialog = ProgressDialog.show(this, "", "Sending. Please wait...", true);

		new SendTask().execute(message.getText().toString());

	}

	public void onSettings(View v) {
		startActivity(new Intent(TruesenderActivity.this,
				TruesenderSettingsActivity.class));
	}

	private boolean settingsOk() {
		SharedPreferences settings = getSharedPreferences(
				getString(R.string.app_name), MODE_PRIVATE);

		// If operator is set, all is well
		return (!settings.getString("operator", "").equals(""));
	}

	private void checkFirstRun() {

		if (settingsOk())
			return;

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