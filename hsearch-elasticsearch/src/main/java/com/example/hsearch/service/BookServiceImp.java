package com.example.hsearch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hsearch.dao.BookDao;
import com.example.hsearch.model.Book;

@Service
@Transactional(readOnly = true)
public class BookServiceImp implements BookService {

   @Autowired
   private BookDao bookDao;

   @Transactional
   @Override
   public long save(Book book) {
      return bookDao.save(book);
   }

   @Override
   public Book get(long id) {
      return bookDao.get(id);
   }
   
   @Override
   @EventListener(ContextRefreshedEvent.class)
   public void startupTest1() {
	   System.out.println("application started :::");
   }
   @Override
   public List<Book> search(String queryString) {
      return bookDao.search(queryString);
   }
   
   @Override
   public List<Book> list() {
      return bookDao.list();
   }

   @Transactional
   @Override
   public void update(long id, Book book) {
      bookDao.update(id, book);
   }

   @Transactional
   @Override
   public void delete(long id) {
      bookDao.delete(id);
   }

@Override
public void deleteAll() {
	bookDao.deleteAll();
	
}

}
