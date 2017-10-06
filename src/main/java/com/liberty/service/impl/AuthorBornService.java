package com.liberty.service.impl;

import com.liberty.dto.AuthorBornDto;
import com.liberty.dto.LoaderDto;
import com.liberty.error.ValidationException;
import com.liberty.model.AuthorBorndateEntity;
import com.liberty.model.AuthorEntity;
import com.liberty.repository.AuthorBorndateRepository;
import com.liberty.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by user on 21.07.2017.
 */
@Service
public class AuthorBornService {

    @Autowired
    private AuthorBorndateRepository authorBorndateRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public LoaderDto<List> loadAuthorsBornAt(String time, Integer page){
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = parser.parse(time);
        }catch (ParseException ex){
            throw new ValidationException("Could not parse date");
        }
        Calendar calendar = new Calendar.Builder().setInstant(date).build();
        Integer month = calendar.get(Calendar.MONTH);
        Integer day = calendar.get(Calendar.DAY_OF_MONTH);
        Integer year = calendar.get(Calendar.YEAR);
        Page<AuthorBorndateEntity> authors = authorBorndateRepository.findAllByBornMonthEqualsAndBornDayEqualsAndNeurolibAuthorIdNotNull(month+1,day,new PageRequest(page,8));
        List<AuthorBornDto> authorBornDtos = authors.getContent().stream().map(authorBorndateEntity -> {
            Long authorId = authorBorndateEntity.getNeurolibAuthorId();
            AuthorEntity authorEntity = authorRepository.getOne((long)authorId);
            AuthorBornDto authorBorn = new AuthorBornDto();
            Integer bornYear = authorBorndateEntity.getBornYear();
            if(bornYear!=null) {
                authorBorn.setAge(year - bornYear);
            }
            authorBorn.setId(authorId);
            authorBorn.setAuthorName(generateFullName(authorEntity));
            return authorBorn;
        }).collect(Collectors.toList());
        LoaderDto<List> listLoaderDto = new LoaderDto<>();
        listLoaderDto.setData(authorBornDtos);
        listLoaderDto.setAvailable(authors.hasNext());
        return listLoaderDto;
    }

    private String generateFullName(AuthorEntity authorEntity){
        String s = String.format("%s %s %s",authorEntity.getLastName(),authorEntity.getFirstName(),authorEntity.getMiddleName());
        return s.replace("  ", " ");
    }
}
