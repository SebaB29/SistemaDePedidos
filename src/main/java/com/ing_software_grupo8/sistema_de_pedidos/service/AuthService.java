package com.ing_software_grupo8.sistema_de_pedidos.service;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ing_software_grupo8.sistema_de_pedidos.DTO.AuthResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.MessageResponseDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.RegisterRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.DTO.RestorePasswordRequestDTO;
import com.ing_software_grupo8.sistema_de_pedidos.entity.User;
import com.ing_software_grupo8.sistema_de_pedidos.exception.ApiException;
import com.ing_software_grupo8.sistema_de_pedidos.repository.IUserRepository;
import com.ing_software_grupo8.sistema_de_pedidos.response.GenericResponse;
import com.ing_software_grupo8.sistema_de_pedidos.utils.RoleEnum;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private static final String USER_NOT_FOUND_MSG = "Usuario no encontrado";
    private static final String INVALID_CREDENTIALS_MSG = "Usuario o contraseña inválidos";
    private static final String EMAIL_CONFLICT_MSG = "Usuario con ese email ya existe";
    private static final String USER_SAVE_ERROR_MSG = "Usuario no se pudo guardar";
    private static final String REGISTER_SUCCESS_MSG = "Registrado correctamente";

    private final IBasicService basicService;
    private final IJwtService jwtService;
    private final IUserRepository userRepository;

    @Override
    public GenericResponse<AuthResponseDTO> login(HttpServletRequest request) {
        String email = basicService.getEmailFromToken(request);
        String password = basicService.getPasswordFromRequest(request);
        User user = authenticate(email, password);

        String accessToken = jwtService.createAccessToken(user);
        String refreshToken = jwtService.createRefreshToken(user);
        user.setRefreshToken(refreshToken);

        saveUser(user);

        AuthResponseDTO authResponse = AuthResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return GenericResponse.<AuthResponseDTO>builder()
                .status(HttpStatus.OK)
                .data(authResponse)
                .build();
    }

    @Override
    public GenericResponse<MessageResponseDTO> register(RegisterRequestDTO request) {
        validateEmailUniqueness(request.getEmail());

        User user = buildNewUser(request);
        saveUser(user);

        return GenericResponse.<MessageResponseDTO>builder()
                .status(HttpStatus.OK)
                .data(new MessageResponseDTO(REGISTER_SUCCESS_MSG))
                .build();
    }

    @Override
    public GenericResponse<MessageResponseDTO> restore(RestorePasswordRequestDTO request) {
        User user = findUserByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, USER_NOT_FOUND_MSG));

        user.setPassword(request.getNewPassword());
        saveUser(user);

        return GenericResponse.<MessageResponseDTO>builder()
                .status(HttpStatus.OK)
                .data(new MessageResponseDTO(REGISTER_SUCCESS_MSG))
                .build();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    private User authenticate(String userEmail, String password) {
        return findUserByEmail(userEmail)
                .filter(user -> password.equals(user.getPassword()))
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, INVALID_CREDENTIALS_MSG));
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT, EMAIL_CONFLICT_MSG);
        }
    }

    private User buildNewUser(RegisterRequestDTO request) {
        return User.builder()
                .username(request.getUsername())
                .lastName(request.getLastName())
                .photo(request.getPhoto())
                .password(request.getPassword())
                .email(request.getEmail())
                .age(request.getAge())
                .gender(request.getGender())
                .address(request.getAddress())
                .role(RoleEnum.USER)
                .build();
    }

    private void saveUser(User user) {
        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, USER_SAVE_ERROR_MSG);
        }
    }
}
