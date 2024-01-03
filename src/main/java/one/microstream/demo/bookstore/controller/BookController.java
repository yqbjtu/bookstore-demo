package one.microstream.demo.bookstore.controller;


import lombok.extern.slf4j.Slf4j;
import one.microstream.demo.bookstore.BookStoreDemo;
import one.microstream.demo.bookstore.data.*;
import one.microstream.demo.bookstore.ui.views.DialogBookCreate;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.money.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api/book/")
public class BookController {

  @GetMapping("/hello")
  public String hello() {
    return "Hello, World!";
  }

  @GetMapping("/add")
  public String addBook(int count) {
    List<Book> newBooks = new ArrayList<>();
    for (int i=0; i<count; i++) {
      addOneBook(i, "", "", newBooks);
    }
    BookStoreDemo.getInstance().data().books().addAll(newBooks);
    return "add %d books done!".formatted(count);
  }

  private void addOneBook(int i, String number, String prefix, List<Book> list)
  {
    //log.info("addOneBook: {}. {}, {}", i, number, prefix);
    String title = "AEricYang" + i;
    City city = new City("Alexisburgh", new State("stat1", new Country("country", "code")));
    Address address = new Address(  "address" , "address2" , "zipCode", city);
    Genre genre = new Genre("Classic");
    Publisher publisher = new Publisher("publisher1", address);
    Language language = new Language(Locale.ENGLISH);


    MonetaryAmount purchasePrice = BookStoreDemo.money(5);
    MonetaryAmount retailPrice   = BookStoreDemo.retailPrice(purchasePrice);
    //"979-1-925077-14-7",
    String isbn = DialogBookCreate.generateIsbn13();
    Book book = new Book(isbn, title, new Author("author1", address), genre, publisher, language, purchasePrice, retailPrice);
    list.add(book);
  }

  @GetMapping("/count")
  public String bookCount() {
    int count = BookStoreDemo.getInstance().data().books().bookCount();
    return "bootCount:" + count;
  }
}
