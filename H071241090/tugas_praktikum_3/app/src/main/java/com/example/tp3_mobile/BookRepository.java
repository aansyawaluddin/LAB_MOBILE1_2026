package com.example.tp3_mobile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class BookRepository {
    private static BookRepository instance;
    private final List<Book> books;

    private BookRepository() {
        books = new ArrayList<>();
        initializeDummyData();
    }

    public static synchronized BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    private String getDrawableUri(String name) {
        return "android.resource://com.example.tp3_mobile/drawable/" + name;
    }

    private void initializeDummyData() {
        books.add(new Book(UUID.randomUUID().toString(), "The Great Gatsby", "F. Scott Fitzgerald", 1925, "A story of wealth and love.", getDrawableUri("great_gatsby"), false, 4.5f, "Classic"));
        books.add(new Book(UUID.randomUUID().toString(), "1984", "George Orwell", 1949, "Dystopian future.", getDrawableUri("book_1984"), true, 4.8f, "Sci-Fi"));
        books.add(new Book(UUID.randomUUID().toString(), "To Kill a Mockingbird", "Harper Lee", 1960, "Racial injustice in the South.", getDrawableUri("mockingbird"), false, 4.9f, "Drama"));
        books.add(new Book(UUID.randomUUID().toString(), "The Hobbit", "J.R.R. Tolkien", 1937, "Bilbo's adventure.", getDrawableUri("the_hobbit"), true, 4.7f, "Fantasy"));
        books.add(new Book(UUID.randomUUID().toString(), "Pride and Prejudice", "Jane Austen", 1813, "Romantic tensions.", getDrawableUri("pride_prejudice"), false, 4.6f, "Romance"));
        books.add(new Book(UUID.randomUUID().toString(), "The Catcher in the Rye", "J.D. Salinger", 1951, "Teenage angst.", getDrawableUri("catcher_rye"), false, 4.0f, "Classic"));
        books.add(new Book(UUID.randomUUID().toString(), "Fahrenheit 451", "Ray Bradbury", 1953, "Burning books.", getDrawableUri("fahrenheit_451"), false, 4.3f, "Sci-Fi"));
        books.add(new Book(UUID.randomUUID().toString(), "Moby Dick", "Herman Melville", 1851, "Whaling obsession.", getDrawableUri("moby_dick"), false, 3.9f, "Adventure"));
        books.add(new Book(UUID.randomUUID().toString(), "War and Peace", "Leo Tolstoy", 1869, "Russian society during wars.", getDrawableUri("war_peace"), false, 4.2f, "Historical"));
        books.add(new Book(UUID.randomUUID().toString(), "Ulysses", "James Joyce", 1922, "Modernist masterpiece.", getDrawableUri("ulysses"), false, 3.5f, "Classic"));
        books.add(new Book(UUID.randomUUID().toString(), "The Odyssey", "Homer", -800, "Epic journey.", getDrawableUri("the_odyssey"), false, 4.8f, "Mythology"));
        books.add(new Book(UUID.randomUUID().toString(), "Crime and Punishment", "Fyodor Dostoevsky", 1866, "Moral dilemmas.", getDrawableUri("crime_punishment"), false, 4.7f, "Psychological"));
        books.add(new Book(UUID.randomUUID().toString(), "Brave New World", "Aldous Huxley", 1932, "Engineered society.", getDrawableUri("brave_new_world"), false, 4.4f, "Sci-Fi"));
        books.add(new Book(UUID.randomUUID().toString(), "The Divine Comedy", "Dante Alighieri", 1320, "Journey through Hell, Purgatory, and Paradise.", getDrawableUri("divine_comedy"), false, 4.9f, "Epic"));
        books.add(new Book(UUID.randomUUID().toString(), "Frankenstein", "Mary Shelley", 1818, "Creation and destruction.", getDrawableUri("frankenstein"), false, 4.5f, "Horror"));
    }

    public List<Book> getAllBooks() {
        List<Book> sortedBooks = new ArrayList<>(books);
        Collections.sort(sortedBooks, (b1, b2) -> Integer.compare(b2.getYear(), b1.getYear()));
        return sortedBooks;
    }

    public List<Book> getFavoriteBooks() {
        List<Book> favorites = new ArrayList<>();
        for (Book book : books) {
            if (book.isFavorite()) {
                favorites.add(book);
            }
        }
        return favorites;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(updatedBook.getId())) {
                books.set(i, updatedBook);
                break;
            }
        }
    }
}
