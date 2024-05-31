package company.controller;


import company.entity.Person;
import company.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;
    private final VerietyPersonRepository verietyPersonRepository;
    private final StatusPersonRepository statusPersonRepository;

    private final PhonePersonRepository phonePersonRepository;

    private final EmailPersonRepository emailPersonRepository;

    @Autowired
    public PersonController(PersonRepository personRepository, VerietyPersonRepository verietyPersonRepository, StatusPersonRepository statusPersonRepository, PhonePersonRepository phonePersonRepository, EmailPersonRepository emailPersonRepository) {
        this.personRepository = personRepository;
        this.verietyPersonRepository = verietyPersonRepository;
        this.statusPersonRepository = statusPersonRepository;
        this.phonePersonRepository = phonePersonRepository;
        this.emailPersonRepository = emailPersonRepository;
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("verieties", verietyPersonRepository.findAll()); // Получаем список всех видов клиентов
        model.addAttribute("statuses", statusPersonRepository.findAll()); // Получаем список всех статусов клиентов
        return "personForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("person") Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("verieties", verietyPersonRepository.findAll());
            model.addAttribute("statuses", statusPersonRepository.findAll());
            return "personForm";
        }
        personRepository.save(person);
        return "redirect:/person/all";
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        model.addAttribute("verieties", verietyPersonRepository.findAll());
        model.addAttribute("statuses", statusPersonRepository.findAll());
        return "personList";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person Id:" + id));
        model.addAttribute("person", person);
        model.addAttribute("verieties", verietyPersonRepository.findAll());
        model.addAttribute("statuses", statusPersonRepository.findAll());
        return "personEditForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("person") Person person, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("verieties", verietyPersonRepository.findAll());
            model.addAttribute("statuses", statusPersonRepository.findAll());
            return "personEditForm";
        }
        person.setId(id);
        personRepository.save(person);
        return "redirect:/person/all";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        if (emailPersonRepository.existsByPersonId(id) || phonePersonRepository.existsByPersonId(id)) {
            model.addAttribute("error", "Нельзя удалить, есть связь.");
            model.addAttribute("persons", personRepository.findAll());
            model.addAttribute("statuses", statusPersonRepository.findAll());
            model.addAttribute("verieties", verietyPersonRepository.findAll());
            return "personList";
        }
        personRepository.deleteById(id);
        return "redirect:/person/all";
    }
}

