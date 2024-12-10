package CouponsProject3;

import CouponsProject3.Controllers.AdminController;
import CouponsProject3.Exceptions.AlreadyExistException;
import CouponsProject3.Exceptions.ExpiredDateException;
import CouponsProject3.Exceptions.NotExistException;
import CouponsProject3.Exceptions.OutOfStockException;
import CouponsProject3.Services.AdminService;
import CouponsProject3.Services.CompanyService;
import CouponsProject3.Services.CustomerService;
import CouponsProject3.Utils.CouponExpirationDailyJob;
import org.springframework.stereotype.Component;

@Component // for singleton
public class TestAll {
//    private final LoginManager loginManager;
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;
    private AdminController adminController;
    private final CouponExpirationDailyJob job;
    private Thread currentThread;

    public TestAll(AdminController adminController, CustomerService customerService, CompanyService companyService, AdminService adminService, CouponExpirationDailyJob job) {
        this.job = job;
        this.customerService = customerService;
        this.companyService = companyService;
        this.adminService = adminService;
        this.adminController = adminController;
//        this.loginManager = loginManager;
    }

    public Thread getCurrentThread() {
        return currentThread;
    }

    public void test() throws NotExistException, AlreadyExistException, OutOfStockException, ExpiredDateException {
            Thread thread = new Thread(job);

            thread.start();
            currentThread = thread;
//
////          //Login manager - good
//            AdminService adminService1 = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.Administrator);
//            System.out.println(adminService1.getOneCustomer(1));
//
//            CompanyService companyService1 = (CompanyService) loginManager.login("non@nonn4", "2222", ClientType.Company);
//            System.out.println(companyService1.getCompanyDetails());
//
//            CustomerService customerService1 = (CustomerService) loginManager.login("ron@gmail.com", "1111", ClientType.Customer);
//            System.out.println(customerService1.getCustomerDetails());
//
////          Admin service-
////          //login- good
//            System.out.println(adminService.login("admin@admin.com", "admin"));
//
////		//Add company - good
//            adminService.addCompany(new Company("non4", "non@nonn4", "2222"));
//
////      //Get one Company - good
//            Company company2 = adminService.getOneCompany(1);
//
////      //Update company - good
//            company2.setEmail("Google@gmail.com");
//            company2.setPassword("1234");
//            company2.setName("bbb");
//            adminService.updateCompany(company2);
////      //Delete company - good
//            adminService.deleteCompany(2);
////      //Get all companies - good
//            System.out.println(adminService.getAllCompanies());
////      // Add customer - good
//            adminService.addCustomer(new Customer("ron", "cohen", "ron@gmail.com", "1111"));
////      // update customer - good
//            Customer customer = adminService.getOneCustomer(2);
//            customer.setFirstName("avraham");
//            adminService.updateCustomer(customer);
////      //Delete customer - good
//            adminService.deleteCustomer(2);
////      //Get all customers - good
//            System.out.println(adminService.getAllCustomers());
//            //Get one customer - good
//            System.out.println(adminService.getOneCustomer(3));
//
////      //Company service:
////      login - good
//            System.out.println(companyService.login("non@nonn3", "123461"));
////      //Add coupon - good
//            companyService.addCoupon(new Coupon(Category.SPA, "massage", "10 alloy as mental therapy", Date.valueOf("2020-09-01"), Date.valueOf("2026-09-01"), 25, 1500.0, null, adminService.getOneCompany(1)));
////      //Update coupon- good
//            Coupon coupon = companyService.getCompanyCoupons().get(1);
//            coupon.setCategory(Category.CINEMA);
//            coupon.setCompany(adminService.getOneCompany(2));
//            companyService.updateCoupon(coupon);
////      //Delete coupon - good
//            System.out.println(companyService.deleteCoupon(1));
//            //Get company coupons - good
//            companyService.login("non@nonn2", "1111");
//            System.out.println(companyService.getCompanyCoupons());
////      //Get company coupon by category - good
//            companyService.login("non@nonn2", "1111");
//            System.out.println(companyService.getCompanyCoupons(Category.SPA));
////      //Get company coupon by max price - good
//            companyService.login("non@nonn2", "1111");
//            System.out.println(companyService.getCompanyCoupons(1500));
////      //Get company details - good
//            companyService.login("non@nonn2", "1111");
//            System.out.println(companyService.getCompanyDetails());
//
//
////      //Customer service:
////      //Login - good
//            System.out.println(customerService.login("ron@gmail.com", "1111"));
//
////      //purchase coupon - good!!
//            System.out.println(customerService.login("ron@gmail.com1", "1111"));
//            customerService.purchaseCoupon(companyService.getCompanyCoupons().get(1));
//
//            //Get customer coupons - good
//            System.out.println(customerService.login("ron@gmail.com", "1111"));
//            System.out.println(customerService.getCustomerCoupons());
//
//            //Get customer coupons by category - good
//            System.out.println(customerService.login("ron@gmail.com", "1111"));
//            System.out.println(customerService.getCustomerCoupons(Category.SPA));
//
//            //Get customer coupons by category - good
//            System.out.println(customerService.login("ron@gmail.com", "1111"));
//            System.out.println(customerService.getCustomerCoupons(1450));
//            System.out.println(customerService.getCustomerCoupons(1550));
//
////      //Get customer details - good
//            System.out.println(customerService.login("ron@gmail.com", "1111"));
//            System.out.println(customerService.getCustomerDetails());
////
////
//////
//////
//            adminService.addCompany(new Company("non1", "non@nonn1", "1111"));
//            adminService.addCompany(new Company("non1", "non@nonn1", "1111"));
//            adminService.addCompany(new Company("non2", "non@nonn2", "2222"));
//            adminService.addCompany(new Company("non3", "non@nonn3", "3333"));
//            adminService.addCompany(new Company("non4", "non@nonn4", "4444"));
//            adminService.addCustomer(new Customer("ron1", "cohen", "ron@gmail.com1", "1111"));
//            adminService.addCustomer(new Customer("ron2", "cohen", "ron@gmail.com2", "1111"));
//            adminService.addCustomer(new Customer("ron3", "cohen", "ron@gmail.com3", "1111"));
//            adminService.addCustomer(new Customer("ron4", "cohen", "ron@gmail.com4", "1111"));
//            companyService.addCoupon(new Coupon(Category.SPA, "massage1", "10 alloy as mental therapy", java.sql.Date.valueOf("2020-09-01"), Date.valueOf("2026-09-01"), 25, 1500.0, null, adminService.getOneCompany(1)));
//            companyService.addCoupon(new Coupon(Category.SPA, "massage2", "10 alloy as mental therapy", java.sql.Date.valueOf("2020-09-01"), Date.valueOf("2026-09-01"), 25, 1500.0, null, adminService.getOneCompany(1)));
//            companyService.addCoupon(new Coupon(Category.SPA, "massage3", "10 alloy as mental therapy", java.sql.Date.valueOf("2020-09-01"), Date.valueOf("2026-09-01"), 25, 1500.0, null, adminService.getOneCompany(1)));
//            companyService.addCoupon(new Coupon(Category.SPA, "massage4", "10 alloy as mental therapy", java.sql.Date.valueOf("2020-09-01"), Date.valueOf("2026-09-01"), 25, 1500.0, null, adminService.getOneCompany(1)));
//            companyService.addCoupon(new Coupon(Category.SPA, "massage5", "10 alloy as mental therapy", java.sql.Date.valueOf("2020-09-01"), Date.valueOf("2026-09-01"), 25, 1500.0, null, adminService.getOneCompany(1)));
//            companyService.addCoupon(new Coupon(Category.SPA, "massage6", "10 alloy as mental therapy", java.sql.Date.valueOf("2020-09-01"), Date.valueOf("2026-09-01"), 25, 1500.0, null, adminService.getOneCompany(1)));

//            thread.interrupt();

    }
}
