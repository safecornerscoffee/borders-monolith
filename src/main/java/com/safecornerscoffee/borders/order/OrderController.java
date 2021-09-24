package com.safecornerscoffee.borders.order;

import com.safecornerscoffee.borders.order.domain.Order;
import com.safecornerscoffee.borders.order.exception.OrderNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {

    private final OrderService service;
    private final OrderModelAssembler assembler;

    public OrderController(OrderService service, OrderModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping("/orders")
    public CollectionModel<EntityModel<Order>> all() {
        List<Order> orders = service.findAll();
        List<EntityModel<Order>> collect = orders.stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(collect,
                linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }

    @GetMapping("/orders/{id}")
    public EntityModel<Order> one(@PathVariable Long id) {
        Order order = service.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return assembler.toModel(order);
    }

    @PostMapping("/orders")
    public EntityModel<Order> place(@RequestBody Order order) {
        return null;
    };


    public EntityModel<Order> cancel() {
        return null;
    };

    public EntityModel<Order> complete() {
        return null;
    };

}
