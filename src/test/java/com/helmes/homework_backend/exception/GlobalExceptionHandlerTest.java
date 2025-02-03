package com.helmes.homework_backend.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private MethodArgumentNotValidException mockException;

    @Mock
    private BindingResult mockBindingResult;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        when(mockException.getBindingResult()).thenReturn(mockBindingResult);
    }

    @Test
    void handleValidationExceptions_invalidInput_returnsErrorMap() {
        FieldError error1 = new FieldError("userDataRequestDTO", "name", "Name is required!");
        FieldError error2 = new FieldError("userDataRequestDTO", "agree", "Agreeing the terms is required!");
        FieldError error3 = new FieldError("userDataRequestDTO", "sectorIds", "At least one sector must be selected!");

        when(mockBindingResult.getFieldErrors()).thenReturn(List.of(error1, error2, error3));

        Map<String, String> response = globalExceptionHandler.handleValidationExceptions(mockException);

        assertThat(response).isNotNull();
        assertThat(response).containsEntry("name", "Name is required!");
        assertThat(response).containsEntry("agree", "Agreeing the terms is required!");
        assertThat(response).containsEntry("sectorIds", "At least one sector must be selected!");

        verify(mockException, times(1)).getBindingResult();
        verify(mockBindingResult, times(1)).getFieldErrors();
    }
}

