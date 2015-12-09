package me.ccampo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MultiAuthApplication.class)
@WebIntegrationTest({ "server.port=0", "management.port=0" })
public class MultiAuthApplicationTests {

    @Value("${local.server.port}")
    int port;

    @Test
    public void apiPathAcceptsAPICredentials() {
        final RestTemplate restTemplate = new TestRestTemplate("apiuser", "apipw", null);
        final ResponseEntity<Response> response =
                restTemplate.getForEntity("http://localhost:" + this.port + "/api", Response.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Response expected = new Response("apiuser", "/api", Collections.singletonList("ROLE_API_USER"));
        assertThat(response.getBody()).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void apiPathIsUnauthorizedForRegularUser() {
        final RestTemplate restTemplate = new TestRestTemplate("user", "pw", null);
        final ResponseEntity<Response> response =
                restTemplate.getForEntity("http://localhost:" + this.port + "/api", Response.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void rootPathAcceptsUserCredentials() {
        final RestTemplate restTemplate = new TestRestTemplate("user", "pw", null);
        final ResponseEntity<Response> response =
                restTemplate.getForEntity("http://localhost:" + this.port, Response.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Response expected = new Response("user", "/", Collections.singletonList("ROLE_USER"));
        assertThat(response.getBody()).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void rootPathIsUnauthorizedForApiUser() {
        final RestTemplate restTemplate = new TestRestTemplate("apiuser", "apipw", null);
        final ResponseEntity<Response> response =
                restTemplate.getForEntity("http://localhost:" + this.port, Response.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void randomPathAcceptsUserCredentials() {
        final RestTemplate restTemplate = new TestRestTemplate("user", "pw", null);
        final ResponseEntity<Response> response =
                restTemplate.getForEntity("http://localhost:" + this.port + "/random", Response.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        final Response expected = new Response("user", "/random", Collections.singletonList("ROLE_USER"));
        assertThat(response.getBody()).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void randomPathIsUnauthorizedForApiUser() {
        final RestTemplate restTemplate = new TestRestTemplate("apiuser", "apipw", null);
        final ResponseEntity<Response> response =
                restTemplate.getForEntity("http://localhost:" + this.port + "/random", Response.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
