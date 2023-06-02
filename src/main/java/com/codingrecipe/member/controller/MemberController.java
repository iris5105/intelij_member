package com.codingrecipe.member.controller;

import com.codingrecipe.member.dto.MemberDTO;
import com.codingrecipe.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            session.setAttribute("loginEmail",loginResult.getMemberEmail());
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
    @GetMapping("/member/{id}")
    //주소에 {id} 값을 넣어온 것을 @PathVariable로 경로상의 값을 가져온다.
    public String findById(@PathVariable Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member",memberDTO);
    return "detail";
    }
    @GetMapping("/member/update")
    public String update(HttpSession session, Model model){
        String myEmail = (String)session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember",memberDTO);
        return "update";
    }
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.upadte(memberDTO);

        return "redirect:/member/"+memberDTO.getId();

    }
    @GetMapping("/member/delete/{id}")
    public String delete(@PathVariable Long id ){
        memberService.deleteById(id);
        return "redirect:/member/";
    }
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }
}
