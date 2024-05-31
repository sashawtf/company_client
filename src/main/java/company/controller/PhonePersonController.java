package company.controller;

import company.entity.PhonePerson;
import company.repository.PersonRepository;
import company.repository.PhonePersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/phone")
public class PhonePersonController {

    private final PhonePersonRepository phonePersonRepository;
    private final PersonRepository personRepository;

    @Autowired
    public PhonePersonController(PhonePersonRepository phonePersonRepository, PersonRepository personRepository) {
        this.phonePersonRepository = phonePersonRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("phonePerson", new PhonePerson());
        model.addAttribute("persons", personRepository.findAll()); // Получаем список всех клиентов
        return "phonePersonForm";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("phonePerson") PhonePerson phonePerson, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("persons", personRepository.findAll());
            return "phonePersonForm";
        }
        phonePersonRepository.save(phonePerson);
        return "redirect:/phone/all";
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("phones", phonePersonRepository.findAll());
        model.addAttribute("persons", personRepository.findAll());
        return "phonePersonList";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        PhonePerson phonePerson = phonePersonRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid phonePerson Id:" + id));
        model.addAttribute("phonePerson", phonePerson);
        model.addAttribute("persons", personRepository.findAll()); // Получаем список всех клиентов
        return "phonePersonEditForm";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("phonePerson") PhonePerson phonePerson, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("persons", personRepository.findAll());
            return "phonePersonEditForm";
        }
        phonePerson.setId(id);
        phonePersonRepository.save(phonePerson);
        return "redirect:/phone/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        phonePersonRepository.deleteById(id);
        return "redirect:/phone/all";
    }
}

