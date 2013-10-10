package com.pduda.angular;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("time")
public class TimeResource {

    @GET
    @Produces(MediaType.WILDCARD)
    public String getCurrentTime() {
        return String.format("It's currently %s!", format(new Date()));
    }

    private String format(Date now) {
        return new SimpleDateFormat("HH:mm").format(now);
    }
}
