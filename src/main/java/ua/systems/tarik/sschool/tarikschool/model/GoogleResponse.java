package ua.systems.tarik.sschool.tarikschool.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "success",
        "challenge_ts",
        "hostname",
        "error-codes"
})
@Data
public class GoogleResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("error-codes")
    private ErrorCode[] errorCodes;

    @JsonIgnore
    public boolean hasClientError() {
        ErrorCode[] errors = ErrorCode.values();
        for(ErrorCode error : errors) {
            switch(error) {
                case InvalidResponse:
                case MissingResponse:
                    return true;
            }
        }
        return false;
    }

    enum ErrorCode {
        MissingSecret,     InvalidSecret,
        MissingResponse,   InvalidResponse;

        private static final Map<String, ErrorCode> errorsMap = new HashMap<String, ErrorCode>(4);

        static {
            errorsMap.put("missing-input-secret",   MissingSecret);
            errorsMap.put("invalid-input-secret",   InvalidSecret);
            errorsMap.put("missing-input-response", MissingResponse);
            errorsMap.put("invalid-input-response", InvalidResponse);
        }

        @JsonCreator
        public static ErrorCode forValue(String value) {
            return errorsMap.get(value.toLowerCase());
        }
    }

}