package CouponsProject3.Utils;

import CouponsProject3.Beans.Company;
import CouponsProject3.Beans.Coupon;
import CouponsProject3.Beans.Customer;
import CouponsProject3.Controllers.LoginController;
import CouponsProject3.Exceptions.*;
import CouponsProject3.Services.AdminService;
import CouponsProject3.Services.CompanyService;
import CouponsProject3.Services.CustomerService;
import org.springframework.context.ApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateDataInDB {
    private ApplicationContext context;

    public CreateDataInDB(ApplicationContext context) {
        this.context = context;
    }

    public void startCreatingData() {
        try {
            LoginController loginController = context.getBean(LoginController.class);
            loginController.login("admin@admin.com", "admin", "Administrator");
            AdminService adminService = context.getBean(AdminService.class);
            try {
                adminService.getAllCompanies().isEmpty();
                throw new AlreadyExistException("Data already exist in the database");
            } catch (Exception e) {
                CompanyService companyService = context.getBean(CompanyService.class);
                CustomerService customerService = context.getBean(CustomerService.class);

                String[] companiesNames = {"Apple", "Microsoft", "Google", "Facebook", "Amazon", "Tesla", "IBM", "Intel", "Samsung", "Sony", "Dell", "HP", "Oracle", "SAP", "Adobe", "Cisco", "Nvidia", "Qualcomm", "LG", "Panasonic", "GeneralElectric", "GeneralMotors", "Verizon", "AT&T", "ExxonMobil", "Chevron", "Shell", "BP", "Citigroup", "GoldmanSachs", "MorganStanley", "AmericanExpress", "Visa", "Mastercard", "PayPal", "Uber", "Lyft", "Airbnb", "Spotify", "Netflix", "Twitter", "Snapchat", "Square", "Salesforce", "Zoom", "Slack", "Dropbox", "AMD", "HTC", "Xiaomi", "ByteDance", "Alibaba", "JD", "Meituan", "Booking", "Expedia", "TripAdvisor", "Lenovo", "Asus", "Acer", "Canon", "Nikon", "Fujifilm", "GoPro", "Deliveroo", "Grubhub", "DoorDash", "Instacart", "Costco", "Walmart", "Target", "BestBuy", "HomeDepot", "Lowes", "Kroger", "Publix", "Walgreens", "CVS", "eBay", "Shopify", "Etsy", "Zara", "H&M", "Uniqlo", "Gap", "Nordstrom", "RalphLauren", "Coach", "MichaelKors", "Burberry", "Prada", "Gucci", "Versace", "Fendi", "Chanel", "Dior", "LouisVuitton", "BMW", "Mercedes", "Audi", "Hyundai", "Toyota", "Honda", "Ford", "Chevrolet", "Nissan", "Porsche", "Volkswagen", "Subaru", "Kia", "Mazda", "Lexus", "Acura", "Jaguar", "LandRover", "Ferrari", "Lamborghini", "Maserati", "Bentley", "AstonMartin", "RollsRoyce", "McLaren", "Rivian", "LucidMotors", "Polestar", "Jeep", "Ram", "Chrysler", "Dodge", "Mini", "Fiat", "Cadillac", "Lincoln", "Buick", "GMC", "Mitsubishi"};
                String[] companiesPasswords = {"Appl3X!", "MicroS7#", "GooglE99", "FaceB55@", "Amaz0nX1", "T3sla77", "IBmXyz@", "Int3l#456", "Sams@202", "Sony888!", "Dell9xyz", "HPPass12", "Or4cle7$", "SAPxy@9", "Adob3Xyz", "C1sco99%", "Nvidia123", "QualComX!", "LG7xyZ@", "PanaXyz8", "G3Electric", "GmMotors1", "Ver1z0n22", "ATTpass99", "ExxM0b1X!", "Ch3vron77", "Shell9XYZ", "BPxyz567", "CitiPass01", "GoldSachsX2", "MorganXyz@", "AmexPass99", "VisaXy77!", "MasterXyz1", "PayP@l987", "UbeRpa55@", "LyfTzXy12", "AirBnb9xyz", "SpotIfy88", "Netflix123@", "TwittXyz7!", "SnapP@ss09", "Squar3XYZ", "SalesXy98@", "ZoomInXyz1", "SlackP@ss77", "DropBXyz123", "AMDpass009", "HTCsecure1!", "XiaomiXyz22", "ByteDance7XY", "AlibaPa55@", "JDpassXy88", "Meituan777", "BookingXyz90", "ExpediaXyz12", "TripAdv007!", "LenovoPass99", "AsusXyz123", "AcerPass45@", "CanonXYZ789", "NikonPass000", "FujiPass777", "GoProSecure9!", "DeliverXyz777", "GrubHubPa55@", "DoorDashXyz99", "InstacartPass!", "CostcoSecure77", "WalmartXyz99", "TargetP@ss88", "BestBuyXyz123", "HomeDepot789!", "LowesSecure1@", "KrogerPass777", "PublixXyz000", "WalgreensP@ss", "CVSsecureXyz!", "eBayPass567", "ShopifyXYZ88", "EtsyP@ss99!", "ZaraSecure123", "H&MPass99!", "UniqloXyz777", "GapSecureXy88", "NordstromPass1@", "RalphLaurenPassX", "CoachXyz007", "MichaelKorsPass9", "BurberrySecure!", "PradaPass777", "GucciXyz88!", "VersaceSecure99", "FendiXyzP@ss", "ChanelPass999", "DiorXyz123@", "LouisVuittonP@ss", "BMWsecure789!"};
                String[] companiesEmails = {"Apple@gmail.com", "Microsoft@outlook.com", "Google@gmail.com", "Facebook@yahoo.com", "Amazon@amazon.com", "Tesla@hotmail.com", "IBM@ibm.com", "Intel@gmail.com", "Samsung@outlook.com", "Sony@yahoo.com", "Dell@gmail.com", "HP@hotmail.com", "Oracle@oracle.com", "SAP@yahoo.com", "Adobe@gmail.com", "Cisco@outlook.com", "Nvidia@gmail.com", "Qualcomm@yahoo.com", "LG@gmail.com", "Panasonic@hotmail.com", "GeneralElectric@ge.com", "GeneralMotors@gmail.com", "Verizon@verizon.com", "AT&T@hotmail.com", "ExxonMobil@gmail.com", "Chevron@yahoo.com", "Shell@hotmail.com", "BP@gmail.com", "Citigroup@outlook.com", "GoldmanSachs@gmail.com", "MorganStanley@yahoo.com", "AmericanExpress@gmail.com", "Visa@visa.com", "Mastercard@hotmail.com", "PayPal@gmail.com", "Uber@yahoo.com", "Lyft@gmail.com", "Airbnb@outlook.com", "Spotify@gmail.com", "Netflix@yahoo.com", "Twitter@gmail.com", "Snapchat@hotmail.com", "Square@outlook.com", "Salesforce@gmail.com", "Zoom@yahoo.com", "Slack@gmail.com", "Dropbox@hotmail.com", "AMD@gmail.com", "HTC@outlook.com", "Xiaomi@gmail.com", "ByteDance@yahoo.com", "Alibaba@gmail.com", "JD@yahoo.com", "Meituan@gmail.com", "Booking@outlook.com", "Expedia@gmail.com", "TripAdvisor@hotmail.com", "Lenovo@gmail.com", "Asus@yahoo.com", "Acer@gmail.com", "Canon@outlook.com", "Nikon@gmail.com", "Fujifilm@yahoo.com", "GoPro@gmail.com", "Deliveroo@hotmail.com", "Grubhub@gmail.com", "DoorDash@yahoo.com", "Instacart@gmail.com", "Costco@outlook.com", "Walmart@gmail.com", "Target@hotmail.com", "BestBuy@gmail.com", "HomeDepot@yahoo.com", "Lowes@gmail.com", "Kroger@gmail.com", "Publix@outlook.com", "Walgreens@gmail.com", "CVS@yahoo.com", "eBay@gmail.com", "Shopify@hotmail.com", "Etsy@gmail.com", "Zara@outlook.com", "H&M@gmail.com", "Uniqlo@yahoo.com", "Gap@gmail.com", "Nordstrom@hotmail.com", "RalphLauren@gmail.com", "Coach@yahoo.com", "MichaelKors@gmail.com", "Burberry@outlook.com", "Prada@gmail.com", "Gucci@yahoo.com", "Versace@gmail.com", "Fendi@hotmail.com", "Chanel@gmail.com"};
                String[] firstNames = {"John", "Michael", "David", "James", "Robert", "William", "Joseph", "Thomas", "Charles", "Daniel", "Matthew", "Anthony", "Mark", "Donald", "Steven", "Andrew", "Paul", "Joshua", "Kevin", "Brian", "Edward", "Timothy", "Ronald", "Jeffrey", "Jason", "Frank", "Scott", "Gregory", "Raymond", "Benjamin", "Samuel", "Patrick", "Jack", "Alexander", "Tyler", "Aaron", "Jose", "Nicholas", "Ethan", "Gary", "Zachary", "Mason", "Henry", "Douglas", "Walter", "Eugene", "Wayne", "Peter", "Ralph", "Jesse", "Louis", "Johnny", "Craig", "Stanley", "Philip", "Bryan", "Carlos", "Bobby", "Martin", "Harold", "Jackie", "Victor", "Keith", "Jay", "George", "Sam", "Russell", "Billy", "Curtis", "Todd", "Don", "Arthur", "Joe", "Leonard", "Lester", "Kyle", "Fred", "Oscar", "Carl", "Alvin", "Vernon", "Milton", "Darryl", "Gilbert", "Howard", "Charlie", "Warren", "Roy", "Earl", "Russell", "Chester", "Everett", "Hugh", "Clyde", "Gordon", "Rex", "Floyd", "Clarence", "Ernest", "Norman", "Bernard", "Lloyd", "Steve", "Felix", "Lonnie", "Adrian", "Morris", "Willie", "Perry", "Victor"};
                String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin", "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Roberts", "Gomez", "Morris", "Murphy", "Rivera", "Cook", "Rogers", "Gutierrez", "Ortiz", "Morgan", "Cooper", "Reed", "Bailey", "Bell", "Kelly", "Howard", "Ward", "Cox", "Diaz", "Richardson", "Wood", "Watson", "Brooks", "Bennett", "Gray", "James", "Bryant", "Mendoza", "Ruiz", "Foster", "Sanders", "Price", "Hughes", "Woods", "Craig", "Chapman", "Kim", "Jameson", "Mills", "Lane", "Austin", "Fisher", "Graham", "Kelley", "Mills", "Cameron", "Washington", "Butler", "Henderson", "Jordan", "Patterson", "Simmons", "Fletcher", "Williamson", "Gibson", "Hunt", "Carr", "Stewart", "Newton", "Palmer", "Fox", "Stone", "West", "Burns", "Weaver", "Lawrence", "Freeman", "Fowler", "Pearson", "Bradley", "Chapman"};
                String[] customersEmails = {"JohnDoe@gmail.com", "JaneSmith@yahoo.com", "RobertBrown@outlook.com", "MaryJohnson@gmail.com", "WilliamDavis@hotmail.com", "ElizabethMiller@gmail.com", "JamesWilson@yahoo.com", "PatriciaMoore@outlook.com", "DavidTaylor@gmail.com", "JenniferAnderson@yahoo.com", "CharlesThomas@outlook.com", "LindaJackson@gmail.com", "ThomasWhite@yahoo.com", "SarahHarris@outlook.com", "JosephMartin@gmail.com", "KarenLee@yahoo.com", "GaryPerez@outlook.com", "NancyClark@gmail.com", "StevenLewis@yahoo.com", "BettyYoung@outlook.com", "DonaldHall@gmail.com", "SandraAllen@yahoo.com", "PaulScott@outlook.com", "SusanKing@gmail.com", "AndrewGreen@yahoo.com", "DeborahAdams@outlook.com", "JoshuaBaker@gmail.com", "BarbaraNelson@yahoo.com", "JacobCarter@outlook.com", "RebeccaMitchell@gmail.com", "BrianRoberts@yahoo.com", "HelenWalker@outlook.com", "MarkGonzalez@gmail.com", "LauraPerez@yahoo.com", "PatrickEdwards@outlook.com", "EmilyCollins@gmail.com", "GeorgeStewart@yahoo.com", "DorothyMorris@outlook.com", "KevinGomez@gmail.com", "SharonKing@yahoo.com", "StephenHill@outlook.com", "AnnaAdams@gmail.com", "GaryNelson@yahoo.com", "JackDavis@outlook.com", "MelissaBaker@gmail.com", "MatthewGonzalez@yahoo.com", "MeganNelson@outlook.com", "JoshuaHernandez@gmail.com", "LauraJohnson@yahoo.com", "DanielCarter@outlook.com", "SophiaMitchell@gmail.com", "RichardHarris@yahoo.com", "VictoriaWilson@outlook.com", "BenjaminWhite@gmail.com", "OliviaYoung@yahoo.com", "JackieTaylor@outlook.com", "MatthewMartin@gmail.com", "JessicaClark@yahoo.com", "DouglasMiller@outlook.com", "HelenBrown@gmail.com", "ThomasMoore@yahoo.com", "CherylWalker@outlook.com", "SamuelRoberts@gmail.com", "JenniferEdwards@yahoo.com", "StevenNelson@outlook.com", "NatalieAdams@gmail.com", "BrianCarter@yahoo.com", "PatriciaPerez@outlook.com", "JonathanHarris@gmail.com", "LindaBaker@yahoo.com", "RobertWilson@outlook.com", "CynthiaBaker@gmail.com", "DavidMitchell@yahoo.com", "BrianTaylor@outlook.com", "JessicaHernandez@gmail.com", "JosephMoore@yahoo.com", "TimothyJohnson@outlook.com", "SarahStewart@gmail.com", "EdwardDavis@yahoo.com", "StephanieMorris@outlook.com", "JoshuaRoberts@gmail.com", "LisaPerez@yahoo.com", "MichaelHernandez@outlook.com", "KarenBrown@gmail.com", "GaryMartin@yahoo.com", "ElizabethCarter@outlook.com", "JessicaDavis@gmail.com", "ChristopherNelson@yahoo.com", "DavidThomas@outlook.com", "VictoriaWalker@gmail.com", "GaryGonzalez@yahoo.com", "WilliamMitchell@outlook.com", "PatriciaMorris@gmail.com", "AndrewScott@yahoo.com", "RobertHarris@outlook.com", "SamanthaMartin@gmail.com", "MichaelTaylor@yahoo.com", "DeborahWhite@outlook.com", "BrianYoung@gmail.com", "CynthiaWilson@yahoo.com", "DouglasDavis@outlook.com", "SamanthaGreen@gmail.com", "JoshuaWalker@yahoo.com", "RebeccaNelson@outlook.com", "EmilyEdwards@gmail.com", "WilliamCarter@yahoo.com", "LindaHernandez@outlook.com", "AnthonyYoung@gmail.com", "RachelScott@yahoo.com", "MichaelAllen@outlook.com", "ElizabethBrown@gmail.com", "DavidEdwards@yahoo.com", "OliviaWalker@outlook.com", "CharlesMitchell@gmail.com", "BarbaraScott@yahoo.com", "DavidGonzalez@outlook.com", "RichardCarter@gmail.com", "DorothyTaylor@yahoo.com", "ChristopherBrown@outlook.com", "GraceNelson@gmail.com", "TimothyAdams@yahoo.com", "PatriciaMartin@outlook.com", "JosephStewart@gmail.com"};
                String[] passwords = {"passw0rd123", "mysecurepassword1", "strongpass#123", "password@987", "1234mypassword", "qwerty12345", "letmein@2024", "password!45", "admin1234", "1234567890", "supersecure!22", "myp@ssword1", "myp@ss!2024", "strong#secure", "password2019", "secuRE@P@ss", "qwerty123", "p@ssword1234", "letmein123", "g3n3r1cpa$$w0rd", "1234#password", "Pa$sw0rD45", "securE45p@ss", "strongP@ss!!1", "password1@22", "MySecure123", "Qwerty@pass1", "12345Secur3", "Pa$$word345", "Testpassword1", "Yourpassword!2", "secure@123", "C0d3Secure!", "Firstpassword1", "StrongP@ss34", "Pass1234sec", "Re3llySecure", "topsecretpassword", "trustedP@ss", "S@feP@ss34", "securecode#123", "5trong@p@ss", "passw0rd@345", "P@$$word56", "bestpassword01", "passSecure@123", "CodeSecureXy", "S3cur3@S3cr3t", "Code1234S3cur", "Pass!12345", "SecureCode@123", "SafeP@$$123", "qwerty!!098", "TopSecure@9", "SecretPa$$word", "Secured!12", "SafetyPassX1", "Secure12345"};
                String[] couponTitle = {"SpecialOffer", "DiscountDeal", "SaveBig", "HotPromo", "SuperSale",
                        "MegaDiscount", "FlashSale", "BigOffer", "SaveMore", "WinterDeal",
                        "ExclusiveDeal", "Clearance", "SeasonSale", "SummerDiscount", "AmazingDeal",
                        "ShopNow", "BigSavings", "LimitedOffer", "LastChance", "FlashDeal",
                        "NewArrival", "WinterPromo", "FlashDiscount", "BigSave", "QuickDeal",
                        "HotDeal", "SummerPromo", "WeekendSale", "EndOfSeason", "HolidaySpecial",
                        "ClearanceSale", "SummerVibes", "DealAlert", "FlashSaleNow", "EarlyBird",
                        "SuperOffer", "SpecialDiscount", "MegaDeal", "SaveToday", "SeasonalDiscount",
                        "BigOfferNow", "FlashSpecial", "SpringSale", "NewOffer", "MegaSavings",
                        "HugeDiscount", "QuickSave", "ExclusiveOffer", "ShopToday", "DealAlertNow",
                        "PriceDrop", "BlackFridayDeal", "NewYearSale", "WinterSpecial", "HotDealsNow",
                        "SuperSavings", "ChristmasSale", "EarlyDeal", "ShopBig", "FinalOffer",
                        "PromoToday", "HotOffer", "BestDeal", "WinterVibes", "WeekendOffer",
                        "SaveBigNow", "SpringDeal", "LastMinuteOffer", "MassiveDiscount", "HolidaySale",
                        "TopOffer", "EndOfYearPromo", "SummerDeal", "FreshDiscount", "SpringDiscount",
                        "FlashOffer", "WinterSavings", "PromoSpecial", "DealOfTheMonth", "ClearanceNow",
                        "HurryUp", "SaveNow", "LimitedTimeOffer", "HotPromoNow", "BestPromo",
                        "ShopMore", "MegaDiscountNow", "SpecialPromo", "HotSale", "SeasonSavings",
                        "ExclusivePromo", "LimitedStock", "HotItemDeal", "FinalDiscount", "PromoAlert"
                };
                Category[] couponCategories = {Category.FOOD, Category.ELECTRICITY, Category.RESTAURANT, Category.VACATION, Category.FASHION,
                        Category.CINEMA, Category.SPA, Category.TECH, Category.SPORT,
                        Category.FOOD, Category.ELECTRICITY, Category.RESTAURANT, Category.VACATION, Category.FASHION,
                        Category.CINEMA, Category.SPA, Category.TECH, Category.SPORT,
                        Category.FOOD, Category.ELECTRICITY, Category.RESTAURANT, Category.VACATION, Category.FASHION,
                        Category.CINEMA, Category.SPA, Category.TECH, Category.SPORT,
                        Category.FOOD, Category.ELECTRICITY, Category.RESTAURANT, Category.VACATION, Category.FASHION,
                        Category.CINEMA, Category.SPA, Category.TECH, Category.SPORT,
                        Category.FOOD, Category.ELECTRICITY, Category.RESTAURANT, Category.VACATION, Category.FASHION,
                        Category.CINEMA, Category.SPA, Category.TECH, Category.SPORT,
                        Category.FOOD, Category.ELECTRICITY, Category.RESTAURANT, Category.VACATION, Category.FASHION,
                        Category.CINEMA, Category.SPA, Category.TECH, Category.SPORT,
                        Category.FOOD, Category.ELECTRICITY, Category.RESTAURANT, Category.VACATION, Category.FASHION,
                        Category.CINEMA, Category.SPA, Category.TECH, Category.SPORT,
                        Category.FOOD, Category.ELECTRICITY, Category.RESTAURANT, Category.VACATION, Category.FASHION,
                        Category.CINEMA, Category.SPA, Category.TECH, Category.SPORT,
                        Category.FOOD, Category.ELECTRICITY, Category.RESTAURANT, Category.VACATION, Category.FASHION,
                        Category.CINEMA, Category.SPA, Category.TECH, Category.SPORT,
                        Category.FOOD, Category.ELECTRICITY, Category.RESTAURANT, Category.VACATION, Category.FASHION,
                        Category.CINEMA, Category.SPA, Category.TECH, Category.SPORT
                };
                int[] couponAmount = {100, 150, 200, 250, 300, 350, 400, 450, 500, 550,
                        600, 650, 700, 750, 800, 850, 900, 950, 1000, 950,
                        100, 200, 300, 400, 500, 600, 700, 800, 900, 1000,
                        100, 200, 300, 400, 500, 600, 700, 800, 900, 1000,
                        100, 200, 300, 400, 500, 600, 700, 800, 900, 1000,
                        100, 200, 300, 400, 500, 600, 700, 800, 900, 1000,
                        100, 200, 300, 400, 500, 600, 700, 800, 900, 1000,
                        100, 200, 300, 400, 500, 600, 700, 800, 900, 1000,
                        100, 200, 300, 400, 500, 600, 700, 800, 900, 1000,
                        100, 200, 300, 400, 500, 600, 700, 800, 900, 1000
                };
                double[] couponPrice = {150.5, 200.75, 300.99, 400.25, 500.65, 600.35, 700.15, 800.99, 900.85, 1000.45,
                        150.95, 200.25, 300.50, 400.85, 500.15, 600.75, 700.55, 800.95, 900.65, 1000.35,
                        150.75, 200.85, 300.65, 400.95, 500.35, 600.45, 700.75, 800.55, 900.95, 1000.65,
                        150.65, 200.95, 300.75, 400.65, 500.85, 600.25, 700.95, 800.65, 900.75, 1000.95,
                        150.35, 200.55, 300.95, 400.25, 500.65, 600.15, 700.25, 800.45, 900.85, 1000.55,
                        150.85, 200.65, 300.95, 400.75, 500.25, 600.95, 700.65, 800.75, 900.95, 1000.85,
                        150.25, 200.45, 300.65, 400.55, 500.95, 600.25, 700.85, 800.95, 900.65, 1000.75,
                        150.95, 200.35, 300.25, 400.45, 500.55, 600.75, 700.65, 800.25, 900.95, 1000.35,
                        150.55, 200.95, 300.45, 400.35, 500.75, 600.55, 700.35, 800.45, 900.75, 1000.15
                };
                int[] companyId = {1, 3, 5, 7, 9, 2, 4, 6, 8, 10,
                        11, 13, 15, 17, 19, 12, 14, 16, 18, 20,
                        21, 23, 25, 27, 29, 22, 24, 26, 28, 30,
                        31, 33, 35, 37, 39, 32, 34, 36, 38, 40,
                        41, 43, 45, 47, 49, 42, 44, 46, 48, 50,
                        1, 3, 5, 7, 9, 2, 4, 6, 8, 10,
                        11, 13, 15, 17, 19, 12, 14, 16, 18, 20,
                        21, 23, 25, 27, 29, 22, 24, 26, 28, 30,
                        31, 33, 35, 37, 39, 32, 34, 36, 38, 40,
                        41, 43, 45, 47, 49, 42};
                String[] couponStartDate = {"2025-02-17", "2025-02-18", "2025-02-19", "2025-02-20", "2025-02-21",
                        "2025-02-22", "2025-02-23", "2025-02-24", "2025-02-25", "2025-02-26",
                        "2025-02-27", "2025-02-28", "2025-03-01", "2025-03-02", "2025-03-03",
                        "2025-03-04", "2025-03-05", "2025-03-06", "2025-03-07", "2025-03-08",
                        "2025-03-09", "2025-03-10", "2025-03-11", "2025-03-12", "2025-03-13",
                        "2025-03-14", "2025-03-15", "2025-03-16", "2025-03-17", "2025-03-18",
                        "2025-03-19", "2025-03-20", "2025-03-21", "2025-03-22", "2025-03-23",
                        "2025-03-24", "2025-03-25", "2025-03-26", "2025-03-27", "2025-03-28",
                        "2025-03-29", "2025-03-30", "2025-03-31", "2025-04-01", "2025-04-02",
                        "2025-04-03", "2025-04-04", "2025-04-05", "2025-04-06", "2025-04-07",
                        "2025-04-08", "2025-04-09", "2025-04-10", "2025-04-11", "2025-04-12",
                        "2025-04-13", "2025-04-14", "2025-04-15", "2025-04-16", "2025-04-17",
                        "2025-04-18", "2025-04-19", "2025-04-20", "2025-04-21", "2025-04-22",
                        "2025-04-23", "2025-04-24", "2025-04-25", "2025-04-26", "2025-04-27",
                        "2025-04-28", "2025-04-29", "2025-04-30", "2025-05-01", "2025-05-02",
                        "2025-05-03", "2025-05-04", "2025-05-05", "2025-05-06", "2025-05-07",
                        "2025-05-08", "2025-05-09", "2025-05-10", "2025-05-11", "2025-05-12"
                };
                String[] couponEndDate = {"2030-02-17", "2030-02-18", "2030-02-19", "2030-02-20", "2030-02-21",
                        "2030-02-22", "2030-02-23", "2030-02-24", "2030-02-25", "2030-02-26",
                        "2030-02-27", "2030-02-28", "2030-03-01", "2030-03-02", "2030-03-03",
                        "2030-03-04", "2030-03-05", "2030-03-06", "2030-03-07", "2030-03-08",
                        "2030-03-09", "2030-03-10", "2030-03-11", "2030-03-12", "2030-03-13",
                        "2030-03-14", "2030-03-15", "2030-03-16", "2030-03-17", "2030-03-18",
                        "2030-03-19", "2030-03-20", "2030-03-21", "2030-03-22", "2030-03-23",
                        "2030-03-24", "2030-03-25", "2030-03-26", "2030-03-27", "2030-03-28",
                        "2030-03-29", "2030-03-30", "2030-03-31", "2030-04-01", "2030-04-02",
                        "2030-04-03", "2030-04-04", "2030-04-05", "2030-04-06", "2030-04-07",
                        "2030-04-08", "2030-04-09", "2030-04-10", "2030-04-11", "2030-04-12",
                        "2030-04-13", "2030-04-14", "2030-04-15", "2030-04-16", "2030-04-17",
                        "2030-04-18", "2030-04-19", "2030-04-20", "2030-04-21", "2030-04-22",
                        "2030-04-23", "2030-04-24", "2030-04-25", "2030-04-26", "2030-04-27",
                        "2030-04-28", "2030-04-29", "2030-04-30", "2030-05-01", "2030-05-02",
                        "2030-05-03", "2030-05-04", "2030-05-05", "2030-05-06", "2030-05-07",
                        "2030-05-08", "2030-05-09", "2030-05-10", "2030-05-11", "2030-05-12"
                };
                String[] couponDescription = {
                        "Huge discount on select products", "Big savings on electronics", "Exclusive offer for new customers",
                        "Save big on vacation packages", "Get 50% off on fashion items", "Special price for movie tickets",
                        "Relax with spa treatments at a discount", "Tech gadgets at a great price", "Save on your favorite sports gear",
                        "Enjoy a meal at 30% off", "Up to 40% off on your electric bill", "Delicious food for less",
                        "Fantastic movie deals just for you", "Holiday offers for spa lovers", "Tech sales happening now",
                        "New year sale on sports equipment", "Eat out and save big", "Exclusive vacation packages just for you",
                        "Fashion deals for everyone", "Relax at the spa and save", "Tech discounts you can’t miss",
                        "Enjoy a night at the movies", "Up to 50% off your restaurant bill", "Save on luxury vacations",
                        "Shop trendy fashion with a discount", "Enjoy your favorite tech at a discount", "Get active with sport savings",
                        "Enjoy a special food offer today", "Save big on restaurant dining", "Exclusive spa packages available",
                        "Get the best deal on electronics", "Fashion items at reduced prices", "Relax at the spa and unwind",
                        "Tech gadgets at unbeatable prices", "Save on your next sporting event", "Exclusive food deals online",
                        "Movie tickets at discounted rates", "Vacation savings for a limited time", "Fashion sales to brighten your day",
                        "Treat yourself with spa discounts", "Tech gadgets on sale now", "Get ready for big sport deals",
                        "Food discounts for the whole family", "Big savings on electronics", "Up to 30% off at restaurants",
                        "Vacation deals for a getaway", "Save on the latest fashion items", "Get your spa treatment at a discount",
                        "Unbeatable tech deals for you", "Sporting gear at discounted prices", "Amazing food deals just for you",
                        "Catch the latest movie at a discount", "Vacation discounts for everyone", "Up to 40% off on fashion",
                        "Spa savings that you’ll love", "Tech products with amazing discounts", "Sporting events at reduced prices",
                        "Delicious meals at a discount", "Massive savings on electronics", "Exclusive restaurant offers",
                        "Take a vacation and save", "Fashion deals on the hottest items", "Get a spa deal today",
                        "Tech items at discount prices", "Enjoy a sport deal", "Food offers that will make you smile",
                        "Exclusive movie ticket offers", "Vacation packages for every budget", "Fashion deals that will surprise you",
                        "Save on spa treatments", "The best tech gadgets for less", "Sport discounts that you’ll love",
                        "Food deals for your next meal", "Restaurant deals you can’t miss", "Exclusive vacation discounts",
                        "Shop fashion items at a discount", "Pamper yourself with a spa offer", "Tech gadgets you’ve been waiting for",
                        "Great savings on your favorite sports", "Food deals for everyone", "Movie offers that will excite you",
                        "Vacation discounts for dream getaways", "Fashion deals for all ages", "Save on your next spa experience",
                        "Tech offers that are too good to miss", "Get the best sport gear for less", "Save on meals and more",
                        "Exclusive movie promotions", "Last chance vacation deals", "Fashion items at irresistible prices",
                        "Treat yourself to a spa experience", "Tech deals that are unbeatable", "Big sport savings for you"
                };

//            creating Company and customer
                for (int i = 0; i < 50; i++) {
                    adminService.addCompany(new Company(companiesNames[i], companiesEmails[i], companiesPasswords[i]));
                    adminService.addCustomer(new Customer(firstNames[i], lastNames[i], customersEmails[i], passwords[i]));
                }

//            Creating coupons
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                for (int i = 0; i < 50; i++) {
                    Company company = adminService.getOneCompany(i + 1);
                    loginController.login(company.getEmail(), company.getPassword(), "Company");
                    String date1 = couponStartDate[i];
                    String date2 = couponEndDate[i];
                    Date startDate = formatter.parse(date1);
                    Date endDate = formatter.parse(date2);
                    for (int j = 0; j < 7; j++) {
                        companyService.addCoupon(new Coupon(couponCategories[i + j], couponTitle[i + j], couponDescription[i + j], startDate, endDate, couponAmount[i + j], couponPrice[i + j], "", adminService.getOneCompany(companyId[i])));
                    }
                }

                for (int i = 0; i < 50; i++) {  //Connecting for each customer (50 customers):
                    customerService.login(customersEmails[i], passwords[i]);
                    for (int j = 0; j < 30; j++) { //for each customer we are purchasing 30 coupons.
                        customerService.purchaseCoupon(j + 1);
                    }
                }
            }
        } catch (AuthorizationException | NotExistException | AlreadyExistException | ParseException |
                 OutOfStockException | ExpiredDateException e) {
            System.out.println("Warning: Some data wasn't successfully saved in the database, please check class: 'CreateDataInDB'");
            System.out.println(e.getMessage());
        }

    }
}
