package com.kodlamio.filterservice.business.abstracts;


import com.kodlamio.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.kodlamio.filterservice.business.dto.responses.GetFilterResponse;
import com.kodlamio.filterservice.entities.Filter;

import java.util.List;
import java.util.UUID;

public interface FilterService {
    List<GetAllFiltersResponse> getAll();

    GetFilterResponse getById(UUID id);

    void add(Filter filter);

    void delete(UUID id);

    void deleteByCarId(UUID carId);

    void deleteAllByBrandId(UUID brandId);
    // mercedes sistemden silindi diyelim mesela
    // bu tür olasılıklar için modelId ve brandId yi de silmemiz gerekebilir

    //bulk delete

    void deleteAllByModelId(UUID modelId);
}
