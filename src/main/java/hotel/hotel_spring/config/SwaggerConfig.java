package hotel.hotel_spring.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@OpenAPIDefinition(
//        info = @Info(title = "hotel api 명세서",
//                description = "JK hotel 명세서",
//                version = "v1"))
//@RequiredArgsConstructor
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public GroupedOpenApi groupedOpenApi(){
//
//        String[] paths = {"/v1/**"};
//
//        return GroupedOpenApi.builder()
//                .group("JK hotel API v1") // 그룹 이름
//                .pathsToMatch(paths)      // 그룹에 속하는 경로 패턴을 지정한다.
//                .build();
//    }
//
//}
