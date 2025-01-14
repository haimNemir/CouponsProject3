package CouponsProject3.Services;

import CouponsProject3.Beans.Company;
import CouponsProject3.Beans.Coupon;
import CouponsProject3.Exceptions.AlreadyExistException;
import CouponsProject3.Exceptions.AuthorizationException;
import CouponsProject3.Exceptions.NotExistException;
import CouponsProject3.Repositories.CompanyRepository;
import CouponsProject3.Repositories.CouponRepository;
import CouponsProject3.Utils.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private int companyId;

    public CompanyService(CompanyRepository companyRepository, CouponRepository couponRepository) {
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
    }

    public boolean login(String email, String password) throws NotExistException {
        Company company = companyRepository.findByEmailAndPassword(email, password);
        if (company == null) {
            throw new NotExistException("The email or the password is not correct");
        }
        companyId = company.getId();
        return true;
    }

    /**
     * @throws AlreadyExistException if the title of the coupon already exist in other coupon of !!!this!!! company
     */
    public Coupon addCoupon(Coupon coupon) throws AlreadyExistException {
        // check if there is existing coupon with this title, return true if exists.
        boolean couponExists = couponRepository.findCouponsByCompanyId(coupon.getCompany().getId()).stream()
                .anyMatch(existingCoupons -> existingCoupons.getTitle().equals(coupon.getTitle()));

        if (couponExists)
            throw new AlreadyExistException("The coupon title already exist");
        return couponRepository.save(coupon);
    }

    /**
     * @param newCoupon gets Coupon with exist ID, cant updated ID or companyId
     * @throws NotExistException if there is no such coupon by ID.
     */
    public Coupon updateCoupon(Coupon newCoupon) throws NotExistException {
        System.out.println(newCoupon);
        if (newCoupon.getCompany() == null || newCoupon.getStartDate() == null || newCoupon.getEndDate() == null){
            throw new NotExistException("Error, please enter a valid values in the inputs");
        }
        Coupon oldCoupon = couponRepository.findById(newCoupon.getId()).orElseThrow(() -> new NotExistException("This coupon does not exist"));
        newCoupon.setCompany(oldCoupon.getCompany());
        newCoupon.setId(newCoupon.getId());
        return couponRepository.save(newCoupon);
    }

    @Transactional // for telling Spring: this method is from INSERT/UPDATE/DELETE
    public boolean deleteCoupon(int couponId) {
        if (couponRepository.existsById(couponId)) {
            couponRepository.deletePurchasedCouponsByCoupon(couponId);
            couponRepository.deleteById(couponId);
            return true;
        }
        return false;
    }

    public ArrayList<Coupon> getCompanyCoupons() throws AuthorizationException {
        if (companyId == 0)
            throw new AuthorizationException("Unauthorized please login first");
        return new ArrayList<>(couponRepository.findCouponsByCompanyId(companyId));
    }

    public ArrayList<Coupon> getCompanyCoupons(Category category) throws AuthorizationException {
        if (companyId == 0)
            throw new AuthorizationException("Unauthorized please login first");
        return new ArrayList<>(couponRepository.findCouponsByCompanyIdAndCategory(companyId, category));
    }

    public ArrayList<Coupon> getCompanyCoupons(double maxPrice) throws AuthorizationException {
        if (companyId == 0)
            throw new AuthorizationException("Unauthorized please login first");
        return new ArrayList<>(couponRepository.findCouponsByCompanyIdAndPriceLessThan(companyId, maxPrice));
    }

    public Company getCompanyDetails() throws AuthorizationException {
        if (companyId == 0)
            throw new AuthorizationException("Unauthorized please login first");
        return companyRepository.findById(companyId).orElseThrow();
    }
}
