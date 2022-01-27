package com.getir.reading.sgood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Setter(AccessLevel.PRIVATE)
    private int id;

    @NotNull(message = "Name of the book is compulsory")
    @Column(length = 155, name = "name")
    private String name;

    @NotNull(message = "Stock of the book is compulsory")
    @Column(name = "stock")
    private int stock;

    @JsonIgnore
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
}
