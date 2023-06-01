package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    //생성자 주입!
    private final MemberService memberService;

    //회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm(){return "save";}

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        System.out.println("MemberController.Save");
        System.out.println("memberDTO = " + memberDTO);
        System.out.println("name="+memberDTO.getMemberEmail());
        System.out.println("name="+memberDTO.getMemberPassword());
        System.out.println("name="+memberDTO.getMemberName());
        memberService.save(memberDTO);
        return "login";
    }

    @GetMapping("/member/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null){
            //로그인 성공
            session.setAttribute("loginEail",loginResult.getMemberEmail());
            return "main";
        }else{
            //로그인 실패
            return "login";
        }

    }
    @GetMapping("/member/")
    public String findall(Model model){
        List<MemberDTO> memberDTOList= memberService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("memberList",memberDTOList);
        return "list";
    }
}
