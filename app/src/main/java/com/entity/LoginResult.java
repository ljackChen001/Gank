package com.entity;

/**
 * Created by chenbaolin on 2017/4/16.
 */

public class LoginResult{

    /**
     * responseCode : 0
     * responseDescription : 操作成功
     * responseData :
     * {"tokenStr":"276cd4de7a38b3a406c3605583012d6d3c1e895b1b9d36f42f3ee8932003021b01bbfb5c78af1df0816d35abd149840d2f48ee5b78c2354bf8510a9121a383946f47216b262ebf469276236b4d2b8363ea63db7a7eb0ea9dc5ac2d6622aa171d754e39d9e9d137e19229d4f6e5f8edf319e4b00ea5d344dfb53ac374889167f39ad54f6ace510d36a91b72b2a14a78efea41660e1d72a4bea1644b47178762281694ca9473976675214095e8f530d293ba53b85e572c175c24e58eda29be2f8939791430a36d2a98ef83955503ca5730ae03b996a35bfc9ad6a912dc63d7df3a8d85d48153555e494b79cfa0d9ae3cf1","appUser":{"id":134,"userName":null,"userNickname":null,"userSource":"02","userLastLogin":1492323328202,"userLogin":"18161194339","userType":90,"userCreateTime":1491974745000,"userAddress":null,"userMobile":"18161194339","userImg":"null","userState":111,"userTag":null,"userFinance":0,"verificationCode":null,"rentalCount":null,"leaseCount":null,"carCount":null,"userBank":null,"deviceToken":""}}
     */

    private int responseCode;
    private String responseDescription;
    private ResponseDataEntity responseData;
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public void setResponseData(ResponseDataEntity responseData) {
        this.responseData = responseData;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public ResponseDataEntity getResponseData() {
        return responseData;
    }
    public static class ResponseDataEntity {
        /**
         * tokenStr :
         * 276cd4de7a38b3a406c3605583012d6d3c1e895b1b9d36f42f3ee8932003021b01bbfb5c78af1df0816d35abd149840d2f48ee5b78c2354bf8510a9121a383946f47216b262ebf469276236b4d2b8363ea63db7a7eb0ea9dc5ac2d6622aa171d754e39d9e9d137e19229d4f6e5f8edf319e4b00ea5d344dfb53ac374889167f39ad54f6ace510d36a91b72b2a14a78efea41660e1d72a4bea1644b47178762281694ca9473976675214095e8f530d293ba53b85e572c175c24e58eda29be2f8939791430a36d2a98ef83955503ca5730ae03b996a35bfc9ad6a912dc63d7df3a8d85d48153555e494b79cfa0d9ae3cf1
         * appUser : {"id":134,"userName":null,"userNickname":null,"userSource":"02","userLastLogin":1492323328202,
         * "userLogin":"18161194339","userType":90,"userCreateTime":1491974745000,"userAddress":null,
         * "userMobile":"18161194339","userImg":"null","userState":111,"userTag":null,"userFinance":0,
         * "verificationCode":null,"rentalCount":null,"leaseCount":null,"carCount":null,"userBank":null,"deviceToken":""}
         */

        private String tokenStr;
        private AppUserEntity appUser;

        public void setTokenStr(String tokenStr) {
            this.tokenStr = tokenStr;
        }

        public void setAppUser(AppUserEntity appUser) {
            this.appUser = appUser;
        }

        public String getTokenStr() {
            return tokenStr;
        }

        public AppUserEntity getAppUser() {
            return appUser;
        }

        public static class AppUserEntity {
            /**
             * id : 134
             * userName : null
             * userNickname : null
             * userSource : 02
             * userLastLogin : 1492323328202
             * userLogin : 18161194339
             * userType : 90
             * userCreateTime : 1491974745000
             * userAddress : null
             * userMobile : 18161194339
             * userImg : null
             * userState : 111
             * userTag : null
             * userFinance : 0.0
             * verificationCode : null
             * rentalCount : null
             * leaseCount : null
             * carCount : null
             * userBank : null
             * deviceToken :
             */

            private int id;
            private Object userName;
            private Object userNickname;
            private String userSource;
            private long userLastLogin;
            private String userLogin;
            private int userType;
            private long userCreateTime;
            private Object userAddress;
            private String userMobile;
            private String userImg;
            private int userState;
            private Object userTag;
            private double userFinance;
            private Object verificationCode;
            private Object rentalCount;
            private Object leaseCount;
            private Object carCount;
            private Object userBank;
            private String deviceToken;

            public void setId(int id) {
                this.id = id;
            }

            public void setUserName(Object userName) {
                this.userName = userName;
            }

            public void setUserNickname(Object userNickname) {
                this.userNickname = userNickname;
            }

            public void setUserSource(String userSource) {
                this.userSource = userSource;
            }

            public void setUserLastLogin(long userLastLogin) {
                this.userLastLogin = userLastLogin;
            }

            public void setUserLogin(String userLogin) {
                this.userLogin = userLogin;
            }

            public void setUserType(int userType) {
                this.userType = userType;
            }

            public void setUserCreateTime(long userCreateTime) {
                this.userCreateTime = userCreateTime;
            }

            public void setUserAddress(Object userAddress) {
                this.userAddress = userAddress;
            }

            public void setUserMobile(String userMobile) {
                this.userMobile = userMobile;
            }

            public void setUserImg(String userImg) {
                this.userImg = userImg;
            }

            public void setUserState(int userState) {
                this.userState = userState;
            }

            public void setUserTag(Object userTag) {
                this.userTag = userTag;
            }

            public void setUserFinance(double userFinance) {
                this.userFinance = userFinance;
            }

            public void setVerificationCode(Object verificationCode) {
                this.verificationCode = verificationCode;
            }

            public void setRentalCount(Object rentalCount) {
                this.rentalCount = rentalCount;
            }

            public void setLeaseCount(Object leaseCount) {
                this.leaseCount = leaseCount;
            }

            public void setCarCount(Object carCount) {
                this.carCount = carCount;
            }

            public void setUserBank(Object userBank) {
                this.userBank = userBank;
            }

            public void setDeviceToken(String deviceToken) {
                this.deviceToken = deviceToken;
            }

            public int getId() {
                return id;
            }

            public Object getUserName() {
                return userName;
            }

            public Object getUserNickname() {
                return userNickname;
            }

            public String getUserSource() {
                return userSource;
            }

            public long getUserLastLogin() {
                return userLastLogin;
            }

            public String getUserLogin() {
                return userLogin;
            }

            public int getUserType() {
                return userType;
            }

            public long getUserCreateTime() {
                return userCreateTime;
            }

            public Object getUserAddress() {
                return userAddress;
            }

            public String getUserMobile() {
                return userMobile;
            }

            public String getUserImg() {
                return userImg;
            }

            public int getUserState() {
                return userState;
            }

            public Object getUserTag() {
                return userTag;
            }

            public double getUserFinance() {
                return userFinance;
            }

            public Object getVerificationCode() {
                return verificationCode;
            }

            public Object getRentalCount() {
                return rentalCount;
            }

            public Object getLeaseCount() {
                return leaseCount;
            }

            public Object getCarCount() {
                return carCount;
            }

            public Object getUserBank() {
                return userBank;
            }

            public String getDeviceToken() {
                return deviceToken;
            }
        }
    }
}
