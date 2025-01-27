//package com.work.assistant.test;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.work.assistant.data.UserTestVO;
//import com.work.assistant.repository.DatabaseCleaner;
//import com.work.assistant.repository.UserJdbcRepository;
//import com.work.assistant.util.DateUtils;
//import com.work.assistant.util.JwtUtils;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AuthApiTest {
//    @Autowired
//    private UserJdbcRepository userJdbcRepository;
//
//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private DatabaseCleaner databaseCleaner;
//
//    public Map<String, String> givenUser = new HashMap<>();
//    private String testAccessToken;
//    private String testRefreshToken;
//
//    @BeforeAll
//    void setUp() throws Exception {
//        givenUser.put("email", "usertest@test.com");
//        givenUser.put("password", "asdfQWER1234!@#1");
//        givenUser.put("password2", "asdfQWER1234!@#1");
//        givenUser.put("nickname", "usertest");
//        givenUser.put("role", "MEMBER");
//
//        userJdbcRepository.insertUser(
//            givenUser.get("email"),
//            passwordEncoder.encode(givenUser.get("password")),
//            givenUser.get("nickname"),
//            givenUser.get("role")
//        );
//
//        givenUser.put("userId", Long.toString(userJdbcRepository.findUser(givenUser.get("email")).getUserId()));
//    }
//
//    @AfterAll
//    void cleanUp() {
//        databaseCleaner.cleanDatabase();
//    }
//
////    @AfterEach
////    void teardown() {
////        databaseCleaner.cleanRedis();
////    }
//
//    private void login() throws Exception {
//        Map<String, String> loginReq = new HashMap<>();
//        loginReq.put("email", givenUser.get("email"));
//        loginReq.put("password", givenUser.get("password"));
//
//        ResultActions ra = mvc.perform(post("/api/users/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(loginReq))
//            )
//            .andExpect(jsonPath("$.accessToken").exists())
//            .andExpect(jsonPath("$.refreshToken").exists());
//
//        setGlobalTestAccessToken(ra);
//    }
//
//    private void setGlobalTestAccessToken(ResultActions ra) throws Exception {
//        String json = ra.andReturn().getResponse().getContentAsString();
//        TypeReference<HashMap<String, String>> mapType = new TypeReference<>() {};
//        HashMap<String, String> map = new ObjectMapper().readValue(json, mapType);
//
//        testAccessToken = map.get("accessToken");
//        testRefreshToken = map.get("refreshToken");
//    }
//
//    @Order(1)
//    @Test
//    @Transactional
//    @DisplayName("API 명세에 맞게 요청하는 경우, 정상적으로 회원가입이 진행되어야 한다.|0.5")
//    void 회원가입_테스트() throws Exception {
//        Map<String, String> request = new HashMap<>();
//        request.put("email", "aaa@test.com");
//        request.put("password", "asdfQWER1234!@#1");
//        request.put("password2", "asdfQWER1234!@#1");
//        request.put("nickname", "aaa1234");
//
//        LocalDateTime now = LocalDateTime.now();
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request))
//            )
//            .andExpect(status().isOk());
//
//        UserTestVO createdUser = userJdbcRepository.findUser("aaa@test.com");
//        assertNotNull(createdUser);
//        assertEquals(createdUser.getEmail(), "aaa@test.com");
//        assertTrue(passwordEncoder.matches("asdfQWER1234!@#1", createdUser.getPassword()));
//        assertEquals("aaa1234", createdUser.getNickname());
//        assertTrue(DateUtils.parseAndCompareUpToMinutes(createdUser.getCreatedAt(), now.toString()));
//        assertTrue(DateUtils.parseAndCompareUpToMinutes(createdUser.getUpdatedAt(), now.toString()));
//    }
//
//    @Order(2)
//    @Test
//    @Transactional
//    @DisplayName("이메일이 없는 경우, 400번대 에러를 발생시킨다.|0.5")
//    void 회원가입_이메일_누락_예외테스트() throws Exception {
//        회원가입_테스트();
//
//        Map<String, String> request = new HashMap<>();
//        // 이메일 없음
//        request.put("password", "asdfQWER1234!@#1");
//        request.put("password2", "asdfQWER1234!@#1");
//        request.put("nickname", "signupT");
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(3)
//    @Test
//    @Transactional
//    @DisplayName("중복된 이메일일 경우, 400번대 에러를 발생시킨다.|0.5")
//    void 회원가입_이메일_중복_예외테스트() throws Exception {
//        회원가입_테스트();
//
//        Map<String, String> request = new HashMap<>();
//        request.put("email", "usertest@test.com"); // 중복된 이메일
//        request.put("password", "asdfQWER1234!@#1");
//        request.put("password2", "asdfQWER1234!@#1");
//        request.put("nickname", "signupT");
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(4)
//    @Test
//    @Transactional
//    @DisplayName("이메일 형식을 지키지 않았을 경우, 400번대 에러를 발생시킨다.|0.5")
//    void 회원가입_이메일_형식_예외테스트() throws Exception {
//        회원가입_테스트();
//
//        Map<String, String> request = new HashMap<>();
//        request.put("email", "test1test.com"); // 이메일 형식 지키지 않음
//        request.put("password", "asdfQWER1234!@#1");
//        request.put("password2", "asdfQWER1234!@#1");
//        request.put("nickname", "signupT");
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(5)
//    @Test
//    @Transactional
//    @DisplayName("‘비밀번호’가 없는 경우, 400번대 에러를 발생시킨다.|0.5")
//    void 회원가입_비밀번호_누락_예외테스트() throws Exception {
//        회원가입_테스트();
//
//        Map<String, String> request = new HashMap<>();
//        request.put("email", "test1@test.com");
//        // 비밀번호 없음
//        request.put("password2", "asdfQWER1234!@#1");
//        request.put("nickname", "signupT");
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(6)
//    @Test
//    @Transactional
//    @DisplayName("‘비밀번호 확인’이 없는 경우, 400번대 에러를 발생시킨다.|0.5")
//    void 회원가입_비밀번호_확인란_누락_예외테스트() throws Exception {
//        회원가입_테스트();
//
//        Map<String, String> request = new HashMap<>();
//        request.put("email", "test1@test.com");
//        request.put("password", "asdfQWER1234!@#1");
//        // 비밀번호 확인 없음
//        request.put("nickname", "signupT");
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(7)
//    @Test
//    @Transactional
//    @DisplayName("‘비밀번호’와 ‘비밀번호 확인’의 값이 불일치할 경우, 400번대 에러를 발생시킨다.|0.5")
//    void 회원가입_비밀번호와_비밀번호확인_불일치_예외테스트() throws Exception {
//        회원가입_테스트();
//
//        Map<String, String> request = new HashMap<>();
//        request.put("email", "test1@test.com");
//        request.put("password", "asdfQWER1256!@#1"); // 비밀번호와 비밀번호 확인 불일치
//        request.put("password2", "asdfQWER1234!@#1");
//        request.put("nickname", "signupT");
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(8)
//    @Test
//    @Transactional
//    @DisplayName("비밀번호 조건 불만족 시, 400번대 에러를 발생시킨다.|0.5")
//    void 회원가입_비밀번호_조건_예외테스트() throws Exception {
//        회원가입_테스트();
//
//        Map<String, String> request1 = new HashMap<>();
//        request1.put("email", "test1@test.com");
//        request1.put("password", "asQW12!@3456aW"); // 비밀번호 조건 불만족: 15자 미만
//        request1.put("password2", "asQW12!@3456aW");
//        request1.put("nickname", "signupT");
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request1))
//            )
//            .andExpect(status().is4xxClientError());
//
//        Map<String, String> request2 = new HashMap<>();
//        request2.put("email", "test1@test.com");
//        request2.put("password", "asdfQWER1234!@#1234ASDFasdf%^&*"); // 비밀번호 조건 불만족: 30자 초과
//        request2.put("password2", "asdfQWER1234!@#1234ASDFasdf%^&*");
//        request2.put("nickname", "signupT");
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request2))
//            )
//            .andExpect(status().is4xxClientError());
//
//        Map<String, String> request3 = new HashMap<>();
//        request3.put("email", "test1@test.com");
//        request3.put("password", "asdfqwer1234!@#1"); // 비밀번호 조건 불만족: 영어 대문자 미포함
//        request3.put("password2", "asdfqwer1234!@#1");
//        request3.put("nickname", "signupT");
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request3))
//            )
//            .andExpect(status().is4xxClientError());
//
//        Map<String, String> request4 = new HashMap<>();
//        request4.put("email", "test1@test.com");
//        request4.put("password", "ASDFQWER1234!@#1"); // 비밀번호 조건 불만족: 영어 소문자 미포함
//        request4.put("password2", "ASDFQWER1234!@#1");
//        request4.put("nickname", "signupT");
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request4))
//            )
//            .andExpect(status().is4xxClientError());
//
//        Map<String, String> request5 = new HashMap<>();
//        request5.put("email", "test1@test.com");
//        request5.put("password", "asdfQWER12341231"); // 비밀번호 조건 불만족: 특수기호 미포함
//        request5.put("password2", "asdfQWER12341231");
//        request5.put("nickname", "signupT");
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request5))
//            )
//            .andExpect(status().is4xxClientError());
//
//        Map<String, String> request6 = new HashMap<>();
//        request6.put("email", "test1@test.com");
//        request6.put("password", "asdfQWERasdf!@#q"); // 비밀번호 조건 불만족: 숫자 미포함
//        request6.put("password2", "asdfQWERasdf!@#q");
//        request6.put("nickname", "signupT");
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request6))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(9)
//    @Test
//    @Transactional
//    @DisplayName("닉네임이 없는 경우, 400번대 에러를 발생시킨다.|0.5")
//    void 회원가입_닉네임_누락_예외테스트() throws Exception {
//        회원가입_테스트();
//
//        Map<String, String> request = new HashMap<>();
//        request.put("email", "test1@test.com");
//        request.put("password", "asdfQWER1234!@#1");
//        request.put("password2", "asdfQWER1234!@#1");
//        // 닉네임 없음
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(10)
//    @Test
//    @Transactional
//    @DisplayName("중복된 닉네임일 경우, 400번대 에러를 발생시킨다.|0.5")
//    void 회원가입_중복된_닉네임_예외테스트() throws Exception {
//        회원가입_테스트();
//
//        Map<String, String> request = new HashMap<>();
//        request.put("email", "test1@test.com");
//        request.put("password", "asdfQWER1234!@#1");
//        request.put("password2", "asdfQWER1234!@#1");
//        request.put("nickname", "usertest"); // 중복된 닉네임
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(11)
//    @Test
//    @Transactional
//    @DisplayName("닉네임 조건 불만족 시, 400번대 에러를 발생시킨다.|0.5")
//    void 회원가입_닉네임_조건_예외테스트() throws Exception {
//        회원가입_테스트();
//
//        Map<String, String> request1 = new HashMap<>();
//        request1.put("email", "test1@test.com");
//        request1.put("password", "asdfQWER1234!@#1");
//        request1.put("password2", "asdfQWER1234!@#1");
//        request1.put("nickname", "us"); // 닉네임 조건 불만족: 4자 미만
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request1))
//            )
//            .andExpect(status().is4xxClientError());
//
//        Map<String, String> request2 = new HashMap<>();
//        request2.put("email", "test1@test.com");
//        request2.put("password", "asdfQWER1234!@#1");
//        request2.put("password2", "asdfQWER1234!@#1");
//        request2.put("nickname", "signupsig"); // 닉네임 조건 불만족: 8자 초과
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request2))
//            )
//            .andExpect(status().is4xxClientError());
//
//        Map<String, String> request3 = new HashMap<>();
//        request3.put("email", "test1@test.com");
//        request3.put("password", "asdfQWER1234!@#1");
//        request3.put("password2", "asdfQWER1234!@#1");
//        request3.put("nickname", "signupsig"); // 닉네임 조건 불만족: 특수문자 포함
//
//        mvc.perform(post("/api/users/signup")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(request3))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(12)
//    @Test
//    @DisplayName("API 명세에 맞게 요청하는 경우, 정상적으로 로그인이 진행되어야 한다.|0.5")
//    void 로그인_테스트() throws Exception {
//        Map<String, String> loginReq = new HashMap<>();
//        loginReq.put("email", givenUser.get("email"));
//        loginReq.put("password", givenUser.get("password"));
//
//        mvc.perform(post("/api/users/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(loginReq))
//            )
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.accessToken").exists())
//            .andExpect(jsonPath("$.refreshToken").exists());
//    }
//
//    @Order(13)
//    @Test
//    @DisplayName("올바른 입력 값으로 요청하는 경우, 유효한 accessToken과 refreshToken이 반환되어야 한다.|0.5")
//    void 로그인_토큰_반환_테스트() throws Exception {
//        login();
//
//        jwtUtils.validateToken(jwtUtils.resolveToken(testAccessToken));
//        jwtUtils.validateToken(jwtUtils.resolveToken(testRefreshToken));
//    }
//
//    @Order(14)
//    @Test
//    @DisplayName("이메일이 없는 경우, 400번대 에러를 발생시킨다.|0.5")
//    void 로그인_이메일_누락_예외테스트() throws Exception {
//        로그인_테스트();
//
//        Map<String, String> loginReq = new HashMap<>();
//        // 이메일 없음
//        loginReq.put("password", givenUser.get("password"));
//
//        mvc.perform(post("/api/users/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(loginReq))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(15)
//    @Test
//    @DisplayName("이메일에 해당하는 유저가 없는 경우, 404 에러를 발생시킨다.|0.5")
//    void 로그인_이메일_연결유저_검증_예외테스트() throws Exception {
//        로그인_테스트();
//
//        Map<String, String> loginReq = new HashMap<>();
//        loginReq.put("email", "hello@hello.com"); // 없는 이메일
//        loginReq.put("password", givenUser.get("password"));
//
//        mvc.perform(post("/api/users/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(loginReq))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(16)
//    @Test
//    @DisplayName("비밀번호가 없는 경우, 400번대 에러를 발생시킨다.|0.5")
//    void 로그인_비밀번호_누락_예외테스트() throws Exception {
//        로그인_테스트();
//
//        Map<String, String> loginReq = new HashMap<>();
//        loginReq.put("email", givenUser.get("email"));
//        // 비밀번호 없음
//
//        mvc.perform(post("/api/users/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(loginReq))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(17)
//    @Test
//    @DisplayName("비밀번호가 일치하지 않는 경우, 400번대 에러를 발생시킨다.|0.5")
//    void 로그인_비밀번호_불일치_예외테스트() throws Exception {
//        로그인_테스트();
//
//        Map<String, String> loginReq = new HashMap<>();
//        loginReq.put("email", givenUser.get("email"));
//        loginReq.put("password", "asdfQWER1234!@#2"); // 비밀번호 불일치
//
//        mvc.perform(post("/api/users/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(new ObjectMapper().writeValueAsString(loginReq))
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//    @Order(18)
//    @Test
//    @DisplayName("API 명세에 맞게 요청하는 경우, 정상적으로 본인 정보 조회가 진행되어야 한다.|0.5")
//    void 로그인_본인정보_조회_테스트() throws Exception {
//        login();
//
//        mvc.perform(get("/api/users/me")
//                .header("Authorization", testAccessToken) // 유효하지 않은 토큰
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//            )
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.userId").value(givenUser.get("userId")))
//            .andExpect(jsonPath("$.email").value(givenUser.get("email")))
//            .andExpect(jsonPath("$.nickname").value(givenUser.get("nickname")))
//            .andExpect(jsonPath("$.role").value(givenUser.get("role")));
//    }
//
//    @Order(19)
//    @Test
//    @DisplayName("토큰이 유효하지 않은 경우, 에러를 발생시킨다.|0.5")
//    void 로그인_토큰_유효성_예외테스트() throws Exception {
//        로그인_본인정보_조회_테스트();
//
//        mvc.perform(get("/api/users/me")
//                .header("Authorization", "asdf") // 유효하지 않은 토큰
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//            )
//            .andExpect(status().is4xxClientError());
//
//        mvc.perform(get("/api/users/me")
//                // 토큰 없음
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//            )
//            .andExpect(status().is4xxClientError());
//    }
//
//}
