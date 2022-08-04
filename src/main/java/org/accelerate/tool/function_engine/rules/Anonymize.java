package org.accelerate.tool.function_engine.rules;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("anonymize")

public class Anonymize implements IRule{

    protected static final Logger LOGGER = LoggerFactory.getLogger(Anonymize.class);

    protected static Map<String, String> cache = new ConcurrentHashMap<>();

    @Function (name="anonymiseUUID")
    public String execute(final String uuidToAnonymize){
        LOGGER.debug("{} - anonymiseUUID - initiated...",this.getClass());
        String uuidString = cache.get(uuidToAnonymize);
        if (uuidString == null){
            uuidString = generateUUID();
            cache.put(uuidToAnonymize, uuidString);
        }
        else
            LOGGER.debug("{} - anonymiseUUID - cache key found",this.getClass());
        LOGGER.debug("{} - anonymiseUUID - terminated.",this.getClass());
        return uuidString;
    }

    @Function (name="generateUUID")
    public String generateUUID(){
        LOGGER.debug("{} - generateUUID - initiated...",this.getClass());
        UUID uuid = UUID.randomUUID();

        StringBuilder uuidString = new StringBuilder(uuid.toString())
            .append(uuid.variant())
            .append(uuid.version());
        LOGGER.debug("{} - generateUUID - terminated.",this.getClass());
        return uuidString.toString();
    }
}
