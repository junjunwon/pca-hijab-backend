#!/bin/bash

# Kafka 토픽 생성 스크립트
# 실행 전에 Kafka가 완전히 시작될 때까지 기다려야 합니다.

echo "🚀 Kafka 토픽 생성 시작..."

# Kafka 컨테이너가 준비될 때까지 대기
echo "⏳ Kafka 서비스 준비 대기 중..."
until docker exec kafka kafka-topics --bootstrap-server localhost:9092 --list > /dev/null 2>&1; do
    echo "Kafka 서비스가 아직 준비되지 않았습니다. 10초 후 재시도..."
    sleep 10
done

echo "✅ Kafka 서비스가 준비되었습니다!"

# color-analysis-topic 생성
echo "📝 color-analysis-topic 생성 중..."
docker exec kafka kafka-topics \
    --bootstrap-server localhost:9092 \
    --create \
    --topic color-analysis-topic \
    --partitions 3 \
    --replication-factor 1 \
    --if-not-exists

# 토픽 목록 확인
echo "📋 생성된 토픽 목록:"
docker exec kafka kafka-topics \
    --bootstrap-server localhost:9092 \
    --list

# 토픽 상세 정보 확인
echo "📊 color-analysis-topic 상세 정보:"
docker exec kafka kafka-topics \
    --bootstrap-server localhost:9092 \
    --describe \
    --topic color-analysis-topic

echo "✅ Kafka 토픽 생성 완료!"
