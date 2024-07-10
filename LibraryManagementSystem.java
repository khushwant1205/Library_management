import java.util.Scanner;

interface BookLocation {
    int getBlockNumber();
}

class BookLocationDetails implements BookLocation {
    private String rowSection;
    private int blockNumber;

    public BookLocationDetails(String rowSection, int blockNumber) {
        this.rowSection = rowSection;
        this.blockNumber = blockNumber;
    }

    public String getRowSection() {
        return rowSection;
    }

    public void setRowSection(String rowSection) {
        this.rowSection = rowSection;
    }

    @Override
    public int getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }
}

class Book extends BookLocationDetails {
    private final String title;
    private final String author;
    private final int year;
    private final String genre;

    public Book(String title, String author, int year, String genre, String rowSection, int blockNumber) {
        super(rowSection, blockNumber);
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
    }
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author + ", year=" + year + ", Genre=" + genre + "]";
    }
}

class Library {
    private final int MAX_BOOKS = 100;
    private final Book[] books;
    private int bookCounter;

    public Library() {
        this.books = new Book[MAX_BOOKS];
        this.bookCounter = 0;
    }

    private String generateRowSection() {
        char rowLetter = (char) ('A' + (bookCounter / 5));
        return Character.toString(rowLetter);
    }

    private int generateBlockNumber() {
        return (bookCounter % 10)  + 1;
    }

    public void addBook(Book book) {
        if (bookCounter < MAX_BOOKS) {
            String rowSection = generateRowSection();
            int blockNumber = generateBlockNumber();
            book.setRowSection(rowSection);
            book.setBlockNumber(blockNumber);
            books[bookCounter++] = book;
        } else {
            System.out.println("Library is full. Cannot add more books.");
        }
    }

    public void removeBook(Book book) {
        for (int i = 0; i < bookCounter; i++) {
            if (books[i].equals(book)) {
                for (int j = i; j < bookCounter - 1; j++) {
                    books[j] = books[j + 1];
                }
                books[--bookCounter] = null;
                break;
            }
        }
    }

    public Book[] searchByTitle(String title) {
        Book[] results = new Book[MAX_BOOKS];
        int count = 0;
        for (int i = 0; i < bookCounter; i++) {
            if (books[i].getTitle().equalsIgnoreCase(title)) {
                results[count++] = books[i];
            }
        }
        return results;
    }

    public Book[] searchByAuthor(String author) {
        Book[] results = new Book[MAX_BOOKS];
        int count = 0;
        for (int i = 0; i < bookCounter; i++) {
            if (books[i].getAuthor().equalsIgnoreCase(author)) {
                results[count++] = books[i];
            }
        }
        return results;
    }

    public Book[] searchByYear(int year) {
        Book[] results = new Book[MAX_BOOKS];
        int count = 0;
        for (int i = 0; i < bookCounter; i++) {
            if (books[i].getYear() == year) {
                results[count++] = books[i];
            }
        }
        return results;
    }

    public Book[] searchByGenre(String genre) {
        Book[] results = new Book[MAX_BOOKS];
        int count = 0;
        for (int i = 0; i < bookCounter; i++) {
            if (books[i].getGenre().equalsIgnoreCase(genre)) {
                results[count++] = books[i];
            }
        }
        return results;
    }

    public void displayBooks() {
        for (int i = 0; i < bookCounter; i++) {
            System.out.println(books[i]);
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("-------- Library Management System --------");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search by Title");
            System.out.println("4. Search by Author");
            System.out.println("5. Search by Year");
            System.out.println("6. Search by Genre");
            System.out.println("7. Display All Books");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter publication year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter the Genre: ");
                    String genre = scanner.nextLine();
                    Book book = new Book(title, author, year, genre, "", 0);
                    library.addBook(book);
                    System.out.println("Book added successfully!");
                }
                case 2 -> {
                    System.out.print("Enter the title of the book to remove: ");
                    String bookTitle = scanner.nextLine();
                    Book[] booksToRemove = library.searchByTitle(bookTitle);
                    if (booksToRemove.length == 0) {
                        System.out.println("No books found with the given title.");
                    } else {
                        System.out.println("Matching books found:");
                        for (int i = 0; i < booksToRemove.length; i++) {
                            if (booksToRemove[i] != null) {
                                System.out.println(i + ". " + booksToRemove[i]);
                            }
                        }
                        System.out.print("Enter the index of the book to remove: ");
                        int index = scanner.nextInt();
                        scanner.nextLine();
                        if (index >= 0 && index < booksToRemove.length && booksToRemove[index] != null) {
                            library.removeBook(booksToRemove[index]);
                            System.out.println("Book removed successfully!");
                        } else {
                            System.out.println("Invalid index!");
                        }
                    }
                }
                case 3 -> {
                    System.out.print("Enter the title to search: ");
                    String searchTitle = scanner.nextLine();
                    Book[] booksByTitle = library.searchByTitle(searchTitle);
                    if (booksByTitle.length == 0) {
                        System.out.println("No books found with the given title.");
                    } else {
                        System.out.println("Matching books found:");
                        for (Book b : booksByTitle) {
                            if (b != null) {
                                System.out.println(b);
                                System.out.println("Row Section: " + b.getRowSection());
                                System.out.println("Block Number: " + b.getBlockNumber());
                            }
                        }
                    }
                }
                case 4 -> {
                    System.out.print("Enter the author to search: ");
                    String searchAuthor = scanner.nextLine();
                    Book[] booksByAuthor = library.searchByAuthor(searchAuthor);
                    if (booksByAuthor.length == 0) {
                        System.out.println("No books found by the given author.");
                    } else {
                        System.out.println("Matching books found:");
                        for (Book b : booksByAuthor) {
                            if (b != null) {
                                System.out.println(b);
                                System.out.println("Row Section: " + b.getRowSection());
                                System.out.println("Block Number: " + b.getBlockNumber());
                            }
                        }
                    }
                }
                case 5 -> {
                    System.out.print("Enter the year to search: ");
                    int searchYear = scanner.nextInt();
                    scanner.nextLine();
                    Book[] booksByYear = library.searchByYear(searchYear);
                    if (booksByYear.length == 0) {
                        System.out.println("No books found published in the given year.");
                    } else {
                        System.out.println("Matching books found:");
                        for (Book b : booksByYear) {
                            if (b != null) {
                                System.out.println(b);
                                System.out.println("Row Section: " + b.getRowSection());
                                System.out.println("Block Number: " + b.getBlockNumber());
                            }
                        }
                    }
                }
                case 6 -> {
                    System.out.print("Enter the Genre of the book to be searched: ");
                    String searchGenre = scanner.nextLine();
                    Book[] booksByGenre = library.searchByGenre(searchGenre);
                    if (booksByGenre.length == 0) {
                        System.out.println("No books found published in the given genre.");
                    } else {
                        System.out.println("Matching books found:");
                        for (Book b : booksByGenre) {
                            if (b != null) {
                                System.out.println(b);
                                System.out.println("Row Section: " + b.getRowSection());
                                System.out.println("Block Number: " + b.getBlockNumber());
                            }
                        }
                    }
                }
                case 7 -> {
                    System.out.println("All books in the library:");
                    library.displayBooks();
                }
                case 8 -> {
                    System.out.println("Exiting Library Management System...");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }

            System.out.println();
        }
    }
}