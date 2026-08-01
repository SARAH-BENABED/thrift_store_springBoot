package com.example.demo;

import org.springframework.web.bind.annotation.* ;
import java.util.List ;

@RestController
@RequestMapping("/orders")
@CrossOrigin

public class OrderController {

    private final ProductRepository productRepository ;
    private final OrderRepository orderRepository ;

    public OrderController(OrderRepository orderRepository, ProductRepository productRepository) {
        this.productRepository = productRepository ;
        this.orderRepository = orderRepository ;
    }

    @PostMapping("/send")
    public String createOrder(@RequestBody Order order) {
        int total = 0 ;
        for(OrderItem item : order.getOrderItems()) {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(()-> new RuntimeException("Product not found !")) ;
            int price = product.getPrice() ;
            item.setProductPrice(price);
            total += price * item.getQuantity() ;
            item.setOrder(order);

        }
        order.setTotalPrice(total);
        orderRepository.save(order) ;
        return "Order saved !" ;
    }

}
