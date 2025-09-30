package com.tutorial.categorysservice.service;

import com.tutorial.categorysservice.entity.CategorysEntity;
import com.tutorial.categorysservice.repository.CategorysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategorysService {

    @Autowired
    CategorysRepository categorysRepository;

    public ArrayList<CategorysEntity> getAll() {return (ArrayList<CategorysEntity>) categorysRepository.findAll();
    }
    public CategorysEntity getSalaryByID(Long id){
        return categorysRepository.findById(id).get();
    }
    public CategorysEntity getExtraHourRateByID(Long id){
        return categorysRepository.findById(id).get();
    }

    //public Book getBookById(int id) {
     //   return bookRepository.findById(id).orElse(null);
   // }

   // public Book save(Book book) {
    //    Book bookNew = bookRepository.save(book);
   //     return bookNew;
   // }

   // public List<Book> byStudentId(int studentId) {
   //     return bookRepository.findByStudentId(studentId);
  //  }
}
