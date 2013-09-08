package org.springsource.cloudfoundry.mvc.services.hello;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("lost")
@Service
@SuppressWarnings("unchecked")
public class HelloLostService implements HelloService {
    @Override
    public String hello() {
        return "I am lost";
    }
}
