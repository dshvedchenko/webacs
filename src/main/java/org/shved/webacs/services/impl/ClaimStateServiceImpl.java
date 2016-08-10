package org.shved.webacs.services.impl;

import org.modelmapper.ModelMapper;
import org.shved.webacs.dao.IClaimStateDAO;
import org.shved.webacs.dto.ClaimStateDTO;
import org.shved.webacs.exception.NotFoundException;
import org.shved.webacs.model.ClaimState;
import org.shved.webacs.services.IClaimStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dshvedchenko on 6/28/16.
 */
@Service
public class ClaimStateServiceImpl implements IClaimStateService {

    @Autowired
    private IClaimStateDAO claimStateDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public List<ClaimStateDTO> getAll() {
        List<ClaimState> list = claimStateDAO.getAll();
        if (list == null || list != null && list.size() == 0) throw new NotFoundException("empty claimstate storage");
        return list.stream().map(item -> modelMapper.map(item, ClaimStateDTO.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClaimStateDTO getById(Integer id) {
        ClaimState res = claimStateDAO.getById(id);
        if (res == null) throw new NotFoundException("Non exiting claim state id");

        return modelMapper.map(res, ClaimStateDTO.class);
    }
}
