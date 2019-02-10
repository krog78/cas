package org.apereo.cas.services;

import org.apereo.cas.config.CouchbaseServiceRegistryConfiguration;
import org.apereo.cas.couchbase.core.CouchbaseClientFactory;
import org.apereo.cas.util.junit.EnabledIfContinuousIntegration;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * This is {@link CouchbaseServiceRegistryTests}.
 *
 * @author Misagh Moayyed
 * @since 4.2.0
 */
@SpringBootTest(classes = {
    CouchbaseServiceRegistryTests.CouchbaseServiceRegistryTestConfiguration.class,
    RefreshAutoConfiguration.class,
    CouchbaseServiceRegistryConfiguration.class
},
    properties = {
        "cas.serviceRegistry.couchbase.password=password",
        "cas.serviceRegistry.couchbase.bucket=testbucket"
    })
@Tag("Couchbase")
@EnabledIfContinuousIntegration
public class CouchbaseServiceRegistryTests extends AbstractServiceRegistryTests {

    @Autowired
    @Qualifier("couchbaseServiceRegistry")
    private ServiceRegistry serviceRegistry;

    @Autowired
    @Qualifier("serviceRegistryCouchbaseClientFactory")
    private CouchbaseClientFactory serviceRegistryCouchbaseClientFactory;

    @Override
    public ServiceRegistry getNewServiceRegistry() {
        return serviceRegistry;
    }

    @BeforeAll
    @SneakyThrows
    public void delay() {
        Thread.sleep(5000);
    }

    @BeforeEach
    @SneakyThrows
    public void clearBucket() {
        Thread.sleep(500);
    }

    @Configuration("CouchbaseServiceRegistryTestConfiguration")
    public static class CouchbaseServiceRegistryTestConfiguration {

        @SneakyThrows
        @EventListener
        public void handleCouchbaseSaveEvent(final CouchbaseRegisteredServiceSavedEvent event) {
            Thread.sleep(100);
        }
        @SneakyThrows
        @EventListener
        public void handleCouchbaseDeleteEvent(final CouchbaseRegisteredServiceDeletedEvent event) {
            Thread.sleep(100);
        }
    }
}
