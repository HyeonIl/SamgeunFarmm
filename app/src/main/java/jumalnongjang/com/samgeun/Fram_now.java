package jumalnongjang.com.samgeun;

import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;


public class Fram_now extends ActionBarActivity {

    //웹뷰 2개를 사용하여 각각의 웹뷰에서 이미지를 출력한다.
    //현재 프로젝트 진행중의 카메라는 2대이고 카메라 갯수가 늘어남에따라 웹뷰의 숫자도 늘어나야한다
    //아무래도 주소가 jpg가 아니라 cgi 를 통한 뭔가가 있는데 그것때문에 testCam이 있어야 한다. 웹뷰의 갯수만큼 testCam뷰의 숫자도 늘어나야함

    WebView cWeb1,cWeb2,testCam,testCam2;
    String url;
    Calendar myCal;
    int cam_Year,cam_Month,cam_Day,cam_Hour;

    //오늘 날짜를 보여주기 위한 텍스트 뷰
    TextView todayInfo;

    //카메라의 ID와 PW 및 IP와 Port선언
    final static String Cam1_IP = "168.115.119.104";
    final static String Cam1_HttpPort = "8081";
    final static String Cam1_ID = "admin";
    final static String Cam1_PW = "gusdlfdl3.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fram_now);
        setTitle("실시간 농장사진");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        url = "http://" + Cam1_IP + ":" + Cam1_HttpPort + "/stw-cgi/video.cgi?msubmenu=snapshot&action=view&Channel=0";

        //캘린더 클래스의 객체를 받아와서 사용가능하게 함, 또한 현재 휴대폰의 날짜 및 시간 정보를 받아오게 한다.
        TimeZone seoul = TimeZone.getTimeZone("Asia/Seoul");
        myCal = Calendar.getInstance(seoul);

        cam_Year = myCal.get(Calendar.YEAR);
        cam_Month = (myCal.get(Calendar.MONTH) + 1);
        cam_Day = myCal.get(Calendar.DAY_OF_MONTH);
        cam_Hour = myCal.get(Calendar.HOUR_OF_DAY);

        //xml 파일과 자바 소스의 연동
        cWeb1 = (WebView)findViewById(R.id.camera1);
        cWeb2 = (WebView)findViewById(R.id.camera2);
        testCam = (WebView)findViewById(R.id.testCam);
        testCam2 = (WebView)findViewById(R.id.testCam2);
        todayInfo = (TextView)findViewById(R.id.todayText);



        openWebView();

        todayInfo.setText("이 영상은 " + cam_Year + "년 " + cam_Month + "월 " + cam_Day + "일 " + cam_Hour + "시"
                + "에 촬영된 영상 입니다.");
    }

    // 버튼이 눌렷을 때 하는 행동
    public void mOnClick(View v){

        String url1 = url;

        Log.d("주소", url1);

        switch (v.getId()){
            case R.id.btnReload:
                cWeb1.loadData(createHtmlBody(url), "text/html", null);
                cWeb2.loadData(createHtmlBody(url), "text/html", null);
                break;
        }
    }

    public void openWebView(){

        cWeb1 = webViewSet(cWeb1);
        cWeb2 = webViewSet(cWeb2);
        testCam = webViewSet(testCam);
        //testCam2 = webViewSet(testCam2);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            cWeb1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        }else{
            cWeb1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }

        testCam.loadUrl(url);
        cWeb1.loadData(createHtmlBody(url), "text/html", null);
        cWeb2.loadData(createHtmlBody(url), "text/html", null);

        Log.d("주소씨발!!", createHtmlBody(url));
    }

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
    private WebView webViewSet(WebView wv){

        wv.setWebViewClient(new MyWebClient());

        cWeb1.getSettings().setBuiltInZoomControls(true);
        cWeb2.getSettings().setBuiltInZoomControls(true);


        /*
        wv.setVerticalScrollBarEnabled(false);
        wv.setVerticalScrollbarOverlay(false);
        wv.setHorizontalScrollBarEnabled(false);
        wv.setHorizontalScrollbarOverlay(false);
        wv.setInitialScale(100);
        */

        WebSettings set = wv.getSettings();
        set.setJavaScriptEnabled(true);
        set.setSupportZoom(true);

        return wv;
    }

    //URL을 접근시 인증을 처리해주는 부분
    class MyWebClient extends WebViewClient{

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            handler.proceed(Cam1_ID,Cam1_PW);
        }
    }
}
