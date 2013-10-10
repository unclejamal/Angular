package com.pduda.angular;

import java.io.File;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
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
        handlers.addHandler(servletContextHandler());
        server.setHandler(handlers);
        try {
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Could not start the server", e);
        }
    }

    private ServletContextHandler servletContextHandler() {
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("/");
        handler.addServlet(new ServletHolder(new ServletContainer(resourceConfig())), "/rest/*");
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
}
