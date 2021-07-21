package com.example.assignment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment.api.NoInternet;
import com.google.android.material.snackbar.Snackbar;

public class Utility {



    public static boolean isequal(String value1, String value2) {
        return TextUtils.equals(value1, value2);
    }



    public static void no_internet(Activity activity) {
        Intent intent = new Intent(activity, NoInternet.class);
        activity.startActivityForResult(intent, 2);
    }

    public static boolean hasConnection(Context ct) {
        ConnectivityManager cm = (ConnectivityManager) ct.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        @SuppressLint("MissingPermission") NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

//    public static void no_internet(Activity activity) {
//        Intent intent = new Intent(activity, NoInternet.class);
//        activity.startActivityForResult(intent, 2);
//    }



    public static void showToast(LinearLayout context, String message){
        Snackbar snackbar = Snackbar
                .make(context, message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
    public static void showSuccesToast(Context context, String message){
        @SuppressLint("WrongConstant") Toast toast = Toast.makeText(context, message, 2000);
        View view = toast.getView();

        //To change the Background of Toast
        view.setBackgroundColor(context.getResources().getColor(R.color.green));
        TextView text = (TextView) view.findViewById(android.R.id.message);

        //Shadow of the Of the Text Color
        text.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
        text.setTextColor(Color.WHITE);
        toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
        text.setPadding(10,10,10,10);
        text.setTextSize(Integer.valueOf(12));
        toast.show();
    }

    public static void showErrorToast(Context context, String message){
        @SuppressLint("WrongConstant") Toast toast = Toast.makeText(context, message, 2000);
        View view = toast.getView();

        //To change the Background of Toast
        view.setBackgroundColor(context.getResources().getColor(R.color.green));
        TextView text = (TextView) view.findViewById(android.R.id.message);

        //Shadow of the Of the Text Color
        text.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
        text.setTextColor(Color.WHITE);
        toast.setGravity(Gravity.CENTER| Gravity.CENTER, 0, 0);
        text.setPadding(10,10,10,10);
        text.setTextSize(Integer.valueOf(12));
        toast.show();
    }



    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


}
