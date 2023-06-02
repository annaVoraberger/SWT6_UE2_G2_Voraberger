package auction;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"auction"})
@Transactional
public class AppConfig {
  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
