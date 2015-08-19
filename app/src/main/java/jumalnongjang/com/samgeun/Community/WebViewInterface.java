package jumalnongjang.com.samgeun.Community;

import android.app.Activity;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.io.File;

/**
 * Created by 현일 on 2015-08-19.
 * 참고 : http://fimtrus.tistory.com/77
 */
public class WebViewInterface {

    private WebView mAppView;
    private Activity mContext;

    /**
     * 생성자
     * @param activity : context
     * @param view : 적용될 웹뷰
     */
    public WebViewInterface(Activity activity,WebView view){
        mAppView = view;
        mContext = activity;
    }

    /**
     *
     * @param key : 어떤 input box를 클릭했는지 구분을 위한 Unique Key
     * @param thumbnail_ID : 선택된 이미지의 썸네일을 보여줄 division id.
     *
     */
    public Uri open(String key, String thumbnail_ID){
        //WebViewImageUploadHelper.getInstance(mContext, mAppView).
        return null;
    }

    /**
     * 파일 전송
     */
    @JavascriptInterface
    public File send(String key){
        //WebViewImageUploadHelper
        return null;
    }
}
