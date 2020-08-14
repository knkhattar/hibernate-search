package com.example.hsearch.dao;

import java.util.List;

import com.example.hsearch.model.Book;

public interface BookDao {

   long save(Book book);

   Book get(long id);
   Book search(String queryString);

   List<Book> list();

   void update(long id, Book book);

   void delete(long id);

}
