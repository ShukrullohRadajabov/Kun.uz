package com.company.service;

import com.company.dto.region.RegionDTO;
import com.company.dto.tag.TagDTO;
import com.company.dto.tag.TagRequestDTO;
import com.company.entity.RegionEntity;
import com.company.entity.TagEntity;
import com.company.exceptions.AppBadRequestException;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagDTO create(TagRequestDTO dto, Integer prtId) {
        TagEntity entity = new TagEntity();
        TagDTO tagDTO = new TagDTO();
        entity.setName(dto.getName());
        entity.setVisible(Boolean.TRUE);
        entity.setPrtId(prtId);
        tagRepository.save(entity);
        tagDTO.setId(entity.getId());
        tagDTO.setName(dto.getName());
        return tagDTO;
    }

    public TagDTO update(Integer id, TagRequestDTO dto, Integer adminId) {
        TagDTO tagDTO = new TagDTO();
        Optional<TagEntity> optional = tagRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Tag is empty");
        }
        TagEntity entity = optional.get();
        entity.setName(dto.getName());
        entity.setPrtId(adminId);
        entity.setVisible(true);
        tagRepository.save(entity);
        tagDTO.setName(entity.getName());
        tagDTO.setId(entity.getId());
        return tagDTO;
    }

    public boolean delete(Integer id, Integer prtId) {
        Optional<TagEntity> byId = tagRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ItemNotFoundException("Tag not found");
        }
        tagRepository.updateTag(id, prtId);
        return true;
    }


    public List<TagDTO> getList() {
        List<TagDTO> dtoList = new LinkedList<>();
        Iterable<TagEntity> tagList = tagRepository.findAll();
        for (TagEntity entity : tagList) {
            TagDTO dto = new TagDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dtoList.add(dto);
        }
        return dtoList;
    }


}
