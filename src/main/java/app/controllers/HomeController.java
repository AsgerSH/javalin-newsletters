package app.controllers;

import app.entities.Newsletter;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.NewsletterMapper;
import app.persistence.SubscriberMapper;
import io.javalin.http.Context;

import java.util.List;

public class HomeController {

    private static ConnectionPool connectionPool;

    public HomeController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public static void home(Context ctx) throws DatabaseException {
        List<Newsletter> newsletters = NewsletterMapper.getAllNewsletters(connectionPool);
        ctx.attribute("newsletters", newsletters);
        ctx.attribute("message", "Vælg en af nedenstående muligheder!");
        ctx.attribute("title", "Velkommen til hovedsiden");
        ctx.render("index.html");
    }

    public static void subscribe(Context ctx) throws DatabaseException {
        String email = ctx.formParam("email");


        // Validate email
        if (email == null || email.isEmpty() || !email.contains(email)) {
            ctx.attribute("error", "Invalid email address");
            ctx.redirect("/subscribe"); // Redirect back to the subscribe page
            return;
        }

        try {
            // Insert email into the database
            SubscriberMapper mapper = new SubscriberMapper();
            mapper.insertSubscriber(connectionPool, email);

            ctx.attribute("title", "Du bliver nu informeret!");
            ctx.attribute("message", "Vi har tilmeldt din mail: '" + email + "' til nyhedsbrevet");
            ctx.render("success.html");
        } catch (DatabaseException e) {
            ctx.attribute("error", "An error occurred while subscribing. Please try again.");
            ctx.redirect("/subscribe"); // Redirect back to the subscribe page
        }
    }

    public static void unsubscribe(Context ctx) throws DatabaseException {
        String email = ctx.formParam("email");

//        if (email == null || email.isEmpty()) {
//            ctx.attribute("error", "Invalid email address");
//            ctx.redirect("/subscribe");
//            return;
//        }

        try {
            // Tries to remove email + creation from database
            SubscriberMapper mapper = new SubscriberMapper();
            mapper.deleteSubscriber(connectionPool, email);

            ctx.attribute("title", "Vi er kede af at se dig gå!");
            ctx.attribute("message", "Vi har afmeldt din mail: '" + email + "' fra nyhedsbrevet");
            ctx.render("success.html");

        } catch (DatabaseException e) {
            ctx.attribute("error", "An error occurred while unsubscribing. Please try again.");
            ctx.redirect("/subscribe");
        }
    }

    public static void viewLoginPage(Context ctx) {
        String message = "Indtast venligst dine oplysninger!";
        ctx.attribute("message", message);
        ctx.attribute("title", "Velkommen til login-siden");
        ctx.render("login.html");
    }


    public static void login(Context ctx) {
        String userName = ctx.formParam("username");
        String password = ctx.formParam("password");
        if (!password.equals("1234")) {
            ctx.redirect("login.html");
        }
        ctx.attribute("username", userName);
        ctx.render("index.html");
    }


    public static void viewSubscribePage(Context ctx) {
        String message = "Hej med dig - her kan du tilmelde dig nyhedsbrevet";
        ctx.attribute("message", message);
        ctx.render("subscribe.html");
    }
}
