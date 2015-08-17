package jumalnongjang.com.samgeun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button Btn_Intro = (Button) findViewById(R.id.Btn_intro);
        Button btn_pic = (Button) findViewById(R.id.Btn_Fram_now);
        Button Btn_Weather = (Button) findViewById(R.id.Btn_weather);
        Button Btn_Talk = (Button) findViewById(R.id.Btn_talk);



        Btn_Intro.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setBackgroundResource(R.drawable.btn_intro);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundResource(R.drawable.btn_introp);


                }
                return false;
            }
        });
        btn_pic.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setBackgroundResource(R.drawable.btn_pic);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundResource(R.drawable.btn_picp);


                }
                return false;
            }
        });
        Btn_Weather.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setBackgroundResource(R.drawable.btn_wea);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundResource(R.drawable.btn_weap);


                }
                return false;
            }
        });
        Btn_Talk.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setBackgroundResource(R.drawable.btn_talk);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundResource(R.drawable.btn_talkp);


                }
                return false;
            }
        });
        Btn_Intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentIntro = new Intent(MainActivity.this, Intro.class);
                startActivity(IntentIntro);


            }
        });
        btn_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentFram = new Intent(MainActivity.this, Fram_now.class);
                startActivity(IntentFram);


            }
        });
        Btn_Weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentWeather = new Intent(MainActivity.this, Weather.class);
                startActivity(IntentWeather);

            }
        });
        Btn_Talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentTalk = new Intent(MainActivity.this, Community.class);
                startActivity(IntentTalk);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the ㄴmenu; this adds items to the action bar if it is present.
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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                new AlertDialog.Builder(this)
                        .setTitle("종료")
                        .setMessage("종료 하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                            }
                        })
                        .setNegativeButton("아니오", null).show();
                return false;
            default:
                return false;
        }
    }
}
