package stb.com.vn.spring.controller;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stb.com.vn.spring.model.request.CardInfoRequest;
import stb.com.vn.spring.model.request.UserCardInfoRequest;
import stb.com.vn.spring.services.CardInfoService;
import stb.com.vn.spring.services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/app/v1/card-info")
@RequiredArgsConstructor
public class CardController {

    private final CardInfoService cardInfoService;
    private final MessageService messageService;
    private final Gson gson;
    private final ProducerTemplate producerTemplate;

    /**
     Jpa
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cardInfoService.getById(id));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CardInfoRequest request) {
        var response = producerTemplate.requestBody("direct:tx-save-card-info", request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(cardInfoService.delete(id));
    }

    /**
     Spring data jpa
     */
    @PostMapping("/message/save")
    public ResponseEntity<?> springJpaSave(@RequestBody CardInfoRequest request) {
        messageService.sendMessageToQueue("thuongpn.spring.tx.savecard.request", request);

        return ResponseEntity.ok(true);
    }
    @PostMapping("/message/save/error")
    public ResponseEntity<?> springJpaSaveError(@RequestBody CardInfoRequest request) {
        messageService.sendMessageToQueue("thuongpn.spring.tx.error.savecard.request", request);

        return ResponseEntity.ok(true);
    }

    @GetMapping("/message/{id}")
    public ResponseEntity<?> springJpaGetOne(@PathVariable Long id) {
        messageService.sendMessageToQueue("thuongpn.spring.getone.request", id);

        return ResponseEntity.ok(true);
    }

    /**
     Store procedure
     */
    @PostMapping("/message/addcarduser/sp")
    public ResponseEntity<?> springJpaAddCardByUserWithSP(@RequestBody UserCardInfoRequest request) {

        messageService.sendMessageToQueue("thuongpn.spring.sp.addcarduser.request", request);

        return ResponseEntity.ok(true);
    }

    @GetMapping("/message/sp/{id}")
    public ResponseEntity<?> springJpaGetOneCardWithSP(@PathVariable Long id) {
        var response = producerTemplate.requestBody("direct:getcardbyidwithsp", id);
        return ResponseEntity.ok(response);
    }
}

