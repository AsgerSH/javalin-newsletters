package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.persistence.ConnectionPool;
import app.persistence.DatabaseException;
import app.persistence.SubscriberMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "cphbusinessnewsletter";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args)
    {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler ->  handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing

        app.get("/", ctx ->  ctx.render("index.html"));
        app.get("/login", ctx -> viewLoginPage(ctx));
        app.post("/", ctx -> login(ctx));
        app.post("/subscribe", ctx -> viewSubscribePage(ctx));
        app.post("/", ctx -> subscribe(ctx));
    }

    private static void viewLoginPage(Context ctx){
        String message =  "Hej med dig - velkommen til loginsiden";
        ctx.attribute("message", message);
        ctx.render("login.html");
    }

    private static void login(Context ctx){
        String userName = ctx.formParam("username");
        String password = ctx.formParam("password");
        if (!password.equals("1234")) {
            ctx.redirect("login.html");
        }
        ctx.attribute("username", userName);
        ctx.render("index.html");
    }


    private static void viewSubscribePage(Context ctx){
        String message =  "Hej med dig - her kan du tilmelde dig nyhedsbrevet";
        ctx.attribute("message", message);
        ctx.render("subscribe.html");
    }

    private static void subscribe(Context ctx) throws DatabaseException {
        String email = ctx.formParam("email");

        if (email == null) {
            ctx.redirect("index.html");
        } else {
            SubscriberMapper mapper = new SubscriberMapper();
            mapper.insertSubscriber(connectionPool, email);
        }

        ctx.attribute("subscribe", true);
        ctx.render("success.html");
    }

}