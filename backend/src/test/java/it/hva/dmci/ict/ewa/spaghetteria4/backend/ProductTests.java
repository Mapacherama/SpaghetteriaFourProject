package it.hva.dmci.ict.ewa.spaghetteria4.backend;

import it.hva.dmci.ict.ewa.spaghetteria4.backend.models.Product;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.repos.ProductRepository;
import it.hva.dmci.ict.ewa.spaghetteria4.backend.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTests {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void findAllOfProductsIsCorrectAmount() {
        when(productRepository.findAll()).thenReturn(Stream
                .of(new Product("Penne all'arrabbiata",
                        "16.5", "Vegetarisch", "Tomatensaus met knoflook, chili, peterselie en pecorino romano"
                        , false), new Product("Spaghetti ai frutti di mare", "14.5", "Vis", "Mosselen en venusschelpen met knoflook, chili, witte wijn, kerstomaat", false)).collect(Collectors.toList()));
        Assertions.assertEquals(2, productService.getAllProducts().size());
    }


    @Test
    public void findTypeOfProductsIsCorrectAmount() {
        when(productRepository.findAllForCategory("Vegetarisch")).thenReturn(Stream
                .of(new Product("Penne all'arrabbiata",
                        "16.5", "Vegetarisch", "Tomatensaus met knoflook, chili, peterselie en pecorino romano"
                        , false)).collect(Collectors.toList()));
        Assertions.assertEquals(1, productService.getAllProductsForCategory("Vegetarisch").size());
    }

    @Test
    public void saveProductTest() {
        Product product = new Product("Spaghetti ai frutti di mare", "14.5", "Vis",
                "Mosselen en venusschelpen met knoflook, chili, witte wijn, kerstomaat", false);

        when(productRepository.save(product)).thenReturn(product);
        Assertions.assertEquals(product, productService.save(product));
    }

    @Test
    public void deleteProductTest() {
        Product product = new Product("Spaghetti ai frutti di mare", "14.5", "Vis",
                "Mosselen en venusschelpen met knoflook, chili, witte wijn, kerstomaat", false);
        product.setId(1L);

        productService.deleteProductById(1L);
        Mockito.verify(productRepository, times(1)).deleteById(product.id);
    }
}
