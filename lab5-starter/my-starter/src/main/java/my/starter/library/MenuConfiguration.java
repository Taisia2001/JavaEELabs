package my.starter.library;

import my.starter.library.menu.Menu;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Menu.class)
public class MenuConfiguration {

}
