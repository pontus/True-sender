package net.soua.truesender;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;

public class MyAlert {

	protected MyAlert(Context ctx, String message, DialogInterface.OnClickListener c) {

		if (c == null) {
			c = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			};

		}

		new AlertDialog.Builder(ctx).setMessage(message)
				.setTitle(ctx.getString(R.string.app_name)).setCancelable(true)
				.setNeutralButton("Ok", c).show();

	}
}
