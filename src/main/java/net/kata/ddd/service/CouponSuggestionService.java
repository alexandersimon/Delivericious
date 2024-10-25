package net.kata.ddd.service;

import net.kata.ddd.domain.Basket;
import net.kata.ddd.domain.BasketItem;
import net.kata.ddd.domain.Coupon;

import java.util.List;
import java.util.Optional;

import static net.kata.ddd.domain.MenuItemCategory.SOUP;

public class CouponSuggestionService {
    public Optional<Coupon> suggestCoupon(Basket basket, List<Coupon> availableCoupons) {
        List<BasketItem> basketItems = basket.basketItems();
        if (isEligibleForDelivericious10(basketItems)) {
            return getFor(availableCoupons, "DELIVERICIOUS_10");
        }
        return Optional.empty();
    }

    private Optional<Coupon> getFor(List<Coupon> coupons, String code) {
        return coupons.stream().filter(coupon -> coupon.getCode().equals(code)).findAny();
    }

    private boolean isEligibleForDelivericious10(List<BasketItem> basketItems) {
        return basketItems
                .stream()
                .anyMatch(basketItem -> basketItem.belongsToCategory(SOUP) && basketItem.getQuantity() > 5);
    }
}
