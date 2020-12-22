package com.example.hsearch.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.hsearch.model.Book;

@Repository
public class BookDaoImp implements BookDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public long save(Book book) {
      sessionFactory.getCurrentSession().save(book);
      return book.getId();
   }

   @Override
   public Book get(long id) {
      return sessionFactory.getCurrentSession().get(Book.class, id);
   }
   
   @Override
   public List<Book> search(String queryString) {
	   Session session = sessionFactory.getCurrentSession();
	   
		FullTextSession fullTextSession = Search.getFullTextSession(session);
//		try {
//			fullTextSession.createIndexer().startAndWait();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Book.class).get();

		// Create lucene query
		// Set indexed field
		org.apache.lucene.search.Query lucenceQuery = qb.keyword().onFields("title", "author").matching(queryString)
				.createQuery();

		org.apache.lucene.search.Query fuzzyQuery = qb.keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(0).
				onFields("title", "author").matching(queryString).createQuery();

		@SuppressWarnings("unchecked")
		Query<Book> query = fullTextSession.createFullTextQuery(fuzzyQuery, Book.class);
		List<Book> books = query.getResultList();
		System.out.println("books.size():::"+books.size());
	   return books;
   }

   @Override
   public List<Book> list() {
      Session session = sessionFactory.getCurrentSession();
      CriteriaBuilder cb = session.getCriteriaBuilder();
      CriteriaQuery<Book> cq = cb.createQuery(Book.class);
      Root<Book> root = cq.from(Book.class);
      cq.select(root);
      Query<Book> query = session.createQuery(cq);
      return query.getResultList();
   }

   @Override
   public void update(long id, Book book) {
      Session session = sessionFactory.getCurrentSession();
      Book book2 = session.byId(Book.class).load(id);
      book2.setTitle(book.getTitle());
      book2.setAuthor(book.getAuthor());
      session.flush();
   }

   @Override
   public void delete(long id) {
      Session session = sessionFactory.getCurrentSession();
      Book book = session.byId(Book.class).load(id);
      session.delete(book);
   }

   @Override
   public void deleteAll() {
      Session session = sessionFactory.getCurrentSession();
      String sql = "DELETE FROM Book";
      Query query = session.createQuery(sql);
       int row = query.executeUpdate();
       if (row == 0)
           System.out.println("Didn't delete any row!");
       else
           System.out.println("Deleted row" + row);
   }
   
}
