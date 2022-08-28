package com.example.ic_hackathon.Model;

public class ContactsModels {

    private int pic;
    private String name;
    private String number;
    private boolean ischecked;

    public ContactsModels() {
    }

    public ContactsModels(int pic, String name, String number) {
        this.pic = pic;
        this.name = name;
        this.number = number;
    }

    public ContactsModels(int pic, String name, String number, boolean ischecked) {
        this.pic = pic;
        this.name = name;
        this.number = number;
        this.ischecked = ischecked;
    }

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
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
}
