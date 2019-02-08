package org.apereo.cas.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

/**
 * This is {@link AbstractServiceRegistry}, that acts as the base parent class
 * for all registry implementations, capturing common ops.
 *
 * @author Misagh Moayyed
 * @since 5.1.0
 */
@Slf4j
@RequiredArgsConstructor
public abstract class AbstractServiceRegistry implements ServiceRegistry {

    private final transient ApplicationEventPublisher eventPublisher;

    /**
     * Publish event.
     *
     * @param event the event
     */
    public void publishEvent(final ApplicationEvent event) {
        if (this.eventPublisher != null) {
            LOGGER.trace("Publishing event [{}]", event);
            this.eventPublisher.publishEvent(event);
        }
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public RegisteredService findServiceByExactServiceId(final String id) {
        LOGGER.debug("findServiceByExactServiceId({})", id);
        return load()
            .stream()
            .filter(r -> StringUtils.isNotBlank(r.getServiceId()) && r.getServiceId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public RegisteredService findServiceByExactServiceName(final String name) {
        LOGGER.debug("findServiceByExactServiceName({})", name);
        return load()
            .stream()
            .filter(r -> r.getName().equals(name))
            .findFirst()
            .orElse(null);
    }

}
