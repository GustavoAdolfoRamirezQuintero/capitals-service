package com.profesorp.capitalsservice;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class CapitalsServiceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CapitalsServiceProxy proxy;

    HashMap<Long, Integer> htPuerto = new HashMap<>();

    /**
     * *
     *
     * @param country
     * @return
     */
    @GetMapping("/{country}")
    public ContactosCliente getCountry(@PathVariable String country) {
        ContactosCliente response = proxy.getCountry(country);
        htPuerto.put(response.getId(), htPuerto.getOrDefault(response.getId(), 0) + 1);
        logger.info("ContactosService -> {} ", response);
        return response;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/listarContactosFeign/{id}")
    public ContactosCliente getContactosFeign(@PathVariable Long id) {
        ContactosCliente response = proxy.getContactos(id);
        // htPuerto.put(response.getId(), htPuerto.getOrDefault(response.getId(), 0) + 1);
        logger.info("ContactosService -> {}--------------------------------------------------------------- ", response);
        return response;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/listarContactosModel/{id}")
    public ModelAndView getContactosModel(@PathVariable("id") Long id) {
        ContactosCliente response = proxy.getContactos(id);
        ModelAndView retorno = new ModelAndView();
        // htPuerto.put(response.getId(), htPuerto.getOrDefault(response.getId(), 0) + 1);
        logger.info("ContactosService -> {}-----------------------modelAndView---------------------------------------- ", response);
        retorno.addObject("datos", response);
        return retorno;
    }

    /**
     *
     * @return
     */
    @GetMapping("/puertos")
    public String getCountryUsingFeign() {
        StringBuffer response = new StringBuffer();
        htPuerto.forEach((k, v) -> response.append(" Puerto: " + k + " Valor: " + v));
        return response.toString();
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/listoContactos/{id}")
    public ContactosCliente getCountryUsingRestTemplate(@PathVariable Long id) {

        logger.error(id + "pribando ingreso al metodo");
        Map<String, Long> uriVariables = new HashMap<>();
        uriVariables.put("id", id);

        ResponseEntity<ContactosCliente> responseEntity = new RestTemplate().getForEntity("http://localhost:8092/contact/list/{id}",
                ContactosCliente.class,
                uriVariables);

        ContactosCliente response = responseEntity.getBody();
        logger.error(response + "visualizando respuesta de controller");

        return response;
    }

    @Autowired
    private CapitalsServiceProxySimple simpleProxy;

    /**
     *
     * @param country
     * @return
     */
    @GetMapping("/feign/{country}")
    public ContactosCliente getCountryUsingFeign(@PathVariable String country) {
        ContactosCliente response = simpleProxy.getCountry(country);
        return response;
    }

}
