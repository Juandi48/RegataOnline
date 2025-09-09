package co.edu.javeriana.regata.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import co.edu.javeriana.regata.domain.ModeloBarco;
import co.edu.javeriana.regata.repository.ModeloBarcoRepository;

@Controller
@RequestMapping("/modelos")
public class ModeloBarcoController {

    private final ModeloBarcoRepository repo;
    public ModeloBarcoController(ModeloBarcoRepository repo){ this.repo = repo; }

    @GetMapping
    public String list(Model model){
        model.addAttribute("items", repo.findAll());
        return "modelo-list";
    }

    @GetMapping("/nuevo")
    public String createForm(Model model){
        model.addAttribute("modeloBarco", new ModeloBarco());
        return "modelo-form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute ModeloBarco modeloBarco, BindingResult br){
        if(br.hasErrors()) return "modelo-form";
        repo.save(modeloBarco);
        return "redirect:/modelos";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model){
        model.addAttribute("modeloBarco", repo.findById(id).orElseThrow());
        return "modelo-view";
    }

    @GetMapping("/{id}/editar")
    public String editForm(@PathVariable Long id, Model model){
        model.addAttribute("modeloBarco", repo.findById(id).orElseThrow());
        return "modelo-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute ModeloBarco modeloBarco, BindingResult br){
        if(br.hasErrors()) return "modelo-form";
        modeloBarco.setId(id);
        repo.save(modeloBarco);
        return "redirect:/modelos";
    }

    @PostMapping("/{id}/borrar")
    public String delete(@PathVariable Long id){
        repo.deleteById(id);
        return "redirect:/modelos";
    }
}
