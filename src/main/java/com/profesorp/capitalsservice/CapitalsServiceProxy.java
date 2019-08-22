package com.profesorp.capitalsservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "contactos-service")
public interface CapitalsServiceProxy {

    /**
     *
     * @param country
     * @return
     */
    @GetMapping("/{country}")
    public ContactosCliente getCountry(@PathVariable("country") String country);

    /**
     *
     */
    @GetMapping("/contact/list/{id}")
    public ContactosCliente getContactos(@PathVariable("id") Long id);
}
