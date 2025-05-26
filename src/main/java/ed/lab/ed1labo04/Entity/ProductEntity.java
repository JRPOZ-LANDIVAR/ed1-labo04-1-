package ed.lab.ed1labo04.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProductEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY )
   private long id;
   private String name;
   private double price;
   private int quantity;

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;

   }

   public String getName() {
      return name;
   }

   public void setName(String nombre) {
      this.name = nombre;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double precio) {
      this.price = precio;
   }

   public int getQuantity() {
      return quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }


}
