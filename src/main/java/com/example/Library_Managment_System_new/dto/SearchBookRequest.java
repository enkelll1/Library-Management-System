package com.example.Library_Managment_System_new.dto;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;


@Data
public class SearchBookRequest {
    @NotEmpty(message = "no tags provided.")
    private Set<String> search;
}