package org.zerock.w22.service;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.zerock.w22.dao.MemberDAO;
import org.zerock.w22.domain.MemberVO;
import org.zerock.w22.dto.MemberDTO;
import org.zerock.w22.util.MapperUtil;

@Log4j2
public enum MemberService {
    INSTANCE;

    private MemberDAO dao;
    private ModelMapper modelMapper;

    MemberService() {

        dao = new MemberDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    public MemberDTO login(String mid, String mpw) throws Exception {
        MemberVO vo = dao.getWithPassword(mid, mpw);

        MemberDTO memberDTO = modelMapper.map(vo, MemberDTO.class);

        return memberDTO;
    }

    public void updateUuid(String mid, String uuid) throws Exception {
        dao.updateUuid(mid, uuid);
    }

    public MemberDTO getByUuid(String uuid) throws Exception {
        MemberVO vo = dao.selectUUID(uuid);

        MemberDTO memberDTO = modelMapper.map(vo, MemberDTO.class);

        return memberDTO;
    }


}
