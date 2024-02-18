package js.toy.security.presentation;

import js.toy.response.TechBedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTestController {

    @GetMapping("/security/api/v1/test")
    public ResponseEntity<TechBedResponse> retrieveApiResult() {
        return ResponseEntity.ok(TechBedResponse.success(null));
    }

    @GetMapping("/security/auth-pass/test")
    public ResponseEntity<TechBedResponse> retrieveAuthPassResult() {
        return ResponseEntity.ok(TechBedResponse.success(null));
    }
}
