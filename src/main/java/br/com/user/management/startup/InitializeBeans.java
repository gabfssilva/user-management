package br.com.user.management.startup;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.servlet.ServletContext;
import java.util.Set;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
public class InitializeBeans {
    public void onStartup(@Observes @Initialized(ApplicationScoped.class) ServletContext webApplication, BeanManager beanManager){
        Set<Bean<?>> beans = beanManager.getBeans(Object.class, new AnnotationLiteral<Startup>() {
        });

        for (Bean<?> bean : beans) {
            beanManager.getReference(bean, bean.getTypes().iterator().next(), beanManager.createCreationalContext(bean)).toString();
        }
    }
}
