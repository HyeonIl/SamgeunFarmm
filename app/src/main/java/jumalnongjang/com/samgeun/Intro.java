package jumalnongjang.com.samgeun;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Intro extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        setTitle("삼근주말농장?");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView textView_intro_main = (TextView) findViewById(R.id.textView_intro_main);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/NanumGothicBold.ttf");
        textView_intro_main.setTypeface(face);

        Button Btn_map = (Button)findViewById(R.id.btn_map);
        Btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://m.map.naver.com/search2/search.nhn?query=%EB%B6%80%EC%82%B0%EA%B4%91%EC%97%AD%EC%8B%9C%20%EA%B8%88%EC%A0%95%EA%B5%AC%20%EB%91%90%EA%B5%AC%EB%8F%99%20629-10&siteLocation=&queryRank=&type=#/map"));
                startActivity(intent);
            }
        });


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
}
