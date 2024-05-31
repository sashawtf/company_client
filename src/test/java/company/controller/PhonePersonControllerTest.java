package company.controller;

import company.entity.Person;
import company.entity.PhonePerson;
import company.repository.PersonRepository;
import company.repository.PhonePersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PhonePersonController.class)
@AutoConfigureMockMvc
public class PhonePersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhonePersonRepository phonePersonRepository;

    @MockBean
    private PersonRepository personRepository;

    @Test
    @WithMockUser
    public void testShowAllPhonePersons() throws Exception {
        List<PhonePerson> phonePersons = new ArrayList<>();
        when(phonePersonRepository.findAll()).thenReturn(phonePersons);
        List<Person> persons = new ArrayList<>();
        when(personRepository.findAll()).thenReturn(persons);

        mockMvc.perform(MockMvcRequestBuilders.get("/phone/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("phonePersonList"))
                .andExpect(model().attribute("phones", phonePersons))
                .andExpect(model().attribute("persons", persons));
    }

    @Test
    @WithMockUser
    public void testShowAddPhonePersonForm() throws Exception {
        List<Person> persons = new ArrayList<>();
        when(personRepository.findAll()).thenReturn(persons);

        mockMvc.perform(MockMvcRequestBuilders.get("/phone/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("phonePersonForm"))
                .andExpect(model().attributeExists("phonePerson"))
                .andExpect(model().attribute("persons", persons));
    }

    @Test
    @WithMockUser
    public void testShowEditPhonePersonForm() throws Exception {
        Long id = 1L;
        PhonePerson phonePerson = new PhonePerson();
        phonePerson.setId(id);
        List<Person> persons = new ArrayList<>();
        when(personRepository.findAll()).thenReturn(persons);
        when(phonePersonRepository.findById(id)).thenReturn(Optional.of(phonePerson));

        mockMvc.perform(MockMvcRequestBuilders.get("/phone/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("phonePersonEditForm"))
                .andExpect(model().attribute("phonePerson", phonePerson))
                .andExpect(model().attribute("persons", persons));
    }

    @Test
    @WithMockUser
    public void testDeletePhonePerson() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/phone/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/phone/all"));
        verify(phonePersonRepository, times(1)).deleteById(id);
    }
}
