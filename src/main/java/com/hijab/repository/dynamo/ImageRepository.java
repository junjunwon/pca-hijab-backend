package com.hijab.repository.dynamo;

import com.hijab.image.entity.Image;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ImageRepository extends CrudRepository<Image, String> {
}
