
package zup.endpoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.WebApplicationContext;
import zup.business.ProductService;
import zup.common.dto.ProductDto;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductEndPointTest {

	@Autowired
	WebApplicationContext context;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@MockBean
    private ProductService productService;

	private MockMvc mvc;

	private static final String URL_BASE = "/products/";
    private static final Long ID = 1L;
    private static final String PROD_NAME = "Apple Macbook Pro";
    private ProductDto prod1;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context)
				.addFilter(springSecurityFilterChain).build();

		prod1 = new ProductDto();
        prod1.setId(1L);
        prod1.setName(PROD_NAME);
        prod1.setDescription("Notebook Macbook Pro I5");
        prod1.setCategory("Eletrônicos");
	}

	@Test
	public void productsUnauthorized() throws Exception {
		mvc.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error", is("unauthorized")));
	}

	private String getAccessToken(String username, String password) throws Exception {
		String authorization = "Basic "
				+ new String(Base64Utils.encode("clientapp:123456".getBytes()));
		String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";

		String content = mvc
				.perform(
						post("/oauth/token")
								.header("Authorization", authorization)
								.contentType(
										MediaType.APPLICATION_FORM_URLENCODED)
								.param("username", username)
								.param("password", password)
								.param("grant_type", "password")
								.param("scope", "read write")
								.param("client_id", "clientapp")
								.param("client_secret", "123456"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
				.andExpect(jsonPath("$.access_token", is(notNullValue())))
				.andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
				.andExpect(jsonPath("$.refresh_token", is(notNullValue())))
				.andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
				.andExpect(jsonPath("$.scope", is(equalTo("read write"))))
				.andReturn().getResponse().getContentAsString();

		return content.substring(17, 53);
	}

	@Test
	public void productsEndpointAuthorized() throws Exception {
		mvc.perform(get("/products")
				.header("Authorization", "Bearer " + getAccessToken("michael", "spring")))
				.andExpect(status().isOk());
	}

	@Test
    public void findProductIdInvalid() throws Exception {

		String accessToken = getAccessToken("michael", "spring");

        given(this.productService.findOne(Mockito.anyLong()))
                .willReturn(Optional.empty());

		mvc.perform(get(URL_BASE + 10)
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Product não encontrado para o id " + 10));
    }

	@Test
    public void findProductIdValid() throws Exception {

		String accessToken = getAccessToken("michael", "spring");

        given(this.productService.findOne(Mockito.anyLong()))
                .willReturn(Optional.of(prod1));

		mvc.perform(get(URL_BASE + ID)
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(jsonPath("$.data.name", equalTo(prod1.getName())))
				.andExpect(jsonPath("$.data.description", equalTo(prod1.getDescription())))
				.andExpect(jsonPath("$.data.category", equalTo(prod1.getCategory())))
				.andExpect(jsonPath("$.errors").isEmpty());
    }

    @Test
    public void removeProduct() throws Exception {

		String accessToken = getAccessToken("michael", "spring");

        given(this.productService.findOne(Mockito.anyLong()))
                .willReturn(Optional.of(new ProductDto()));

		mvc.perform(delete(URL_BASE + ID)
				.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk());
    }
}
