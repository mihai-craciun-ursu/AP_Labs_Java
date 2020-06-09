package sample;


import java.io.Serializable;


public class Card implements Serializable{

    private static final long serialVersionUID = 7526472295622776149L;

    private String sign;
    private String number;
    private String url;

    public Card(String number, String sign, String url) {
        this.sign = sign;
        this.number = number;
        this.url = url;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
