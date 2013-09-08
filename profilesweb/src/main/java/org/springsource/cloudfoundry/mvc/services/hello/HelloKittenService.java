package org.springsource.cloudfoundry.mvc.services.hello;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("kitten")
@Service
@SuppressWarnings("unchecked")

public class HelloKittenService implements HelloService{


    public String hello() {
        return "Pur";
    }
}