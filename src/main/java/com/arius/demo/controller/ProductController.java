//package com.arius.demo.controller;
//
//import com.arius.demo.dto.EProduct;
//import com.arius.demo.entity.Product;
//import com.arius.demo.repository.CategoryRepository;
//import com.arius.demo.service.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/product")
//public class ProductController {
//    private final ProductService productService;
//
//    private final CategoryRepository categoryRepo;
//
//    @Autowired
//    public ProductController(ProductService productService, CategoryRepository categoryRepo) {
//        this.productService = productService;
//        this.categoryRepo = categoryRepo;
//    }
//
//    @GetMapping("")
//    public String getAll(Model theModel, Pageable pageable) {
//        // get all products config by pageable
//        List<EProduct> products = productService.findAll();
//
//        String page = "product_list";
//
////        theModel.addAttribute("totalPage", products.getTotalPages());
////        theModel.addAttribute("currentPage", products.getNumber());
//        theModel.addAttribute("products", products);
//        theModel.addAttribute("page", page);
//        return "home_index";
//    }
//
//    @GetMapping("delete/{id}")
//    public String delete(@PathVariable Integer id) {
//        Optional<EProduct> tempProduct = Optional.ofNullable(productService.findById(id));
//        if (tempProduct.isEmpty()) {
//            System.out.println("Not found the id :" + id);
//        } else
//            productService.deleteById(id);
//        return "redirect:/product";
//    }
//
//    @GetMapping("addForm")
//    public String showFormForAdd (Model theModel)
//    {
//        // create model attribute to the bind form data
//        Product theProduct = new Product();
//
//        theModel.addAttribute("page","product-add");
//
//        theModel.addAttribute("product", theProduct);
//
//        theModel.addAttribute("categories",  categoryRepo.findAll());
//
//        return "home_index";
//    }
//
//    @PostMapping("save")
//    public String save(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile file) {
//        productService.save(product, file);
//        return "redirect:/product";
//    }
//
//    @GetMapping("edit/{id}")
//    public String edit (@PathVariable Integer id, Model model)
//    {
//        Optional<EProduct> editProduct = Optional.ofNullable(productService.findById(id));
//
//        if (editProduct.isEmpty())
//            System.out.println("Not found product id: " + id);
//
//        EProduct product = editProduct.get();
//
//        model.addAttribute("page", "product-edit");
//
//        model.addAttribute("product", product);
//
//        model.addAttribute("categories",  categoryRepo.findAll());
//
//        return "home_index";
//    }
//}
