package rivera.sanchez.raul.tiendaonline.repositories;

import org.springframework.data.repository.CrudRepository;
import rivera.sanchez.raul.tiendaonline.models.LibroModel;

import java.util.ArrayList;

public interface LibroRepository extends CrudRepository<LibroModel, Long> {
    public abstract ArrayList<LibroModel> findByAutor(String autor);
    public abstract ArrayList<LibroModel> findByTitulo(String titulo);
    public abstract ArrayList<LibroModel> findByEditorial(String editorial);
    public abstract ArrayList<LibroModel> findByIsbn(String isbn);
}
