package zup.presentation.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import zup.business.ProductService;
import zup.common.dto.ProductDto;
import zup.common.entity.Product;
import zup.common.mapper.ProductMapper;
import zup.response.Response;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Endpoint para acesso a funcionalidades de products.
 */
@RestController
@RequestMapping("/products")
public class ProductEndpoint {

    private static final Logger log = LoggerFactory.getLogger(ProductEndpoint.class);

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductEndpoint(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    /**
     * Retorna a listagem de products.
     * @return ResponseEntity<List<ProductDto>>
     */
    @GetMapping("")
    public ResponseEntity<List<ProductDto>> list() {

        log.info("Buscando todos os products");
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * Retorna um product dado um ID.
     *
     * @param id
     * @return ResponseEntity<ProductDto>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductDto>> get(@PathVariable("id") Long id) {

        log.info("Buscando product por ID: {}", id);
        Response<ProductDto> response = new Response<>();
        Optional<ProductDto> product = productService.findOne(id);

        if (!product.isPresent()) {
            log.info("Product não encontrado para o ID: {}", id);
            response.getErrors().add("Product não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(product.get());
        return ResponseEntity.ok(response);
    }

    /**
     * Adicionando um product.
     *
     * @param entidade
     * @return ResponseEntity<ProductDto>
     */
    @PostMapping("")
    public ResponseEntity<Response<ProductDto>> save(@RequestBody Product entidade, BindingResult result) {

        log.info("Adicionando um product: {}", entidade.getName());

        Response<ProductDto> response = new Response<>();
        validarProduct(entidade, result);

        if (result.hasErrors()) { return getResponseResponseEntity(result, response); }

        response.setData(productService.save(entidade).get());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Atualiza os dados de um product.
     *
     * @param id
     * @param productDto
     * @param result
     * @return ResponseEntity<Response<ProductDto>>
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<ProductDto>> atualizar(@PathVariable("id") Long id,
                                                          @Valid @RequestBody ProductDto productDto, BindingResult result) {

        log.info("Atualizando product: {}", productDto.toString());
        Response<ProductDto> response = new Response<>();

        productService.findOne(id)
                .map(x -> {
                    x.setName(productDto.getName());
                    x.setDescription(productDto.getDescription());
                    x.setCategory(productDto.getCategory());
                    return productService.save(productMapper.toEntity(x));
                })
                .orElseGet(() -> {
                    productDto.setId(id);
                    return productService.save(productMapper.toEntity(productDto));
                });

        response.setData(productDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Deleta um product pelo id.
     *
     * @param id
     * @return ResponseEntity<String>>
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) {

        log.info("Removendo um product pelo ID: {}", id);
        Response<String> response = new Response<>();
        Optional<ProductDto> productDto = productService.findOne(id);

        if (!productDto.isPresent()) {
            log.info("Erro ao remover devido ao product ID: {} ser inválido.", id);
            response.getErrors().add("Erro ao remover product. Registro não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        productService.delete(id);
        return ResponseEntity.ok(new Response<>());
    }

    /**
     * Deleta todos os products.
     *
     * @return ResponseEntity<String>>
     */
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllProduct() {

        log.info("Removendo todos os products");
        productService.deleteAll();
        return new ResponseEntity<>("All products have been deleted!", HttpStatus.OK);
    }

    /**
     * Valida um product, verificando se tem o nome informado.
     *
     * @param product
     * @param result
     */
    private void validarProduct(Product product, BindingResult result) {
        if (product.getName().isEmpty()) {
            result.addError(new ObjectError("product", "Name não informado."));
        }
    }

    /**
     * Gera o log de erro e captura os erros.
     *
     *  @param result
     *  @param response
     */

    private ResponseEntity<Response<ProductDto>> getResponseResponseEntity(BindingResult result, Response<ProductDto> response) {
        log.error("Erro validando product: {}", result.getAllErrors());
        result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(response);
    }


    /**
     * Atualiza os dados do product com base nos dados encontrados no DTO.
     *  @param product
     * @param productDto
     */
    private void atualizarDadosProduct(ProductDto product, ProductDto productDto) {

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(productDto.getCategory());
    }
}
