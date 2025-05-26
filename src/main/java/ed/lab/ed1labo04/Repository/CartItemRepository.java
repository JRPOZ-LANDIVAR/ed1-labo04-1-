package ed.lab.ed1labo04.Repository;

import ed.lab.ed1labo04.Entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
}
