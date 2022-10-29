package edu.ucsb.cs156.example.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ucsb.cs156.example.entities.UCSBOrganization;
import edu.ucsb.cs156.example.errors.EntityNotFoundException;
import edu.ucsb.cs156.example.repositories.UCSBOrganizationRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(description = "UCSBOrganization")
@RequestMapping("/api/UCSBOrganization")
@RestController
@Slf4j
public class UCSBOrganizationController extends ApiController{
    
    @Autowired
    UCSBOrganizationRepository ucsbOrganizationRepository;

    @ApiOperation(value = "List all UCSB organizations")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/all")
    public Iterable<UCSBOrganization> allOrganizations() {
        Iterable<UCSBOrganization> organizations = ucsbOrganizationRepository.findAll();
        return organizations;
    }

    @ApiOperation(value = "Get a single organization")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public UCSBOrganization getById(
            @ApiParam("orgCode") @RequestParam String orgCode) {
        UCSBOrganization organization = ucsbOrganizationRepository.findById(orgCode)
                .orElseThrow(() -> new EntityNotFoundException(UCSBOrganization.class, orgCode));

        return organization;
    }

    @ApiOperation(value = "Create a new organization")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/post")
    public UCSBOrganization postOrganization(
        @ApiParam("orgCode") @RequestParam String orgCode,
        @ApiParam("orgTranslationShort") @RequestParam String orgTranslationShort,
        @ApiParam("orgTranslation") @RequestParam String orgTranslation,
        @ApiParam("inactive") @RequestParam boolean inactive
        
    )
    
    {
        UCSBOrganization organization = new UCSBOrganization();
        organization.setOrgCode(orgCode);
        organization.setOrgTranslationShort(orgTranslationShort);
        organization.setOrgTranslation(orgTranslation);
        organization.setInactive(inactive);

        UCSBOrganization savedOrganization = ucsbOrganizationRepository.save(organization);

        return savedOrganization;
    }

    @ApiOperation(value = "Update a single commons")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public UCSBOrganization updateOrganization(
            @ApiParam("code") @RequestParam String orgCode,
            @RequestBody @Valid UCSBOrganization incoming) {

        UCSBOrganization org = ucsbOrganizationRepository.findById(orgCode)
                .orElseThrow(() -> new EntityNotFoundException(UCSBOrganization.class, orgCode));
        
        org.setOrgCode(incoming.getOrgCode());
        org.setOrgTranslationShort(incoming.getOrgTranslationShort());
        org.setOrgTranslation(incoming.getOrgTranslation());
        org.setInactive(incoming.getInactive());


        ucsbOrganizationRepository.save(org);

        return org;
    }

    @ApiOperation(value = "Delete a UCSBOrganization")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("")
    public Object deleteOrganization(
            @ApiParam("orgCode") @RequestParam String orgCode) {
        UCSBOrganization org = ucsbOrganizationRepository.findById(orgCode)
                .orElseThrow(() -> new EntityNotFoundException(UCSBOrganization.class, orgCode));

        ucsbOrganizationRepository.delete(org);
        return genericMessage("UCSBOrganization with id %s deleted".formatted(orgCode));
    }
}
