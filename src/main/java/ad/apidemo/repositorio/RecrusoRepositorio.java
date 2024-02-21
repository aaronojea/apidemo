package ad.apidemo.repositorio;

import ad.apidemo.modelo.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecrusoRepositorio extends JpaRepository<Recurso, Long> {


}
