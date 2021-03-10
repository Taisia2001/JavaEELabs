package my.starter.library.menu;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "my.eating_preferences", havingValue = "no_preferences", matchIfMissing = true)
public class DefaultMenu implements InitializingBean, Menu {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Our best menu special for you");
    }

    @Override
    public void printMenu() {
        System.out.println("List of standard menu dishes");
    }
}
