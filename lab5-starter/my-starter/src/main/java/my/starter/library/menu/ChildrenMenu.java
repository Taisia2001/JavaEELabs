package my.starter.library.menu;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "my.eating_preferences", havingValue = "children_food")
public class ChildrenMenu implements InitializingBean, Menu {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Children menu special for you");
    }

    @Override
    public void printMenu() {
        System.out.println("List of children`s menu dishes");
    }
}
