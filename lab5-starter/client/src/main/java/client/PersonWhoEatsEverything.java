package client;

import my.starter.library.menu.ChildrenMenu;
import my.starter.library.menu.VegetarianMenu;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean({VegetarianMenu.class, ChildrenMenu.class})
public class PersonWhoEatsEverything implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("I`m ready to eat everything");
    }
}
