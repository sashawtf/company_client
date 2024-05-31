package company.controller;

import company.entity.StatusPerson;
import company.repository.PersonRepository;
import company.repository.StatusPersonRepository;
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

@WebMvcTest(StatusPersonController.class)
@AutoConfigureMockMvc
public class StatusPersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatusPersonRepository statusPersonRepository;

    @MockBean
    private PersonRepository personRepository;

    @Test
    @WithMockUser
    public void testShowAllStatuses() throws Exception {
        List<StatusPerson> statuses = new ArrayList<>();
        when(statusPersonRepository.findAll()).thenReturn(statuses);

        mockMvc.perform(MockMvcRequestBuilders.get("/status/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("statusPersonList"))
                .andExpect(model().attribute("statuses", statuses));
    }

    @Test
    @WithMockUser
    public void testShowAddStatusPersonForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/status/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("statusPersonForm"))
                .andExpect(model().attributeExists("statusPerson"));
    }

    @Test
    @WithMockUser
    public void testShowEditStatusPersonForm() throws Exception {
        Long id = 1L;
        StatusPerson statusPerson = new StatusPerson();
        statusPerson.setId(id);
        when(statusPersonRepository.findById(id)).thenReturn(Optional.of(statusPerson));

        mockMvc.perform(MockMvcRequestBuilders.get("/status/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("statusPersonEditForm"))
                .andExpect(model().attribute("statusPerson", statusPerson));
    }

    @Test
    @WithMockUser
    public void testDeleteStatusPerson() throws Exception {
        Long id = 1L;
        when(personRepository.existsByStatusId(id)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/status/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/status/all"));
        verify(statusPersonRepository, times(1)).deleteById(id);
    }

    @Test
    @WithMockUser
    public void testDeleteStatusPersonWithExistingRelationship() throws Exception {
        Long id = 1L;
        List<StatusPerson> statuses = new ArrayList<>();
        when(statusPersonRepository.findAll()).thenReturn(statuses);
        when(personRepository.existsByStatusId(id)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/status/delete/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("statusPersonList"))
                .andExpect(model().attribute("error", "Нельзя удалить, есть связь."))
                .andExpect(model().attribute("statuses", statuses));
        verify(statusPersonRepository, never()).deleteById(id);
    }
}
