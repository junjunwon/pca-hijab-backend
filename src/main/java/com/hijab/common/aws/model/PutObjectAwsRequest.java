package com.hijab.common.aws.model;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.util.Date;

public class PutObjectAwsRequest extends PutObjectRequest {

    public PutObjectAwsRequest(String bucketName, String key, File file, Date expirationDate) {
        super(bucketName, key, file);

        // 만료일을 설정하기 위한 메타데이터 생성
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setExpirationTime(expirationDate);
        this.setMetadata(metadata);
    }
}
