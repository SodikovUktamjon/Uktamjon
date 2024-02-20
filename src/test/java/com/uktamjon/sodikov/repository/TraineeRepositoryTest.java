package com.uktamjon.sodikov.repository;

import com.uktamjon.sodikov.domains.Trainee;
import com.uktamjon.sodikov.domains.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class TraineeRepositoryTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Test
    void testFindByUserId() {
        Trainee trainee = new Trainee();
        trainee.setUser(User.builder().id(1).build());
        traineeRepository.save(trainee);
        when(traineeRepository.findByUserId(1)).thenReturn(trainee);
        Trainee foundTrainee = traineeRepository.findByUserId(1);

        assertNotNull(foundTrainee);
        assertEquals(1, foundTrainee.getUser().getId());
    }

    @Test
    void testFindAll() {
        Trainee trainee1 = new Trainee();
        Trainee trainee2 = new Trainee();
        traineeRepository.saveAll(List.of(trainee1, trainee2));
        when(traineeRepository.findAll()).thenReturn(List.of(trainee1, trainee2));
        List<Trainee> allTrainees = traineeRepository.findAll();

        assertNotNull(allTrainees);
        assertEquals(2, allTrainees.size());
    }
}
