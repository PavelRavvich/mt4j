package pro.laplacelab.mt4j.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    @NotNull(message = "id required")
    @JsonProperty("id")
    private Long id;

    @NotNull(message = "balance required")
    @JsonProperty("balance")
    private Double balance;

    @NotNull(message = "margin required")
    @JsonProperty("margin")
    private Double margin;

    @NotNull(message = "freeMargin required")
    @JsonProperty("freeMargin")
    private Double freeMargin;

    @NotNull(message = "owner required")
    @NotEmpty(message = "owner name cant be empty")
    @JsonProperty("owner")
    private String owner;

    @NotEmpty(message = "company name cant be empty")
    @NotNull(message = "company required")
    @JsonProperty("company")
    private String company;

}
