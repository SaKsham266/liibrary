package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;

public record IssueBookRequest(
        @NotNull Integer bookId,
        @NotNull Integer memberId
) {
}
