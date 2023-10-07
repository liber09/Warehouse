package org.example.interfaces;

import java.util.UUID;

public interface UuidProvider {
    UUID uuid();

    UUID genterateUuid(int testId);
}
