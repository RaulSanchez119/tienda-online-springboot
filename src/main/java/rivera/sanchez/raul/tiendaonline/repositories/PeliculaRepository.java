package rivera.sanchez.raul.tiendaonline.repositories;

import org.springframework.data.repository.CrudRepository;
import rivera.sanchez.raul.tiendaonline.enums.Formato;
import rivera.sanchez.raul.tiendaonline.models.PeliculaModel;

import java.util.ArrayList;

public interface PeliculaRepository extends CrudRepository<PeliculaModel, Long> {
    public abstract ArrayList<PeliculaModel> findByTitulo(String titulo);
    public abstract ArrayList<PeliculaModel> findByFormato(Formato formato);
    public abstract ArrayList<PeliculaModel> findByAnyo(int anyo);
    public abstract ArrayList<PeliculaModel> findByDirector(String director);
}
