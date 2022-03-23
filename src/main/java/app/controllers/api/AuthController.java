package app.controllers.api;

import app.configuration.captcha.CaptchaSettings;
import app.configuration.captcha.GoogleResponse;
import app.configuration.security.JwtTokenUtil;
import app.dto.auth.AuthRequest;
import app.dto.auth.UserView;
import app.entities.UserEntity;
import app.mapper.UserMapper;
import app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.client.RestOperations;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/public/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final CaptchaSettings captchaSettings;
    private final RestOperations restTemplate;
    private final String RECAPTCHA_URL_TEMPLATE = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @PostMapping("/login")
    public ResponseEntity<UserView> login(@RequestBody @Valid AuthRequest request) {
        try {
            String url = String.format(RECAPTCHA_URL_TEMPLATE, captchaSettings.getSicretkey(), request.getToken());
            try {
                final GoogleResponse googleResponse = restTemplate.getForObject(url, GoogleResponse.class);
                ;
                if (!googleResponse.isSuccess()) {
                    throw new Exception("reCaptcha was not successfully validated");
                }
            }
            catch (Exception rce) {
                String str = rce.getMessage();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            System.out.println("Captcha is good ----");

//            if(1==1)
//                return null;
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()));

            User user = (User) authenticate.getPrincipal();
            UserEntity dbUser = userRepository
                    .findByUsername(user.getUsername());
            UserView userView = userMapper.UserToUserView(dbUser);// new UserView();
            //userView.setUsername(user.getUsername());
            String token = jwtTokenUtil.generateAccessToken(dbUser);
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(userView);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
