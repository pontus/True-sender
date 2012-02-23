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
	public boolean send(String message, String source, String destination)
			throws Exception {

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
			String responseText = new Scanner(response.getEntity().getContent())
					.next();

			Log.i(TAG, "Response from True senses: " + responseText);

			String respCode = responseText.substring(0, 2);

			if (respCode.equals("01"))
				return true;
			else if (respCode.equals("90"))
				throw new Exception("Unknown account " + username);
			else if (respCode.equals("91"))
				throw new Exception("Incorrect password " + password);
			else if (respCode.equals("93"))
				throw new Exception(
						"You need to refill your account (add credits)");
			else if (respCode.equals("94"))
				throw new Exception(
						"Untrusted host error, you need to allow this device to send (easiest through "
								+ " Configuration->Configure HTTP-authentication and select 'Grant all hostnames or IP-Addressess.'");
			else if (respCode.equals("95"))
				throw new Exception("Destination number " + destination
						+ " is invalid");
			else if (respCode.equals("97"))
				throw new Exception("Recipient " + destination
						+ " is blacklisted");
			else
				throw new Exception("Unexpected error from True Senses, please report this to truesender@soua.net. Please include the following text: "
						+ responseText);

		} catch (ClientProtocolException e) {

			Log.e(TAG, "ClientProtocolException when talking to True senses");
			throw new Exception(
					"ClientProtocolException when talking to True senses");

		} catch (IOException e) {
			Log.e(TAG, "IOException when talking to True senses");
			throw new Exception(
					"There was a problem when communicating with True Senses, please ensure you have internet connectivity.");
		}

		// TODO Auto-generated method stub

	}

}
