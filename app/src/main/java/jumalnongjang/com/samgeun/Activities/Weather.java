package jumalnongjang.com.samgeun.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jumalnongjang.com.samgeun.R;

public class Weather extends ActionBarActivity {


    TextView edit_REH,edit_T1H,edit_RN1,edit_time;
    ImageView view_sky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("농장날씨");



        edit_REH = (TextView)findViewById(R.id.textView_REH);
        edit_T1H = (TextView)findViewById(R.id.textView_T1H);
        edit_RN1 = (TextView)findViewById(R.id.textView_RN1);
        edit_time = (TextView)findViewById(R.id.textView_time);

        view_sky = (ImageView)findViewById(R.id.view_sky);

        new WeatherGetdate().execute();


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

    public class WeatherGetdate extends AsyncTask<Void, Void, String>{

        JSONArray resultJSONArray;

        int dd_t, mm_t, yyyy_t,hours_t,minutes_t;
        NodeList cate, items;


        protected String doInBackground(Void... params){
            Log.i("ddddd", "on doInBackground");
            byte[] buff = new byte[4096 * 5];
            try{

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = factory.newDocumentBuilder();

                Calendar cal = new GregorianCalendar(Locale.KOREA);
                dd_t = cal.get(cal.DAY_OF_MONTH);
                mm_t = cal.get(cal.MONTH) + 1;
                yyyy_t = cal.get(cal.YEAR);
                hours_t = cal.get(cal.HOUR_OF_DAY);
                minutes_t = cal.get(cal.MINUTE);




            String dd,mm,yyyy,hours;

            String uri = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService/ForecastGrib";
            String apiKey = "HK7J2gFFcJGfJowki5%2BSF7jg%2Fb2076YEzXHdweL2T9dBJBjDTv1WmKzCbe7WryY%2Fcd2KasCjGuQ7s81Jnt5RpA%3D%3D";
            String nx = "98";
            String ny = "78";

            //API 사용하는 파라미터 시간 계산
            if(minutes_t < 30){
                // 30분보다 작으면 한시간 전 값
                hours_t = hours_t - 1;
                if(hours_t < 0){
                    // 자정 이전은 전날로 계산

                    dd_t = dd_t-1;
                    hours_t = 23;
                }
            }
            if(hours_t<10) {
                hours ='0'+Integer.toString(hours_t);
            }else{
                hours = Integer.toString(hours_t);
            }
            if(mm_t<10) {
                mm = '0' + Integer.toString(mm_t);
            }else{
                mm = Integer.toString(mm_t);
            }
            if(dd_t<10) {
                dd='0'+Integer.toString(dd_t);
            }else{
                dd = Integer.toString(dd_t);
            }
            yyyy = Integer.toString(yyyy_t);

            //파라미터 만들기
            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            //params1.add(new BasicNameValuePair("ServiceKey",apiKey));
            params1.add(new BasicNameValuePair("nx",nx));
            params1.add(new BasicNameValuePair("ny",ny));
            params1.add(new BasicNameValuePair("base_date",yyyy+mm+dd));
            params1.add(new BasicNameValuePair("base_time", hours + "00"));

            String paramString = URLEncodedUtils.format(params1, "utf-8");
            uri += "?" + "ServiceKey=" + apiKey + "&" + paramString + "&type=json";


                Log.i("ddddd", uri);

            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
                Document doc = documentBuilder.parse(is);
                Element element = doc.getDocumentElement();
                //읽어올 태그명 정하기
                cate = element.getElementsByTagName("category");
                items = element.getElementsByTagName("obsrValue");
                //자료를 누적시킬 stringBuffer 객체
                StringBuffer sBuffer = new StringBuffer();



                    is.close();
            conn.disconnect();


            }catch (Exception e){
                if (e.getMessage() != null)
                    Log.e("ddddd", e.getMessage());
            }



            return null;
        }
        protected void onPostExecute(String getURL) {

            Log.i("ddddd", "on post execute");

            edit_time.setText(yyyy_t+"/"+mm_t+"/"+dd_t+ " "+ hours_t + ":" + minutes_t + "\n 정보제공: 기상청");
            String PTY_tmp = null;
            for(int i=0; i<items.getLength(); i++){
                Node item = cate.item(i);
                Node text = item.getFirstChild();
                String itemValue = text.getNodeValue();
                if(itemValue.equals("REH")){//습도
                    item = items.item(i);
                    text = item.getFirstChild();
                    edit_REH.setText(text.getNodeValue()+ " mm");
                    Log.i("ddddd", "습도 " + text.getNodeValue());
                }else if(itemValue.equals("T1H")){//기온
                    item = items.item(i);
                    text = item.getFirstChild();
                    Log.i("ddddd", "기온 " + text.getNodeValue());
                    edit_T1H.setText(text.getNodeValue()+" ℃");
                }else if(itemValue.equals("RN1")){//강수량
                    item = items.item(i);
                    text = item.getFirstChild();
                    edit_RN1.setText(text.getNodeValue()+" mm");
                    Log.i("ddddd", "강수량 " + text.getNodeValue());
                }else if(itemValue.equals("SKY")){//하늘상태
                    item = items.item(i);
                    text = item.getFirstChild();
                    Log.i("ddddd", "하늘상태 " + text.getNodeValue() + "                 맑음 1, 구름 2 3 , 흐림 4");
                    if(text.getNodeValue().equals("2")||text.getNodeValue().equals("3"))
                        view_sky.setImageResource(R.drawable.cloud);
                    else if(text.getNodeValue().equals("4"))
                        view_sky.setImageResource(R.drawable.cloud);
                }else if(itemValue.equals("PTY")){//강수형태

                    item = items.item(i);
                    text = item.getFirstChild();
                    Log.i("ddddd", "강수형태 " + text.getNodeValue() + "                비 1, 비눈2, 눈 3");
                    PTY_tmp = text.getNodeValue();
                }

            }//items loop end

            if(!PTY_tmp.equals("0")){//강수형태
                if(PTY_tmp.equals("1") || PTY_tmp.equals("2"))
                    view_sky.setImageResource(R.drawable.rain);
                else if(PTY_tmp.equals("3"))
                    view_sky.setImageResource(R.drawable.snowflake);
            }



        }


    }



}
