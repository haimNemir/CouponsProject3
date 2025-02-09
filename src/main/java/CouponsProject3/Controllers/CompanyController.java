package CouponsProject3.Controllers;
import CouponsProject3.Beans.Company;
import CouponsProject3.Beans.Coupon;
import CouponsProject3.Exceptions.AlreadyExistException;
import CouponsProject3.Exceptions.AuthorizationException;
import CouponsProject3.Exceptions.NotExistException;
import CouponsProject3.Services.CompanyService;
import CouponsProject3.Utils.Category;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("company_controller")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    //++
    @PostMapping("/login")
    public boolean login(@RequestParam String email, @RequestParam String password) throws NotExistException, AuthorizationException {
        return companyService.login(email, password);
    }

    //++
    @PostMapping("/add_coupon")
    public Coupon addCoupon(@RequestBody Coupon coupon) throws AlreadyExistException {
        return companyService.addCoupon(coupon);
    }

    //++
    @PutMapping("/update_coupon")
    public Coupon updateCoupon(@RequestBody Coupon coupon) throws NotExistException {
        return companyService.updateCoupon(coupon);
    }

    //++
    @DeleteMapping("/delete_coupon")
    public void deleteCoupon(@RequestParam int couponId) throws NotExistException {
        companyService.deleteCoupon(couponId);
    }

    //++
    @GetMapping("/get_company_coupons")
    public ArrayList<Coupon> getCompanyCoupons() throws AuthorizationException {
        return companyService.getCompanyCoupons();
    }

    //++
    @GetMapping("/get_company_coupons_by_category")
    public ArrayList<Coupon> getCompanyCoupons(@RequestParam Category category) throws AuthorizationException {
        return companyService.getCompanyCoupons(category);
    }

    //++
    @GetMapping("/get_company_coupons_by_price")
    public ArrayList<Coupon> getCompanyCoupons(@RequestParam double maxPrice) throws AuthorizationException {
        return companyService.getCompanyCoupons(maxPrice);
    }

    //++
    @GetMapping("/get_company_details")
    public Company getCompanyDetails() throws AuthorizationException {
        return companyService.getCompanyDetails();
    }
}
