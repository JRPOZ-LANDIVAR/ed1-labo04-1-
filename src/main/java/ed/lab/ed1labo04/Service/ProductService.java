package ed.lab.ed1labo04.Service;

import ed.lab.ed1labo04.Entity.ProductEntity;
import ed.lab.ed1labo04.Model.CreateProductRequest;
import ed.lab.ed1labo04.Model.UpdateProductRequest;
import ed.lab.ed1labo04.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

        private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductEntity CreateProduct(CreateProductRequest createProductRequest){
        if (createProductRequest.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio tiene que ser mayor a 0");
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(createProductRequest.getName());
        productEntity.setPrice(createProductRequest.getPrice());

        return productRepository.save(productEntity);
    }


    public ProductEntity updateProduct(long id, UpdateProductRequest updateProductrequest){
        if (updateProductrequest.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio tiene que ser mayor a 0");
        }
        if(updateProductrequest.getQuantity() < 0){
            throw new IllegalArgumentException("La cantidad tiene que ser mayor o igual a 0");
        }

       ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        productEntity.setPrice(updateProductrequest.getPrice());
        productEntity.setQuantity(updateProductrequest.getQuantity());
        return productRepository.save(productEntity);
    }



    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<ProductEntity> getProductById(Long id) {
    return productRepository.findById(id);
    }
}
