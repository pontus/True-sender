package net.soua.truesender;

import org.apache.http.impl.client.*;

import org.apache.http.client.methods.*;
import org.apache.http.client.*;
import org.apache.http.*;
import org.apache.http.client.entity.*;
import org.apache.http.message.*;

import java.util.Scanner;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class TruesensesOperator implements OperatorInterface {

	private static final String TAG = "True Sender";

	private String username;
	private String password;

	@Override
	public boolean passwordAuth(String username, String password) {

		this.username = username;
		this.password = password;

		return true;
	}

	@Override
	public boolean send(String message, String source, String destination) {

		String url = "https://secure.simmcomm.ch/cgi-bin/smsgateway.cgi";

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		HttpResponse response;
		

		
		try {
			
			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("CMD", "sendmessage"));
			pairs.add(new BasicNameValuePair("ACCOUNT", username));
			pairs.add(new BasicNameValuePair("PASSWORD", password));
			pairs.add(new BasicNameValuePair("NUMBER", destination));
			pairs.add(new BasicNameValuePair("ORIGIN", source));
			pairs.add(new BasicNameValuePair("TEST", "ON"));
			pairs.add(new BasicNameValuePair("MESSAGE", message));
			post.setEntity(new UrlEncodedFormEntity(pairs));

			response = client.execute(post);
			String responseText = new Scanner(response.getEntity().getContent()).next();

			Log.i(TAG, "Response from True senses: " +responseText);
			if (false) {}
			
			
		} catch (ClientProtocolException e) {

			Log.e(TAG, "ClientProtocolException when talking to True senses");
			return false;
		} catch (IOException e) {
			// send_error = "There was a problem when communicating with True Senses, please ensure you have internet connectivity.";
			Log.e(TAG,"IOException when talking to True senses");
			return false;
		}

		// TODO Auto-generated method stub
		return true;
	}

}
