package CouponsProject3.Controllers;


import CouponsProject3.Beans.Company;
import CouponsProject3.Beans.Customer;
import CouponsProject3.Exceptions.AlreadyExistException;
import CouponsProject3.Exceptions.AuthorizationException;
import CouponsProject3.Exceptions.NotExistException;
import CouponsProject3.Services.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("admin_controller")
public class AdminController{
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //++
    @PostMapping("/login")
    public boolean login(@RequestParam String email,@RequestParam String password) throws NotExistException, AuthorizationException {
        return adminService.login(email, password);
    }

    //++
    @PostMapping("/add_company")
    public Company addCompany(@RequestBody Company company) throws AlreadyExistException {
        return adminService.addCompany(company);
    }

    //++
    @PutMapping("/update_company")
    public Company updateCompany(@RequestBody Company company) throws NotExistException {
        return adminService.updateCompany(company);
    }

    //++
    @DeleteMapping("/delete_company")
    public boolean deleteCompany(@RequestParam int companyId){
        return adminService.deleteCompany(companyId);
    }

    //++
    @GetMapping("/get_all_companies")
    public ArrayList<Company> getAllCompanies() throws NotExistException {
        return adminService.getAllCompanies();
    }

    //++
    @GetMapping("/get_one_company")
    public Company getOneCompany(@RequestParam int companyId) throws NotExistException {
        return adminService.getOneCompany(companyId);
    }

    //++
    @PostMapping("add_customer")
    public Customer addCustomer(@RequestBody Customer customer) throws AlreadyExistException {
        return adminService.addCustomer(customer);
    }

    //++
    @PutMapping("/updateCustomer")
    public Customer updateCustomer(@RequestBody Customer customer) throws NotExistException {
        return adminService.updateCustomer(customer);
    }

    //++
    @DeleteMapping("/delete_customer")
    public boolean deleteCustomer(@RequestParam int customerId) throws NotExistException {
        return adminService.deleteCustomer(customerId);
    }

    //++
    @GetMapping("/get_all_customers")
    public ArrayList<Customer> getAllCustomers() throws NotExistException {
        return adminService.getAllCustomers();
    }

    //++
    @GetMapping("/get_one_customer")
    public Customer getOneCustomer(@RequestParam int customerId) throws NotExistException {
        return adminService.getOneCustomer(customerId);
    }
}
