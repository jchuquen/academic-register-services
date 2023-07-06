package co.grow.plan.academic.register.admissions.controllers;

import co.grow.plan.academic.register.admissions.models.IdentificationType;
import co.grow.plan.academic.register.admissions.services.IdentificacionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdentificationTypeRestController {

    @Autowired
    private IdentificacionTypeService identificacionTypeService;

    @PostMapping("/api/rest/identificationType")
    public IdentificationType createIdentificacionType(@RequestParam String name) {
        return identificacionTypeService.createIdentificationType(name);
    }

}
