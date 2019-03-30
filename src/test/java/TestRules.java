import com.testasaitec.domain.offers.AppleOfferRule;
import com.testasaitec.domain.offers.PearsAndOrangeOfferRule;
import com.testasaitec.domain.offers.PearsDiscountOfferRule;
import com.testasaitec.domain.offers.PromotionManager;
import com.testasaitec.domain.order.Order;
import com.testasaitec.domain.order.OrderItem;
import com.testasaitec.domain.product.Product;
import com.testasaitec.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestRules {

    @Before
    public void setup(){
        ProductRepository.addProduct(Product.of("Apple", 0.9));
        ProductRepository.addProduct(Product.of("Pear", 0.75));
        ProductRepository.addProduct(Product.of("Orange", 1.0));

    }

    @Test
    public void testAppleOfferRule(){

        List<OrderItem> items = Arrays.asList(
                OrderItem.of(ProductRepository.findProductByCode("Apple").get(), 3L)
        );


        Order order = Order.of(items);

        Order orderWithOffer = PromotionManager.getInstance().applyOffersFor(order);

        assertThat(orderWithOffer.getOffers()
                .get()
                .stream()
                .anyMatch( offer -> offer.getId().equals(AppleOfferRule.ID))).isTrue();
    }

    @Test
    public void testPearsAndAppleRule(){

        List<OrderItem> items = Arrays.asList(
                OrderItem.of(ProductRepository.findProductByCode("Pear").get(), 2L),
                OrderItem.of(ProductRepository.findProductByCode("Orange").get(), 1L)
        );


        Order order = Order.of(items);

        Order orderWithOffer = PromotionManager.getInstance().applyOffersFor(order);

        assertThat(orderWithOffer.getOffers()
                .get()
                .stream()
                .anyMatch( offer -> offer.getId().equals(PearsAndOrangeOfferRule.ID))).isTrue();

    }

    @Test
    public void testPearDiscountRule(){
        List<OrderItem> items = Arrays.asList(
                OrderItem.of(ProductRepository.findProductByCode("Pear").get(), 6L)
        );


        Order order = Order.of(items);

        Order orderWithOffer = PromotionManager.getInstance().applyOffersFor(order);

        assertThat(orderWithOffer.getOffers()
                .get()
                .stream()
                .anyMatch( offer -> offer.getId().equals(PearsDiscountOfferRule.ID))).isTrue();

    }

    @Test
    public void noOfferAPplied(){
        List<OrderItem> items = Arrays.asList(
                OrderItem.of(ProductRepository.findProductByCode("Pear").get(), 1L),
                OrderItem.of(ProductRepository.findProductByCode("Orange").get(), 1L),
                OrderItem.of(ProductRepository.findProductByCode("Apple").get(), 1L)
        );


        Order order = Order.of(items);

        Order orderWithOffer = PromotionManager.getInstance().applyOffersFor(order);

        assertThat(orderWithOffer.getOffers()).isEmpty();


    }

}
