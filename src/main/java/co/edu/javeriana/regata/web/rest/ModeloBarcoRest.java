package co.edu.javeriana.regata.web.rest;

import co.edu.javeriana.regata.domain.ModeloBarco;
import co.edu.javeriana.regata.repository.ModeloBarcoRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/modelos")
@CrossOrigin(origins = "http://localhost:4200")
public class ModeloBarcoRest {

    private final ModeloBarcoRepository repo;

    public ModeloBarcoRest(ModeloBarcoRepository repo) {
        this.repo = repo;
    }

    // ================= VALIDACIÓN MANUAL =================

    private void validarAdmin(UserDetails userDetails) {
        boolean esAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!esAdmin) {
            throw new RuntimeException("Acceso denegado. Se requiere rol ADMIN.");
        }
    }

    // ================= LECTURA =================

    @GetMapping
    public List<ModeloBarco> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ModeloBarco get(@PathVariable Long id) {
        return repo.findById(id).orElseThrow();
    }

    // ================= CREAR (SOLO ADMIN) =================

    @PostMapping
    public ModeloBarco crear(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestBody ModeloBarco m) {

        validarAdmin(userDetails);
        return repo.save(m);
    }

    // ================= ACTUALIZAR (SOLO ADMIN) =================

    @PutMapping("/{id}")
    public ModeloBarco actualizar(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable Long id,
                                  @RequestBody ModeloBarco m) {

        validarAdmin(userDetails);

        ModeloBarco existente = repo.findById(id).orElseThrow();
        existente.setNombre(m.getNombre());
        existente.setColorHex(m.getColorHex());
        return repo.save(existente);
    }

    // ================= ELIMINAR (SOLO ADMIN) =================

    @DeleteMapping("/{id}")
    public void eliminar(@AuthenticationPrincipal UserDetails userDetails,
                         @PathVariable Long id) {

        validarAdmin(userDetails);
        repo.deleteById(id);
    }
}
