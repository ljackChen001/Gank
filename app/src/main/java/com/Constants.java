package com;

/**
 * Created by chenbaolin on 2017/4/5.
 */

public class Constants {
    public static final String BASE_URL = "http://www.girlboy.cn:1101/al/api/";
    //public static final String url = "http://10.143.132.203:1101/al/api/";
    //public static final String url = "http://10.143.132.133:8080/al/api/";
    //	public static final String url = "http://10.143.132.154:1101/al/api/";
    //public static final String url = "http://192.168.0.107:8080/al/api/";
    /**
     * 文件上传
     */
    //	public static final String FILE_UPURL_MD = "http://10.143.132.203:1101/al/api/uploadFile";
    //	public static final String FILE_URL = "http://10.143.132.203:1101/al/";
    //	public static final String FILE_UPURL_MD = "http://10.143.132.154:1101/al/api/uploadFile";
    //	public static final String FILE_URL = "http://10.143.132.154:1101/al/";
    //	public static final String FILE_UPURL_MD = "http://192.168.0.107:8080/al/api/uploadFile";
    //	public static final String FILE_URL = "http://192.168.0.107:8080/al/";
    public static final String FILE_UPURL_MD = "http://www.girlboy.cn:1101/al/api/uploadFile";
    public static final String FILE_URL = "http://www.girlboy.cn:1101/al/";
    public static final int WEB_RESP_CODE_SUCCESS = 0;
    public static final int TOKEN_EXPRIED = -1;

    public static final class ApiInterface {
        public static final String LOGIN_REGISTER = "loginOrRegister";//登录接口
        public static final String SEND_CODE = "sendCode";//获取验证码
        public static final String FIND_MY_CARS = "findMyCars";//我的车辆
        public static final String FIND_RENTAL_CARS = "findRentalCars";//获取验证码

    }

    //租车人订单状态 0-待付款；1-待取车；2-待还车；3-待退款；4-已退款；5-已完成；6-已取消，7-已评价；
    public static class OrderState {
        public static final int ORDER_DAIFUKUAN = 0;
        public static final int ORDER_DAIQUCHE = 1;
        public static final int ORDER_DAIHUANCHE = 2;
        public static final int ORDER_DAITUIKUAN = 3;
        public static final int ORDER_YITUIKUAN = 4;
        public static final int ORDER_YIWANCHENG = 5;
        public static final int ORDER_YIQUXIAO = 6;
        public static final int ORDER_YIPINGJIA = 7;
    }

    //车辆状态 0、 待审核 1、审核通过 2、审核不通过 3、上架  4、下架
    public static class CarOrderState {
        public static final int CarOrder_DAISEHNHE = 0;
        public static final int CarOrder_SHENHETONGGUO = 1;
        public static final int CarOrder_SEHNHEBUTONGGUO = 2;
        public static final int CarOrder_SHANGJIA = 3;
        public static final int CarOrder_XIAJIA = 4;
    }

}
