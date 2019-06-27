package zup.core;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zup.business.ProductService;
import zup.common.dto.ProductDto;
import zup.common.entity.Product;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Teste de unitário dos serviços de Product, garantindo a cobertura.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntTest {

    @Autowired
	private ProductService productService;

	@Before
    public void setUp() {
        this.productService.deleteAll();
		saveDadosProduct();
    }
	
	@Test
    public void deveListarOsProductsDoScriptDeInicializacao() {
		List<ProductDto> products = productService.findAll();
		
		assertNotNull(products);
        assertEquals("A lista deve conter 2 elementos", 2, products.size());

        ProductDto macbook = products.get(0);
        assertEquals("O primeiro product deve ser o Apple Macbook Pro.", "Apple Macbook Pro", macbook.getName());

        ProductDto telefone = products.get(1);
        assertEquals("O segundo product deve ser o Telefone Intelbras.", "Telefone Intelbras", telefone.getName());
	}


    private void saveDadosProduct() {

        Product prod1 = new Product();
        prod1.setName("Apple Macbook Pro");
        prod1.setDescription("Notebook Macbook Pro I5");
        prod1.setCategory("Eletrônicos");
        productService.save(prod1);

        Product prod2 = new Product();
        prod2.setName("Telefone Intelbras");
        prod2.setDescription("Telefone sem fio preto Intelbras");
        prod2.setCategory("Eletrônicos");
        productService.save(prod2);

    }
}
