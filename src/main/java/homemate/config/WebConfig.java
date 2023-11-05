package homemate.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .maxAge(3000)
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.LOCATION);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // images로 오는 요청에 대한 정적 파일 서빙 설정
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/images/")
                .setCachePeriod(60 * 60 * 24 * 365);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoggerInterceptor())
//                .excludePathPatterns("/css/**", "/images/**", "/js/**");

        registry.addInterceptor(new LoginCheckInterceptor())
                //.addPathPatterns("/*")
                .excludePathPatterns("/log*");
    }

}
