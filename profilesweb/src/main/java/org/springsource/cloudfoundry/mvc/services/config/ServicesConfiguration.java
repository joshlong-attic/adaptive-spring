package org.springsource.cloudfoundry.mvc.services.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springsource.cloudfoundry.mvc.services.hello.HelloService;


@Configuration
@PropertySource("/config.properties")
@Import({/*CloudFoundryDataSourceConfiguration.class, LocalDataSourceConfiguration.class*/})
@ComponentScan(basePackageClasses = {HelloService.class})
public class ServicesConfiguration {


}
