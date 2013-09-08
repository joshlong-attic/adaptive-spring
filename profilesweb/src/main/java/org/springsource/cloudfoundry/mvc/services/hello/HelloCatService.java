package org.springsource.cloudfoundry.mvc.services.hello;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("cat")
@Service
@SuppressWarnings("unchecked")

public class HelloCatService implements HelloService {


    public String hello() {
        return "Meow";
    }
}
