package me.ccampo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;

@SpringBootApplication
public class MultiAuthApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MultiAuthApplication.class, args);
    }

    @RestController
    public static class Controller {

        @RequestMapping("/")
        public Response root(final Principal principal) {
            final Authentication auth = (Authentication) principal;
            return new Response(auth.getName(), "/",
                    new ArrayList<>(AuthorityUtils.authorityListToSet(auth.getAuthorities())));
        }

        @RequestMapping("/random")
        public Response random(final Principal principal) {
            final Authentication auth = (Authentication) principal;
            return new Response(auth.getName(), "/random",
                    new ArrayList<>(AuthorityUtils.authorityListToSet(auth.getAuthorities())));
        }

        @RequestMapping("/api")
        public Response api(final Principal principal) {
            final Authentication auth = (Authentication) principal;
            return new Response(auth.getName(), "/api",
                    new ArrayList<>(AuthorityUtils.authorityListToSet(auth.getAuthorities())));
        }
    }
}
