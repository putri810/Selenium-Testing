package testing.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestLogin {
        @JsonProperty("email")
        public String email;

        @JsonProperty("password")
        public String password;

        public RequestLogin(String emails, String passwords) {
            this.email = emails;
            this.password = passwords;
        }
}
