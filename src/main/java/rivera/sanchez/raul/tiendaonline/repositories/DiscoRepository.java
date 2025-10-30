package rivera.sanchez.raul.tiendaonline.repositories;

import org.springframework.data.repository.CrudRepository;
import rivera.sanchez.raul.tiendaonline.models.DiscoModel;

import java.util.ArrayList;

public interface DiscoRepository extends CrudRepository<DiscoModel, Long> {
    public abstract ArrayList<DiscoModel> findByTitulo(String titulo);
    public abstract ArrayList<DiscoModel> findByPistas(int pistas);
    public abstract ArrayList<DiscoModel> findByAutor(String autor);
    public abstract ArrayList<DiscoModel> findByAnyo(int anyo);
}
