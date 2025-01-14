package CouponsProject3.Repositories;

import CouponsProject3.Beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByNameOrEmail(String name, String email);
    Company findByEmailAndPassword(String email, String password);




}
