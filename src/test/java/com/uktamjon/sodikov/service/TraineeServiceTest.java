package com.uktamjon.sodikov.service;

import com.uktamjon.sodikov.domains.Trainee;
import com.uktamjon.sodikov.domains.User;
import com.uktamjon.sodikov.dtos.CreateResponse;
import com.uktamjon.sodikov.repository.TraineeRepository;
import com.uktamjon.sodikov.services.TraineeService;
import com.uktamjon.sodikov.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TraineeServiceTest {

    @Mock
    private TraineeRepository traineeRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TraineeService traineeService;

    @BeforeEach
    void setUp() {

        traineeRepository = mock(TraineeRepository.class);
        userService = mock(UserService.class);
        traineeService = new TraineeService( userService, traineeRepository);
    }

    @Test
    public void testCreateTrainee_UserExists() {
        Trainee trainee = new Trainee();
        User user = new User();
        user.setId(1);
        trainee.setUserId(user);


        when(userService.getUserById(trainee.getUserId().getId())).thenReturn(new User());

        CreateResponse createdTrainee = traineeService.createTrainee(trainee);

        assertNull(createdTrainee);
    }

    @Test
    public void testCreateTrainee_UserDoesNotExist() {
        Trainee trainee = new Trainee();
        User user = new User();
        trainee.setUserId(user);


        when(userService.getUserById(trainee.getUserId().getId())).thenReturn(null);

        when(userService.createUser(user)).thenReturn(user);

        when(traineeRepository.save(trainee)).thenReturn(trainee);

        CreateResponse createdTrainee = traineeService.createTrainee(trainee);

        assertEquals(trainee.getUserId().getUsername(), createdTrainee.getUsername());
        assertEquals(trainee.getUserId().getPassword(), createdTrainee.getPassword());
    }
    @Test
    public void testUpdateTrainee_UserAndTraineeExist() {
        Trainee trainee = new Trainee();
        trainee.setId(1);

        User user = new User();
        user.setId(1);
        trainee.setUserId(user);


        when(userService.getUserById(trainee.getUserId().getId())).thenReturn(user);


        when(traineeRepository.existsById(trainee.getId())).thenReturn(true);



        when(traineeRepository.save(trainee)).thenReturn(trainee);

        Trainee updatedTrainee = traineeService.updateTrainee(trainee);


        assertEquals(trainee, updatedTrainee);


        verify(userService, times(1)).updateUser(user);
        verify(traineeRepository, times(1)).save(trainee);
    }
    @Test
    public void testDeleteTrainee_TraineeExists() {

        Trainee trainee = new Trainee();
        trainee.setId(1);
        trainee.setUserId(new User());


        when(traineeRepository.findById(1)).thenReturn(Optional.of(trainee));


        traineeService.deleteTrainee(1);


        verify(userService, times(1)).deleteUserById(trainee.getUserId().getId());
        verify(traineeRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteTrainee_TraineeDoesNotExist() {
        int traineeId = 1;


        when(traineeRepository.findById(traineeId)).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class, () -> traineeService.deleteTrainee(traineeId));


    }
    @Test
    public void testGetTrainee_TraineeExists() {
        int traineeId = 1;
        Trainee trainee = new Trainee();
        trainee.setId(traineeId);


        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));


        Trainee retrievedTrainee = traineeService.getTrainee(traineeId);


        assertEquals(trainee, retrievedTrainee);
    }




}


