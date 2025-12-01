package com.ecommerce.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ecommerce.dto.UserLoginDTO;
import com.ecommerce.exception.GlobalExceptionHandler;
import com.ecommerce.service.AuthService;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthController.class, GlobalExceptionHandler.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class AuthControllerDiffblueTest {
    @Autowired
    private AuthController authController;

    @MockBean
    private AuthService authService;

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    /**
     * Test {@link AuthController#login(UserLoginDTO, HttpServletRequest)}.
     *
     * <ul>
     *   <li>Given array of {@link String} with {@code X-Forwarded-For}.
     *   <li>Then status {@link StatusResultMatchers#isInternalServerError()}.
     * </ul>
     *
     * <p>Method under test: {@link AuthController#login(UserLoginDTO, HttpServletRequest)}
     */
    @Test
    @DisplayName(
            "Test login(UserLoginDTO, HttpServletRequest); given array of String with 'X-Forwarded-For'; then status isInternalServerError()")
    @Tag("MaintainedByDiffblue")
    void testLogin_givenArrayOfStringWithXForwardedFor_thenStatusIsInternalServerError()
            throws Exception {
        // Arrange
        when(authService.login(Mockito.<UserLoginDTO>any(), Mockito.<String>any()))
                .thenReturn(new HashMap<>());

        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/auth/login");
        postResult.accept("X-Forwarded-For");

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setCaptcha("Captcha");
        userLoginDTO.setCaptchaKey("Captcha Key");
        userLoginDTO.setEmail("jane.doe@example.org");
        userLoginDTO.setPassword("iloveyou");
        userLoginDTO.setRememberMe(true);

        MockHttpServletRequestBuilder requestBuilder =
                postResult
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                JsonMapper.builder().findAndAddModules().build().writeValueAsString(userLoginDTO));

        // Act and Assert
        MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(globalExceptionHandler)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isInternalServerError());
    }
}
