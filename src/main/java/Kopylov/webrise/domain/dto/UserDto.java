package Kopylov.webrise.domain.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDto(
        @Size(min = 2, max = 100, message = "Имя пользователя должно содержать от 2 до 100 символов.")
        @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Некорректное имя пользователя. Используйте только буквы и пробелы")
        String name) {
}
