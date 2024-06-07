package med.voll.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class TratadorDeErrores {


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<DatosErrorValidation> errores = e.getFieldErrors().stream()
                .map(DatosErrorValidation::new)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ValidacionDeIntegridad.class)
    public ResponseEntity errorHandlerValidacionesIntegridad(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

   /* @ExceptionHandler(HttpMessageNotReadableException)
    public ResponseEntity defaulerror(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }*/

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        // Devuelve una respuesta con el mensaje de error personalizado
        String errorMessage = "Hubo un error al analizar el JSON. Por favor, verifique el formato y los datos proporcionados.";
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    private record DatosErrorValidation(
            String campo,
            String error) {
        public DatosErrorValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }




}//cierre clase
