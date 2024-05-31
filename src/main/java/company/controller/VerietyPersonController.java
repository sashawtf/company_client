package company.controller;

import company.entity.VerietyPerson;
import company.repository.PersonRepository;
import company.repository.VerietyPersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/veriety")
public class VerietyPersonController {

    private final VerietyPersonRepository verietyPersonRepository;
    private final PersonRepository personRepository;

    @Autowired
    public VerietyPersonController(VerietyPersonRepository verietyPersonRepository, PersonRepository personRepository) {
        this.verietyPersonRepository = verietyPersonRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("verietyPerson", new VerietyPerson());
        return "verietyPersonForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("verietyPerson") VerietyPerson verietyPerson, BindingResult result) {
        if (result.hasErrors()) {
            return "verietyPersonForm";
        }
        verietyPersonRepository.save(verietyPerson);
        return "redirect:/veriety/all";
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("verieties", verietyPersonRepository.findAll());
        return "verietyPersonList";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        VerietyPerson verietyPerson = verietyPersonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verietyPerson Id:" + id));
        model.addAttribute("verietyPerson", verietyPerson);
        return "verietyPersonEditForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("verietyPerson") VerietyPerson verietyPerson, BindingResult result) {
        if (result.hasErrors()) {
            return "verietyPersonEditForm";
        }
        verietyPerson.setId(id);
        verietyPersonRepository.save(verietyPerson);
        return "redirect:/veriety/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        if (personRepository.existsByVerietyId(id)) {
            model.addAttribute("error", "Нельзя удалить, есть связь.");
            model.addAttribute("verieties", verietyPersonRepository.findAll());
            return "verietyPersonList";
        }
        verietyPersonRepository.deleteById(id);
        return "redirect:/veriety/all";
    }
}
