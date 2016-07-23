package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IClaimStateDAO;
import org.shved.webacs.dao.IResTypeDAO;
import org.shved.webacs.dto.ClaimStateDTO;
import org.shved.webacs.dto.ResTypeDTO;
import org.shved.webacs.exception.AppException;
import org.shved.webacs.exception.NotFoundException;
import org.shved.webacs.model.ClaimState;
import org.shved.webacs.model.ResType;
import org.shved.webacs.model.Resource;
import org.shved.webacs.services.IClaimStateService;
import org.shved.webacs.services.IResTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 6/28/16.
 */
@Service
public class ResTypeServiceImpl implements IResTypeService {

    @Autowired
    private IResTypeDAO resTypeDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ResTypeDTO> getAll() {
        List<ResType> list = resTypeDAO.findAll();

        if (list == null || list != null && list.size() == 0) throw new NotFoundException("empty ResType storage");
        return list.stream().map(item -> modelMapper.map(item, ResTypeDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ResTypeDTO getById(Integer id) {
        ResType res = resTypeDAO.findById(id);
        if (res == null) throw new NotFoundException("Non exiting ResType id");

        return modelMapper.map(res, ResTypeDTO.class);
    }

    @Override
    public void deleteById(Integer id) {
        try {
            resTypeDAO.deleteById(id);
        } catch (Exception e) {
            throw new AppException("error during delete resource type", e);
        }
    }


    @Override
    public ResTypeDTO save(ResTypeDTO resTypeDTO) {
        ResType newObj = modelMapper.map(resTypeDTO, ResType.class);
        ResType dbObj = null;
        if (resTypeDTO.getId() != null) {
            dbObj = resTypeDAO.findById(resTypeDTO.getId());
            dbObj.update(newObj);
        } else {
            dbObj = newObj;
        }
        try {
            resTypeDAO.save(dbObj);
        } catch (Exception e) {
            throw new AppException(e.getMessage(), e);
        }

        return modelMapper.map(dbObj, ResTypeDTO.class);
    }


}
