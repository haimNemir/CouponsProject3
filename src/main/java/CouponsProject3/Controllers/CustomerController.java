package CouponsProject3.Controllers;
import CouponsProject3.Beans.Coupon;
import CouponsProject3.Beans.Customer;
import CouponsProject3.Exceptions.*;
import CouponsProject3.Services.CustomerService;
import CouponsProject3.Utils.Category;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/customer_controller")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //++
    @PostMapping("/login")
    public boolean login(@RequestParam String email, @RequestParam String password) throws NotExistException, AuthorizationException {
        return customerService.login(email, password);
    }

    //++
    @PostMapping("/purchase_coupon")
    public Coupon purchaseCoupon(@RequestParam int couponId) throws OutOfStockException, ExpiredDateException, NotExistException, AlreadyExistException, AuthorizationException {
        return customerService.purchaseCoupon(couponId);
    }

    //++
    @GetMapping("/get_customer_coupons")
    public ArrayList<Coupon> getCustomerCoupon() throws AuthorizationException {
        return customerService.getCustomerCoupons();
    }

    //++
    @GetMapping("/get_customer_coupons_by_category")
    public ArrayList<Coupon> getCustomerCouponByCategory(@RequestParam Category category) throws AuthorizationException {
        return customerService.getCustomerCoupons(category);
    }

    //++
    @GetMapping("/get_customer_coupons_by_price")
    public ArrayList<Coupon> getCustomerCouponsByPrice(@RequestParam double maxPrice) throws AuthorizationException {
        return customerService.getCustomerCoupons(maxPrice);
    }

    //++
    @GetMapping("/get_customer_details")
    public Customer getCustomerDetails() throws AuthorizationException {
        return customerService.getCustomerDetails();
    }


    //Those methods are not part of the instructions:
    //To show the customer all the coupons:
    @GetMapping("/get_all_coupons")
    public ArrayList<Coupon> getAllCoupons() throws AuthorizationException {
        return new ArrayList<>(customerService.getAllCoupons());
    }
    //++
    //To get couponDetails by id:
    @GetMapping("/get_one_coupon")
    public Coupon getOneCoupon(@RequestParam int id) throws AuthorizationException, NoSuchElementException {
        return customerService.getOneCoupon(id);
    }
}
