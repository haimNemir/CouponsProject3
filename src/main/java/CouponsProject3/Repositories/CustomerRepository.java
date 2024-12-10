package CouponsProject3.Repositories;

import CouponsProject3.Beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);
    Customer findByEmailAndPassword(String email, String password);
}
