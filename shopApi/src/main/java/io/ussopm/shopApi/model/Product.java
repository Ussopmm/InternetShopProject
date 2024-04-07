package io.ussopm.shopApi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name")
    @NotNull(message = "Product name have to be not null")
    @Size(min = 3, max = 100, message = "Product name size min = 3, max = 100")
    private String productName;

    @Column(name = "definition")
    @Size( max = 500, message = "Definition has limitation of word 500")
    private String description;

}
