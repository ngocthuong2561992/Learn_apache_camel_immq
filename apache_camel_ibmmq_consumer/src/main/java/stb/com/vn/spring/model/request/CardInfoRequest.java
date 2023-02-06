package stb.com.vn.spring.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardInfoRequest {

    private String cifId;

    private String cardNumber;

    private String custName;

    private String cardType;
}