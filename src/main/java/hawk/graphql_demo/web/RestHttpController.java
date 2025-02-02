package hawk.graphql_demo.web;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import hawk.graphql_demo.model.transaction.Transaction;
import hawk.graphql_demo.repository.TransactionDB;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
class RestHttpController {
    private final TransactionDB transactionDB;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/transactions")
    ResponseEntity<List<Transaction>> getTransactions() {
        return ResponseEntity.ok(transactionDB.getTransactions().values().stream().toList());
    }

    @GetMapping("/fields")
    ResponseEntity<List<ObjectNode>> getFields(@RequestBody List<String> fields) {
        var missingFields = new LinkedHashSet<>();

        var transactionFields = transactionDB.getTransactions().values().stream()
            .map(objectMapper::valueToTree)
            .map(JsonNode.class::cast)
            .map(it -> {
                var resultNode = objectMapper.createObjectNode();

                fields.forEach(field -> Optional.ofNullable(it.get(field))
                    .ifPresentOrElse(node -> resultNode.put(field, node.asText()), () ->
                        missingFields.add(field)));

                return resultNode;
            }).toList();

        if (!missingFields.isEmpty()) {
            log.warn("The following requested fields do not exist in the model: {}", missingFields);
        }

        return ResponseEntity.ok(transactionFields);
    }
}
