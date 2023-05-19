package com.kodlamaio.commonpackage.utils.mappers;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@AllArgsConstructor
public class ModelMapperManager implements ModelMapperService {
    private ModelMapper mapper;

    @Override
    public ModelMapper forResponse() {
        mapper.getConfiguration()
                //false belirsizlikte hata vaerir
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        //loose esnek her alan birbirne uymak zorunda değil diyor

        return mapper;
    }

    @Override
    public ModelMapper forRequest() {
        mapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setMatchingStrategy(MatchingStrategies.STANDARD);

        return mapper;
    }
}
