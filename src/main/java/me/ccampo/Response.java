package me.ccampo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author Chris Campo
 */
public class Response {

    public final String username;
    public final String path;
    public final List<String> authorities;

    @JsonCreator
    public Response(@JsonProperty("username") final String username,
            @JsonProperty("path") final String path,
            @JsonProperty("authorities") final List<String> authorities) {
        this.username = username;
        this.path = path;
        this.authorities = authorities;
    }
}
