package net.kata.ddd.service;

import net.kata.ddd.domain.Basket;
import net.kata.ddd.infra.BasketCheckedOutEvent;
import net.kata.ddd.infra.EventPublisher;
import net.kata.ddd.infra.PaymentService;

public class CheckoutService {
    private final PaymentService paymentService;
    private       EventPublisher eventPublisher;

    public CheckoutService(EventPublisher eventPublisher, PaymentService paymentService) {
        this.eventPublisher = eventPublisher;
        this.paymentService = paymentService;
    }

    public void checkout(Basket basket) throws PaymentFailedException {
        if (paymentService.pay(basket.totalPrice())) {
            basket.markAsCheckedOut();
            eventPublisher.publish(new BasketCheckedOutEvent(basket));
        } else
            throw new PaymentFailedException();
    }
}
