package eventdriven.kafka.basedomains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private static int idCounter = 0;

    private int productId;
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;

        this.productId = Product.idCounter++;


    }

}
