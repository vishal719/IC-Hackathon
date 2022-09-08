package com.example.ic_hackathon.Model;

public class EmergencyModel {

    private String name;
    private String number;
    private String pic;
    private String msg1;
    private String msg2;
    private String msg3;
    private String msg4;
    private String location;
    private String Image_then;

    public EmergencyModel() {
    }

    public EmergencyModel(String name, String number, String pic, String msg1, String msg2, String msg3, String msg4, String location, String image_then) {
        this.name = name;
        this.number = number;
        this.pic = pic;
        this.msg1 = msg1;
        this.msg2 = msg2;
        this.msg3 = msg3;
        this.msg4 = msg4;
        this.location = location;
        Image_then = image_then;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }

    public String getMsg2() {
        return msg2;
    }

    public void setMsg2(String msg2) {
        this.msg2 = msg2;
    }

    public String getMsg3() {
        return msg3;
    }

    public void setMsg3(String msg3) {
        this.msg3 = msg3;
    }

    public String getMsg4() {
        return msg4;
    }

    public void setMsg4(String msg4) {
        this.msg4 = msg4;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage_then() {
        return Image_then;
    }

    public void setImage_then(String image_then) {
        Image_then = image_then;
    }
}
