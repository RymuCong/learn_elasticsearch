//package com.arius.demo.controller;
//
//import com.arius.demo.entity.Category;
//import com.arius.demo.entity.Product;
//import com.arius.demo.repository.CategoryRepository;
//import com.arius.demo.repository.EProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/category")
//public class CategoryController {
//    private final CategoryRepository categoryRepo;
//    private final EProductRepository productRepo;
//
//    @Autowired
//    public CategoryController(CategoryRepository categoryRepo, EProductRepository productRepo) {
//        this.categoryRepo = categoryRepo;
//        this.productRepo = productRepo;
//    }
//
//    @GetMapping("")
//    public String getAll (Model model, Pageable pageable)
//    {
//        // lấy tất cả nhãn hàng theo pageable
//        Page<Category> categories = categoryRepo.findAll(pageable);
//
//        String page = "categories_list";
//
//        model.addAttribute("totalPage", categories.getTotalPages());
//        model.addAttribute("currentPage", categories.getNumber());
//        model.addAttribute("categories",categories.toList());
//        model.addAttribute("page",page);
//        return "home_index";
//    }
//
//    @GetMapping("/addForm")
//    public String showFormForAdd (Model theModel)
//    {
//        // create model attribute to the bind form data
//        Category theCategory = new Category();
//
//        theModel.addAttribute("page","category-add");
//
//        theModel.addAttribute("category", theCategory);
//
//        return "home_index";
//    }
//
//    @PostMapping("/save")
//    public String save(@ModelAttribute Category category)
//    {
//        // save the category
//        categoryRepo.save(category);
//        return "redirect:/category";
//    }
//
//    @GetMapping("edit/{id}")
//    public String edit (@PathVariable Integer id, Model model)
//    {
//        Optional <Category> editCategory = categoryRepo.findById(id);
//
//        if (editCategory.isEmpty())
//            System.out.println("Not found product id: " + id);
//
//        Category theCategory = editCategory.get();
//
//        model.addAttribute("page", "category-edit");
//
//        model.addAttribute("category", theCategory);
//
//        return "home_index";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable Integer id)
//    {
//        Optional<Category> tempCategory = categoryRepo.findById(id);
//        if (tempCategory.isEmpty())
//        {
//            System.out.println("Not found the id :" + id);
//        }
//        else
//        {
//            List<Product> products = productRepo.findByCategoryId(id);
//            // Set the category of these products to null
//            for (Product product : products) {
//                product.setCategory(null);
//                productRepo.save(product);
//            }
//            // Delete the category
//            categoryRepo.deleteById(id);
//        }
//
//        return "redirect:/category";
//    }
//
//}
