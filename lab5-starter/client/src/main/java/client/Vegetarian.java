package client;

import my.starter.library.menu.VegetarianMenu;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(VegetarianMenu.class)
public class Vegetarian implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("I`m waiting for vegetarian dishes");
    }
}
