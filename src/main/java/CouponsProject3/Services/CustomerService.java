package CouponsProject3.Services;

import CouponsProject3.Beans.Coupon;
import CouponsProject3.Beans.Customer;
import CouponsProject3.Exceptions.*;
import CouponsProject3.Repositories.CouponRepository;
import CouponsProject3.Repositories.CustomerRepository;
import CouponsProject3.Utils.Category;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Scope("prototype") // To make this class a prototype, the default scope of a Spring component is singleton. We need to set the scope to prototype to allow multiple customers to connect at the same time to the "Service" without the customerId variable being overridden by another customer.
public class CustomerService {
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    private final CompanyService companyService;
    private int customerId;

    public CustomerService(CouponRepository couponRepository, CustomerRepository customerRepository, CompanyService companyService) {
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
        this.companyService = companyService;
    }

    public boolean login(String email, String password) throws NotExistException {
        Customer customer = customerRepository.findByEmailAndPassword(email, password);
        if (customer == null) {
            throw new NotExistException("The email or the password is not correct");
        }
        customerId = customer.getId();
        return true;
    }

    public Coupon purchaseCoupon(int couponId) throws NotExistException, OutOfStockException, ExpiredDateException, AlreadyExistException, AuthorizationException {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new NotExistException("This coupon does not exist"));
        if (!couponRepository.existsById(coupon.getId())) {
            throw new NotExistException("This coupon does not exist");
        }
        if (coupon.getAmount() <= 0) {
            throw new OutOfStockException("The coupon is out of stock");
        }
        Date currentDate = new Date();
        if (currentDate.after(coupon.getEndDate())) {
            throw new ExpiredDateException("The expiration date is left");
        }
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NotExistException("We did not find a customer to purchase the coupon, please log in first"));
        ArrayList<Coupon> coupons = getCustomerCoupons();
        for (Coupon purchasedCoupon : coupons) {
            if (coupon.getId() == purchasedCoupon.getId())
                throw new AlreadyExistException("You can't purchase the same coupon twice");
        }
        coupons.add(coupon);
        customer.setCoupons(new HashSet<>(coupons)); // converting the array list "Set"
        customerRepository.save(customer);
        Coupon updatedCoupon = couponRepository.findById(coupon.getId()).orElseThrow();
        updatedCoupon.setAmount(updatedCoupon.getAmount() - 1);
        return companyService.updateCoupon(updatedCoupon);
    }

    public ArrayList<Coupon> getCustomerCoupons() throws AuthorizationException {
        Customer connectedCustomer = customerRepository.findById(customerId).orElseThrow(()->new AuthorizationException("Unauthorized please login first"));
        return new ArrayList<>(connectedCustomer.getCoupons());
    }

    public ArrayList<Coupon> getCustomerCoupons(Category category) throws AuthorizationException {
        Customer connectedCustomer = customerRepository.findById(customerId).orElseThrow(()->new AuthorizationException("Unauthorized please login first"));
        return new ArrayList<>(connectedCustomer.getCoupons().stream().filter((coupon) -> coupon.getCategory() == category).toList());
    }

    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws AuthorizationException {
        Customer connectedCustomer = customerRepository.findById(customerId).orElseThrow(()->new AuthorizationException("Unauthorized please login first"));
        return new ArrayList<>(connectedCustomer.getCoupons().stream().filter(coupon -> coupon.getPrice() <= maxPrice).toList());
    }

    public Customer getCustomerDetails() throws AuthorizationException {
        return customerRepository.findById(customerId).orElseThrow(()->new AuthorizationException("Unauthorized please login first"));
    }

    public List<Coupon> getAllCoupons() throws AuthorizationException {
        if (customerId == 0)
            throw new AuthorizationException("Unauthorized please login first");
        return couponRepository.findAll();
    }

    public Coupon getOneCoupon(int id) throws AuthorizationException, NoSuchElementException {
        if (customerId == 0)
            throw new AuthorizationException("Unauthorized please login first");
        return couponRepository.findById(id).orElseThrow(() -> new NoSuchElementException("There is no such a coupon in the inventory"));
    }
}
