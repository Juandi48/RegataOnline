package co.edu.javeriana.regata.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import co.edu.javeriana.regata.domain.Barco;
import co.edu.javeriana.regata.domain.ModeloBarco;
import co.edu.javeriana.regata.domain.Jugador;
import co.edu.javeriana.regata.repository.BarcoRepository;
import co.edu.javeriana.regata.repository.ModeloBarcoRepository;
import co.edu.javeriana.regata.repository.JugadorRepository;

@Controller
@RequestMapping("/barcos")
public class BarcoController {

    private final BarcoRepository repo;
    private final ModeloBarcoRepository modeloRepo;
    private final JugadorRepository jugadorRepo;

    public BarcoController(BarcoRepository repo, ModeloBarcoRepository modeloRepo, JugadorRepository jugadorRepo){
        this.repo = repo; this.modeloRepo = modeloRepo; this.jugadorRepo = jugadorRepo;
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("items", repo.findAll());
        return "barco-list";
    }

    @GetMapping("/nuevo")
    public String createForm(Model model){
        model.addAttribute("barco", new Barco());
        model.addAttribute("modelos", modeloRepo.findAll());
        model.addAttribute("jugadores", jugadorRepo.findAll());
        return "barco-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Barco barco, BindingResult br,
                         @RequestParam Long modeloId, @RequestParam Long jugadorId,
                         Model model){
        if(br.hasErrors()) {
            model.addAttribute("modelos", modeloRepo.findAll());
            model.addAttribute("jugadores", jugadorRepo.findAll());
            return "barco-form";
        }
        ModeloBarco modelo = modeloRepo.findById(modeloId).orElseThrow();
        Jugador jugador = jugadorRepo.findById(jugadorId).orElseThrow();
        barco.setModelo(modelo);
        barco.setJugador(jugador);
        repo.save(barco);
        return "redirect:/barcos";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model){
        model.addAttribute("barco", repo.findById(id).orElseThrow());
        return "barco-view";
    }

    @GetMapping("/{id}/editar")
    public String editForm(@PathVariable Long id, Model model){
        Barco barco = repo.findById(id).orElseThrow();
        model.addAttribute("barco", barco);
        model.addAttribute("modelos", modeloRepo.findAll());
        model.addAttribute("jugadores", jugadorRepo.findAll());
        return "barco-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Barco barco, BindingResult br,
                         @RequestParam Long modeloId, @RequestParam Long jugadorId,
                         Model model){
        if(br.hasErrors()) {
            model.addAttribute("modelos", modeloRepo.findAll());
            model.addAttribute("jugadores", jugadorRepo.findAll());
            return "barco-form";
        }
        barco.setId(id);
        barco.setModelo(modeloRepo.findById(modeloId).orElseThrow());
        barco.setJugador(jugadorRepo.findById(jugadorId).orElseThrow());
        repo.save(barco);
        return "redirect:/barcos";
    }

    @PostMapping("/{id}/borrar")
    public String delete(@PathVariable Long id){
        repo.deleteById(id);
        return "redirect:/barcos";
    }
}
