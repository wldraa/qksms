package com.moez.QKSMS.data;


/**
 * Created by zhangqian on 2016/10/29.
 * for message NOT IN system message
 */
public class SimpleMessage {
    private int id;
    private String body;
    private String address;
    private long dateSend;

    public SimpleMessage() {

    }

    public SimpleMessage(String msg, String sender, long sendTime) {
        this.body = msg;
        this.address = sender;
        this.dateSend = sendTime;
    }

    public long getDateSend() {
        return dateSend;
    }

    public void setDateSend(long dateSend) {
        this.dateSend = dateSend;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAddress() {
        return address.replace(" ", "");
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
