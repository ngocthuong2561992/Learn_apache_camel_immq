package stb.com.vn.spring.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stb.com.vn.spring.model.request.UserRequest;
import stb.com.vn.spring.services.MessageService;
import stb.com.vn.spring.services.UserService;

@RestController
@RequestMapping("/app/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MessageService messageService;
    private final Gson gson;
    private final ProducerTemplate producerTemplate;

    // Card Information
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getOneUser(id));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.save(request));
    }

    @PostMapping("/save/proc")
    public ResponseEntity<?> saveStoreProc(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.saveWithStoreProc(request));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }

}