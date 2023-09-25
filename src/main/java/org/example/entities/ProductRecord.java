package org.example.entities;

import java.time.LocalDate;

public record ProductRecord(int id, String name, Category category, int rating, LocalDate creationDate, LocalDate modifiedDate) {

}
