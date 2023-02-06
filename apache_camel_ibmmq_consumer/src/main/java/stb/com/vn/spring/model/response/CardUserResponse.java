package stb.com.vn.spring.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import stb.com.vn.spring.entity.card.CardInformation;
import stb.com.vn.spring.entity.user.User;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardUserResponse {
    private User user;
    private CardInformation cardInformation;
}