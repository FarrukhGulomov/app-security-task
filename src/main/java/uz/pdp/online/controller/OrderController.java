package uz.pdp.online.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.dto.OrderDto;
import uz.pdp.online.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','CUSTOMER','OPERATOR')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody OrderDto dto){
        return orderService.add(dto);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','CUSTOMER','OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id){
        return orderService.getOrder(id);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public ResponseEntity<?> getOrders(@RequestParam int pageNum,@RequestParam int pageSize){
        return orderService.getOrders(pageNum,pageSize);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody OrderDto dto){
        return orderService.edit(id,dto);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return orderService.delete(id);
    }
}
