package com.example.myapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaDrm;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RemoteViews;

import static com.example.myapplication.App.CHANNEL_1_ID;
import static com.example.myapplication.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        notificationManager = NotificationManagerCompat.from(this);

        editTextTitle = findViewById(R.id.editText_title);
        editTextMessage = findViewById(R.id.editText_message);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendOnChannel1(View v){
//        String title = editTextTitle.getText().toString();
//        String message = editTextMessage.getText().toString();

        RemoteViews collapsedView = new RemoteViews(getPackageName(),R.layout.notification_collapse);
        RemoteViews ExpandedView = new RemoteViews(getPackageName(),R.layout.notification_expand);

        collapsedView.setTextViewText(R.id.text_view_collapse1,"hello");

        Intent clickIntent = new Intent(this,NotificationReceiver.class);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this,0,clickIntent,0);

        ExpandedView.setOnClickPendingIntent(R.id.image_view_expand,clickPendingIntent);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(ExpandedView)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .build();

        notificationManager.notify(1,notification);
    }

    public void sendOnChannel2(View v){
        String title = editTextTitle.getText().toString();
        String message = "Thank you for your feedback";

        RemoteViews SelectView = new RemoteViews(getPackageName(),R.layout.notification_select); //this is my custom laytout

        //This Intent is for broadcasting the toast message
        Intent broadCastIntent = new Intent(this,NotificationReceiver.class);
        broadCastIntent.putExtra("toastMessage",message);
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,0,broadCastIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        //This intent opens the gatherFeedback activity
        Intent activityIntent = new Intent(this,gatherFeedback.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,activityIntent,0);

        SelectView.setOnClickPendingIntent(R.id.yes_button,actionIntent);
        SelectView.setOnClickPendingIntent(R.id.no_button,contentIntent);

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setCustomContentView(SelectView)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .build();

        notificationManager.notify(2,notification);
    }


}

