package dasturlash.uz.controller;

import dasturlash.uz.dto.AuthDTO;
import dasturlash.uz.dto.ProfileDTO;
import dasturlash.uz.dto.RegistrationDTO;
import dasturlash.uz.dto.VerificationDTO;
import dasturlash.uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto){

        return ResponseEntity.ok(authService.registration(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@Valid @RequestBody AuthDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/regVerification")
    public ResponseEntity<String> regVerification(@RequestBody VerificationDTO dto){
        return ResponseEntity.ok(authService.regVerification(dto));
    }

}
