package org.springsource.cloudfoundry.mvc.web;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class SpringApplicationContextInitializer implements ApplicationContextInitializer<AnnotationConfigWebApplicationContext> {


    @Override
    public void initialize(AnnotationConfigWebApplicationContext applicationContext) {


        String profileSystemProperty = System.getProperty("profile");

        System.out.println("********** profileSystemProperty=" + profileSystemProperty);

        String profile;

        if ("bacon".equalsIgnoreCase(profileSystemProperty))
            profile = "bacon";
        else if ("cat".equalsIgnoreCase(profileSystemProperty))
            profile = "cat";
        else if ("kitten".equalsIgnoreCase(profileSystemProperty))
            profile = "kitten";
        else
            profile = "lost";


        System.out.println("********** profile=" + profile);

        applicationContext.getEnvironment().setActiveProfiles(profile);

        applicationContext.register(WebMvcConfiguration.class);
        applicationContext.refresh();
    }
}