package com.company.dto.article;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleRequestDTO {
    @NotNull(message = "title required")
    @Size(max = 225, message = "Title must be between 10 and 225 characters")
    private String title;
    @NotBlank(message = "Field must have some value")
    private String description;
    @NotEmpty(message = "Content qani")
    private String content;
    @NotNull(message = " Attach required")
    private String attachId;
    @NotNull(message = " Region required")
    private Integer regionId;
    @NotNull(message = " Category required")
    private Integer categoryId;
    @NotNull(message = " articleType required")
    private Integer typeId;
}
