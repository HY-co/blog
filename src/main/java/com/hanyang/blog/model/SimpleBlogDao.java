package com.hanyang.blog.model;

import com.hanyang.blog.dao.BlogDao;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlogDao implements BlogDao {
    private List<BlogEntry> blogEntries;

    public SimpleBlogDao() {
        blogEntries = new ArrayList<>();
    }

    public boolean addEntry(BlogEntry blogEntry) {
        return blogEntries.add(blogEntry);
    };

    public List<BlogEntry> findAllEntries() {
        return new ArrayList<>(blogEntries);
    }

    public BlogEntry findEntryBySlug(String slug) {
        return blogEntries.stream()
                .filter(blog -> blog.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(() -> new NotFoundException());
    }
}
