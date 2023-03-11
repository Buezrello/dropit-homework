package com.example.dropit.controller;

import com.example.dropit.dto.FormattedAddressDto;
import com.example.dropit.dto.SingleLineAddressDto;
import com.example.dropit.exception.ResolveAddressException;
import com.example.dropit.service.DropitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class DropitControllerTests {

    @InjectMocks
    private DropitController dropitController;

    @Mock
    private DropitService dropitService;

    @Test
    public void resolveAddress_Success() {
        when(dropitService.resolveAddress(any(SingleLineAddressDto.class))).thenReturn(new FormattedAddressDto());
        ResponseEntity<FormattedAddressDto> responseEntity = dropitController.resolveAddress(new SingleLineAddressDto());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(FormattedAddressDto.class);
    }

    @Test
    public void resolveAddress_Failure() {
        String errorMessage = "ResolveAddressException";
        when(dropitService.resolveAddress(any(SingleLineAddressDto.class))).thenThrow(new ResolveAddressException(errorMessage));
        ResolveAddressException resolveAddressException =
                assertThrows(ResolveAddressException.class, () -> dropitController.resolveAddress(new SingleLineAddressDto()));
        assertThat(resolveAddressException.getMessage()).isEqualTo(errorMessage);
    }
}
