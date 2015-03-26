package ru.sergeirodionov.shopee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.sergeirodionov.shopee.dao.UserDao;
import ru.sergeirodionov.shopee.model.*;
import ru.sergeirodionov.shopee.service.CategoryService;
import ru.sergeirodionov.shopee.service.ProductService;
import ru.sergeirodionov.shopee.service.StorageService;
import ru.sergeirodionov.shopee.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// DEMO DATA FILL DATABASE


@Controller
public class DemoInitDBController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StorageService storageService;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserService userService;

    // fill DB demo data
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public ModelAndView fillDB() {

        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword(encoder.encode("admin")); // ENCODED !!!
        user1.setEnabled(true);
        UserRole role = new UserRole(user1,"ROLE_ADMIN");
        Set<UserRole> r1 = new HashSet<UserRole>();
        r1.add(role);
        user1.setUserRole(r1);
        userService.saveUser(user1);
        userService.saveUserRoles(r1);

        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword(encoder.encode("user")); // ENCODED !!!
        user2.setEnabled(true);
        UserRole role2 = new UserRole(user2,"ROLE_USER");
        Set<UserRole> r2 = new HashSet<UserRole>();
        r2.add(role2);
        user2.setUserRole(r2);
        userService.saveUser(user2);
        userService.saveUserRoles(r2);

        /* =============================================================== */
        Category catPhones = new Category("Phones");

        Product prod1 = new Product("Samsung Galaxy S3", catPhones);
        Specific prod1spec1 = new Specific("Type", "GSM/3G (samsung)");
        Specific prod1spec2 = new Specific("Memory", "16 Gb (samsung)");
        Specific prod1spec3 = new Specific("Color", "White");
        Specific prod1spec4 = new Specific("Diagonal", "4,7");

        prod1.setSpecifics(new HashSet<Specific>(Arrays.asList(prod1spec1, prod1spec2, prod1spec3, prod1spec4)));
        productService.saveProduct(prod1);

        Product prod2 = new Product("iPhone 5S", catPhones);
        Specific prod2spec1 = new Specific("Type", "GSM/LTE (iphone)");
        Specific prod2spec2 = new Specific("Memory", "32 Gb (iphone)");
        prod2.setSpecifics(new HashSet<Specific>(Arrays.asList(prod2spec1, prod2spec2)));
        productService.saveProduct(prod2);

        storageService.saveStorage(new Storage(prod1, 10, 20100.0));
        storageService.saveStorage(new Storage(prod2, 5, 40200.0));
        /* =============================================================== */

        Category catSoft = new Category("Software");

        Product prod3 = new Product("MS Windows 10", catSoft);
        Specific prod3spec1 = new Specific("Language", "Russian");
        Specific prod3spec2 = new Specific("Version", "64 bit");
        prod3.setSpecifics(new HashSet<Specific>(Arrays.asList(prod3spec1, prod3spec2)));
        productService.saveProduct(prod3);

        Product prod4 = new Product("Eset Smart Security 8", catSoft);
        Specific prod4spec1 = new Specific("Description", "Antivirus software");
        Specific prod4spec2 = new Specific("Extra", "1 license for 3 PC");
        prod4.setSpecifics(new HashSet<Specific>(Arrays.asList(prod4spec1, prod4spec2)));
        productService.saveProduct(prod4);

        storageService.saveStorage(new Storage(prod3, 5, 10532.0));
        storageService.saveStorage(new Storage(prod4, 2, 1520.0));

        /* =============================================================== */
        Category catLight = new Category("Home light");

        Product prod5 = new Product("Light bulb 10W LED", catLight);
        Specific prod5spec1 = new Specific("Console", "E27");
        Specific prod5spec2 = new Specific("Glass", "matte");
        prod5.setSpecifics(new HashSet<Specific>(Arrays.asList(prod5spec1, prod5spec2)));
        productService.saveProduct(prod5);

        storageService.saveStorage(new Storage(prod5, 15, 332.0));
        /* =============================================================== */
        Category catBook = new Category("Book");
        Product prod6 = new Product("Java for proff", catBook);
        Specific prod6spec1 = new Specific("Language", "Russian");
        Specific prod6spec2 = new Specific("Author", "John Doe");
        prod6.setSpecifics(new HashSet<Specific>(Arrays.asList(prod6spec1, prod6spec2)));
        productService.saveProduct(prod6);

        Product prod7 = new Product("jQuery for me", catBook);
        Specific prod7spec1 = new Specific("Description", "Manual jQuery framework");
        Specific prod7spec2 = new Specific("Year", "2015");
        prod7.setSpecifics(new HashSet<Specific>(Arrays.asList(prod7spec1,prod7spec2)));
        productService.saveProduct(prod7);

        storageService.saveStorage(new Storage(prod6, 2, 1532.0));
        storageService.saveStorage(new Storage(prod7, 21, 1020.0));


        return new ModelAndView("redirect:/");
    }

}
