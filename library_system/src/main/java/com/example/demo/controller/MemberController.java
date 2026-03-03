package com.example.demo.controller;

import com.example.demo.model.Member;
import com.example.demo.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final LibraryService libraryService;

    public MemberController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // POST /api/members  → replaces case 2: registerMember()
    @PostMapping
    public ResponseEntity<Member> registerMember(@RequestBody Member member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryService.registerMember(member));
    }

    // GET /api/members
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(libraryService.getAllMembers());
    }

    // GET /api/members/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Integer id) {
        return ResponseEntity.ok(libraryService.getMemberById(id));
    }
}




