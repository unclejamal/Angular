package com.pduda.angular;

import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewriteRegexRule;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class TimeExpertServer {

    private Server server;

    public void start() {
        server = new Server(6666);
        HandlerCollection handlers = new HandlerCollection();
        handlers.addHandler(rewriteHandler());
        handlers.addHandler(servletContextHandler());
        handlers.addHandler(staticContentHandler());
        server.setHandler(handlers);
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Could not start the server", e);
        }
    }

    private ServletContextHandler servletContextHandler() {
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/rest");
        handler.addServlet(new ServletHolder(new ServletContainer(resourceConfig())), "/*");
        return handler;
    }

    private ServletContextHandler staticContentHandler() {
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/static");
        handler.addServlet(new ServletHolder(new DefaultServlet()), "/*");
        handler.setResourceBase("src/main/webapp");
        handler.setClassLoader(Thread.currentThread().getContextClassLoader());
        handler.setWelcomeFiles(new String[]{"index.html"});
        return handler;
    }

    private ResourceConfig resourceConfig() {
        return new ResourceConfig().register(new TimeResource());
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException("Could not stop the server", e);
        }
    }

    public void join() {
        try {
            server.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Could not join the thread", e);
        }
    }

    private Handler rewriteHandler() {
        RewriteHandler rewrite = new RewriteHandler();
        rewrite.setRewriteRequestURI(true);
        rewrite.setRewritePathInfo(false);
        rewrite.setOriginalPathAttribute("originalPath");

        RewriteRegexRule reverse = new RewriteRegexRule();
//        reverse.setRegex("/static/([^.])+$");
        reverse.setRegex("/static/([^.])+$");
        reverse.setReplacement("/index.html");
        rewrite.addRule(reverse);

        return rewrite;
    }
}
