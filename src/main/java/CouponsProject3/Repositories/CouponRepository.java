package CouponsProject3.Repositories;

import CouponsProject3.Beans.Coupon;
import CouponsProject3.Utils.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    List<Coupon> findCouponsByCompanyId(int companyId);

    List<Coupon> findCouponsByCompanyIdAndCategory(int companyId, Category category);

    List<Coupon> findCouponsByCompanyIdAndPriceLessThan(int companyId, double maxPrice);

    ArrayList<Coupon> findByEndDateBefore(Date date);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from `customer_coupon` WHERE (`coupon_id` = ?1);")
    void deletePurchasedCouponsByCoupon(int couponId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "delete from `customer_coupon` WHERE (`customer_id` = ?1);")
    void deletePurchasedCouponsByCustomer(int customerId);



}
