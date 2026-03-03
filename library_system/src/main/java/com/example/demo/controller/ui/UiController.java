package com.example.demo.controller.ui;

import com.example.demo.dto.BookForm;
import com.example.demo.exception.LibraryException;
import com.example.demo.model.Book;
import com.example.demo.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UiController {

    private final LibraryService libraryService;

    public UiController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/ui/dashboard";
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/ui/dashboard";
        }
        return "login";
    }

    @GetMapping("/ui/dashboard")
    public String dashboard(Model model) {
        long totalBooks = libraryService.getTotalBooks();
        long issuedBooks = libraryService.getIssuedBooksCount();
        long availableBooks = libraryService.getAvailableBooksCount();

        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("totalMembers", libraryService.getTotalMembers());
        model.addAttribute("issuedBooks", issuedBooks);
        model.addAttribute("availableBooks", availableBooks);
        return "dashboard";
    }

    @GetMapping("/ui/books")
    public String books(Model model) {
        model.addAttribute("books", libraryService.getAllBooks());
        return "books/list";
    }

    @GetMapping("/ui/books/new")
    public String createBookForm(Model model) {
        model.addAttribute("bookForm", new BookForm());
        model.addAttribute("pageTitle", "Add Book");
        model.addAttribute("submitText", "Save Book");
        model.addAttribute("formAction", "/ui/books");
        return "books/form";
    }

    @PostMapping("/ui/books")
    public String createBook(@Valid @ModelAttribute("bookForm") BookForm bookForm,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Add Book");
            model.addAttribute("submitText", "Save Book");
            model.addAttribute("formAction", "/ui/books");
            return "books/form";
        }
        libraryService.addBook(toBook(bookForm));
        redirectAttributes.addFlashAttribute("successMessage", "Book added successfully.");
        return "redirect:/ui/books";
    }

    @GetMapping("/ui/books/{id}/edit")
    public String editBookForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            Book book = libraryService.getBookById(id);
            BookForm bookForm = new BookForm();
            bookForm.setTitle(book.getTitle());
            bookForm.setAuthor(book.getAuthor());
            bookForm.setPublisher(book.getPublisher());
            model.addAttribute("bookForm", bookForm);
            model.addAttribute("pageTitle", "Edit Book");
            model.addAttribute("submitText", "Update Book");
            model.addAttribute("formAction", "/ui/books/" + id);
            return "books/form";
        } catch (LibraryException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
            return "redirect:/ui/books";
        }
    }

    @PostMapping("/ui/books/{id}")
    public String updateBook(@PathVariable Integer id,
                             @Valid @ModelAttribute("bookForm") BookForm bookForm,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Edit Book");
            model.addAttribute("submitText", "Update Book");
            model.addAttribute("formAction", "/ui/books/" + id);
            return "books/form";
        }
        try {
            libraryService.updateBook(id, toBook(bookForm));
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully.");
        } catch (LibraryException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/ui/books";
    }

    @PostMapping("/ui/books/{id}/delete")
    public String deleteBook(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            libraryService.deleteBook(id);
            redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully.");
        } catch (LibraryException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/ui/books";
    }

    @GetMapping("/ui/members")
    public String members(Model model) {
        model.addAttribute("members", libraryService.getAllMembers());
        return "members/list";
    }

    private Book toBook(BookForm form) {
        Book book = new Book();
        book.setTitle(form.getTitle());
        book.setAuthor(form.getAuthor());
        book.setPublisher(form.getPublisher());
        return book;
    }
}
