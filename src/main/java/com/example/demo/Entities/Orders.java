package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`order`")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "created_date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "price")
    private double price;

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "paid_date")
    private Date paidDate;

    @ManyToMany
    @JoinTable(
            name = "product_order_table",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> product;

    @ManyToOne
    @JoinColumn(name = "card_id")
    @JsonIgnore
    private CreditCards creditCardNumber;

    @OneToMany(fetch=FetchType.LAZY,mappedBy = "order")
    private List<OrderedList> orderedList;


}
