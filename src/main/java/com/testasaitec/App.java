package com.testasaitec;

import com.testasaitec.domain.offers.PromotionManager;
import com.testasaitec.domain.order.Order;
import com.testasaitec.domain.product.Product;
import com.testasaitec.repositories.ProductRepository;
import org.apache.commons.lang.ArrayUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.testasaitec.domain.mappers.Mappers.mapLinesToOrder;
import static com.testasaitec.domain.mappers.Mappers.mapLinesToProducts;
import static org.apache.commons.lang.StringUtils.isEmpty;

public class App {

    public static void main(String[] args) throws IOException, URISyntaxException {

        //"/home/diogo/IdeaProjects/testasaitec/src/main/resources/products.txt"
        //"/home/diogo/IdeaProjects/testasaitec/src/main/resources/order.txt"


        if(ArrayUtils.isEmpty(args)) throw new IllegalArgumentException("NO ARGUMENT FOUND! 1st argument is products file, 2nd argument is order file");
        if(isEmpty(args[0])) throw new IllegalArgumentException("1st parameter should be the path for the list of products file");
        if(isEmpty(args[1])) throw new IllegalArgumentException("2nd parameter should be the path for the order file");

        List<String> linesProducts = readLines(args[0]);
        List<String> order = readLines(args[1]);

        List<Product> products = mapLinesToProducts(linesProducts);

        products.forEach(ProductRepository::addProduct);

        Order mappedOrder = mapLinesToOrder(order);
        Order orderWithOffers = PromotionManager.getInstance().applyOffersFor(mappedOrder);

        displayInConsole(orderWithOffers);

    }


    private static List<String> readLines(String pathStr) throws IOException, URISyntaxException {

        Path path = Paths.get(pathStr);

        Stream<String> lines = Files.lines(path);
        List<String> collectedLines = lines.skip(1).collect(Collectors.toList());
        lines.close();

        return collectedLines;
    }

    private static void displayInConsole(Order order){
        //In case there is a better way of displaying it , it should be done in this method
        System.out.println(order.toString());
    }

}
