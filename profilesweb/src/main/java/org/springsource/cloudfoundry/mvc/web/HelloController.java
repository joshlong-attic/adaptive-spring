package org.springsource.cloudfoundry.mvc.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springsource.cloudfoundry.mvc.services.hello.HelloService;

@Controller
public class HelloController {

    @Autowired  private HelloService helloService;

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String hello(@RequestParam(required = false, value="q") String query) throws Exception {

        return helloService.hello();
    }
}