package CouponsProject3.Utils;
import CouponsProject3.Beans.Coupon;
import CouponsProject3.Exceptions.NotExistException;
import CouponsProject3.Repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Component
public class CouponExpirationDailyJob extends Thread {
    private final HashMap<String, String> activeTokens;
    @Autowired // need @Autowired here and not constructor.
    private CouponRepository couponRepository;

    public CouponExpirationDailyJob(HashMap<String, String> activeTokens) {
        this.activeTokens = activeTokens;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Date currentDate = new Date();
                ArrayList<Coupon> expiredCoupons = couponRepository.findByEndDateBefore(currentDate);
                for (Coupon coupon : expiredCoupons) {
                    couponRepository.deletePurchasedCouponsByCoupon(coupon.getId());
                    couponRepository.deleteById(coupon.getId());
                }

                //Check overflow connections:
                if (activeTokens.size() > 10){
                    System.out.println("Too many connections! check 'LoginController + CouponExpirationDailyJob + Main'");
                }

              sleep(3_600_000 * 24); // 24 hours
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted!!");
                break;
            }
        }
    }
}
