package homemate.controller.chatbot;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatBotController {

    // 기본 질문 스크립트
    List<String> basicQuestions = Arrays.asList(
            "안녕하세요.\n사용자님 취향에 맞는 매물을 찾아드릴게요!\n우선 기본 조건 질문에 답해주세요.\n" +
                    "아파트/빌라/원룸/오피스텔 중 원하는 공간 형태는 무엇인가요? 단어 형태로 답변해주세요:)",
            "매매/전세/월세 중 원하는 주거 형태는 무엇인가요? 단어 형태로 답변해주세요:)",
            "서울/성남 범위 이내 원하는 지역을 선택해주세요.(예시: 경기도 성남시 복정동)\n" +
                    "만약 여러 지역을 원하시면 띄어쓰기로 구분해서 입력해주세요.",
            "희망하는 가격대를 알려주세요(만원 단위로 입력)\n" +
                    "월세의 경우 보증금/월세 형태로 입력해주세요:)",
            "원하시는 전용면적을 알려주세요. 단, 숫자만 입력해주세요",
            "사용자님 맞춤형 매물을 추천해드릴게요!\n" +
                    "추가로 원하는 조건을 문장으로 말씀해주세요. 없으면 '아니요'를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요",
            "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요"
    );

    @GetMapping(value = "/basic-question", produces="application/json; charset=utf8")
    public String getBasicQuestion(@RequestParam int index){
        // 해당 인덱스에 해당하는 기본 질문 반환
        if(index >= 0 && index < basicQuestions.size()){
            return basicQuestions.get(index);
        } else {
            return "해당하는 기본 질문이 없습니다.";
        }
    }

    @PostMapping(value = "/anyquestion", produces="application/json; charset=utf8")
    public String anyQuestion(@RequestParam String answer){
        if("아니요".equals(answer) || "아니오".equals(answer)){
            return "채팅을 종료합니다.";
        } else {
            return "원하는 추가 조건이 더 있으신가요? 없으면 아니요를 입력해주세요";
        }
    }
}
