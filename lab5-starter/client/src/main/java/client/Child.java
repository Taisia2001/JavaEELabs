package client;

import my.starter.library.menu.ChildrenMenu;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(ChildrenMenu.class)
public class Child implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Mommy I want eat, give me something or I`ll start to cry");
    }
}
