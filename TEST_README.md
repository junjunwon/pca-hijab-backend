## 단위 테스트 시 보안 설정 방법
### 컨트롤러 로직 단위 테스트	@WebMvcTest + TestSecurityConfig
- 보안 적용 제외 ❌
- 컨트롤러만 빠르게 검증
- 방법
  - 실제 보안 관련 Config는 @Profile("!test")를 설정해 단위테스트에서 제외한다. 

### @SpringBootTest 실제 보안 필터 동작을 포함	
- 보안 적용 ✅
- 인증/인가 흐름을 통합 테스트