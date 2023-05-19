package com.kodlamio.filterservice.business.concretes;

import com.kodlamaio.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamio.filterservice.business.abstracts.FilterService;
import com.kodlamio.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.kodlamio.filterservice.business.dto.responses.GetFilterResponse;
import com.kodlamio.filterservice.entities.Filter;
import com.kodlamio.filterservice.repository.FilterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
//@RequiredArgsConstructor
// final eklersen çalışır eklmezsen çalışmaz
public class FilterManager implements FilterService {
    private final FilterRepository repository;
    private ModelMapperService mapper;

    @Override
    public List<GetAllFiltersResponse> getAll() {
        var filters = repository.findAll();
        var response = filters
                .stream()
                .map(filter -> mapper.forResponse().map(filter, GetAllFiltersResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetFilterResponse getById(String id) {
        var filter = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(filter, GetFilterResponse.class);

        return response;
    }

    @Override
    public void add(Filter filter) {
        repository.save(filter);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByCarId(UUID carId) {
        repository.deleteByCarId(carId);
    }

    @Override
    public void deleteAllByBrandId(UUID brandId) {
        repository.deleteAllByBrandId(brandId);
    }

    @Override
    public void deleteAllByModelId(UUID modelId) {

    }

    @Override
    public Filter getByCarId(UUID carId) {
        return repository.findByCarId(carId);
    }
}
