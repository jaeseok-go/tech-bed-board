package js.toy.member.presentation;

import js.toy.member.application.MemberService;
import js.toy.response.TechBedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {
    @GetMapping("/api/v1/test")
    public ResponseEntity<TechBedResponse> retrieveTest() {
        return ResponseEntity.ok(TechBedResponse.success(null));
    }
}
