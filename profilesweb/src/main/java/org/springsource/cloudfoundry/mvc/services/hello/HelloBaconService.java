package org.springsource.cloudfoundry.mvc.services.hello;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("bacon")
@Service
@SuppressWarnings("unchecked")
public class HelloBaconService implements HelloService {

    public String hello(){
        return "bacon is great";
    }


}
