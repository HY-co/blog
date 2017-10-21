package com.hanyang.blog;

import com.hanyang.blog.dao.BlogDao;
import com.hanyang.blog.model.BlogEntry;
import com.hanyang.blog.model.Comment;
import com.hanyang.blog.model.SimpleBlogDao;
import com.sun.org.apache.xpath.internal.operations.Mod;
import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        staticFileLocation("/");
        BlogDao dao = new SimpleBlogDao();
        BlogEntry be1 = new BlogEntry("The best day I’ve ever had","");
        BlogEntry be2 = new BlogEntry("The absolute worst day I’ve ever had","");
        BlogEntry be3 = new BlogEntry("That time at the mall","");
        BlogEntry be4 = new BlogEntry("Dude, where’s my car?","");
        dao.addEntry(be1);
        dao.addEntry(be2);
        dao.addEntry(be3);
        dao.addEntry(be4);

        before((req, res)-> {
            if (req.cookie("username") != null) {
                req.attribute("username", req.cookie("username"));
            }
        });

        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });

        get("/index.html", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("entries", dao.findAllEntries());
            return new ModelAndView(model, "index.hbs");
        },new HandlebarsTemplateEngine());

        get("/index.html/new", (req, res) -> {
            return new ModelAndView(null, "new.hbs");
        }, new HandlebarsTemplateEngine());

        get("/index.html/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("entry", dao.findEntryBySlug(req.params("slug")));
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());

        get("/index.html/:slug/edit", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("edit", dao.findEntryBySlug(req.params("slug")));
            return new ModelAndView(model, "edit.hbs");
        }, new HandlebarsTemplateEngine());

        post("/index.html", (req, res) -> {
            String title = req.queryParams("title");
            String entry  = req.queryParams("entry");
            BlogEntry be = new BlogEntry(title, entry);
            dao.addEntry(be);
            res.redirect("/index.html/" + be.getSlug());
            return null;
        });

        post("/index.html/:slug", (req, res) -> {
            String title = req.queryParams("title");
            String entry  = req.queryParams("entry");
            BlogEntry be = dao.findEntryBySlug(req.params("slug"));
            be.setTitle(title);
            be.setContent(entry);
            be.slugify(title, new Date());
            res.redirect("/index.html/" + be.getSlug());
            return null;
        });

        post("/index.html/:slug/comment", (req, res) -> {
            String name = req.queryParams("name");
            String comment = req.queryParams("comment");
            Comment ct = new Comment(name, comment);
            BlogEntry be = dao.findEntryBySlug(req.params("slug"));
            be.addComment(ct);
            res.redirect("/index.html/" + req.params("slug"));
            return null;
        });
    }
}
