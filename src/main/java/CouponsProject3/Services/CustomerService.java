package CouponsProject3.Services;
import CouponsProject3.Beans.Coupon;
import CouponsProject3.Beans.Customer;
import CouponsProject3.Exceptions.*;
import CouponsProject3.Repositories.CouponRepository;
import CouponsProject3.Repositories.CustomerRepository;
import CouponsProject3.Utils.Category;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CustomerService implements ClientService {
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

//  TODO: check if in purchasing a coupon its throws all the exception and if the coupon is get -1 in the count.
    public Coupon purchaseCoupon(Coupon coupon) throws NotExistException, OutOfStockException, ExpiredDateException, AlreadyExistException, AuthorizationException {
        if (!couponRepository.existsById(coupon.getId())) {
            throw new NotExistException("This coupon does not exist");
        }
        if (coupon.getAmount() <= 0) {
            throw new OutOfStockException("The coupon is out of stock");
        }
        Date currentDate = new Date();
        if (currentDate.after(coupon.getEndDate())) {
            throw new ExpiredDateException("The expiration date is left!");
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
        if (customerId == 0)
            throw new AuthorizationException("Unauthorized please login first");
        Customer connectedCustomer = customerRepository.findById(customerId).orElseThrow();
         return new ArrayList<>(connectedCustomer.getCoupons());
    }

    public ArrayList<Coupon> getCustomerCoupons(Category category) throws AuthorizationException {
        Customer connectedCustomer = customerRepository.findById(customerId).orElseThrow();
        if (customerId == 0)
            throw new AuthorizationException("Unauthorized please login first");
        return new ArrayList<>(connectedCustomer.getCoupons().stream().filter((coupon) -> coupon.getCategory() == category).toList());
    }

    public ArrayList<Coupon> getCustomerCoupons(double maxPrice) throws AuthorizationException {
        if (customerId == 0)
            throw new AuthorizationException("Unauthorized please login first");
        Customer connectedCustomer = customerRepository.findById(customerId).orElseThrow();
        return new ArrayList<>(connectedCustomer.getCoupons().stream().filter((coupon) -> coupon.getPrice() <= maxPrice).toList());
    }

//    TODO: check if you need the validation of if(customerId == 0) or the filter layer handle it.
    public Customer getCustomerDetails() throws AuthorizationException {
        if (customerId == 0)
            throw new AuthorizationException("Unauthorized please login first");
        return customerRepository.findById(customerId).orElseThrow();
    }
}
