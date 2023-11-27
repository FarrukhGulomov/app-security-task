package uz.pdp.online.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.dto.OrderDto;
import uz.pdp.online.entity.Order;
import uz.pdp.online.entity.Product;
import uz.pdp.online.entity.User;
import uz.pdp.online.entity.utils.Currency;
import uz.pdp.online.repository.OrderRepository;
import uz.pdp.online.repository.ProductRepository;
import uz.pdp.online.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    List<Product> products = new ArrayList<>();

    public ResponseEntity<?> add(OrderDto dto) {
        products.clear();
        BigDecimal total = BigDecimal.ZERO;
        for (Long productsId : dto.getProductsIds()) {
            Optional<Product> optionalProduct = productRepository.findById(productsId);
            if (optionalProduct.isEmpty()) return ResponseEntity.status(404).build();
            Product product = optionalProduct.get();

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(product.getAmount())));

            products.add(optionalProduct.get());
        }
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isEmpty()) return ResponseEntity.status(404).body("User is not found!");
        Order order = new Order();
        order.setUser(optionalUser.get());
        order.setProducts(products);
        order.setTotal(total);
        orderRepository.save(order);
        return ResponseEntity.ok().build();

    }
    public ResponseEntity<?> getOrder(Long id){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isEmpty()) return ResponseEntity.status(404).build();
        return ResponseEntity.ok(optionalOrder.get());
    }
    public ResponseEntity<?> getOrders(int pageNum,int pageSize){
         Pageable pageable = PageRequest.of(pageNum, pageSize);
         return ResponseEntity.ok(orderRepository.findAll(pageable));
    }

    public ResponseEntity<?> edit(Long id,OrderDto dto){
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if(optionalOrder.isEmpty()) return ResponseEntity.status(404).build();
        BigDecimal total = BigDecimal.ZERO;
        Order order = optionalOrder.get();
        for (Long productsId : dto.getProductsIds()) {
            Optional<Product> optionalProduct = productRepository.findById(productsId);
            if(optionalProduct.isEmpty()) return ResponseEntity.status(404).build();
            Product product = optionalProduct.get();
            total=total.add(product.getPrice().multiply(BigDecimal.valueOf(product.getAmount())));

            products.add(product);
        }
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if(optionalUser.isEmpty()) return ResponseEntity.status(404).build();

        order.setUser(optionalUser.get());
        order.setTotal(total);
        order.setProducts(products);
        orderRepository.save(order);
        return ResponseEntity.ok().build();

    }

    public ResponseEntity<?> delete(Long id){
        try{
            orderRepository.deleteById(id);

        }
        catch (Exception e){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok().build();
    }
}
