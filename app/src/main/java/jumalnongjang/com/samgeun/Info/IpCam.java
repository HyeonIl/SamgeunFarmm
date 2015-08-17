package jumalnongjang.com.samgeun.Info;

/**
 * Created by 현일 on 2015-08-17.
 */
public class IpCam {
    private String ipAddress;
    private String httpPort;
    private String camID;
    private String camPW;
    private int camNumber;

    //포트가 따로 지정되있을 경우의 생성자
    public IpCam(String ipAddress, String httpPort, String camID, String camPW, int camNumber) {
        this.ipAddress = ipAddress;
        this.httpPort = httpPort;
        this.camID = camID;
        this.camPW = camPW;
        this.camNumber = camNumber;
    }

    //포트가 기본포트인 80포트 경우 생성자
    public IpCam(String ipAddress, String camID, String camPW,int camNumber) {
        this(ipAddress,"80",camID,camPW,camNumber);
    }

    //Getter and Setter
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public String getHttpPort() {
        return httpPort;
    }
    public void setHttpPort(String httpPort) {
        this.httpPort = httpPort;
    }
    public String getCamID() {
        return camID;
    }
    public void setCamID(String camID) {
        this.camID = camID;
    }
    public String getCamPW() {
        return camPW;
    }
    public void setCamPW(String camPW) {
        this.camPW = camPW;
    }
    public int getCamNumber() {
        return camNumber;
    }
    public void setCamNumber(int camNumber) {
        this.camNumber = camNumber;
    }



    //URL 반환 메소드
    public String getJpegSnapShotURL(){

        String resultURL;

        //실시간 Jpg 스냅샷을 보기위한 Api 주소
        String apiUrl = "/stw-cgi/video.cgi?msubmenu=snapshot&action=view&Channel=0";

        //http 기본 포트인 80포트일 경우 포트명을 명시하지 않은 주소로 리턴 그 이외에는 포트번호를 명시한다.
        if(this.httpPort == "80")
            resultURL = "http://" + this.ipAddress + apiUrl;
        else
            resultURL = "http://" + this.ipAddress + ":" + this.httpPort + apiUrl;

        return resultURL;
    }
}
