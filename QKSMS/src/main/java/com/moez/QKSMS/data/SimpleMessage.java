package com.moez.QKSMS.data;


/**
 * Created by zhangqian on 2016/10/29.
 * for message NOT IN system message
 */
public class SimpleMessage {
    private String body;
    private String address;
    private long dateSend;

    public SimpleMessage(String msg, String sender, long sendTime) {
        this.body = msg;
        this.address = sender;
        this.dateSend = sendTime;
    }
    public String getBody() {
        return body;
    }
    public String getAddress() {
        return address;
    }
    public long getDateSend() {
        return dateSend;
    }
}
