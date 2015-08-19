package jumalnongjang.com.samgeun.Community;

import android.content.Context;

import java.io.File;

/**
 * Created by 현일 on 2015-08-19.
 */
public class WebViewImageUploadHelper {

    public final static int INTENT_CALL_GALLERY = 3001; // 갤러리 requestCode
    public final static int INTENT_CALL_CAMERA = 4001; //카메라 requestCode

    public static final int KITKAT_FILECHOOSER = 10002;
    public static final int KITKAT_CAMERA = 10003;

    private static WebViewImageUploadHelper mHelper;
    private Context mContext;

    private File mContents; //파일객체

    private WebViewImageUploadHelper(Context context){
        mContext = context;
        //mDialog = new CommonDialogs(context);
    }
}
