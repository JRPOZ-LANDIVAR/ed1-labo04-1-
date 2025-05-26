package ed.lab.ed1labo04.Service;

import ed.lab.ed1labo04.Entity.*;
import ed.lab.ed1labo04.Model.*;
import ed.lab.ed1labo04.Repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public CartResponse createCart(CreateCartRequest request) {
        List<CartItemEntity> cartItems = new ArrayList<>();
        double totalPrice = 0;

        for (CartItemRequest itemReq : request.getCartItems()) {
            ProductEntity product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + itemReq.getProductId()));

            if (itemReq.getQuantity() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
            }

            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new IllegalArgumentException("Inventario insuficiente para: " + product.getName());
            }

            // Restar inventario
            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
            productRepository.save(product);

            CartItemEntity cartItem = new CartItemEntity();
            cartItem.setProduct(product);
            cartItem.setQuantity(itemReq.getQuantity());
            cartItems.add(cartItem);

            totalPrice += product.getPrice() * itemReq.getQuantity();
        }

        CartEntity cart = new CartEntity();
        for (CartItemEntity item : cartItems) {
            item.setCart(cart); // establecer relación bidireccional
        }
        cart.setCartItems(cartItems);

        CartEntity savedCart = cartRepository.save(cart);

        return mapToResponse(savedCart);
    }

    public CartResponse getCart(long id) {
        CartEntity cart = cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado."));

        return mapToResponse(cart);
    }

    private CartResponse mapToResponse(CartEntity cart) {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());

        List<CartItemResponse> items = new ArrayList<>();
        double total = 0;

        for (CartItemEntity item : cart.getCartItems()) {
            ProductEntity product = item.getProduct();

            CartItemResponse itemResp = new CartItemResponse();
            itemResp.setProductId(product.getId());
            itemResp.setName(product.getName());
            itemResp.setPrice(product.getPrice());
            itemResp.setQuantity(item.getQuantity());

            items.add(itemResp);

            total += product.getPrice() * item.getQuantity();
        }

        response.setCartItems(items);
        response.setTotalPrice(total);

        return response;
    }
}
