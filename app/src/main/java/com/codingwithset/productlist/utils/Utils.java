package com.codingwithset.productlist.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import com.codingwithset.productlist.R;
import com.codingwithset.productlist.model.Product;

public class Utils {
    public static final String FIRST_TIME_LAUNCH = "first_launch";
    private static final String TAG = "Utils";

    public static void openWhatsapp(Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.seller_whatsap_link)));
        context.startActivity(browserIntent);
    }

    public static void callSeller(Context context) {
        String phone = context.getString(R.string.seller_number);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        context.startActivity(intent);

    }

    public static void smsSeller(Context context, Product product) {
        Uri sms_uri = Uri.parse(context.getString(R.string.sms_seller));
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body", "Hi, am interested in : \n" + product.getName());
        context.startActivity(sms_intent);
    }

    public static boolean isFirstLaunch(Context context) {
        boolean flag = false;
        Log.d(TAG, "isFirstLogin: checking if this is the first login.");

        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isFirstLogin = preferences.getBoolean(FIRST_TIME_LAUNCH, true);

        if (isFirstLogin) {
            Log.d(TAG, "isFirstLogin: launching alert dialog.");

            // launch the info dialog for first-time-users
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setMessage(context.getString(R.string.first_time_user_message));
            alertDialogBuilder.setPositiveButton("OK", (dialogInterface, i) -> {
                Log.d(TAG, "onClick: closing dialog");


                // now that the user has logged in, save it to shared preferences so the dialog won't
                // pop up again
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(FIRST_TIME_LAUNCH, false);
                editor.commit();
                dialogInterface.dismiss();
            });
            alertDialogBuilder.setTitle(" ");
            alertDialogBuilder.setIcon(R.drawable.ic_shopping_cart_black_24dp);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            flag = true;
        }
        return flag;
    }


}
