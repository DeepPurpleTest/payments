package com.example.payments.util.mapper;

import com.example.payments.dto.RequestDto;
import com.example.payments.entity.Request;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses = {CardDtoMapper.class, UserDtoMapper.class})
public interface RequestDtoMapper extends GenericMapper<Request, RequestDto> {
}
