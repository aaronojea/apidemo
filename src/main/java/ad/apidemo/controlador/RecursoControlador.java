package ad.apidemo.controlador;

import ad.apidemo.modelo.Categoria;
import ad.apidemo.modelo.Recurso;
import ad.apidemo.repositorio.CategoriaRepositorio;
import ad.apidemo.repositorio.RecrusoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recurso")
public class RecursoControlador {

    @Autowired
    private RecrusoRepositorio recursoRepositorio;
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    //todos los recursos
    @GetMapping
    public List<Recurso> obtenerRecursos() {
        return recursoRepositorio.findAll();
    }

    //obtener un recurso por su id
     @GetMapping("/{id}")
    public Recurso obtenerRecursoPorId(@PathVariable Long id) {
         Optional<Recurso> resultado = recursoRepositorio.findById(id);
         return resultado.orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
     }

     //crear un recurso
    @PostMapping("/categoria/{id}")
    public Recurso crearRecurso(@PathVariable Long id, @RequestBody Recurso recurso) {
        Recurso rec = categoriaRepositorio.findById(id).map(categoria -> {
                    recurso.setCategoria(categoria);
                    return recursoRepositorio.save(recurso);
                }
        ).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        return rec;
    }

    //actualizar un recurso
    @PutMapping("/{id}")
    public Recurso actulizarRecurso(@PathVariable Long id, @RequestBody Recurso recurso) {
        return recursoRepositorio.findById(id).map(recursoTemp -> {
            recursoTemp.setNombre(recurso.getNombre() != null ? recurso.getNombre(): recursoTemp.getNombre());
            recursoTemp.setLatitud(recurso.getLatitud() != 0 ? recurso.getLatitud(): recursoTemp.getLatitud());
            recursoTemp.setLongitud(recurso.getLongitud() != 0 ? recurso.getLongitud(): recursoTemp.getLongitud());
            recursoTemp.setLocalidad(recurso.getLocalidad() != null ? recurso.getLocalidad(): recursoTemp.getLocalidad());
            recursoTemp.setWeb(recurso.getWeb() != null ? recurso.getWeb(): recursoTemp.getWeb());
            return recursoRepositorio.save(recursoTemp);
        }).orElseThrow(() -> new RuntimeException("Recurso no encontrado"));
    }

    //eliminar un recurso
    @DeleteMapping("/{id}")
    public void eliminarRecurso(@PathVariable Long id) {
        recursoRepositorio.deleteById(id);
    }
}
