package com.excilys.sramirez.formation.MvnComputerDataBase.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;

import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Company;
import com.excilys.sramirez.formation.MvnComputerDataBase.bean.Computer;




public class HibernateConfig {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(HibernateConfig.class);

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernateConfig.xml");
            configuration.addAnnotatedClass(Computer.class);
            configuration.addAnnotatedClass(Company.class);
            LOGGER.info("Hibernate Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            LOGGER.info("Hibernate serviceRegistry created");

            return configuration.buildSessionFactory(serviceRegistry);

        } catch (Throwable ex) {
            LOGGER.error("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }
}
