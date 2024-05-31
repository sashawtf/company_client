package company.controller;

import company.entity.StatusPerson;
import company.repository.PersonRepository;
import company.repository.StatusPersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/status")
public class StatusPersonController {

    private final StatusPersonRepository statusPersonRepository;
    private final PersonRepository personRepository;

    @Autowired
    public StatusPersonController(StatusPersonRepository statusPersonRepository, PersonRepository personRepository) {
        this.statusPersonRepository = statusPersonRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("statusPerson", new StatusPerson());
        return "statusPersonForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("statusPerson") StatusPerson statusPerson, BindingResult result) {
        if (result.hasErrors()) {
            return "statusPersonForm";
        }
        statusPersonRepository.save(statusPerson);
        return "redirect:/status/all";
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("statuses", statusPersonRepository.findAll());
        return "statusPersonList";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        StatusPerson statusPerson = statusPersonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid status Id:" + id));
        model.addAttribute("statusPerson", statusPerson);
        return "statusPersonEditForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("statusPerson") StatusPerson statusPerson, BindingResult result) {
        if (result.hasErrors()) {
            return "statusPersonEditForm";
        }
        statusPerson.setId(id);
        statusPersonRepository.save(statusPerson);
        return "redirect:/status/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model ) {
        if (personRepository.existsByStatusId(id)) {
            model.addAttribute("error", "Нельзя удалить, есть связь.");
            model.addAttribute("statuses", statusPersonRepository.findAll());
            return "statusPersonList";
        }
        statusPersonRepository.deleteById(id);
        return "redirect:/status/all";
    }
}

