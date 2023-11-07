//package homemate.config.websocket.handler;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Slf4j
//@RequiredArgsConstructor
//public class WebSocketHandler extends TextWebSocketHandler {
//
//    private final ObjectMapper objectMapper;
//    private List<WebSocketSession> sessions = new ArrayList<>();
//    private List<String> questions = new ArrayList<>();
//
//    private static final String FLASK_SERVER_URL = "http://localhost:5001/test";
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        //소켓 연결
//        super.afterConnectionEstablished(session);
//
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//
//        log.info("웹소켓 시작");
//        // 사용자의 대답을 세션에 저장
//        String question = message.getPayload();
//
//        // 사용자가 입력한 내용을 검사
//        boolean validAnswer = isValidAnswer(question);
//
//        if (validAnswer) {
//            // 사용자가 유효한 대답을 제공한 경우 저장
//            questions.add(question);
//
//            // 세 번째 질문은 선택지가 필요없는 질문이므로
//        } else if(!isAnotherQuestion(question)){
//
//            questions.add(question);
//        }
//        else {
//            // 사용자가 유효하지 않은 대답을 제공한 경우 메세지를 전송
//            String invalidMessage = "유효하지 않은 대답입니다. 선택지에 있는 단어만 입력해주세요.";
//            session.sendMessage(new TextMessage(invalidMessage));
//        }
//
//        // 사용자가 '아니요'를 보낼 때 Flask 서버에 질문 이력을 전송
//        if ("no".equals(question)) {
//            log.info("소켓 종료!");
//            String responseFlask = sendQuestionsToFlask(questions);
//            session.sendMessage(new TextMessage(responseFlask));
//            session.close();
//        }
//
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        //javascript에서  session.close해서 연결 끊음. 그리고 이 메소드 실행.
//        //소켓 종료
//        super.afterConnectionClosed(session, status);
//    }
//
//    private boolean isValidAnswer(String answer) {
//        // 사용자의 대답이 유효한지 검사하는 로직을 구현
//        // 예를 들어, 첫 번째 질문에서 예상되는 선택지와 두 번째 질문에서 예상되는 선택지와 비교
//        // 만약 유효한지 확인 가능한 규칙이 있다면 해당 규칙을 적용
//        return answer.equals("빌라") || answer.equals("오피스텔") || answer.equals("아파트") || answer.equals("원룸")
//                || answer.equals("매매") || answer.equals("전세") || answer.equals("월세");
//    }
//
//    private boolean isAnotherQuestion(String answer) {
//        // 두번째 질문 이후에 대한 유효성을 검사하는 로직을 구현
//        // 세 번째 질문 부터는 자유로운 텍스트 입력을 허용
//        return true;
//    }
//
//    private String sendQuestionsToFlask(List<String> questions) throws JsonProcessingException {
//        RestTemplate restTemplate = new RestTemplate();
//        // 챗봇에게 사용자 대답 전달
//        restTemplate.postForObject(FLASK_SERVER_URL, questions, Void.class);
//
//        // 매물 추천 결과 반환
//        List<String> response = restTemplate.getForObject(FLASK_SERVER_URL, List.class);
//
//        // 리스트를 JSON 문자열로 변환
//        String responseJson = objectMapper.writeValueAsString(response);
//
//        return responseJson;
//    }
//
//}
