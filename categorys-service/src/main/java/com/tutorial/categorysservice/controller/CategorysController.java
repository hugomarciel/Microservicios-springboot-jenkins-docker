package com.tutorial.categorysservice.controller;

import com.tutorial.categorysservice.entity.CategorysEntity;
//import com.tutorial.categoryservice.entity.CategorysEntity;
import com.tutorial.categorysservice.service.CategorysService;
//import com.tutorial.categoryservice.service.CategorysService;
//import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorys")
public class CategorysController {

    @Autowired
    CategorysService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<CategorysEntity>> getAll() {
        List<CategorysEntity> categorys = categoryService.getAll();
        if(categorys.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(categorys);
    }
    @GetMapping("/getsalary/{id}")
    public ResponseEntity<Integer> getSalaryByID(@PathVariable Long id) {
        CategorysEntity category = categoryService.getSalaryByID(id);
        return ResponseEntity.ok(category.getSalary());
    }

    @GetMapping("/getextrahourrate/{id}")
    public ResponseEntity<Integer> getExtraHourRateByID(@PathVariable Long id) {
        CategorysEntity category = categoryService.getExtraHourRateByID(id);
        return ResponseEntity.ok(category.getExtraHourRate());
    }
    //@GetMapping("/{id}")
   // public ResponseEntity<Book> getById(@PathVariable("id") int id) {
       // Book book = bookService.getBookById(id);
       // if(book == null)
       //     return ResponseEntity.notFound().build();
       // return ResponseEntity.ok(book);
   // }

   // @PostMapping()
   // public ResponseEntity<Book> save(@RequestBody Book book) {
    //    Book bookNew = bookService.save(book);
    //    return ResponseEntity.ok(bookNew);
   // }

   // @GetMapping("/bystudent/{studentId}")
   // public ResponseEntity<List<Book>> getByStudentId(@PathVariable("studentId") int studentId) {
    //    List<Book> books = bookService.byStudentId(studentId);
     //   return ResponseEntity.ok(books);
   // }

}
