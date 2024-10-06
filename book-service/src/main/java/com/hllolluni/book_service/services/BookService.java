package com.hllolluni.book_service.services;

import com.hllolluni.book_service.exceptions.BookException;
import com.hllolluni.book_service.transfers.requests.BookDeletionDTO;
import com.hllolluni.book_service.transfers.requests.BookRequest;
import com.hllolluni.book_service.data.entities.Author;
import com.hllolluni.book_service.data.entities.Book;
import com.hllolluni.book_service.data.entities.Category;
import com.hllolluni.book_service.data.entities.Classification;
import com.hllolluni.book_service.data.repositories.AuthorRepository;
import com.hllolluni.book_service.data.repositories.BookRepository;
import com.hllolluni.book_service.data.repositories.CategoryRepository;
import com.hllolluni.book_service.mappers.BookConverter;
import com.hllolluni.book_service.transfers.BookResponse;
import com.hllolluni.book_service.utils.ImageUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
@AllArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;
    private final AuthorService authorService;
    private final CategoryRepository categoryRepository;
    private final ClassificationService classificationService;
    private final AuthorRepository authorRepository;
    private final CategoryService categoryService;


    public BookResponse saveBook(MultipartFile image, BookRequest request) throws Exception {
        Optional<Book> bookOptional = bookRepository.findByTitleAndIsbn(request.getTitle(), request.getIsbn());
        if (bookOptional.isPresent()) {
            throw new BookException("This book is present in the database!");
        }

        Category category = null;
        Classification classification = null;
        try {
            category = categoryService.getCategoryEntityByName(request.getCategory());
            classification = category.getClassification();

            Set<Author> authors = request.getAuthors().stream()
                    .map(author -> {
                        var name = author.split(" ");
                        Author existingAuthor = authorService.getAuthorObjectByName(name[0], name[1]);
                        if (existingAuthor != null) {
                            return existingAuthor;
                        }
                        Author authorToBeSaved = Author.builder()
                                .firstName(name[0])
                                .lastName(name[1])
                                .build();

                        return authorRepository.saveAndFlush(authorToBeSaved);
                    }).collect(Collectors.toSet());


            classification.addAuthors(authors);
            category.addAuthors(authors);
            category.setClassification(classification);
            categoryService.updateCategoryByEntity(category);
            classificationService.updateClassificationByEntity(classification);

            Book book = Book.builder()
                    .title(request.getTitle())
                    .isbn(request.getIsbn())
                    .image(ImageUtils.compressImage(image.getBytes()))
                    .authors(authors)
                    .copiesNumber(request.getCopiesNumber())
                    .description(request.getDescription())
                    .freeToBorrow(true)
                    .numberOfPages(request.getPages())
                    .daysToBeReturn(request.getDaysToBeReturn())
                    .category(category)
                    .classification(classification)
                    .build();

            return bookConverter.convertBookEntityToBookResponse(bookRepository.save(book));
        } catch (Exception e) {
            throw new BookException(e.getMessage());
        }
    }

    public List<BookResponse> saveAll(List<BookRequest> bookList) {
        List<Book> books = bookList.stream()
                .map(bookConverter::convertBookRequestToBook)
                .collect(Collectors.toList());

        return bookRepository.saveAll(books).stream()
                .map(bookConverter::convertBookEntityToBookResponse)
                .collect(Collectors.toList());
    }

    public BookResponse getBookById(Long id) {
        return bookConverter.convertBookEntityToBookResponse(bookRepository.findById(id).orElseThrow(() -> new BookException("The book is not present!")));
    }

    public BookResponse getBookByTitle(String title) throws BookException {
        return bookConverter.convertBookEntityToBookResponse(bookRepository.findBookByTitle(title).orElseThrow(() -> new BookException("The book is not present!")));
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> {
                    BookResponse bookResponse = bookConverter.convertBookEntityToBookResponse(book);
                    try {
                        bookResponse.setImage(ImageUtils.decompressImage(book.getImage()));
                        return bookResponse;
                    } catch (DataFormatException | IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    public void decreaseAvailableCopies(String isbn, int newNumberOfCopies) {
        bookRepository.updateNumberOfCopiesById(isbn, newNumberOfCopies);
    }

    public List<BookResponse> getBooksByAuthorId(Long authorId) {
        List<Book> books = bookRepository.findBooksByAuthorsContaining(authorId);

        return books.stream()
                .map(bookConverter::convertBookEntityToBookResponse)
                .collect(Collectors.toList());
    }

    public void deleteBookById(Long bookId) throws BookException{
        bookRepository.findById(bookId).ifPresentOrElse(bookRepository::delete,
                () -> {throw new BookException("Book not found!");});
    }

    public List<BookResponse> getBooksByCategoryId(Long categoryId) {
        List<Book> books = bookRepository.findBooksByCategoryContaining(categoryId);

        return books.stream()
                .map(bookConverter::convertBookEntityToBookResponse)
                .collect(Collectors.toList());
    }

    public List<BookResponse> getBooksByClassificationId(Long classificationId) {
        List<Book> books = bookRepository.findBooksByClassificationContaining(classificationId);

        return books.stream()
                .map(bookConverter::convertBookEntityToBookResponse)
                .collect(Collectors.toList());
    }

    public void deleteBookByTitleAndIsbn(BookDeletionDTO bookDeletionDTO) throws Exception {
        Optional<Book> book = bookRepository.findByTitleAndIsbn(bookDeletionDTO.title(), bookDeletionDTO.isbn());
        if (book.isEmpty()){
            throw new BookException("This book is not present");
        }

        bookRepository.deleteByTitleAndIsbn(bookDeletionDTO.title(), bookDeletionDTO.isbn());
    }

    public List<BookResponse> getBooksByCategoryName(String categoryName) throws Exception {
        List<Book> books = bookRepository.findBooksByCategoryName(categoryName);

        if (!books.isEmpty()){
            return books.stream()
                     .map(book -> {
                         BookResponse bookResponse = bookConverter.convertBookEntityToBookResponse(book);
                        try {
                            bookResponse.setImage(ImageUtils.decompressImage(book.getImage()));
                            return bookResponse;
                        } catch (DataFormatException | IOException e) {
                            throw new RuntimeException(e);
                        }
                     })
                    .collect(Collectors.toList());
        }

        throw new Exception();
    }
}
