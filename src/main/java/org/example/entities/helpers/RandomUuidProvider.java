package org.example.entities.helpers;
import org.example.interfaces.UuidProvider;

import java.util.UUID;

//Production class to create random UUID that we do not need to know
public class RandomUuidProvider implements UuidProvider {
    @Override
    public UUID uuid() {
        return UUID.randomUUID();
    }

    @Override
    public UUID genterateUuid(int testId) {
        return null;
    }
}
