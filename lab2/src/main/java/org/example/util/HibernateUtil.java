package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.database.entity.House;
import org.example.database.entity.Street;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(Street.class);
        configuration.addAnnotatedClass(House.class);
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
