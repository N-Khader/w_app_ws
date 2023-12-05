package com.developerblog.ws.scurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Value("${token.secret}")
    String token;

    public  String getTokenSecret(){
        return token;
    }
}
