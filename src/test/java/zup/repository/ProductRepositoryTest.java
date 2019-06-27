package zup.repository;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zup.common.entity.Product;

import javax.validation.ConstraintViolationException;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Product monitor;
	private static final Long ID = 1L;

	@Before
	public void setUp() {
		this.productRepository.save(getDadosProduct());
		monitor = new Product("Monitor 20 polegadas", "Monitor sem base", "Eletrônicos");
	}

	@Test
	public void findProductPorId() {
		Optional<Product> product = this.productRepository.findById(ID);

		assertEquals(ID, product.get().getId());
	}

	@Test
	public void findProductPorIdInvalido() {
		Optional<Product> product = this.productRepository.findById(201L);

		assertNotNull(product);
	}

	@Test
	public void createShouldPersistData() {

		this.productRepository.save(monitor);
		assertThat(monitor.getId()).isNotNull();
		assertThat(monitor.getName()).isEqualTo("Monitor 20 polegadas");
		assertThat(monitor.getDescription()).isEqualTo("Monitor sem base");
	}

//	@Test
//	public void deleteShouldRemoveData() {
//
//		this.productRepository.save(monitor);
//		productRepository.delete(monitor);
//		Assertions.assertThat(productRepository.findById(monitor.getId())).isEmpty();
//	}

	@Test
	public void updateShouldChangeAndPersistData() {

		this.productRepository.save(monitor);
		monitor.setName("LG 29 polegadas");
		monitor.setDescription("Monitor branco sem base");
		this.productRepository.save(monitor);

		Optional<Product> monitorUpdate = this.productRepository.findById(monitor.getId());
		assertThat(monitorUpdate.get().getName()).isEqualTo("LG 29 polegadas");
		assertThat(monitorUpdate.get().getDescription()).isEqualTo("Monitor branco sem base");
	}

	@Test
	public void createWhenNameIsNullShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("O campo name do product é obrigatório");
		this.productRepository.save(new Product());
	}

	private Product getDadosProduct() {

		Product prod1 = new Product();
		prod1.setName("Apple Macbook Pro");
		prod1.setDescription("Notebook Macbook Pro I5");
		prod1.setCategory("Eletrônicos");

		return prod1;
	}
}
