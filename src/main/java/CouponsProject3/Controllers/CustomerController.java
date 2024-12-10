package CouponsProject3.Controllers;
import CouponsProject3.Beans.Coupon;
import CouponsProject3.Beans.Customer;
import CouponsProject3.Exceptions.*;
import CouponsProject3.Services.CustomerService;
import CouponsProject3.Utils.Category;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@CrossOrigin("*")
@RestController
@RequestMapping("customer_controller")
public class CustomerController extends ClientController{
    private final CustomerService customerService;
    private final CustomerController customerController;

    public CustomerController(ArrayList<String> activeTokens, CustomerService customerService, CustomerController customerController) {
        super(activeTokens);
        this.customerService = customerService;
        this.customerController = customerController;
    }

    @PostMapping("/login")
    public String  login(@RequestParam String email, @RequestParam String password) throws NotExistException, AuthorizationException {
        customerService.login(email, password);
        return super.createToken(customerController);
    }

    @PostMapping("/purchase_coupon")
    public Coupon purchaseCoupon(@RequestBody Coupon coupon) throws OutOfStockException, ExpiredDateException, NotExistException, AlreadyExistException, AuthorizationException {
        return customerService.purchaseCoupon(coupon);
    }

    @GetMapping("/get_customer_coupons")
    public ArrayList<Coupon> getCustomerCoupon() throws AuthorizationException {
        return customerService.getCustomerCoupons();
    }

    @GetMapping("/get_customer_coupons_by_category")
    public ArrayList<Coupon> getCustomerCouponByCategory(@RequestBody Category category) throws AuthorizationException {
        return customerService.getCustomerCoupons(category);
    }

    @GetMapping("/get_customer_coupons_by_price")
    public ArrayList<Coupon> getCustomerCouponsByPrice(@RequestParam double maxPrice) throws AuthorizationException {
        return customerService.getCustomerCoupons(maxPrice);
    }

    @GetMapping("/get_customer_details")
    public Customer getCustomerDetails() throws AuthorizationException {
        return customerService.getCustomerDetails();
    }
}
