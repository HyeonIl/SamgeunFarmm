package jumalnongjang.com.samgeun.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

import jumalnongjang.com.samgeun.Info.IpCam;
import jumalnongjang.com.samgeun.R;

public class Farm_now extends ActionBarActivity {

    //웹뷰 2개를 사용하여 각각의 웹뷰에서 이미지를 출력한다.
    //현재 프로젝트 진행중의 카메라는 2대이고 카메라 갯수가 늘어남에따라 웹뷰의 숫자도 늘어나야한다
    //아무래도 주소가 jpg가 아니라 cgi 를 통한 뭔가가 있는데 그것때문에 testCam이 있어야 한다. 웹뷰의 갯수만큼 testCam뷰의 숫자도 늘어나야함

    IpCam cam1,cam2,cam3;
    WebView cWeb1,cWeb2,cWeb3;
    WebView cWeb1_detail,cWeb2_detail,cWeb3_detail;
    LinearLayout cam1_View,cam2_View,cam3_View;
    String url,url2,url3;
    Calendar myCal;
    int cam_Year,cam_Month,cam_Day,cam_Hour;

    //오늘 날짜를 보여주기 위한 텍스트 뷰
    TextView todayInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_now);
        setTitle("실시간 농장사진");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //캘린더 클래스의 객체를 받아와서 사용가능하게 함, 또한 현재 휴대폰의 날짜 및 시간 정보를 받아오게 한다.
        //TimeZone은 휴대폰 시간과 실시간이 제대로 안맞을경우 서울표준시간을 받아오기 위하여 선언한다.

        TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
        myCal = Calendar.getInstance(seoul);
        cam_Year = myCal.get(Calendar.YEAR);
        cam_Month = (myCal.get(Calendar.MONTH) + 1);
        cam_Day = myCal.get(Calendar.DAY_OF_MONTH);
        cam_Hour = myCal.get(Calendar.HOUR_OF_DAY);

        //카메라의 ID와 PW 및 IP와 Port선언
        //카메라 클래스를 선언하여 정보를 대입
        //      res/values/cam.xml 에 있는 value값들을 가져와서 생성자로 만든다
        String[] cam1_Value = getResources().getStringArray(R.array.cam1);
        String[] cam2_Value = getResources().getStringArray(R.array.cam2);
        String[] cam3_Value = getResources().getStringArray(R.array.cam3);

        cam1 = new IpCam(cam1_Value,0);
        Log.d("카메라", "" + cam1.getCamNumber());
        cam2 = new IpCam(cam2_Value,1);
        Log.d("카메라", "" + cam2.getCamNumber());
        cam3 = new IpCam(cam3_Value,2);

        url = cam1.getJpegSnapShotURL();
        url2 = cam2.getJpegSnapShotURL();
        url3 = cam3.getJpegSnapShotURL();

        //xml 파일과 자바 소스의 연동
        cWeb1 = (WebView)findViewById(R.id.camera1);
        cWeb2 = (WebView)findViewById(R.id.camera2);
        cWeb3 = (WebView)findViewById(R.id.camera3);
        cWeb1_detail = (WebView)findViewById(R.id.cam1_detail);
        cWeb2_detail = (WebView)findViewById(R.id.cam2_detail);
        cWeb3_detail = (WebView)findViewById(R.id.cam3_detail);
        todayInfo = (TextView)findViewById(R.id.todayText);
        cam1_View = (LinearLayout)findViewById(R.id.cam1_View);
        cam2_View = (LinearLayout)findViewById(R.id.cam2_View);
        cam3_View = (LinearLayout)findViewById(R.id.cam3_View);

        //웹뷰를 Load함
        openWebView();

        //사진의 날짜 정보가 나오는 텍스트뷰 세팅 메소드
        textInfoViewSet();

        //확대기능에 대한 토스트 메시지
        //Toast.makeText(Farm_now.this, "확대 기능은 Kit-Kat 이후의 단말에서만 지원됩니다.", Toast.LENGTH_LONG).show();
    }

    /**
     * 웹뷰들을 열고 해당 주소를 웹뷰에 전송하여 실행시킴
     */
    public void openWebView(){

        cWeb1 = webViewSet(cWeb1,cam1);
        cWeb2 = webViewSet(cWeb2,cam2);
        cWeb3 = webViewSet(cWeb3,cam3);
        cWeb1_detail = webViewSet(cWeb1_detail,cam1);
        cWeb2_detail = webViewSet(cWeb2_detail,cam2);
        cWeb3_detail = webViewSet(cWeb3_detail,cam3);

        //API Version 이 KitKat 이하일 때와 이상일때를 나눈다.
        /*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            cWeb1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }else{
            cWeb1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        */

        cWeb1_detail.loadUrl(url);
        cWeb2_detail.loadUrl(url2);
        cWeb3_detail.loadUrl(url3);
        cWeb1.loadData(createHtmlBody(url), "text/html", null);
        cWeb2.loadData(createHtmlBody(url2), "text/html", null);
        cWeb3.loadData(createHtmlBody(url3), "text/html", null);
    }

    //웹뷰의 이미지가 디바이스의 크기에 맞추어서 가운데 정렬과 100%의 크기에 나올 수 있도록 Http Tag를 추가하여 주소를
    //로드 하는 메소드
    public String createHtmlBody(String imageURL){
        StringBuffer sb = new StringBuffer("<HTML>");
        sb.append("<HEAD>");
        sb.append("</HEAD>");
        sb.append("<BODY style ='margin:0; padding:0; text-align:center;'>");
        sb.append("<img width='" + "100%" + "' height'100%' src=\"" + imageURL + "\">");
        sb.append("</BODY>");
        sb.append("</HTML>");
        return sb.toString();
    }

    //웹뷰의 셋팅을 담당하는 함수
    private WebView webViewSet(WebView wv, final IpCam cam){

        //웹뷰 클라이언트를 불러오면서 onReceivedHttpAuthRequest메소드를 재정의 하여 카메라의 ID와 PW를
        //이용하여 카메라에 접근할 수 있도록 한다
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                handler.proceed(cam.getCamID(), cam.getCamPW());
            }
        });

        WebSettings set = wv.getSettings();
        set.setJavaScriptEnabled(true);

        //줌기능 잠시 없앤다
        //set.setSupportZoom(true);
        //set.setBuiltInZoomControls(true);

        return wv;
    }

    //날짜 나오는 텍스트를 변경시키는 함수
    private void textInfoViewSet(){
        todayInfo.setText("이 영상은 " + cam_Year + "년 " + cam_Month + "월 " + cam_Day + "일 " + cam_Hour + "시"
                + "에 촬영된 영상 입니다.");
    }


    /**
     * 버튼들의 처리
     */

    //카메라1 버튼
    public void mOnclick(View v){

        switch (v.getId()){
            case R.id.cam1_Btn:
                cam1_View.setVisibility(View.VISIBLE);
                cam2_View.setVisibility(View.INVISIBLE);
                cam3_View.setVisibility(View.INVISIBLE);
                break;
            case R.id.cam2_Btn:
                cam1_View.setVisibility(View.INVISIBLE);
                cam2_View.setVisibility(View.VISIBLE);
                cam3_View.setVisibility(View.INVISIBLE);
                break;
            case R.id.cam3_Btn:
                cam1_View.setVisibility(View.INVISIBLE);
                cam2_View.setVisibility(View.INVISIBLE);
                cam3_View.setVisibility(View.VISIBLE);
                break;
        }
    }
}
