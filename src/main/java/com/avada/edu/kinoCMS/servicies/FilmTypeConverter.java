package com.avada.edu.kinoCMS.servicies;

import com.avada.edu.kinoCMS.model.FilmType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilmTypeConverter implements Converter<String, FilmType> {

    private final FilmTypeService filmTypeService;

    public FilmTypeConverter(FilmTypeService filmTypeService) {
        this.filmTypeService = filmTypeService;
    }

    @Override
    public FilmType convert(String id) {

        int parseId = Integer.parseInt(id);
        List<FilmType> filmTypeList = filmTypeService.findAll();
        int index = parseId - 1;
        return filmTypeList.get(index);
    }
}
