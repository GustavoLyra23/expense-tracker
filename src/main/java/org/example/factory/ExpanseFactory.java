package org.example.factory;

import org.example.models.Expanse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class ExpanseFactory {

    public static Expanse createExpanse(String description, String amount) {
        return new Expanse(UUID.randomUUID().toString(), description, new BigDecimal(amount), Instant.now());
    }


}
