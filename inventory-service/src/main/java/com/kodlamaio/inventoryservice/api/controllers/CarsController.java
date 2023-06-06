package com.kodlamaio.inventoryservice.api.controllers;

import com.kodlamaio.commonpackage.utils.constants.Roles;
import com.kodlamaio.commonpackage.utils.dto.ClientResponse;
import com.kodlamaio.inventoryservice.business.abstracts.CarService;
import com.kodlamaio.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetCarResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.all.GetAllCarsResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cars")
public class CarsController {
    private final CarService service;

    @GetMapping // gelall işlemine security uyguladık
    // todo: bak Secured, PreAuthorize, PostAuthorize
    //PostFilter, PreFilter = parametredeki bir koleksiyona uygulanıyor
    // @Secured("ROLE_admin") daha basit kurallar için parantez içi gibi ROLE yazdık bu bir prefix
    // bu işlemle yapmak istediğimiz rol ü admin olanlar sadece kullanabilsin getAll ı
    // tam olarak bu ifadeyi tanımıyor bu nedenle converter aracılığıyla dönüştürdük
    //ROLE_admin; özellikle bu şekilde olmasının nedeni spring security bu şekilde çalışıyor
    //@PreAuthorize("hasRole("user")and hasRole("admin")") // alttaki gibi de bu şekilde de olabilir
    @PreAuthorize(Roles.AdminAndUser) // SPeL //todo common da security de convert ettik
    //PreAuthorize: istek atılmadan önce secured ile aynı şekilde çalışıyor daha complex sadece
    //todo SPeL: spring expration language
    public List<GetAllCarsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    //
    @PostAuthorize("hasRole('admin') || returnObject.modelYear == 2019")
    // @AuthenticationPrincipal Jwt jwt: bu benim token la yolladığım jwt oluyor, artık buna ulaşabileceğim
    // mesela admin değil ama araç model yılı 2019 araç gösterilicek
    // bunu farklı şekilde düzenleyebilirsin
    public GetCarResponse getById(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        System.out.println(jwt.getClaims().get("email"));
        System.out.println(jwt.getClaims().get("sub"));
        System.out.println(jwt.getClaims().get("given_name"));
        System.out.println(jwt.getClaims().get("family_name"));
// console a bilgilerini yazdırdık
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCarResponse add(@Valid @RequestBody CreateCarRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateCarResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateCarRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/check-car-available/{id}")
    public ClientResponse checkIfCarAvailable(@PathVariable UUID id) {
        return service.checkIfCarAvailable(id);
    }
}