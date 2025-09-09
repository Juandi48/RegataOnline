package co.edu.javeriana.regata.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.repository.JugadorRepository;

@Controller
@RequestMapping("/jugadores")
public class JugadorController {

    private final JugadorRepository repo;
    public JugadorController(JugadorRepository repo){ this.repo = repo; }

    @GetMapping
    public String list(Model model){
        model.addAttribute("items", repo.findAll());
        return "jugador-list";
    }

    @GetMapping("/nuevo")
    public String createForm(Model model){
        model.addAttribute("jugador", new Jugador());
        return "jugador-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Jugador jugador, BindingResult br){
        if(br.hasErrors()) return "jugador-form";
        repo.save(jugador);
        return "redirect:/jugadores";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model){
        model.addAttribute("jugador", repo.findById(id).orElseThrow());
        return "jugador-view";
    }

    @GetMapping("/{id}/editar")
    public String editForm(@PathVariable Long id, Model model){
        model.addAttribute("jugador", repo.findById(id).orElseThrow());
        return "jugador-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Jugador jugador, BindingResult br){
        if(br.hasErrors()) return "jugador-form";
        jugador.setId(id);
        repo.save(jugador);
        return "redirect:/jugadores";
    }

    @PostMapping("/{id}/borrar")
    public String delete(@PathVariable Long id){
        repo.deleteById(id);
        return "redirect:/jugadores";
    }
}
