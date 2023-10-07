package org.example.entities.helpers;

import org.example.interfaces.UuidProvider;

import java.util.UUID;

public class FakeUuidProvider implements UuidProvider {

    public UUID genterateUuid(int testId){
        return UUID.fromString("0000-00-00-00-0000"+testId);
    }
    public UUID uuid() {
        return UUID.fromString("0000-00-00-00-000000");
    }
}
