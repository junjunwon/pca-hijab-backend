## 추가 고려 사항
### 이미지 저장
얼굴 이미지를 저장하거나 분석 후 삭제 여부를 결정해야 합니다.
AWS S3와 같은 클라우드 스토리지 사용을 추천.
### 성능 최적화
분석 요청이 많을 경우 비동기 처리 및 캐싱 전략을 고려하세요.
### 보안
사용자 데이터는 암호화하여 저장하며, 민감한 정보는 최소한으로 저장합니다.# pca-hijab-backend

## 프로젝트 구조
1. 클라이언트 요청 시: color_analysis_request 테이블에 status = PENDING 으로 저장
2. Kafka에 메시지 전송
3. 분석 서버에서 결과 수신 후:
4. DB에 status = DONE, result = ... 로 업데이트
5. SSE로 결과 push

## 도커
### Kafka 컨테이너 실행
docker-compose up -d
###  Kafka 컨테이너 정상 작동 확인
docker ps
### Kafka 컨테이너 중지
docker-compose down
### 컨테이너 정리 + 볼륨 삭제까지
docker-compose down -v
### Kafka CLI가 있다면, 현재 토픽 리스트 확인 가능
docker exec -it kafka \
kafka-topics --bootstrap-server localhost:9092 --list
### Kafka 로그 확인
docker logs kafka