package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controllers.AdminController;
import app.controllers.HomeController;
import app.entities.Newsletter;
import app.persistence.ConnectionPool;
import app.exceptions.DatabaseException;
import app.persistence.NewsletterMapper;
import app.persistence.SubscriberMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "cphbusinessnewsletter";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    private static final HomeController homeController= new HomeController(connectionPool);
    private static final AdminController adminController= new AdminController(connectionPool);
    public static void main(String[] args) {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing

        app.get("/", ctx -> homeController.home(ctx));
        // Login pages
        app.get("/login", ctx -> homeController.viewLoginPage(ctx));
        app.post("/", ctx -> homeController.login(ctx));
        // Subscribe pages
        app.get("/subscribe", ctx -> homeController.viewSubscribePage(ctx));
        app.post("/subscribe", ctx -> homeController.subscribe(ctx));
        app.post("/unsubscribe", ctx -> homeController.unsubscribe(ctx));
        // Add pages
        app.get("/admin/newsletters/add", ctx -> adminController.viewAddPage(ctx));
        app.post("/admin/newsletters/add", ctx -> adminController.addNewsletter(ctx));
    }


}


