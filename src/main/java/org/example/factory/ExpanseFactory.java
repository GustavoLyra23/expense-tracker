package org.example.factory;

import org.example.models.Expanse;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpanseFactory {

    public static Expanse createExpanse(String description, String amount) {
        return new Expanse(null, description, new BigDecimal(amount), LocalDate.now());
    }


}
