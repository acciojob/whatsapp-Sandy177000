package com.driver;

import java.util.Date;

public class Message {
    private int id;
    private String content;
    private Date timestamp;

    Message(int id, String content){
        this.id = id;
        this.content = content;
    }

}
