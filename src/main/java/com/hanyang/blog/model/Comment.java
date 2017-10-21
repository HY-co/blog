package com.hanyang.blog.model;

import java.util.Date;

public class Comment {
    private String name;
    private String comment;
    private Date date;

    public Comment(String name, String comment) {
        this.name = name;
        this.comment = comment;
        date = new Date();
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date.toString();
    }
}
