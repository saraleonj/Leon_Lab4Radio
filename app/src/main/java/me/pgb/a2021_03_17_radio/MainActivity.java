package me.pgb.a2021_03_17_radio;

import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;

import me.pgb.a2021_03_17_radio.models.RadioStationArray;
import me.pgb.a2021_03_17_radio.models.RadioStation;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MAIN__";
    private MediaPlayer mediaPlayer;
    private static String url = "http://stream.whus.org:8000/whusfm";
    private Button internetRadioButton;
    private boolean radioOn;
    private boolean radioWasOnBefore;
    private Spinner menuSpinner;
    private ImageView imageView;
    private static String[] radioArray;
    private static String[] streamLinks;
    private static String[] Images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //spinner that will hold array contents aka radio stations
        menuSpinner = findViewById(R.id.menu_spinner);
        //imageView to display radio images
        imageView = findViewById(R.id.image);
        //getting the radio station array
        radioArray = RadioStationArray.getArrayOfRadioNames();
        //getting radio stream links array
        streamLinks = RadioStationArray.getArrayOfRadioLinks();
        //getting radio images
        Images = RadioStationArray.getArrayOfRadioImages();
        //adapter for spinner, linking radio stations array
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, radioArray);
        //drop down menu
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuSpinner.setAdapter(adapter);



        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String chosenStation = adapterView.getItemAtPosition(i).toString();
                if(chosenStation.equals("WHUS")){
                    url = streamLinks[i];
                    Picasso.get().load(Images[i]).into(imageView);


                }
                if(chosenStation.equals("Radio Casablanca")){
                    url = streamLinks[i];
                    Picasso.get().load(Images[i]).into(imageView);

                }
                if(chosenStation.equals("AirlessRadio - Smooth Grooves")){
                    url = streamLinks[i];
                    Picasso.get().load(Images[i]).into(imageView);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        radioOn = false;
        radioWasOnBefore = false;

        mediaPlayer = new MediaPlayer();

        internetRadioButton = findViewById(R.id.internet_radio_button);

        internetRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioOn) { // ON so Turn OFF
                    radioOn = false;
                    internetRadioButton.setText("Turn radio ON");
                    if (mediaPlayer.isPlaying()) {
                        Log.i(TAG, "Radio is playing- turning off " );
                        radioWasOnBefore = true;
                    }
                    mediaPlayer.pause();
                } else { // OFF so Turn ON
                    radioOn = true;
                    internetRadioButton.setText("Turn radio OFF");
                    if (!mediaPlayer.isPlaying()) {
                        if (radioWasOnBefore) {
                            mediaPlayer.release();
                            mediaPlayer = new MediaPlayer();
                        }
                        radioSetup(mediaPlayer);
                        mediaPlayer.prepareAsync();
                    }
                }

            }
        });
    }

    public void radioSetup(MediaPlayer mediaPlayer) {

        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i(TAG, "onPrepared" );
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i(TAG, "onError: " + String.valueOf(what).toString());
                return false;
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i(TAG, "onCompletion" );
                mediaPlayer.reset();
            }
        });

        try {
            mediaPlayer.setDataSource(url);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = null;
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

    private void setUpMediaPlayer() {
        Handler handler = null;

        HandlerThread handlerThread = new HandlerThread("media player") {
            @Override
            public void onLooperPrepared() {
                Log.i(TAG, "onLooperPrepared");

            }
        };

    }
}