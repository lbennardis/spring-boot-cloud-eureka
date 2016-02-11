package it.luigibennardis.eureka.microservice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


 

//C:\development\codem2016\feign-eureka-master>java -jar server/target/feign-eureka-hello-server-0.0.1-SNAPSHOT.jar --server.port=7112

//C:\development\codem2016\feign-eureka-master
//java -jar server/target/feign-eureka-hello-server-0.0.1-SNAPSHOT.jar --server.port=7112

/**
 * @author Luigi Bennardis l.bennardis@email.it
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class MicroserviceServerApplication {
	@Autowired
	DiscoveryClient client;

	@RequestMapping("/")
	public String hello() {
		//CLIENT REFERENCE
		ServiceInstance localInstance = client.getLocalServiceInstance();
			
		
		return "Hello World: "+ localInstance.getServiceId()+":"+localInstance.getHost()+":"+localInstance.getPort();
	}
	
	
	
	
	

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceServerApplication.class, args);
	}
	
	//INIZIALIZZA LA BASE DATI 
    @Bean
    CommandLineRunner init(final AnagraficaRepository anagRepository) {
        return args -> {
        	anagRepository.deleteAll();

        	   Arrays.asList("LUIGI", "BENNARDIS").forEach(n -> anagRepository.save(new Anagrafica(n,n)));
        	   Arrays.asList("MIKE", "DANTONI").forEach(n -> anagRepository.save(new Anagrafica(n,n)));
        	   Arrays.asList("AKEEM", "OLAJUWON").forEach(n -> anagRepository.save(new Anagrafica(n,n)));
        	   System.out.println(" ");
           		System.out.println("LOADED RECORDS ");
              	System.out.println(" ");
              	};
    }
	
	
    
}



    
    @RestController
    @RequestMapping("/{userId}/giocatore")
    class AnagraficaRestController {
    	@Autowired
    	DiscoveryClient client;

        @Autowired
        private AnagraficaRepository anagRepository;

        @RequestMapping(method = RequestMethod.GET)
        Collection<Anagrafica> getGiocatore(@PathVariable String userId) {
        	ServiceInstance localInstance = client.getLocalServiceInstance();
        	
        	System.out.println(" ");
        	System.out.println("ISANCE RUNNING " +localInstance.getHost()+":"+localInstance.getPort());
           	System.out.println(" ");
           	
        	return this.anagRepository.findByUserId(userId);
        }

       
        @RequestMapping(method = RequestMethod.POST)
        Anagrafica createGiocatore(@PathVariable String nome,
        		@PathVariable String cognome) {

        	Anagrafica istanzaAnag = new Anagrafica(nome,cognome);

            return this.anagRepository.save(istanzaAnag);
            
        }

    }   




    interface AnagraficaRepository extends JpaRepository<Anagrafica, Long> {

    	Anagrafica findByUserIdAndId(String userId, Long id);

        List<Anagrafica> findByUserId(String userId);
    }
    	
    	
    @Entity
    class Anagrafica {

        private String userId;

        @Id
        @GeneratedValue
        private Long id;

        private String cognome;
        
        Anagrafica() {
        }

        public Anagrafica(String userId, String cognome) {
            this.userId = userId;
            this.cognome = cognome;
             
        }
        
        public String getCognome() {
            return cognome;
        }

        public String getUserId() {
            return userId;
        }

        public Long getId() {
            return id;
        }
        
       

        
      }

	

 


