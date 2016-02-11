package it.luigibennardis.microservice.client;

import java.util.List;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Spencer Gibb
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class HelloClientApplication {
	@Autowired
	HelloClient client;

	 
	@RequestMapping("/ciao")
	public String hello() {
		return  client.hello();
	}
	
	
	 
	@RequestMapping("/")
	public String getGiocatore() {
		return client.getGiocatore("LUIGI").toString() + " - " +  client.hello();
	}
	
		
	//DEVE ESSERE DICHIARATO IL SERVIZIO COME REGISTRATO SU EUREKA
	@FeignClient("microservice-server")
	interface HelloClient {
		@RequestMapping(method = RequestMethod.GET, value = "/{userId}/giocatore")
		List<Anagrafica> getGiocatore(@PathVariable("userId") String userId);
		//@RequestMapping(value = "/", method = GET)
		@RequestMapping(method = RequestMethod.GET, value = "/")
		String hello();
	}

	
	
	public static void main(String[] args) {
		SpringApplication.run(HelloClientApplication.class, args);
	}

	
		
	
}




class Anagrafica {
    private Long id;
    private String userId, cognome;

    @Override
    public String toString() {
        return "Anagrafica{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", cognome='" + cognome + '\'' +
                '}';
    }

    public Anagrafica() {
    }

    public Long getId() {
        return id;
    }

     
    public String getCognome() {
        return cognome;
    }

    public String getUserId() {
        return userId;
    }
}



