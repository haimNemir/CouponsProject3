//package CouponsProject2.Utils;
//import CouponsProject2.Exceptions.NotExistException;
//import CouponsProject2.Services.AdminService;
//import CouponsProject2.Services.ClientService;
//import CouponsProject2.Services.CompanyService;
//import CouponsProject2.Services.CustomerService;
//import org.springframework.stereotype.Component;
//
//// not needed this layer in phase 3.
//TODO: check if this layer redundant.
//@Component
//public class LoginManager {
//    private final AdminService adminService;
//    private final CompanyService companyService;
//    private final CustomerService customerService;
//
//    private LoginManager(AdminService adminService, CompanyService companyService, CustomerService customerService) {
//        this.adminService = adminService;
//        this.companyService = companyService;
//        this.customerService = customerService;
//    }
//
//    public ClientService login(String email, String password, ClientType clientType) throws NotExistException {
//        switch (clientType){
//            case Administrator:
//                if (adminService.login(email,password))
//                    return adminService;
//                break;
//            case Company:
//                if (companyService.login(email,password))
//                    return companyService;
//                 break;
//            case Customer:
//                if (customerService.login(email,password))
//                    return customerService;
//        }
//        return null;
//    }
//}
