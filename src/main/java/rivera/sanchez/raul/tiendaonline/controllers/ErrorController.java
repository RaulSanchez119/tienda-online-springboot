package rivera.sanchez.raul.tiendaonline.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        switch (ex.getClass().getSimpleName()) {
            case "ConstraintViolationException" -> model.addAttribute("error", "Hay restricciones que no permiten eso");
            case "HttpMessageNotReadableException" -> model.addAttribute("error", "No se puede leer el contenido");
            case "DataAccessException" -> model.addAttribute("error", "Error al interactuar con la base de datos");
            case "NullPointerException" -> model.addAttribute("error", "El valor es nulo");
            case "MethodArgumentNotValidException" -> model.addAttribute("error", "Fallo al validar los datos");
            case "NoResourceFoundException" -> model.addAttribute("error", "Página no encontrada");
            case "MethodArgumentTypeMismatchException" -> model.addAttribute("error", "El tipo del valor es incorrecto");
            case "RuntimeException" -> model.addAttribute("error", "Excepción al ejecutarse");
            case "IllegalArgumentException" -> model.addAttribute("error", "El valor es incorrecta");
            default -> model.addAttribute("error", ex.getClass().getSimpleName());
        }
        return "error";
    }
}