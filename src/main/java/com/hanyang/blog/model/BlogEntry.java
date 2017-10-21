package com.hanyang.blog.model;

import com.github.slugify.Slugify;
import java.util.*;

public class BlogEntry {
    private List<Comment> comments;
    private String title;
    private String content;
    private Date date;
    private String slug;

    public BlogEntry(String title, String content) {
        comments = new ArrayList<>();
        this.title = title;
        this.content = content;
        date = new Date();
        slugify(title, date);
    }

    public boolean addComment(Comment comment) {
        // Store these comments!
        return comments.add(comment);
    }

    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    public String getSlug() {
        return slug;
    }

    public String getDate() {
        return date.toString();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void slugify(String title, Date date) {
        Slugify slugify = new Slugify();
        slug = slugify.slugify(title + date.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogEntry blogEntry = (BlogEntry) o;

        if (title != null ? !title.equals(blogEntry.title) : blogEntry.title != null) return false;
        return date != null ? date.equals(blogEntry.date) : blogEntry.date == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
