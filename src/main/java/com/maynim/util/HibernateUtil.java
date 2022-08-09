package com.maynim.util;

import com.maynim.entity.Audit;
import com.maynim.listener.AuditTableListener;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = buildConfiguration();
        configuration.configure();

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        SessionFactoryImpl sessionFactoryImpl = sessionFactory.unwrap(SessionFactoryImpl.class);
        registerListeners(sessionFactoryImpl);
        return sessionFactory;
    }

    private static void registerListeners(SessionFactoryImpl sessionFactoryImpl) {
        EventListenerRegistry listenerRegistry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
        AuditTableListener auditTableListener = new AuditTableListener();
        listenerRegistry.appendListeners(EventType.PRE_INSERT, auditTableListener);
        listenerRegistry.appendListeners(EventType.PRE_DELETE, auditTableListener);
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Audit.class);
        configuration.registerTypeOverride(new JsonBinaryType());
        return configuration;
    }
}
