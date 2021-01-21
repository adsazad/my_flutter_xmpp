package com.elex.flutter_xmpp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("com.sarabit.startxmpp")) {
            Log.d("MyReveiver", "Arashdeep Xmpp Start");
            Bundle extras = intent.getExtras();
            String userName = extras.getString("chatId");
            String password = extras.getString("password");
            String domain = extras.getString("domain");
            String port = extras.getString("port");
            Intent in1 = new Intent(context, FlutterXmppConnectionService.class);
            in1.putExtra("hostName", domain);
            in1.putExtra("domin", domain);
            in1.putExtra("port", port);
            context.startService(in1);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else if (action.equals(FlutterXmppConnectionService.CONNECT_STATUS)) {
            Log.d("MyReveiver", "Arashdeep Connected Here");
            Bundle extras = intent.getExtras();
            String code = extras.getString("code");
            Log.d("MyReveiver", code);

            if (code.equals("200")) {
                Intent in2 = new Intent();
                Log.d("PACKAGE", context.getPackageName());
                in2.setAction("com.sarabit.xmpp.connected");
                in2.setPackage(context.getPackageName());
                in2.putExtra("code", extras.getString("code"));
                context.sendBroadcast(in2);
            }
        } else if (action.equals("com.sarabit.xmpp.login")) {
            Bundle extras = intent.getExtras();
            String userName = extras.getString("chatId");
            String password = extras.getString("password");
            Intent int2 = new Intent(FlutterXmppConnectionService.LOGIN);
            int2.putExtra(FlutterXmppConnectionService.USERNAME, userName);
            int2.putExtra(FlutterXmppConnectionService.PASSWORD, password);
            context.sendBroadcast(int2);
        } else if (action.equals("com.elex.xmpp.flutter_xmpp.receivemessage")) {
            Log.d("Message Received", "Message Received");
            Bundle bundle = intent.getExtras();
            String body = bundle.getString(FlutterXmppConnectionService.BUNDLE_MESSAGE_BODY);
            String to = bundle.getString(FlutterXmppConnectionService.BUNDLE_TO);
            String from = bundle.getString(FlutterXmppConnectionService.BUNDLE_FROM_JID);
            String type = bundle.getString(FlutterXmppConnectionService.BUNDLE_MESSAGE_TYPE);
            Intent messageIntent = new Intent();
            messageIntent.setAction("com.sarabit.xmpp.receivemessage");
            messageIntent.setPackage(context.getPackageName());
            messageIntent.putExtra("body", body);
            messageIntent.putExtra("to", to);
            messageIntent.putExtra("from", from);
            messageIntent.putExtra("type", type);
            context.sendBroadcast(messageIntent);
        }
//        if(intent.getAction().equals("com.elex.xmpp.flutter_xmpp.login")){
//            Log.d("MyReveiver","Bluetooth connect");
//        }
    }
}
