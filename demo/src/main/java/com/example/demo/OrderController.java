package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.* ;
import org.springframework.web.server.ResponseStatusException;

import java.util.List ;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@CrossOrigin

public class OrderController {

    private final ProductRepository productRepository ;
    private final OrderRepository orderRepository ;
    private final UserRepository userRepository ;

    public OrderController(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository ;
        this.orderRepository = orderRepository ;
        this.userRepository = userRepository ;
    }

    @PostMapping("/send")
    public String createOrder(@RequestBody Order order, Authentication authentication) {

        if(authentication != null && authentication.isAuthenticated() ) {
            String email = authentication.getName() ;

            User user = userRepository.findByEmail(email).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found !")) ;
            order.setUser(user);
            order.setGuestEmail(email);
            order.setGuestName(user.getName());
            order.setGuestPhone(user.getPhoneNum());
        }
        else if(order.getGuestEmail() != null) {
            Optional<User> existingUser = userRepository.findByEmail(order.getGuestEmail()) ;

            if(existingUser.isPresent()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This email is already registered . please login first") ;
            }
        }
        int total = 0 ;
        for(OrderItem item : order.getOrderItems()) {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found !")) ;
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
