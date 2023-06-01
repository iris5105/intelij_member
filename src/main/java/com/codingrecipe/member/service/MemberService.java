package com.codingrecipe.member.service;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.entity.MemberEntity;
import com.codingrecipe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        //1. dto -> entity 변환
        //2. repository의 save 메서드호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberDTO){
        /*
            1.회원이 입력한 이메일로 DB에서 조회
            2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
       Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
       if(byMemberEmail.isPresent()){
           //조회 결과가 존재한다(해당 이메일을 가진 회원이 존재)
           MemberEntity memberEntity = byMemberEmail.get(); //optinal 객체에서 memberEntity를 꺼낸다.
           if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
               //비밀번호가 일치
               //로그인 성공
               //entity -> dto로 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
           }else{
               //비밀번호 불일치
               //로그인 실패
               return null;
           }
       }else{
           //조회 결과가 없다 (해당 이메일을 가진 회원이 없다)
           return null;
       }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(MemberEntity memberEntity : memberEntityList){
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
            /*
            memberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            memberDTOList.add(memberDTO);
             */
        }
        return memberDTOList;
    }
}
