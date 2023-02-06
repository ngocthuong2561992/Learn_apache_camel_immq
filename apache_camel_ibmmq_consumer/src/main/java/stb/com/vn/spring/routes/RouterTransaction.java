package stb.com.vn.spring.routes;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RouterTransaction {
    String functionName;
    String requestFrClient;
    String responseToClient;
    String requestToHost;
    String responseFrHost;
}
