package com.example.assigment3;

import com.example.assigment3.wrappers.EmailValidator;
import com.example.assigment3.wrappers.PasswordValidator;
import com.example.assigment3.wrappers.PhoneValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {
    private final User defaultUser = new User((long) 1,"Vardenis", "Pavardenis",
            "+37062527965", "email@prov.com", "an address", "A!abc1324");

    @Mock
    private UserRepository repository;
    @Mock
    private EmailValidator emailValidator;
    @Mock
    private PhoneValidator phoneValidator;
    @Mock
    private PasswordValidator passwordValidator;

    private UserHandler userHandler;

    @BeforeEach
    void setUp() {
        userHandler = new UserHandler(emailValidator, phoneValidator, passwordValidator, repository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void all() {
        var users = new ArrayList<User>();
        users.add(defaultUser);
        when(repository.findAll()).thenReturn(users);

        var returnedUsers = userHandler.all();

        assertEquals(returnedUsers, users);
    }

    @Test
    void newUser_invalidEmail() {
        when(emailValidator.validate(any(String.class))).thenReturn(false);
        Exception exception = assertThrows(UserInvalidRequestException.class, () -> {
            userHandler.newUser(defaultUser);
        });

        String expectedMessage = "Email '" + defaultUser.getEmail() + "' is invalid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void newUser_invalidPhone() {
        when(emailValidator.validate(any(String.class))).thenReturn(true);
        when(phoneValidator.validate(any(String.class))).thenReturn(false);

        Exception exception = assertThrows(UserInvalidRequestException.class, () -> {
            userHandler.newUser(defaultUser);
        });

        String expectedMessage = "Phone number '" + defaultUser.getPhoneNumber() + "' is invalid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void newUser_invalidPassword() {
        when(emailValidator.validate(any(String.class))).thenReturn(true);
        when(phoneValidator.validate(any(String.class))).thenReturn(true);
        when(passwordValidator.validate(any(String.class))).thenReturn(false);

        Exception exception = assertThrows(UserInvalidRequestException.class, () -> {
            userHandler.newUser(defaultUser);
        });

        String expectedMessage = "Password is invalid. Password must contain at least 8 symbols, an uppercase and a lowercase letter, a number and a special symbol";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void newUser() {
        when(emailValidator.validate(any(String.class))).thenReturn(true);
        when(phoneValidator.validate(any(String.class))).thenReturn(true);
        when(passwordValidator.validate(any(String.class))).thenReturn(true);
        when(repository.save(defaultUser)).thenReturn(defaultUser);

        var returnedUser = userHandler.newUser(defaultUser);

        assertEquals(returnedUser, defaultUser);
    }

    @Test
    void one() {
        when(repository.findById(defaultUser.getId())).thenReturn(java.util.Optional.of(defaultUser));

        var returnedUser = userHandler.one(defaultUser.getId());

        assertEquals(returnedUser, defaultUser);
    }


    @Test
    void replaceUser_invalidEmail() {
        when(emailValidator.validate(any(String.class))).thenReturn(false);
        when(repository.findById(defaultUser.getId())).thenReturn(java.util.Optional.of(defaultUser));

        Exception exception = assertThrows(UserInvalidRequestException.class, () -> {
            userHandler.replaceUser(defaultUser, defaultUser.getId());
        });

        String expectedMessage = "Email '" + defaultUser.getEmail() + "' is invalid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void replaceUser_invalidPhone() {
        when(emailValidator.validate(any(String.class))).thenReturn(true);
        when(phoneValidator.validate(any(String.class))).thenReturn(false);
        when(repository.findById(defaultUser.getId())).thenReturn(java.util.Optional.of(defaultUser));

        Exception exception = assertThrows(UserInvalidRequestException.class, () -> {
            userHandler.replaceUser(defaultUser, defaultUser.getId());
        });

        String expectedMessage = "Phone number '" + defaultUser.getPhoneNumber() + "' is invalid";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void replace_invalidPassword() {
        when(emailValidator.validate(any(String.class))).thenReturn(true);
        when(phoneValidator.validate(any(String.class))).thenReturn(true);
        when(passwordValidator.validate(any(String.class))).thenReturn(false);
        when(repository.findById(defaultUser.getId())).thenReturn(java.util.Optional.of(defaultUser));

        Exception exception = assertThrows(UserInvalidRequestException.class, () -> {
            userHandler.replaceUser(defaultUser, defaultUser.getId());
        });

        String expectedMessage = "Password is invalid. Password must contain at least 8 symbols, an uppercase and a lowercase letter, a number and a special symbol";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void replaceUser() {
        var user = new User();
        user.setId(defaultUser.getId());

        when(emailValidator.validate(any(String.class))).thenReturn(true);
        when(phoneValidator.validate(any(String.class))).thenReturn(true);
        when(passwordValidator.validate(any(String.class))).thenReturn(true);
        when(repository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        when(repository.save(defaultUser)).thenReturn(defaultUser);

        var returnedUser = userHandler.replaceUser(defaultUser, defaultUser.getId());

        assertEquals(returnedUser, defaultUser);
    }

    @Test
    void deleteUser() {
        when(repository.findById(any(long.class))).thenReturn(java.util.Optional.of(defaultUser));

        assertDoesNotThrow(() -> {
            userHandler.deleteUser((long) 1);
        });
    }

    @Test
    void replaceUser_UserNotFound() {
        when(repository.findById(any(long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userHandler.replaceUser(defaultUser, (long) 3);
        });

        String expectedMessage = "Could not find user 3";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void one_UserNotFound() {
        when(repository.findById(any(long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userHandler.one((long) 2);
        });

        String expectedMessage = "Could not find user 2";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void deleteUser_UserNotFound() {
        when(repository.findById(any(long.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userHandler.deleteUser((long) 1);
        });

        String expectedMessage = "Could not find user 1";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}