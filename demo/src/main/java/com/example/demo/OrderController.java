package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.* ;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List ;
import java.util.Optional;


@RestController
@RequestMapping("/orders")
@CrossOrigin

public class OrderController {

    private final ProductRepository productRepository ;
    private final OrderRepository orderRepository ;
    private final UserRepository userRepository ;
    private final SimpleEmailVerificationService verificationService ;

    public OrderController(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository, SimpleEmailVerificationService verificationService) {
        this.productRepository = productRepository ;
        this.orderRepository = orderRepository ;
        this.userRepository = userRepository ;
        this.verificationService = verificationService ;
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
            System.out.println(order.getVerificationCode());
            if(existingUser.isPresent()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "This email is already registered . please login first") ;
            }
            if(! verificationService.verifyCode(order.getGuestEmail(), order.getVerificationCode()) ) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid or missing verification code . please try again") ;
            }
        }
        int total = 0 ;
        for(OrderItem item : order.getOrderItems()) {
            Product product = productRepository.findById(item.getProductId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found !")) ;
            int price = product.getPrice() ;

            if(price != item.getProductPrice()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Product price does not match") ;
            }

            String size = product.getSize() ;
            if(! size.equals(item.getProductSize())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Product size does not match") ;
            }

            item.setProductPrice(price);
            total += price * item.getQuantity() ;
            item.setOrder(order);

        }
        order.setTotalPrice(total);
        order.setPlacedAt(Instant.now());
        order.setOrderStatus(OrderStatus.PENDING);

        orderRepository.save(order) ;
        return "Order saved !" ;
    }

    @GetMapping("/my-orders")
    public List<Order> getMyOrders(Authentication authentication) {

        String email = authentication.getName() ;
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found !")) ;
        return orderRepository.findByUser(user) ;
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderRepository.findAll() ;
    }

    @PatchMapping("/{id}/orderStatus")
    public Order updateOrderStatus(@PathVariable long id, @RequestBody StatusRequest request) {

        Order order = orderRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")) ;
        if(order.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't change delivered orders") ;
        }
        if(order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't change canceled orders") ;
        }
        order.setOrderStatus(request.getOrderStatus());

        return orderRepository.save(order) ;
    }




}
