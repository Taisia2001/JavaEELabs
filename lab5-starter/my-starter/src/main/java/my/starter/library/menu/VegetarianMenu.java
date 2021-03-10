package my.starter.library.menu;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "my.eating_preferences", havingValue = "vegetarian")
public class VegetarianMenu implements InitializingBean, Menu {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Vegetarian menu special for you");
    }

    @Override
    public void printMenu() {
        System.out.println("List of vegetarian menu dishes");
    }
}
