package ed.lab.ed1labo04.Repository;

import ed.lab.ed1labo04.Entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
